package com.app.homeCircle.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.homeCircle.Reserva.Reserva;
import com.app.homeCircle.Reserva.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public void createReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    //? buscar todas las reservas
    public List<Reserva> searchReservas() {
        return reservaRepository.findAll();
    }

    public void updateReserva(Integer id, Reserva reservaData) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva con id: " + id + " no encontrada"));

        reservaData.setId(reserva.getId());
        reservaRepository.save(reservaData);
    }

    public void deleteReserva(Integer id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva con id: " + id + " no encontrada"));
        reservaRepository.delete(reserva);
    }
}
