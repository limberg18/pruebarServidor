package com.taller.service.Impl;
import com.taller.dto.LoginRequestDTO;
import com.taller.dto.LoginResponseDTO;
import com.taller.model.Usuario;
import com.taller.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!usuario.getRol().name().equals(dto.getRol())) {
            throw new RuntimeException("Rol no autorizado");
        }

        return LoginResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .status(true)
                .mensaje(true)
                .build();
    }

}
