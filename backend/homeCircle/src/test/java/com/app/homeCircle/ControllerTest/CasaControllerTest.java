package com.app.homeCircle.ControllerTest;

import com.app.homeCircle.Controller.CasaController;
import com.app.homeCircle.Entity.Casa;
import com.app.homeCircle.Service.CasaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CasaController.class)
public class CasaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CasaService casaService;

    @Test
    void createCasa_ShouldCallService() throws Exception {
        doNothing().when(casaService).createCasa(any(Casa.class));

        mockMvc.perform(post("/casas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Casa Bonita\"}"))
                .andExpect(status().isOk());

        verify(casaService, times(1)).createCasa(any(Casa.class));
    }

    @Test
    void searchCasas_ShouldReturnList() throws Exception {
        List<Casa> casas = Arrays.asList(new Casa(), new Casa());
        when(casaService.searchCasas()).thenReturn(casas);

        mockMvc.perform(get("/casas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(casaService, times(1)).searchCasas();
    }

    @Test
    void searchCasas_ShouldReturnEmptyList() throws Exception {
        when(casaService.searchCasas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/casas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(casaService, times(1)).searchCasas();
    }

    @Test
    void deleteCasa_ShouldCallService() throws Exception {
        Integer casaId = 1;
        doNothing().when(casaService).deleteCasa(casaId);

        mockMvc.perform(delete("/casas/" + casaId))
                .andExpect(status().isOk());

        verify(casaService, times(1)).deleteCasa(casaId);
    }
}

