package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Etiqueta;
import com.eventbookspring.eventbookspring.entity.Evento;
import com.eventbookspring.eventbookspring.repository.EtiquetaRepository;
import com.eventbookspring.eventbookspring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {
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

    public void guardarEtiqueta(Etiqueta etiqueta){
        this.etiquetaRepository.save(etiqueta);
    }

    public Etiqueta findEtiquetaById(Integer id){
        Etiqueta e = this.etiquetaRepository.getById(id);
        return e;
    }

    public void borrarEtiqueta(Integer id){
        Etiqueta e = this.etiquetaRepository.getById(id);
        this.etiquetaRepository.delete(e);
    }

    public List<Etiqueta> listarEtiquetas() {
        return this.etiquetaRepository.findSortedAlphabetically();
    }
}
