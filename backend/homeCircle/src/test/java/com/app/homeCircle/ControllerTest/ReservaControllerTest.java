package com.app.homeCircle.ControllerTest;

import com.app.homeCircle.Controller.ReservaController;
import com.app.homeCircle.Entity.Reserva;
import com.app.homeCircle.Service.ReservaService;
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

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService reservaService;

    @Test
    void createReserva_ShouldCallService() throws Exception {
        doNothing().when(reservaService).createReserva(any(Reserva.class));

        mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"detalle\":\"Reserva de ejemplo\"}"))
                .andExpect(status().isOk());

        verify(reservaService, times(1)).createReserva(any(Reserva.class));
    }

    @Test
    void searchReservas_ShouldReturnList() throws Exception {
        List<Reserva> reservas = Arrays.asList(new Reserva(), new Reserva());
        when(reservaService.searchReservas()).thenReturn(reservas);

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reservaService, times(1)).searchReservas();
    }

    @Test
    void searchReservas_ShouldReturnEmptyList() throws Exception {
        when(reservaService.searchReservas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(reservaService, times(1)).searchReservas();
    }

    @Test
    void deleteReserva_ShouldCallService() throws Exception {
        Integer reservaId = 1;
        doNothing().when(reservaService).deleteReserva(reservaId);

        mockMvc.perform(delete("/reservas/" + reservaId))
                .andExpect(status().isOk());

        verify(reservaService, times(1)).deleteReserva(reservaId);
    }
}

