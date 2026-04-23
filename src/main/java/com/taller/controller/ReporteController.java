package com.taller.controller;

import com.taller.service.Impl.ReporteService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/reporte")
@CrossOrigin("*")
@AllArgsConstructor
public class ReporteController {
private final ReporteService reporteService;



    @GetMapping("{id}")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Integer id) {
        try {
            byte[] reporte = reporteService.generarReporte(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Blank_A4.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reporte);

        } catch (JRException e) {
            e.printStackTrace(); // 👈 IMPORTANTE
            throw new RuntimeException(e.getMessage());
        }
    }
}
