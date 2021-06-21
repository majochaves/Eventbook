package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.entity.Usuarioeventos;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import com.eventbookspring.eventbookspring.repository.UsuarioeventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AutenticacionService {

    private UsuarioRepository usuarioRepository;

    private UsuarioeventosRepository usuarioeventosRepository;

    @Autowired
    public void setUsuarioeventosRepository(UsuarioeventosRepository usuarioeventosRepository) {
        this.usuarioeventosRepository = usuarioeventosRepository;
    }

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


    public void guardarUsuarioeventos(UsuarioDTO userDTO) {

        Usuario u = new Usuario();

        u.setNombre(userDTO.getNombre());
        u.setApellidos(userDTO.getApellidos());
        u.setUsername(userDTO.getUsername());
        u.setPassword(userDTO.getPassword());
        u.setDomicilio(userDTO.getDomicilio());
        u.setCiudadResidencia(userDTO.getCiudadResidencia());
        u.setSexo(userDTO.getSexo());
        u.setFechaCreacion(new Date());

        usuarioRepository.save(u);
        Usuarioeventos uE = new Usuarioeventos(u.getId());
        u.setUsuarioeventos(uE);
        uE.setUsuario(u);
        usuarioRepository.save(u);
        usuarioeventosRepository.save(uE);


    }
}
