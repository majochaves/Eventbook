/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.UsuarioFacade;
import dao.UsuarioeventosFacade;
import entity.Creadoreventos;
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fcode
 */
@WebServlet(name = "ServletRegistrarUsuario", urlPatterns = {"/ServletRegistrarUsuario"})
public class ServletRegistrarUsuario extends HttpServlet {

    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String pass1 = request.getParameter("pass1");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String domicilio = request.getParameter("domicilio");
        String ciudad = request.getParameter("ciudad");
        String sexo = request.getParameter("sexo");
        
        String error = "";
        String url = "usuario-registrar.jsp";
        
        for(Usuario u: this.usuarioFacade.findAll()){
            if(u.getUsername().equalsIgnoreCase(usuario)){
                error = "El nombre de usuario "+usuario+" ya está en uso.";
                request.setAttribute("error", error);
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
        
        
        if(usuario == null || usuario.isEmpty() || pass.isEmpty() || pass == null
                || pass1 == null || pass1.isEmpty()){
            error = "Error, campos vacios.";
            request.setAttribute("error", error);
        } else if(!pass.equals(pass1)){
            error = "Error, las contraseñas no coinciden.";
            request.setAttribute("error", error);
        } else {
            
            Usuario u = new Usuario();
            u.setUsername(usuario);
            u.setPassword(pass);
            u.setNombre(nombre);
            u.setApellidos(apellidos);
            u.setDomicilio(domicilio);
            u.setCiudadResidencia(ciudad);
            u.setSexo(sexo);
            u.setFechaCreacion(new Date());
            
            this.usuarioFacade.create(u);
            
            Usuarioeventos uE = new Usuarioeventos(u.getId());
            u.setUsuarioeventos(uE);
            uE.setUsuario(u);
            this.usuarioeventosFacade.create(uE);
            
            this.usuarioFacade.edit(u);
            
        }
        
        request.getRequestDispatcher("usuario-iniciar-sesion.jsp").forward(request, response);
        
        
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
