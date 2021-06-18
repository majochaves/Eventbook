package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Analista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AnalistaRepository extends JpaRepository<Analista, Integer> {


}