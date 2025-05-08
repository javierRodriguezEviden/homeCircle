package com.app.homeCircle.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.homeCircle.Usuario.Usuario;

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
        return usuario.getEmail(); // Devuelve el email como identificador principal
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambia según tu lógica de negocio
    }
}