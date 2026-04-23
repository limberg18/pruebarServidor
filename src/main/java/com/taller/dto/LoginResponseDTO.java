package com.taller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String nombre;
    private String rol;
    private boolean status;
    private boolean mensaje=false;
}