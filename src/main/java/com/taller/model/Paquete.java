package com.taller.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paquetes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class   Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;
}
