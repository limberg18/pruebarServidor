package com.taller.service;
import com.taller.model.Cliente;
import java.util.List;


import java.util.Optional;

public interface IClienteService {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    ApiResponse<Cliente> save(Cliente cliente);

    ApiResponse<Cliente> update(Long id, Cliente cliente);

    void deleteById(Long id);

    Cliente findByNumeroDocumento(String numeroDocumento);



}
