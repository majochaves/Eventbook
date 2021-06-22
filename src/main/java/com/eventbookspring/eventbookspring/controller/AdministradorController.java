package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.*;
import com.eventbookspring.eventbookspring.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdministradorController {

    private AdministradorService service;

    @Autowired
    public void setService(AdministradorService service) {
        this.service = service;
    }

    @GetMapping("/administracion")
    public String administracion(Model model, HttpSession session) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            model.addAttribute("usuarios", service.listarUsuarios());

            return "usuario-listar";
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }

    @GetMapping("/usuario-crear")
    public String usuarioCrear(Model model, HttpSession session) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            model.addAttribute("usuarioDTO", new UsuarioDTO());

            List<String> sexos = new LinkedList<>();
            sexos.add("hombre");
            sexos.add("mujer");
            model.addAttribute("sexos", sexos);

            return "usuario-crear";
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }

    @GetMapping("/usuario-editar/{id}")
    public String usuarioEditar(Model model, HttpSession session, @PathVariable("id") String id) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            model.addAttribute("usuarioDTO", service.findUsuarioById(Integer.parseInt(id)));

            List<String> sexos = new LinkedList<>();
            sexos.add("hombre");
            sexos.add("mujer");
            model.addAttribute("sexos", sexos);

            return "usuario-crear";
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }

    @GetMapping("/usuario-borrar/{id}")
    public String usuarioBorrar(HttpSession session, Model model, @PathVariable("id") String id) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            return service.borrarUsuario(session, Integer.parseInt(id));
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }

    @PostMapping("/usuario-guardar")
    public String usuarioGuardar(
            Model model,
            HttpSession session,
            @ModelAttribute("dto") UsuarioDTO dto,
            @RequestParam(value = "rol", required = false) String rol
    ) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            boolean edicion = dto != null && dto.getId() != null;
            UsuarioDTO resultado = service.guardarUsuario(dto, rol, edicion);

            if (resultado == null) {
                model.addAttribute("error", "El nombre de usuario '" + dto.getUsername() +  "' ya está en uso");
                model.addAttribute("usuarioDTO", dto);
                List<String> sexos = new LinkedList<>();
                sexos.add("hombre");
                sexos.add("mujer");
                model.addAttribute("sexos", sexos);

                return "usuario-crear";
            }

            return "redirect:administracion";
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }

    @GetMapping("/usuario-filtrar")
    public String usuarioFiltrar(Model model,
                                 HttpSession session,
         @RequestParam(value = "rol", defaultValue = "") List<String> rolesStr,
         @RequestParam(value = "sexo", defaultValue = "") List<String> sexos,
         @RequestParam(defaultValue = "") String username,
         @RequestParam(value = "nombre", defaultValue = "") String nombre,
         @RequestParam(value = "apellidos", defaultValue = "") String apellidos,
         @RequestParam(value = "domicilio", defaultValue = "") String domicilio,
         @RequestParam(value = "ciudad", defaultValue = "") String ciudad
     ) {
        try {
            Autenticacion.tieneRolExcepcion(session, Autenticacion.PERMISOS, Administrador.class);

            // Lista de los DTOs de los usuarios filtrados
            model.addAttribute("usuarios", service.filtrar(rolesStr, sexos, username, nombre, apellidos, domicilio, ciudad));

            return "usuario-listar";
        } catch (AutenticacionException e) {
            return Autenticacion.getErrorJsp(model, e.getMessage());
        }
    }
}
