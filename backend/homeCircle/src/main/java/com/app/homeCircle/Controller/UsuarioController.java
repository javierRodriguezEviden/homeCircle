package com.app.homeCircle.Controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.app.homeCircle.dto.UsuarioBasicDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    private void createUsuario(@RequestBody Usuario usuario) {
        usuarioService.createUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> searchUsuarios() {
        return usuarioService.searchUsuarios();
    }

    //EN RESERVA
    /*@GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.searchById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    // Endpoint para buscar los datos de perfil de usuario por email
    @GetMapping("/search/{email}")
    public ResponseEntity<UsuarioBasicDTO> searchByEmail(@PathVariable(name = "email") String emailCodificado) {
        try {
            // Decodificar el email
            String email = URLDecoder.decode(emailCodificado, StandardCharsets.UTF_8);
            UsuarioBasicDTO usuario = usuarioService.searchByEmail(email);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para buscar los datos bancarios de usuario por email
    @GetMapping("/cbsearch/{email}")
    public ResponseEntity<String> getCuentaBancaria(@PathVariable(name = "email") String emailCodificado) {
        try {
            String email = URLDecoder.decode(emailCodificado, StandardCharsets.UTF_8);
            Optional<String> cuentaBancaria = usuarioService.getCuentaBancariaByEmail(email);
            if (cuentaBancaria.isPresent()) {
                return ResponseEntity.ok(cuentaBancaria.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /* Metodo para actualizar el usuario en el perfil */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioData) {
        Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioData);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }

}
