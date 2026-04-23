package com.taller.service.Impl;

import com.taller.model.Vehiculo;
import com.taller.repository.VehiculoRepository;
import com.taller.service.ApiResponse;
import com.taller.service.IVehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class VehiculoServiceImpl implements IVehiculoService {
private final VehiculoRepository vehiculoRepository;

    @Override
    public List<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    @Override
    public ApiResponse<Vehiculo> guardar(Vehiculo vehiculo) {

        if (vehiculoRepository.findByPlaca(vehiculo.getPlaca()).isPresent()) {
            return ApiResponse.<Vehiculo>builder()
                    .success(false)
                    .message("Ya existe un vehículo con esa placa")
                    .data(null)
                    .build();
        }

        Vehiculo nuevo = vehiculoRepository.save(vehiculo);

        return ApiResponse.<Vehiculo>builder()
                .success(true)
                .message("Vehículo registrado correctamente")
                .data(nuevo)
                .build();
    }

    @Override
    public Optional<Vehiculo> buscar(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public ApiResponse<Vehiculo> update(Long id, Vehiculo vehiculo) {

        Vehiculo actual = vehiculoRepository.findById(id).orElse(null);

        if (actual == null) {
            return ApiResponse.<Vehiculo>builder()
                    .success(false)
                    .message("Vehículo no encontrado")
                    .build();
        }

        String placa = vehiculo.getPlaca().toUpperCase().trim();

        Optional<Vehiculo> existente = vehiculoRepository.findByPlaca(placa);

        if (existente.isPresent() &&
                !existente.get().getId().equals(id)) {

            return ApiResponse.<Vehiculo>builder()
                    .success(false)
                    .message("Ya existe un vehículo con esa placa")
                    .build();
        }

        actual.setPlaca(placa);
        actual.setAnhoFabricacion(vehiculo.getAnhoFabricacion());
        actual.setCliente(vehiculo.getCliente());

        Vehiculo actualizado = vehiculoRepository.save(actual);

        return ApiResponse.<Vehiculo>builder()
                .success(true)
                .message("Vehículo actualizado correctamente")
                .data(actualizado)
                .build();
    }

    @Override
    public void eliminar(Long id) {
       vehiculoRepository.deleteById(id);
    }

    @Override
    public List<Vehiculo> obtenerVehiculosPorcliente(String Ndocumento) {
        return vehiculoRepository.findByClienteNroDocumento(Ndocumento);
    }
}
