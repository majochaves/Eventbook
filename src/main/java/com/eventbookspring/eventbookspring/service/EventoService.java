package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.dto.EventoDTO;
import com.eventbookspring.eventbookspring.entity.Evento;
import com.eventbookspring.eventbookspring.entity.Reserva;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.EtiquetaRepository;
import com.eventbookspring.eventbookspring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {
    private EtiquetaRepository etiquetaRepository;
    private EventoRepository eventoRepository;

    @Autowired
    public void setEventoRepository(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
    @Autowired
    public void setEtiquetaRepository(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    protected List<EventoDTO> convertirAListaDTO (List<Evento> lista) {
        if (lista != null) {
            List<EventoDTO> listaDTO = new ArrayList<EventoDTO>();
            for (Evento evento:lista) {
                listaDTO.add(evento.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
    }

    public void borrarEvento (Integer id) {
        Evento evento = this.eventoRepository.findById(id).orElse(null);
        this.eventoRepository.delete(evento);
    }

    public EventoDTO getEventoById(Integer id){
        Evento e = this.eventoRepository.getById(id);
        return e.getDTO();
    }


    public List<EventoDTO> listarEventos () {
        List<Evento> lista;

        lista = this.eventoRepository.findAll();

        return this.convertirAListaDTO(lista);
    }

    public void agregarReserva(Integer eventoId, Reserva reserva){
        Evento evento = this.eventoRepository.getById(eventoId);
        evento.getReservaList().add(reserva);
        this.eventoRepository.save(evento);
    }


    public Evento guardarEvento (EventoDTO dto) throws ParseException {

        Evento evento;
        if (dto.getId() == null) {
            evento = new Evento();
        } else {
            evento = this.eventoRepository.findById(dto.getId()).orElse(new Evento());
        }

        evento.setId(dto.getId());
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getFecha()));
        evento.setFechaLimite(dto.getFechaLimite().isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(dto.getFechaLimite()));
        evento.setCosteEntrada(dto.getCosteEntrada());
        evento.setAforo(dto.getAforo());
        evento.setAsientosFijos(dto.getAsientosFijos() == null || dto.getAsientosFijos() == 'N' ? 'N' : 'S');
        evento.setMaxEntradas(dto.getMaxEntradas());
        evento.setNumFilas(dto.getNumFilas());
        evento.setNumAsientosFila(dto.getNumAsientosFila());
        evento.setEtiquetaList(dto.getEtiquetaList());
        evento.setCreadoreventosId(dto.getCreadoreventos());

        this.eventoRepository.save(evento);

        return evento;
    }

    public void borrarReserva(Integer eventoId, Reserva r){
        Evento e = this.eventoRepository.getById(eventoId);
        e.getReservaList().remove(r);
        this.eventoRepository.save(e);
    }

    public List<Reserva> getReservasDeUsuario(Usuario u, Integer eventoId){
        Evento e = this.eventoRepository.getById(eventoId);
        List<Reserva> reservasUsuario = new ArrayList<>();
        for(Reserva r: e.getReservaList()){
            if(r.getUsuarioeventosId().equals(u.getUsuarioeventos())){
                reservasUsuario.add(r);
            }
        }
        return reservasUsuario;
    }

    public List<EventoDTO> getEventosDeUsuario(Usuario u){
        List<EventoDTO> eventosUsuario = new ArrayList<>();
        List<EventoDTO> eventos = this.listarEventos();

        for(EventoDTO e: eventos){
            boolean usuarioTieneReserva = false;
            for(Reserva r: e.getReservaList()){
                if(r.getUsuarioeventosId().equals(u.getUsuarioeventos())){
                    usuarioTieneReserva = true;
                }
            }
            if(usuarioTieneReserva) eventosUsuario.add(e);
        }
        return eventosUsuario;
    }
}
