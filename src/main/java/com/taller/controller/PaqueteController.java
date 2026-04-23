package com.taller.controller;

import com.taller.model.Paquete;
import com.taller.service.ApiResponse;
import com.taller.service.IPaqueteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paquete")
@CrossOrigin("*")
@AllArgsConstructor
public class PaqueteController {
    private final IPaqueteService paqueteService;

    @GetMapping
    public List<Paquete> listar(){
        return paqueteService.listar();
    }

    @GetMapping("{id}")
    public Paquete buscar(@PathVariable  Integer id){
              return paqueteService.buscar(id).get();
    }

    @PostMapping
    public ApiResponse<Paquete> guardar(@RequestBody Paquete paquete){
        return paqueteService.guardar(paquete);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable  Integer id){

        paqueteService.eliminar(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<Paquete> actualizar(@PathVariable  int id, @RequestBody Paquete paquete){
      return  paqueteService.update(id, paquete);
    }
}
