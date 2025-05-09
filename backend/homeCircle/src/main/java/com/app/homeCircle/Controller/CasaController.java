package com.app.homeCircle.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final CasaService casaService;

    @PostMapping
    private void createCasa(@RequestBody Casa casa){
        casaService.createCasa(casa);
    }

    @GetMapping
    public List<Casa> searchCasas(){
        return casaService.searchCasas();
    }

    @DeleteMapping("/{id}")
    public void deleteCasa(@PathVariable Integer id){
        casaService.deleteCasa(id);
    }
}
