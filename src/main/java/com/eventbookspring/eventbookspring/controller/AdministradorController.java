package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdministradorController {

    @Autowired
    private EntityManager em;

    private UsuarioRepository usuarioRepository;

    private AdministradorRepository administradorRepository;

    private AnalistaRepository analistaRepository;

    private UsuarioeventosRepository usuarioeventosRepository;

    private CreadoreventosRepository creadoreventosRepository;

    private TeleoperadorRepository teleoperadorRepository;

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

    @GetMapping("/administracion")
    public String administracion(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());

        return "usuario-listar";
    }

    @GetMapping("/usuario-crear")
    public String usuarioCrear(Model model) {

        return "usuario-crear";
    }

    @GetMapping("/usuario-editar")
    public String usuarioEditar(Model model, @RequestParam("id") String id) {
        model.addAttribute("usuarioEditar", usuarioRepository.getById(Integer.parseInt(id)));

        return "usuario-crear";
    }

    @GetMapping("/usuario-borrar")
    public String usuarioBorrar(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("id") String id) {
        Optional<Usuario> u = usuarioRepository.findById(Integer.parseInt(id));

        if (u.isPresent()) {
            usuarioRepository.delete(u.get());

            if (u.get().equals(Autenticacion.getUsuarioLogeado(request, response)))
                return "redirect:/logout";
        }


        return "redirect:administracion";
    }

    @PostMapping("/usuario-guardar")
    public String usuarioGuardar(
        Model model,
        @RequestParam("id") String id,
        @RequestParam("usuario") String nombreUsuario,
        @RequestParam("contrasena") String contrasena,
        @RequestParam("nombre") String nombre,
        @RequestParam("apellidos") String apellidos,
        @RequestParam("sexo") String sexo,
        @RequestParam("domicilio") String domicilio,
        @RequestParam("ciudad") String ciudad,
        @RequestParam(value = "rol", required = false) String rol
    ) {
        Usuario usuarioEditar = "".equals(id) ? null : usuarioRepository.getById(Integer.parseInt(id));
        boolean edicion = usuarioEditar != null;

        for (Usuario u : usuarioRepository.findAll()) {
            if ((edicion && !usuarioEditar.equals(u) && u.getUsername().equalsIgnoreCase(nombreUsuario)) || (!edicion && u.getUsername().equalsIgnoreCase(nombreUsuario))) {
                model.addAttribute("error", "El nombre de usuario '" + nombreUsuario +  "' ya está en uso");
                model.addAttribute("usuarioEditar", usuarioEditar);

                return "redirect:usuario-crear";
            }
        }

        Date fechaCreacion = new Date();

        Usuario u = edicion ? usuarioEditar : new Usuario();
        u.setUsername(nombreUsuario);
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setPassword(contrasena);
        u.setSexo(sexo);
        u.setDomicilio(domicilio);
        u.setCiudadResidencia(ciudad);
        u.setFechaCreacion(fechaCreacion);

        if (!edicion) {
            usuarioRepository.save(u);
            switch(rol) {
                case "administrador":
                    crearAdministrador(u);
                    crearCreadorEventos(u);
                    crearTeleoperador(u);
                    crearAnalista(u);
                    crearUsuarioEventos(u);
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
        }
        usuarioRepository.save(u);

        return "redirect:administracion";
    }

    @GetMapping("/usuario-filtrar")
    public String usuarioFiltrar(Model model,
         @RequestParam(value = "rol", defaultValue = "") List<String> rolesStr,
         @RequestParam(value = "sexo", defaultValue = "") List<String> sexos,

         @RequestParam(defaultValue = "") String username,
         @RequestParam(value = "nombre", defaultValue = "") String nombre,
         @RequestParam(value = "apellidos", defaultValue = "") String apellidos,
         @RequestParam(value = "domicilio", defaultValue = "") String domicilio,
         @RequestParam(value = "ciudad", defaultValue = "") String ciudad
     ) {

        model.addAttribute("usuarios", usuarioRepository.findAll());

        List<Usuario> query = usuarioRepository.findAll();

        Class[] roles = new Class[5];
        int i = 0;
        if(rolesStr.contains("administradores"))
            roles[i++] = Administrador.class;
        if(rolesStr.contains("usuarioEventos"))
            roles[i++] = Usuarioeventos.class;
        if(rolesStr.contains("creadorEventos"))
            roles[i++] = Creadoreventos.class;
        if(rolesStr.contains("teleoperadores"))
            roles[i++] = Teleoperador.class;
        if(rolesStr.contains("analistas"))
            roles[i++] = Analista.class;

        query = query.stream()
                .filter(u -> {                                                                              // FILTRO ROL
                    try {
                        return Autenticacion.tieneRol(u, roles);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return false;
                })
                .filter(u -> sexos.contains(u.getSexo()))                                                   // FILTRO SEXO
                .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))                // FILTRO NOMBRE DE USUARIO
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))                    // FILTRO NOMBRE
                .filter(u -> u.getApellidos().toLowerCase().contains(apellidos.toLowerCase()))              // FILTRO APELLIDOS
                .filter(u -> u.getDomicilio().toLowerCase().contains(domicilio.toLowerCase()))              // FILTRO DOMICILIO
                .filter(u -> u.getCiudadResidencia().toLowerCase().contains(ciudad.toLowerCase()))          // FILTRO CIUDAD
                .collect(Collectors.toList());



        model.addAttribute("usuarios", query);

        return "usuario-listar";
    }

    private void crearAdministrador(Usuario u) {
        Administrador administrador = new Administrador(u.getId());
        u.setAdministrador(administrador);
        administrador.setUsuario(u);
        usuarioRepository.save(u);
        administradorRepository.save(administrador);
    }

    private void crearUsuarioEventos(Usuario u) {
        Usuarioeventos usuarioEventos = new Usuarioeventos(u.getId());
        u.setUsuarioeventos(usuarioEventos);
        usuarioEventos.setUsuario(u);
        usuarioRepository.save(u);
        usuarioeventosRepository.save(usuarioEventos);
    }

    private void crearAnalista(Usuario u) {
        Analista analista = new Analista(u.getId());
        u.setAnalista(analista);
        analista.setUsuario(u);
        usuarioRepository.save(u);
        analistaRepository.save(analista);
    }

    private void crearCreadorEventos(Usuario u) {
        Creadoreventos creadorEventos = new Creadoreventos(u.getId());
        u.setCreadoreventos(creadorEventos);
        creadorEventos.setUsuario(u);
        usuarioRepository.save(u);
        creadoreventosRepository.save(creadorEventos);
    }

    private void crearTeleoperador(Usuario u) {
        Teleoperador teleoperador = new Teleoperador(u.getId());
        u.setTeleoperador(teleoperador);
        teleoperador.setUsuario(u);
        usuarioRepository.save(u);
        teleoperadorRepository.save(teleoperador);
    }

    private String nullToStr(String s) {
        return s == null ? "" : s;
    }
}
