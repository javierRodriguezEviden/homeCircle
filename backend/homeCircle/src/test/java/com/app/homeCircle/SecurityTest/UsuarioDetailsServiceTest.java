package com.app.homeCircle.SecurityTest;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;
import com.app.homeCircle.Security.UsuarioDetails;
import com.app.homeCircle.Security.UsuarioDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDetailsService usuarioDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = usuarioDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof UsuarioDetails);
        assertEquals("test@example.com", ((UsuarioDetails) userDetails).getUsername());
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        when(usuarioRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioDetailsService.loadUserByUsername("notfound@example.com");
        });
    }
}