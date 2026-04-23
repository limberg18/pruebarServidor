package com.taller.controller;

import com.taller.dto.ProformaDto;
import com.taller.model.Proforma;
import com.taller.service.IProformaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proformas")
@CrossOrigin("*")
public class ProformaController {

    private final IProformaService proformaService;

    public ProformaController(IProformaService proformaService) {
        this.proformaService = proformaService;
    }

    @GetMapping
    public ResponseEntity<List<ProformaDto>> listar() {
        return ResponseEntity.ok(proformaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProformaDto> buscar(@PathVariable Long id) {
        return proformaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProformaDto crear(@RequestBody ProformaDto proforma) {
        return proformaService.save(proforma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proformaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ProformaDto actualizar(@PathVariable Long id, @RequestBody ProformaDto proforma) {
        return proformaService.update(id, proforma);
    }
}
