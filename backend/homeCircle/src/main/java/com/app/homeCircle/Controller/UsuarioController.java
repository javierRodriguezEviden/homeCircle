package com.app.homeCircle.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.homeCircle.Usuario.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService; /* añadir servicio */

    @PostMapping
    private void crearUsuario(@RequestBody Usuario usuario){
        usuarioService.crearUsuario(usuario);
    }


    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id){
        usuarioService.eliminarUsuario(id);
    }

}
