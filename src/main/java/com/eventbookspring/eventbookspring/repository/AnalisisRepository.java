package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Analisis;
import com.eventbookspring.eventbookspring.entity.Analista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalisisRepository extends JpaRepository<Analisis, Integer> {

    public List<Analisis> findAnalisisByAnalistaUsuarioId(Analista thisAnalista);

}