/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.clases;

import com.eventbookspring.eventbookspring.entity.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josie
 */
public class Autenticacion {
    
    public static Usuario getUsuarioLogeado(HttpServletRequest request, HttpServletResponse response) {
        return (Usuario) request.getSession().getAttribute("logged-user");
    }

    public static void login(HttpServletRequest request, Usuario u) {
        request.getSession().setAttribute("logged-user", u);
    }

    public static void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("logged-user");
    }
    
    public static final String PERMISOS = "Parece que no dispones de los permisos necesarios para acceder a esta página.";
    
    public static boolean estaLogeado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return getUsuarioLogeado(request, response) != null;
    }
    
            
    public static boolean tieneRol(Usuario u, Class... roles) 
            throws ServletException, IOException {
        
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
        
    public static boolean tieneRol(HttpServletRequest request, HttpServletResponse response, Class... roles) 
            throws ServletException, IOException {
        
        boolean resultado = false;
        List<Class> rolesList = Arrays.asList(roles);
        Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");

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
}
