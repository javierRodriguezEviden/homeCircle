package com.app.homeCircle.SecurityTest;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Security.UsuarioDetails;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDetailsTest {

    @Test
    void testGetUsernameAndPassword() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("secret");

        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);

        assertEquals("test@example.com", usuarioDetails.getUsername());
        assertEquals("secret", usuarioDetails.getPassword());
    }

    @Test
    void testGetAuthoritiesReturnsEmptyList() {
        Usuario usuario = new Usuario();
        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);

        Collection<? extends GrantedAuthority> authorities = usuarioDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }
}
