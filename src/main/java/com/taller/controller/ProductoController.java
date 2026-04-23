package com.taller.controller;

import com.taller.model.Producto;
import com.taller.service.ApiResponse;
import com.taller.service.IProductoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin("*")
@AllArgsConstructor
public class ProductoController {

    private final IProductoService productoService;



    @GetMapping
    public ResponseEntity<List<Producto>> listar() {

        return ResponseEntity.ok(productoService.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        return productoService.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ApiResponse<Producto> crear(@Valid  @RequestBody Producto producto) {
        return productoService.guardar(producto);

    }

    @PutMapping("/{id}")
    public ApiResponse<Producto> actualizar(
            @PathVariable Long id,
           @Valid @RequestBody Producto producto) {
        return productoService.update(id,producto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paquete/{paqueteId}")
    public ResponseEntity<List<Producto>> listarPorPaquete(
            @PathVariable Long paqueteId) {

        List<Producto> productos = productoService.listarPorPaqueteId(paqueteId);
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/stock/{codigo}")
    public Integer existePorPaquete(@PathVariable String codigo) {
      return   productoService.Stock(codigo);
    }

    @GetMapping("descripcion/{descripcion}")
    public List<Producto> buscarPorDescripcion(@PathVariable String descripcion) {
        return  productoService.findByDescripcionContaining(descripcion);
    }
    }
