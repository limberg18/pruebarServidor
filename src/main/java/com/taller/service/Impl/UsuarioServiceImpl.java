package com.taller.service.Impl;

import com.taller.model.Usuario;
import com.taller.repository.UsuarioRepository;
import com.taller.service.IUsuarioService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return  usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        u.setUsername(usuario.getUsername());
        u.setNombre(usuario.getNombre());
        u.setEmail(usuario.getEmail());
        u.setRol(usuario.getRol());
        u.setPassword(usuario.getPassword());
        u.setActivo(usuario.getActivo());

        return usuarioRepository.save(u);
    }

    @Override
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
