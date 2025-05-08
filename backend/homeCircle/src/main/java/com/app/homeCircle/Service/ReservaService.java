package com.app.homeCircle.Service;

import org.springframework.stereotype.Service;

import com.app.homeCircle.Reserva.Reserva;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public void createReserva(Reserva reserva) {
        reservaRepository.save(reserva);
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
