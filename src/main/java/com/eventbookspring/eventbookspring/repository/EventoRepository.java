package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.entity.Etiqueta;
import com.eventbookspring.eventbookspring.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("SELECT e FROM Evento e WHERE e.etiquetaList IN :etiqueta")
    public List<Evento> findByFiltro(@Param("etiqueta") Etiqueta etiqueta);

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(e.titulo), COUNT(e.titulo)) FROM Evento e GROUP BY UPPER(e.titulo)")
    public List<Par<?, ?>> getNumEventosGroupByTitulos();
}