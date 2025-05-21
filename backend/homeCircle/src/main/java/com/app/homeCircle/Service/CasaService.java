package com.app.homeCircle.Service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;
import com.app.homeCircle.Entity.Casa;
import com.app.homeCircle.Entity.CasaRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CasaService {

    private final CasaRepository casaRepository;
    private final UsuarioRepository usuarioRepository;

    public Casa createCasa(Casa casa) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    
    String email = auth.getName(); 
    
    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    casa.setUsuario(usuario); 
    
    return casaRepository.save(casa);
}

        //? buscar todas las reservas
    public List<Casa> searchCasas() {
        return casaRepository.findAll();
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
