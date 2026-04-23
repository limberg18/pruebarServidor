package com.taller.service.Impl;

import com.taller.model.Paquete;
import com.taller.model.Producto;
import com.taller.repository.PaqueteRepository;
import com.taller.repository.ProductoRepository;
import com.taller.service.ApiResponse;
import com.taller.service.IProductoService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;
   private final PaqueteRepository paqueteRepository;
    public ProductoServiceImpl(ProductoRepository productoRepository, PaqueteRepository paqueteRepository) {
        this.productoRepository = productoRepository;
        this.paqueteRepository = paqueteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listar() {
        return productoRepository.findAll(Sort.by(Sort.Direction.ASC, "paquete"));
    }

    @Override
    public ApiResponse<Producto> guardar(Producto producto) {
producto.setCodigo(producto.getCodigo().toUpperCase());
producto.setDescripcion(producto.getDescripcion().toUpperCase());


boolean existe=productoRepository.existsByCodigo(producto.getCodigo());
if(existe){
    return ApiResponse.<Producto>builder()
            .success(false)
            .message("Ya existe un producto ese codigo  ").build();
}
        Paquete paqueteBD = paqueteRepository
                .findById(Math.toIntExact(producto.getPaquete().getId()))
                .orElse(null);

        if (paqueteBD == null) {
            return ApiResponse.<Producto>builder()
                    .success(false)
                    .message("El paquete no existe")
                    .build();
        }

        // Validar duplicado
        boolean existe1 = productoRepository
                .existsByDescripcionAndPaquete_Id(
                        producto.getDescripcion(),
                        paqueteBD.getId()
                );

        if (existe1) {
            return ApiResponse.<Producto>builder()
                    .success(false)
                    .message("Producto ya existe en este paquete")
                    .build();
        }

        producto.setPaquete(paqueteBD);
        productoRepository.save(producto);

        return ApiResponse.<Producto>builder()
                .success(true)
                .message("Producto guardado")
                .build();
    }

    @Override
    public List<Producto> listarPorPaqueteId(Long paqueteId) {
        return productoRepository.findAll().stream()
                .filter(e -> e.getPaquete().getId()==paqueteId)  // Filtra por el id del paquete
                .collect(Collectors.toList());  // Recoge el resultado en una lista
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscar(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public Integer Stock(String codigo ) {
        Producto producto = productoRepository.findProductoByCodigo(codigo);
        if(producto==null){
            throw new RuntimeException("Producto no encontrado con id: " + codigo);
    }


        return producto.getStock();
    }

    @Override
    public ApiResponse<Producto> update(Long id, Producto producto) {


        producto.setCodigo(producto.getCodigo().toUpperCase());
        producto.setDescripcion(producto.getDescripcion().toUpperCase());

        //VALIDAR QUE EL CODIGO SEA UNICO.


        // 1️⃣ Verificar que el producto exista
        Producto productoBD = productoRepository.findById(id).orElse(null);

        if (productoBD == null) {
            return ApiResponse.<Producto>builder()
                    .success(false)
                    .message("Producto no existe")
                    .build();
        }

        // 2️⃣ Verificar que el paquete exista
        Paquete paqueteBD = paqueteRepository
                .findById(Math.toIntExact(producto.getPaquete().getId()))
                .orElse(null);

        if (paqueteBD == null) {
            return ApiResponse.<Producto>builder()
                    .success(false)
                    .message("El paquete no existe")
                    .build();
        }

        // 3️⃣ Validar duplicado (sin contarse a sí mismo)
        boolean existe = productoRepository
                .existsByDescripcionAndPaquete_IdAndIdNot(
                        producto.getDescripcion(),
                        paqueteBD.getId(),
                        id
                );

        if (existe) {
            return ApiResponse.<Producto>builder()
                    .success(false)
                    .message("Ya existe un producto con esa descripción en este paquete")
                    .build();
        }

        // 4️⃣ Actualizar campos
        productoBD.setCodigo(producto.getCodigo());
        productoBD.setDescripcion(producto.getDescripcion());
        productoBD.setPrecioCompra(producto.getPrecioCompra());
        productoBD.setPrecioVenta(producto.getPrecioVenta());
        productoBD.setStock(producto.getStock());
        productoBD.setPaquete(paqueteBD);

        productoRepository.save(productoBD);

        return ApiResponse.<Producto>builder()
                .success(true)
                .message("Producto actualizado correctamente")
                .build();
    }

    @Override
    public Optional<Producto> findProductoByDescripcionAndPaquete(String descripcion, Paquete paquete) {
        return productoRepository.findProductoByDescripcionAndPaquete(descripcion, paquete);
    }

    //List<Producto> findByDescripcionContaining(String descripcion);
    @Override
    public List<Producto> findByDescripcionContaining(String descripcion) {
       return productoRepository.findByDescripcionContaining(descripcion);
    }

}
