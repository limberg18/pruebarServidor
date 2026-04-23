package com.taller.service.Impl;

import com.taller.model.Cliente;
import com.taller.repository.ClienteRepository;
import com.taller.service.ApiResponse;
import com.taller.service.IClienteService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
          return clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public ApiResponse<Cliente> save(Cliente cliente) {

        if (clienteRepository.findByNroDocumento(cliente.getNroDocumento()).isPresent()) {
            return ApiResponse.<Cliente>builder()
                    .success(false)
                    .message("El número de documento ya está registrado")
                    .data(null)
                    .build();
        }

        Cliente nuevo = clienteRepository.save(cliente);

        return ApiResponse.<Cliente>builder()
                .success(true)
                .message("Cliente registrado correctamente")
                .data(nuevo)
                .build();
    }
    @Override
    public ApiResponse<Cliente> update(Long id, Cliente cliente) {

        Cliente c = clienteRepository.findById(id)
                .orElse(null);

        if (c == null) {
            return ApiResponse.<Cliente>builder()
                    .success(false)
                    .message("Cliente no encontrado")
                    .data(null)
                    .build();
        }

        // 🔥 Validar que el nroDocumento no pertenezca a otro cliente
        Optional<Cliente> clienteExistente =
                clienteRepository.findByNroDocumento(cliente.getNroDocumento());

        if (clienteExistente.isPresent() &&
                !clienteExistente.get().getId().equals(id)) {

            return ApiResponse.<Cliente>builder()
                    .success(false)
                    .message("El número de documento ya está registrado")
                    .data(null)
                    .build();
        }

        // Actualizar datos
        c.setTipoDocumento(cliente.getTipoDocumento());
        c.setNroDocumento(cliente.getNroDocumento());
        c.setNombre(cliente.getNombre());
        c.setDireccion(cliente.getDireccion());
        c.setTelefono(cliente.getTelefono());
        c.setEmail(cliente.getEmail());

        Cliente actualizado = clienteRepository.save(c);

        return ApiResponse.<Cliente>builder()
                .success(true)
                .message("Cliente actualizado correctamente")
                .data(actualizado)
                .build();
    }

    @Override
    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente findByNumeroDocumento(String numeroDocumento) {

        return clienteRepository.findByNroDocumento(numeroDocumento).orElse(new Cliente());

    }
}
