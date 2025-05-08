package com.app.homeCircle.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.homeCircle.Reserva.Reserva;
import com.app.homeCircle.Service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    private void createReserva(@RequestBody Reserva reserva){
        reservaService.createReserva(reserva);
    }

    @DeleteMapping("/{id}")
    private void deleteReserva(@PathVariable Integer id) {
        reservaService.deleteReserva(id);
    }

}
