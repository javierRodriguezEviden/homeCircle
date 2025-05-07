package com.app.homeCircle.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.homeCircle.Casa.Casa;
import com.app.homeCircle.Service.CasaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/casas")
@RequiredArgsConstructor
public class CasaController {

    private final CasaService casaServicio;

    @PostMapping
    private void crearCasa(@RequestBody Casa casa){
        casaServicio.createCasa(casa);
    }


    @DeleteMapping("/{id}")
    public void eliminarCasa(@PathVariable Integer id){
        casaServicio.deleteCasa(id);
    }



}
