package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Teleoperador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeleoperadorRepository extends JpaRepository<Teleoperador, Integer> {
    // Named query
    public Teleoperador findByUsuarioId(Integer usuarioId);
}