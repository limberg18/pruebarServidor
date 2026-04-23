package com.taller.repository;

import com.taller.model.Paquete;
import com.taller.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findProductoByCodigo(String codigo);
    Optional<Producto> findProductoByDescripcionAndPaquete(String descripcion, Paquete paquete);
    boolean existsByDescripcionAndPaquete_IdAndIdNot(
            String descripcion,
            Long paqueteId,
            Long id
    );
    boolean existsByDescripcionAndPaquete_Id(String descripcion, Long paqueteId);
    boolean existsByCodigo(String codigo);
    List<Producto> findByDescripcionContaining(String descripcion);
}
