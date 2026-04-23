package com.taller.service;

import com.taller.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    Usuario update(Long id, Usuario usuario);

    void deleteById(Long id);
}
