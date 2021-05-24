/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.UsuarioFacade;
import entity.Administrador;
import entity.Analista;
import entity.Creadoreventos;
import entity.Teleoperador;
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josie
 */
@WebServlet(name = "ServletUsuarioFiltrar", urlPatterns = {"/ServletUsuarioFiltrar"})
public class ServletUsuarioFiltrar extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> query = usuarioFacade.findAll();
        List<String> rolesStr = request.getParameterValues("rol") == null ? new LinkedList<>() : Arrays.asList(request.getParameterValues("rol"));
        List<String> sexos = request.getParameterValues("sexo") == null ? new LinkedList<>() : Arrays.asList(request.getParameterValues("sexo"));
        
        String username = request.getParameter("username");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String domicilio = request.getParameter("domicilio");
        String ciudad = request.getParameter("ciudad");
        
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
                        } catch (ServletException ex) {
                            Logger.getLogger(ServletUsuarioFiltrar.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ServletUsuarioFiltrar.class.getName()).log(Level.SEVERE, null, ex);
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
                
        request.setAttribute("usuarios", query);
        request.getRequestDispatcher("usuario-listar.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
