package com.taller.service;

import com.taller.model.Paquete;
import com.taller.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listar();

   ApiResponse<Producto>  guardar(Producto producto);

    List<Producto> listarPorPaqueteId(Long paqInteger);

    Optional<Producto> buscar(Long id);

    void eliminar(Long id);

    Integer Stock(String codigo);
ApiResponse<Producto> update(Long codigo, Producto producto);
    Optional<Producto> findProductoByDescripcionAndPaquete(String descripcion, Paquete paquete);
    List<Producto> findByDescripcionContaining(String descripcion);
}
