/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.AdministradorFacade;
import dao.AnalistaFacade;
import dao.CreadoreventosFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioFacade;
import dao.UsuarioeventosFacade;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ServletUsuarioBorrar", urlPatterns = {"/ServletUsuarioBorrar"})
public class ServletUsuarioBorrar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    UsuarioFacade usuarioFacade;
    
    @EJB
    private AdministradorFacade administradorFacade;
    
    @EJB
    private CreadoreventosFacade creadorEventosFacade;
    
    @EJB
    private TeleoperadorFacade teleoperadorFacade;
    
    @EJB
    private AnalistaFacade analistaFacade;
    
    @EJB
    private UsuarioeventosFacade usuarioEventosFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        Usuario usuario = usuarioFacade.find(Integer.parseInt(id));
        
        /*
        if (usuario.getAdministrador() != null) {
            administradorFacade.remove(usuario.getAdministrador());
        } else if (usuario.getAnalista()!= null) {
            analistaFacade.remove(usuario.getAnalista());
        } else if (usuario.getTeleoperador()!= null) {
            teleoperadorFacade.remove(usuario.getTeleoperador());
        } else if (usuario.getUsuarioeventos() != null) {
            usuarioEventosFacade.remove(usuario.getUsuarioeventos());
        } else if (usuario.getCreadoreventos()!= null) {
            creadorEventosFacade.remove(usuario.getCreadoreventos());
        }
        */
        usuarioFacade.remove(usuario);
        
        response.sendRedirect("ServletUsuarioListar");
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
