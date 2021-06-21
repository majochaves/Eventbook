package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import com.eventbookspring.eventbookspring.service.AutenticacionService;
import com.eventbookspring.eventbookspring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class AutenticacionController {

    private AutenticacionService autenticacionService;

    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    @Autowired
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public void setAutenticacionService(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }



    @GetMapping("/login")
    public String goLogin() {
        return "usuario-iniciar-sesion";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request,
            @RequestParam("usuario") String username, @RequestParam("contrasena") String password) {

//        Usuario u = null;
//
//        List<Usuario> q = em
//                .createNamedQuery("Usuario.findByUsername")
//                .setParameter("username", username)
//                .getResultList();
//
//        if (!q.isEmpty()) {
//            u = q.iterator().next();
//            if (u.getPassword().equals(password)) {
//                // Se autentica al usuario
//                Autenticacion.login(request, u.getDTO());
//
//                return "redirect:/";
//            }
//        }

        try{
            UsuarioDTO thisUsuarioDto = this.autenticacionService.getUsuarioDto(username, password);
            Autenticacion.login(request, thisUsuarioDto);
            return "redirect:/";
        } catch (AutenticacionException ex){
            request.setAttribute("error", ex.getMessage());
            return "usuario-iniciar-sesion";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Autenticacion.logout(request);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String goRegistrar(){
        return "usuario-registrar";
    }

    @PostMapping("/register")
    public String doRegistrar(@RequestParam("usuario") String usuario, @RequestParam("pass") String pass,
                              @RequestParam("pass1") String pass1, @RequestParam("nombre") String nombre,
                              @RequestParam("apellidos") String apellidos, @RequestParam("domicilio") String domicilio,
                              @RequestParam("ciudad") String ciudad, @RequestParam("sexo") String sexo, Model model){


        String error = "";
        Boolean registrado = false;

        List<Usuario> usuarios = this.usuarioRepository.findAll();
        for(Usuario u:usuarios){
            if(u.getUsername().equalsIgnoreCase(usuario)){
                error = "Error, el nombre de usuario ya existe.";
                model.addAttribute("error", error);
                registrado = true;
                return "usuario-registrar";
            }
        }


        if(!pass.equals(pass1)){ // Contraseñas diferentes

            error = "Error, las contraseñas no coinciden.";
            model.addAttribute("error", error);
            return "usuario-registrar";

        } else if(!registrado) {

            UsuarioDTO u = new UsuarioDTO();

            u.setUsername(usuario);
            u.setPassword(pass);
            u.setNombre(nombre);
            u.setApellidos(apellidos);
            u.setDomicilio(domicilio);
            u.setCiudadResidencia(ciudad);
            u.setSexo(sexo);
            u.setFechaCreacion(new Date());

            autenticacionService.guardarUsuarioeventos(u);
        }


        return "redirect:/login";
    }

}
