package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {




}