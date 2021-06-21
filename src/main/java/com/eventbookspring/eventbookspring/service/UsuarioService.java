package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuarioById(Integer id){
        return this.usuarioRepository.getById(id);
    }
}
