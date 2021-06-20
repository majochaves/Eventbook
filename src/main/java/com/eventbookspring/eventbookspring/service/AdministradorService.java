package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    private UsuarioRepository usuarioRepository;

    private AdministradorRepository administradorRepository;

    private AnalistaRepository analistaRepository;

    private UsuarioeventosRepository usuarioeventosRepository;

    private CreadoreventosRepository creadoreventosRepository;

    private TeleoperadorRepository teleoperadorRepository;

    private EntityManager em;

    @Autowired
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired
    public void setAdministradorRepository(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Autowired
    public void setAnalistaRepository(AnalistaRepository analistaRepository) {
        this.analistaRepository = analistaRepository;
    }

    @Autowired
    public void setCreadoreventosRepository(CreadoreventosRepository creadoreventosRepository) {
        this.creadoreventosRepository = creadoreventosRepository;
    }

    @Autowired
    public void setTeleoperadorRepository(TeleoperadorRepository teleoperadorRepository) {
        this.teleoperadorRepository = teleoperadorRepository;
    }

    @Autowired
    public void setUsuarioeventosRepository(UsuarioeventosRepository usuarioeventosRepository) {
        this.usuarioeventosRepository = usuarioeventosRepository;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO guardarUsuario(UsuarioDTO dto, String rol, boolean edicion) {
        UsuarioDTO result = null;
        if (edicion)
            result = editarUsuario(dto);
        else
            result = crearUsuario(dto, rol);

        return result;
    }

    // NUEVO USUARIO
    public UsuarioDTO crearUsuario(UsuarioDTO dto, String rol) {
        UsuarioDTO result = null;

        if (esUnico(dto.getUsername(), null, false)) {
            Usuario u = new Usuario();

            u.setNombre(dto.getNombre());
            u.setApellidos(dto.getApellidos());
            u.setUsername(dto.getUsername());
            u.setPassword(dto.getPassword());
            u.setDomicilio(dto.getDomicilio());
            u.setCiudadResidencia(dto.getCiudadResidencia());
            u.setSexo(dto.getSexo());
            u.setFechaCreacion(new Date());

            usuarioRepository.save(u);
            switch(rol) {
                case "administrador":
                    crearAdministrador(u);
                    break;
                case "creador-eventos":
                    crearCreadorEventos(u);
                    break;
                case "teleoperador":
                    crearTeleoperador(u);
                    break;
                case "analista":
                    crearAnalista(u);
                    break;
                default:
                    break;
            }
            usuarioRepository.save(u);

            result = u.getDTO();
        }

        return result;
    }

    // EDITAR USUARIO EXISTENTE
    public UsuarioDTO editarUsuario(UsuarioDTO dto) {
        UsuarioDTO result = null;
        Optional<Usuario> optionalU = usuarioRepository.findById(dto.getId());

        if (optionalU.isPresent() && esUnico(dto.getUsername(), null, false)) {
            Usuario u = optionalU.get();

            u.setNombre(dto.getNombre());
            u.setApellidos(dto.getApellidos());
            u.setUsername(dto.getUsername());
            u.setPassword(dto.getPassword());
            u.setDomicilio(dto.getDomicilio());
            u.setCiudadResidencia(dto.getCiudadResidencia());
            u.setSexo(dto.getSexo());

            usuarioRepository.save(u);

            result = u.getDTO();
        }

        return result;
    }

    public void crearAdministrador(Usuario u) {
        Administrador administrador = new Administrador(u.getId());
        u.setAdministrador(administrador);
        administrador.setUsuario(u);
        usuarioRepository.save(u);
        administradorRepository.save(administrador);

        // El Administrador debe tener todos los roles
        crearCreadorEventos(u);
        crearTeleoperador(u);
        crearAnalista(u);
        crearUsuarioEventos(u);
    }

    public void crearUsuarioEventos(Usuario u) {
        Usuarioeventos usuarioEventos = new Usuarioeventos(u.getId());
        u.setUsuarioeventos(usuarioEventos);
        usuarioEventos.setUsuario(u);
        usuarioRepository.save(u);
        usuarioeventosRepository.save(usuarioEventos);
    }

    public void crearAnalista(Usuario u) {
        Analista analista = new Analista(u.getId());
        u.setAnalista(analista);
        analista.setUsuario(u);
        usuarioRepository.save(u);
        analistaRepository.save(analista);
    }

    public void crearCreadorEventos(Usuario u) {
        Creadoreventos creadorEventos = new Creadoreventos(u.getId());
        u.setCreadoreventos(creadorEventos);
        creadorEventos.setUsuario(u);
        usuarioRepository.save(u);
        creadoreventosRepository.save(creadorEventos);
    }

    public void crearTeleoperador(Usuario u) {
        Teleoperador teleoperador = new Teleoperador(u.getId());
        u.setTeleoperador(teleoperador);
        teleoperador.setUsuario(u);
        usuarioRepository.save(u);
        teleoperadorRepository.save(teleoperador);
    }

    public boolean esUnico(String nombreUsuario, Integer id, boolean edicion) {
        boolean resultado = true;
        List<Usuario> q = em
                .createQuery("SELECT u from Usuario u WHERE u.username = :nombreUsuario")
                .setParameter("nombreUsuario", nombreUsuario)
                .getResultList();

        Usuario u = q.isEmpty() ? null : q.get(0);

        if (!edicion && u != null) {
            resultado = false;
        } else if (edicion && u != null && !u.getId().equals(id)) {
            resultado = false;
        }

        return resultado;
    }
}
