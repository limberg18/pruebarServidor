package com.taller.service;

import com.taller.model.Paquete;
import com.taller.model.Producto;
import com.taller.model.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface IPaqueteService {
    List<Paquete> listar();

   ApiResponse<Paquete>  guardar(Paquete paquete);

    Optional<Paquete> buscar(Integer id);
    ApiResponse<Paquete> update(Integer id, Paquete paquete);
    void eliminar(Integer id);
    Optional<Paquete> findByNombre(String   nombre);
}
