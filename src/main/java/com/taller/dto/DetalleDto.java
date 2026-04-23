package com.taller.dto;

import com.taller.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class DetalleDto {
    private Integer cantidad;
    private Double precioUnitario;
    private Producto producto; // Código del producto
}