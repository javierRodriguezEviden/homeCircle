package com.app.homeCircle.ControllerTest;

import com.app.homeCircle.Controller.UsuarioController;
import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Service.UsuarioService;
import com.app.homeCircle.dto.UsuarioBasicDTO;
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
        // Arrange
        String email = "test@example.com";
        UsuarioBasicDTO expectedDto = new UsuarioBasicDTO(
                "Nombre",
                "Apellidos",
                "test@example.com",
                "123456789",
                "12345678A",
                "SedeApellidos"
        );
        when(usuarioService.searchByEmail(email)).thenReturn(expectedDto);

        // Act & Assert
        mockMvc.perform(get("/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value(expectedDto.getNombre()))
                .andExpect(jsonPath("$.apellidos").value(expectedDto.getApellidos()))
                .andExpect(jsonPath("$.email").value(expectedDto.getEmail()))
                .andExpect(jsonPath("$.telefono").value(expectedDto.getTelefono()))
                .andExpect(jsonPath("$.dni").value(expectedDto.getDni()))
                .andExpect(jsonPath("$.sedeapellidos").value(expectedDto.getSede()));
    }

    @Test
    void searchByEmail_ShouldReturnNotFound() throws Exception {
        // Arrange
        String email = "noexiste@example.com";
        when(usuarioService.searchByEmail(email)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/{email}", email))
                .andExpect(status().isNotFound());
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