/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.clases;

import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.sun.istack.NotNull;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josie
 */
public class Autenticacion {
    
    public static UsuarioDTO getUsuarioLogeado(HttpServletRequest request, HttpServletResponse response) {
        return (UsuarioDTO) request.getSession().getAttribute("logged-user");
    }



    public static void login(HttpServletRequest request, UsuarioDTO u) {
        request.getSession().setAttribute("logged-user", u);
    }

    public static void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("logged-user");
    }
    
    public static final String PERMISOS = "Parece que no dispones de los permisos necesarios para acceder a esta página.";
    
    public static boolean estaLogeado(HttpServletRequest request, HttpServletResponse response) {
        return getUsuarioLogeado(request, response) != null;
    }

    public static boolean tieneRol(UsuarioDTO u, Class... roles) {
        
        boolean resultado = false;
        List<Class> rolesList = Arrays.asList(roles);
        
        if (u.getAdministrador() != null && rolesList.contains(Administrador.class))
            resultado = true;
        else if (u.getAnalista() != null && rolesList.contains(Analista.class))
            resultado = true;
        else if (u.getCreadoreventos() != null && rolesList.contains(Creadoreventos.class))
            resultado = true;
        else if (u.getTeleoperador() != null && rolesList.contains(Teleoperador.class))
            resultado = true;
        else if (u.getUsuarioeventos() != null && rolesList.contains(Usuarioeventos.class))
            resultado = true;
        else if (rolesList.contains(Usuario.class))
            resultado = true;
        
        return resultado;
    }
        
    public static boolean tieneRol(HttpServletRequest request, HttpServletResponse response, Class... roles) {
        boolean resultado = false;
        List<Class> rolesList = Arrays.asList(roles);
        UsuarioDTO loggedUser = (UsuarioDTO) request.getSession().getAttribute("logged-user");

        return estaLogeado(request, response) && tieneRol(loggedUser, roles);
    }

    public static void autenticar(HttpServletRequest request, HttpServletResponse response, String errorMsg, Class... roles)
            throws ServletException, IOException {
        
        if (!tieneRol(request, response, roles)) error(request, response, errorMsg);
    }
    
    public static void error(HttpServletRequest request, HttpServletResponse response, String errorMsg)
            throws ServletException, IOException {
        
        request.setAttribute("error", errorMsg);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }


    //HACIENDO USO SOLO DE SESSION
    public static UsuarioDTO getUsuarioLogeado(HttpSession session) {
        return (UsuarioDTO) session.getAttribute("logged-user");
    }

    public static boolean tieneRol(HttpSession session, Class... roles){

        boolean resultado = false;
        List<Class> rolesList = Arrays.asList(roles);
        UsuarioDTO loggedUser = getUsuarioLogeado(session);

        return loggedUser!=null && tieneRol(loggedUser, roles);
    }

    public static String getErrorJsp(Model model, String errorMsg){
        model.addAttribute("error", errorMsg);
        return "error";
    }
}
