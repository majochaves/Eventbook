package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO getUsuarioDto(String username, String password) throws AutenticacionException {
        Usuario thisUsuario = this.usuarioRepository.getUsuarioByUsernameLike(username);
        if(!thisUsuario.getPassword().equals(password))
            throw new AutenticacionException("El usuario o contraseña proporcionados son incorrectos");

        return thisUsuario.getDTO();
    }
}
