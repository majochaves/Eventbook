package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Integer> {

    @Query("SELECT et FROM Etiqueta et ORDER BY et.descripcion ASC")
    public List<Etiqueta> findSortedAlphabetically();



}