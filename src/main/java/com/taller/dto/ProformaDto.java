package com.taller.dto;
import com.taller.model.Cliente;
import com.taller.model.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class ProformaDto {
    private Long id;
        private String numero;
        private LocalDate fechaEmision;
        private LocalDate fechaVencimiento;
        private String observacion;
        private Cliente cliente;
        private List<DetalleDto> detalles;
        private String estado;
        private String nombre;
        private Vehiculo vehiculo;
    private Double total;




}
