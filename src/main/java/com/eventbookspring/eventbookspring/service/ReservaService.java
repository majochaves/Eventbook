package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Reserva;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservaService {
    private ReservaRepository reservaRepository;

    @Autowired
    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(Integer fila, Integer numAsiento, Integer eventoId, Usuario usuario){
        Reserva reserva = new Reserva(fila, numAsiento, eventoId);
        reserva.setFecha(new Date());
        reserva.setUsuarioeventosId(usuario.getUsuarioeventos());
        this.reservaRepository.save(reserva);

        return reserva;
    }

    public void borrarReserva(Reserva r){
        this.reservaRepository.delete(r);
    }

}
