package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Etiqueta;
import com.eventbookspring.eventbookspring.entity.Evento;
import com.eventbookspring.eventbookspring.repository.EtiquetaRepository;
import com.eventbookspring.eventbookspring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

        Optional<Etiqueta> thisEtiquetaOpt = this.etiquetaRepository.findById(id);
        if(thisEtiquetaOpt.isPresent()){
            Etiqueta thisEtiqueta = thisEtiquetaOpt.get();
            List<Evento> listaEventos = thisEtiqueta.getEventoList();
            if(listaEventos!=null){
                for(Evento e : listaEventos){
                    List<Etiqueta> listaEtiquetas = e.getEtiquetaList();
                    listaEtiquetas.remove(thisEtiqueta);
                    e.setEtiquetaList(listaEtiquetas);
                    this.eventoRepository.save(e);
                }
            }
            this.etiquetaRepository.delete(thisEtiqueta);
        }


    }

    public List<Etiqueta> listarEtiquetas() {
        return this.etiquetaRepository.findSortedAlphabetically();
    }
}
