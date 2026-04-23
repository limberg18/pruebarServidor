package com.taller.service.Impl;
import com.taller.dto.DetalleDto;
import com.taller.dto.ProformaDto;
import com.taller.model.*;
import com.taller.repository.ClienteRepository;
import com.taller.repository.ProductoRepository;
import com.taller.repository.ProformaRepository;
import com.taller.service.IProformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProformaServiceImpl implements IProformaService {

    private final ProformaRepository proformaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ProformaServiceImpl(ProformaRepository proformaRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.proformaRepository = proformaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProformaDto> findAll() {

        return proformaRepository
                .findAll(Sort.by(Sort.Direction.DESC, "fechaEmision")) // 🔥 ordenado
                .stream()
                .map(entity -> {
                    ProformaDto proformaDto = new ProformaDto();
                    proformaDto.setId(entity.getId());
                    proformaDto.setFechaEmision(entity.getFechaEmision());
                    proformaDto.setNumero(entity.getNumero());
                    proformaDto.setNombre(entity.getCliente().getNombre());
                    proformaDto.setTotal(entity.getTotal());
                    return proformaDto;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProformaDto> findById(Long id) {
        return proformaRepository.findById(id).map(proforma -> {
            ProformaDto proformaDto = new ProformaDto();

            // Mapear campos básicos
            proformaDto.setId(proforma.getId());
            proformaDto.setNumero(proforma.getNumero());
            proformaDto.setFechaEmision(proforma.getFechaEmision());
            proformaDto.setFechaVencimiento(proforma.getFechaVencimiento());

            proformaDto.setTotal(proforma.getTotal());
            proformaDto.setEstado(proforma.getEstado());
            proformaDto.setObservacion(proforma.getObservacion());
            Vehiculo vehiculo = proforma.getVehiculo();
            proformaDto.setVehiculo(vehiculo);
            // Mapear cliente
            Cliente clienteDto = new Cliente();
            clienteDto.setId(proforma.getCliente().getId());
            clienteDto.setNombre(proforma.getCliente().getNombre());
            clienteDto.setNroDocumento(proforma.getCliente().getNroDocumento());
            clienteDto.setTipoDocumento(proforma.getCliente().getTipoDocumento());
            clienteDto.setDireccion(proforma.getCliente().getDireccion());
            clienteDto.setTelefono(proforma.getCliente().getTelefono());
            proformaDto.setCliente(clienteDto);

            // Mapear detalles usando Stream
            List<DetalleDto> detallesDto = proforma.getDetalles().stream()
                    .map(detalle -> {
                        DetalleDto detalleDto = new DetalleDto();
                        //detalleDto.setId(detalle.getId());
                        detalleDto.setCantidad(detalle.getCantidad());
                        detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
                       // detalleDto.setTotal(detalle.getTotal());

                        Producto productoDto = new Producto();
                        productoDto.setId(detalle.getProducto().getId());
                        productoDto.setCodigo(detalle.getProducto().getCodigo());
                        productoDto.setDescripcion(detalle.getProducto().getDescripcion());
                        detalleDto.setProducto(productoDto);
                        productoDto.setPrecioVenta(productoDto.getPrecioVenta());
                        productoDto.setStock(productoDto.getStock());
                        return detalleDto;
                    })
                    .toList();

            proformaDto.setDetalles(detallesDto);

            return proformaDto;
        });
    }
    private String generarNumeroProforma() {
        Long correlativo = jdbcTemplate.queryForObject(
                "SELECT nextval('seq_proforma')", Long.class
        );

        return "PF-" + String.format("%03d", correlativo);
    }
    @Override
    @Transactional
    public ProformaDto save(ProformaDto dto) {



        dto.setNumero(generarNumeroProforma());
        // PASO 1: Buscar cliente
        Cliente cliente = clienteRepository.findById(dto.getCliente().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con ID: " + dto.getCliente().getId()));

        // PASO 2: Crear proforma
        Proforma proforma = Proforma.builder()
                .numero(dto.getNumero())
                .fechaEmision(dto.getFechaEmision() != null
                        ? dto.getFechaEmision()
                        : LocalDate.now())
                .fechaVencimiento(dto.getFechaVencimiento())
                .observacion(dto.getObservacion())
                .cliente(cliente)
                .vehiculo(dto.getVehiculo())
                .estado(dto.getEstado())
                .detalles(new ArrayList<>())
                .build();

        double subtotal = 0;

        // PASO 3: Procesar detalles
        for (DetalleDto detalleDto : dto.getDetalles()) {

            Producto producto = productoRepository.findById(detalleDto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado con ID: " + detalleDto.getProducto().getId()));

            int cantidad = detalleDto.getCantidad();

            // 🔥 DESCONTAR STOCK DE FORMA SEGURA
            int filas = proformaRepository.descontarStockSeguro(producto.getId(), cantidad);

            if (filas == 0) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: "
                                + producto.getDescripcion()
                                + " | Disponible: " + producto.getStock());
            }

            double precioUnitario = detalleDto.getPrecioUnitario();
            double totalDetalle = cantidad * precioUnitario;
            subtotal += totalDetalle;

            DetalleProforma detalle = DetalleProforma.builder()
                    .cantidad(cantidad)
                    .precioUnitario(precioUnitario)
                    .total(totalDetalle)
                    .producto(producto)
                    .proforma(proforma)
                    .build();

            proforma.getDetalles().add(detalle);
        }

        // PASO 4: Totales
        double igv = subtotal * 0.18;
        double total = subtotal + 0;

        proforma.setSubtotal(subtotal);
        proforma.setIgv(igv);
        proforma.setTotal(total);

        // PASO 5: Guardar proforma
        Proforma proformaGuardada = proformaRepository.save(proforma);

        return convertirADto(proformaGuardada);
    }

    @Override
    public void deleteById(Long id) {
        if (!proformaRepository.existsById(id)) {
            throw new RuntimeException("Proforma no encontrada con id: " + id);
        }
        proformaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProformaDto update(Long id, ProformaDto dto) {

        // PASO 1: Buscar la proforma existente
        Proforma proforma = proformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proforma no encontrada con ID: " + id));

        // PASO 2: Buscar el cliente
        Cliente cliente = clienteRepository.findById(dto.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getCliente().getId()));

        // PASO 3: Actualizar datos básicos
        proforma.setNumero(dto.getNumero());
        proforma.setFechaEmision(dto.getFechaEmision() != null ? dto.getFechaEmision() : proforma.getFechaEmision());
        proforma.setFechaVencimiento(dto.getFechaVencimiento());
        proforma.setObservacion(dto.getObservacion());
        proforma.setCliente(cliente);
        proforma.setEstado(dto.getEstado() != null ? dto.getEstado() : proforma.getEstado());

        // 🔥 PASO 4: DEVOLVER STOCK ANTERIOR
        for (DetalleProforma detalleAnterior : proforma.getDetalles()) {
            Producto producto = detalleAnterior.getProducto();
            producto.setStock(producto.getStock() + detalleAnterior.getCantidad());
            productoRepository.save(producto);
        }

        // Limpiar detalles anteriores
        proforma.getDetalles().clear();

        // PASO 5: Procesar nuevos detalles
        double subtotal = 0;

        for (DetalleDto detalleDto : dto.getDetalles()) {

            Producto producto = productoRepository.findById(detalleDto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado con ID: " + detalleDto.getProducto().getId()));

            int cantidad = detalleDto.getCantidad();
            double precioUnitario = detalleDto.getPrecioUnitario();
            double totalDetalle = cantidad * precioUnitario;

            // 🔥 VALIDAR STOCK ANTES DE DESCONTAR
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getDescripcion());
            }

            subtotal += totalDetalle;

            // Crear nuevo detalle
            DetalleProforma detalle = DetalleProforma.builder()
                    .cantidad(cantidad)
                    .precioUnitario(precioUnitario)
                    .total(totalDetalle)
                    .producto(producto)
                    .proforma(proforma)
                    .build();

            // 🔥 DESCONTAR NUEVO STOCK
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);

            proforma.getDetalles().add(detalle);
        }

        // PASO 6: Recalcular totales
        double igv = subtotal * 1;  // lo dejo como lo tienes
        double total = subtotal + 0;

        proforma.setSubtotal(subtotal);
        proforma.setIgv(igv);
        proforma.setTotal(total);

        // PASO 7: Guardar la proforma actualizada
        Proforma proformaActualizada = proformaRepository.save(proforma);

        // PASO 8: Retornar DTO
        return convertirADto(proformaActualizada);
    }
    // Método auxiliar para convertir Proforma a ProformaDto (agregar al final de la clase)
    private ProformaDto convertirADto(Proforma proforma) {
        ProformaDto dto = new ProformaDto();
        dto.setId(proforma.getId());
        dto.setNumero(proforma.getNumero());
        dto.setFechaEmision(proforma.getFechaEmision());
        dto.setFechaVencimiento(proforma.getFechaVencimiento());
        //dto.setS(proforma.getSubtotal());
        //dto.setIgv(proforma.getIgv());
        dto.setTotal(proforma.getTotal());
        dto.setEstado(proforma.getEstado());
        dto.setObservacion(proforma.getObservacion());

        // Mapear cliente
        ProformaDto clienteDto = new ProformaDto();
        clienteDto.setId(proforma.getCliente().getId());
        clienteDto.setNombre(proforma.getCliente().getNombre());
        clienteDto.setNumero(proforma.getCliente().getNroDocumento());
       // dto.setCliente(clienteDto);

        // Mapear detalles
        List<DetalleDto> detallesDto = new ArrayList<>();

        dto.setDetalles(detallesDto);

        return dto;
    }


}
