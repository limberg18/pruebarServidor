package com.taller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    @Min(0)
    @PositiveOrZero(message = "el stock no puede ser negativo")
  private Integer stock;
    @ManyToOne
    @JoinColumn(name = "paquete_id")
    private Paquete paquete;
}
