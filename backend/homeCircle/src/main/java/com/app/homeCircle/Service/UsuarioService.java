package com.app.homeCircle.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void createUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario searchById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // buscar todos los usuarios
    public List<Usuario> searchUsuarios() {
        return usuarioRepository.findAll();
    }

    // buscar usuario por email
    public Usuario searchByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario updateUsuario(Integer id, Usuario usuarioData) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioData.getNombre());
            usuario.setApellidos(usuarioData.getApellidos());
            usuario.setEmail(usuarioData.getEmail());
            usuario.setTelefono(usuarioData.getTelefono());
            usuario.setDni(usuarioData.getDni());
            usuario.setSede(usuarioData.getSede());
            usuario.setCuenta_banco(usuarioData.getCuenta_banco());
            // ...otros campos si los tienes...
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public boolean existsByDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
