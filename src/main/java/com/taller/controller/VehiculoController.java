package com.taller.controller;

import com.taller.model.Vehiculo;
import com.taller.service.ApiResponse;
import com.taller.service.IVehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vehiculo")
@AllArgsConstructor
@CrossOrigin("*")
public class VehiculoController {

    private final IVehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> listar()
    {
      return  vehiculoService.listar();
    }

    @GetMapping("/{id}")
    public Vehiculo buscarPorId(@PathVariable Long id){
        return vehiculoService.buscar(id).orElseThrow();
    }

    @PostMapping
    public ApiResponse<Vehiculo> guardar(@RequestBody Vehiculo vehiculo){
        return vehiculoService.guardar(vehiculo);
    }

    @PutMapping("/{id}")
    public ApiResponse<Vehiculo> modificar(@PathVariable  long id, @RequestBody Vehiculo vehiculo){
        return vehiculoService.update(id,vehiculo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable  long id){
         vehiculoService.eliminar(id);
    }

    @GetMapping("/cliente/{numerodoc}")
    public List<Vehiculo> obtenerVehiculosPorcliente(@PathVariable  String numerodoc) {
    return vehiculoService.obtenerVehiculosPorcliente(numerodoc);
}
}
