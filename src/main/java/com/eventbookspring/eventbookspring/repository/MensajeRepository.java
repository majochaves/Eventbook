package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
}