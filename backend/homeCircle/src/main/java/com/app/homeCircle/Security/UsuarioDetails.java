package com.app.homeCircle.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.homeCircle.Entity.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si tienes roles o permisos, puedes devolverlos aquí.
        // Por ahora, devolvemos una lista vacía.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword(); // Devuelve la contraseña del usuario
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // Devuelve el email del usuario como nombre de usuario
    }

}
