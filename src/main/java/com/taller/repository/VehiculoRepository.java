package com.taller.repository;

import com.taller.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {

    List<Vehiculo> findByClienteNroDocumento(String nroDocumento);


    Optional<Vehiculo> findByPlaca(String placa);
}
