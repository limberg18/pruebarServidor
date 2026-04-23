package com.taller.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private Double subtotal;
    private Double igv;
    private Double total;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(
            mappedBy = "proforma",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleProforma> detalles;
    private String observacion;

    @ManyToOne
    @JoinColumn(
            name = "vehiculo_id",
            referencedColumnName = "id"
    )
    private Vehiculo vehiculo;

}
