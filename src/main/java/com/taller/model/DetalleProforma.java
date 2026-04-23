package com.taller.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "proforma_id")
    private Proforma proforma;//<------ este nombre es de la relacion
}

