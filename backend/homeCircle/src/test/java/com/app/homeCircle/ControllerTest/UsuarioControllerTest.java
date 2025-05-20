package com.app.homeCircle.ControllerTest;

import com.app.homeCircle.Controller.UsuarioController;
import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void createUsuario_ShouldCallService() throws Exception {
        doNothing().when(usuarioService).createUsuario(any(Usuario.class));

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
    }

    @Test
    void searchUsuarios_ShouldReturnList() throws Exception {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioService.searchUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(usuarioService, times(1)).searchUsuarios();
    }

    @Test
    void searchUsuarios_ShouldReturnEmptyList() throws Exception {
        when(usuarioService.searchUsuarios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(usuarioService, times(1)).searchUsuarios();
    }

    @Test
    void searchByEmail_ShouldReturnUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        when(usuarioService.searchByEmail("test@example.com")).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(usuarioService, times(1)).searchByEmail("test@example.com");
    }

    @Test
    void searchByEmail_ShouldReturnNotFound() throws Exception {
        when(usuarioService.searchByEmail("notfound@example.com")).thenReturn(null);

        mockMvc.perform(get("/usuarios/notfound@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(usuarioService, times(1)).searchByEmail("notfound@example.com");
    }

    @Test
    void deleteUsuario_ShouldCallService() throws Exception {
        Integer userId = 1;
        doNothing().when(usuarioService).deleteUsuario(userId);

        mockMvc.perform(delete("/usuarios/" + userId))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).deleteUsuario(userId);
    }
}