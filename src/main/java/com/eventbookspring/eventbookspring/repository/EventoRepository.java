package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}