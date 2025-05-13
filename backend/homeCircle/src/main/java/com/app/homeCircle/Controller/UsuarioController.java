package com.app.homeCircle.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Service.UsuarioService;

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

    @GetMapping
    public List<Usuario> searchUsuarios(){
        return usuarioService.searchUsuarios();
    }

    @GetMapping("/{email}")
    public Usuario searchByEmail(@PathVariable String email){
        return usuarioService.searchByEmail(email);
    }
    


    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id){
        usuarioService.deleteUsuario(id);
    }

}
