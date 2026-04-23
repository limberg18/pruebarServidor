package com.taller.service.Impl;

import com.taller.model.Paquete;
import com.taller.model.Producto;
import com.taller.repository.PaqueteRepository;
import com.taller.service.ApiResponse;
import com.taller.service.IPaqueteService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaqueteServiceImpl implements IPaqueteService {

    private final PaqueteRepository paqueteRepository;

    public PaqueteServiceImpl(PaqueteRepository paqueteRepository) {
        this.paqueteRepository = paqueteRepository;
    }

    @Override
    public List<Paquete> listar() {

        return paqueteRepository.findAll( Sort.by(Sort.Direction.ASC, "nombre"));

    }

    @Override
    public ApiResponse<Paquete> guardar(Paquete paquete) {

        paquete.setNombre(paquete.getNombre().toUpperCase());
        if(paqueteRepository.findByNombre(paquete.getNombre()).isPresent()){
            return ApiResponse.<Paquete>builder().success(false).message("Paquete ya existe").build();

        }
        paqueteRepository.save(paquete);
        return ApiResponse.<Paquete>builder().success(true).message("Paquete registrado correctamente").build();
    }

    @Override
    public Optional<Paquete> buscar(Integer id) {

        return paqueteRepository.findById(id);
    }

    @Override
    public ApiResponse<Paquete> update(Integer id, Paquete paquete) {

        paquete.setNombre(paquete.getNombre().toUpperCase());
        Paquete paqueteActual = paqueteRepository.findById(id).orElse(null);

        if (paqueteActual == null) {
            return ApiResponse.<Paquete>builder()
                    .success(false)
                    .message("Paquete no encontrado")
                    .data(null)
                    .build();
        }

        // 🔥 Validar nombre duplicado (pero permitir el mismo)
        Optional<Paquete> existente =
                paqueteRepository.findByNombre(paquete.getNombre());

        if (existente.isPresent() &&
                !existente.get().getId().equals(id)) {

            return ApiResponse.<Paquete>builder()
                    .success(false)
                    .message("Ya existe un paquete con ese nombre")
                    .data(null)
                    .build();
        }

        // ✅ Actualizar datos
        paqueteActual.setNombre(paquete.getNombre());


        Paquete actualizado = paqueteRepository.save(paqueteActual);

        return ApiResponse.<Paquete>builder()
                .success(true)
                .message("Paquete actualizado correctamente")
                .data(actualizado)
                .build();
    }

    @Override
    public void eliminar(Integer id) {
        try {
            if (!paqueteRepository.existsById(id)) {
                throw new RuntimeException("paquete no encontrado con id: " + id);
            }
            paqueteRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Optional<Paquete> findByNombre(String nombre) {
        return paqueteRepository.findByNombre(nombre);
    }
}
