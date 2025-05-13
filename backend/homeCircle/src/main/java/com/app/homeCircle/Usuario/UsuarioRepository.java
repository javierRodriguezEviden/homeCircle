package com.app.homeCircle.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{

    
    Optional<Usuario> findByEmail(String email);

    boolean existsByDni(String dni); // Added method declaration
    boolean existsByEmail(String email); // Added method declaration
}
