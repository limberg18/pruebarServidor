package com.taller.controller;

import com.taller.model.Cliente;
import com.taller.service.ApiResponse;
import com.taller.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ApiResponse<Cliente> crear(@RequestBody Cliente cliente) {

        cliente.setNombre(cliente.getNombre().toUpperCase());
      cliente.setDireccion(String.valueOf(cliente.getDireccion()).toUpperCase());
        return  clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public ApiResponse<Cliente> actualizar(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        cliente.setNombre(cliente.getNombre().toUpperCase());
        cliente.setDireccion(String.valueOf(cliente.getDireccion()).toUpperCase());

        ApiResponse<Cliente> response = clienteService.update(id, cliente);

        if (!response.isSuccess()) {
            return response;
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("dni/{dni}")
    public ResponseEntity<Cliente> buscarDni(@PathVariable String dni) {
        clienteService.findByNumeroDocumento(dni);
        return ResponseEntity.ok(clienteService.findByNumeroDocumento(dni));
    }
}
