package com.app.homeCircle.Service;

import org.springframework.stereotype.Service;

import com.app.homeCircle.Casa.Casa;
import com.app.homeCircle.Casa.CasaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CasaService {

    private final CasaRepository casaRepository; /* Añadir el Repositorio */

    public void createCasa(Casa casa) {
        casaRepository.save(casa);
    }   

    public void updateCasa(Integer id, Casa casaData) {
        Casa casa = casaRepository.findById(id).orElseThrow(() -> new RuntimeException("Casa con id: " + id + " no encontrada"));

        casaData.setId(casa.getId());
        casaRepository.save(casaData);
    }

    public void deleteCasa(Integer id) {
        Casa casa = casaRepository.findById(id).orElseThrow(() -> new RuntimeException("Casa con id: " + id + " no encontrada"));
        casaRepository.delete(casa);
    }
}
