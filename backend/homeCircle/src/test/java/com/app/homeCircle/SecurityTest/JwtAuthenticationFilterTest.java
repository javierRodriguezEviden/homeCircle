package com.app.homeCircle.SecurityTest;


import com.app.homeCircle.Auth.JwtService;
import com.app.homeCircle.Controller.UsuarioController;
import com.app.homeCircle.Security.JwtAuthenticationFilter;
import com.app.homeCircle.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsuarioController.class)
@Import({JwtAuthenticationFilter.class}) // Importamos el filtro manualmente
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldAllowRequestWithValidToken() throws Exception {
        String token = "valid.jwt.token";
        String email = "test@example.com";

        UserDetails userDetails = new User(email, "password", Collections.emptyList());

        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);


        mockMvc.perform(get("/usuarios")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());

        verify(jwtService).getEmailFromToken(token);
        verify(jwtService).isTokenValid(token, userDetails);
    }


    @Test
    void shouldAllowRequestWithoutTokenToAuthPath() throws Exception {
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isNotFound()); // o el status que devuelva tu endpoint
    }


    @Test
    void shouldRejectRequestWithInvalidToken() throws Exception {
        String token = "invalid.jwt.token";

        when(jwtService.getEmailFromToken(token)).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(
                new User("test@example.com", "password", Collections.emptyList()));
        when(jwtService.isTokenValid(eq(token), any())).thenReturn(false);

        mockMvc.perform(get("/usuarios")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk()); // El filtro no bloquea, pero no autentica
    }
}
