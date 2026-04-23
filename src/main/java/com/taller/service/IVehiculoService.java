package com.taller.service;

import com.taller.model.Producto;
import com.taller.model.Usuario;
import com.taller.model.Vehiculo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface IVehiculoService {

    List<Vehiculo> listar();

    ApiResponse<Vehiculo> guardar(Vehiculo vehiculo);


    Optional<Vehiculo> buscar(Long id);

    ApiResponse<Vehiculo> update(Long id, Vehiculo vehiculo);
    void eliminar(Long id);

    List<Vehiculo> obtenerVehiculosPorcliente(String Ndocumento);


}
