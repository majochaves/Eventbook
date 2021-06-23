package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(e.titulo), COUNT(e.titulo)) FROM Evento e GROUP BY UPPER(e.titulo)")
    public List<Par<?, ?>> getNumEventosGroupByTitulos();

}