package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Reserva;
import com.eventbookspring.eventbookspring.entity.ReservaPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, ReservaPK> {
}