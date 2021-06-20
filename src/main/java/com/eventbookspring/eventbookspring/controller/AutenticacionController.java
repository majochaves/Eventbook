package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AutenticacionController {

    @Autowired
    private EntityManager em;

    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String goLogin() {
        return "usuario-iniciar-sesion";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request,
            @RequestParam("usuario") String username, @RequestParam("contrasena") String password) {

        Usuario u = null;

        List<Usuario> q = em
                .createNamedQuery("Usuario.findByUsername")
                .setParameter("username", username)
                .getResultList();

        if (!q.isEmpty()) {
            u = q.iterator().next();
            if (u.getPassword().equals(password)) {
                // Se autentica al usuario
                Autenticacion.login(request, u.getDTO());

                return "redirect:/";
            }
        }
        request.setAttribute("error", "El usuario o contraseña proporcionados son incorrectos");

        return "usuario-iniciar-sesion";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Autenticacion.logout(request);

        return "redirect:/";
    }
}
