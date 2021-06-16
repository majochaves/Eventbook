package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}