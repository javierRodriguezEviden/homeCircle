package com.app.homeCircle.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.homeCircle.Usuario.Usuario;
import com.app.homeCircle.Usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void createUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    //? buscar todos los usuarios
    public List<Usuario> searchUsuarios() {
        return usuarioRepository.findAll();
    }

    //?buscar usuario por email
    public Usuario searchByEmail(String email){
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public void updateUsuario(Integer id, Usuario usuarioData){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado"));

        usuarioData.setId(usuario.getId());
        usuarioRepository.save(usuarioData);
    }

    public void deleteUsuario(Integer id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado"));
        usuarioRepository.delete(usuario);
    }
}
