package com.app.homeCircle.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.homeCircle.Service.UsuarioService;
import com.app.homeCircle.Usuario.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    private void createUsuario(@RequestBody Usuario usuario){
        usuarioService.createUsuario(usuario);
    }


    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id){
        usuarioService.deleteUsuario(id);
    }

}
