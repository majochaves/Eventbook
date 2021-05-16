/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Analisis;

import clases.Autenticacion;
import dao.AnalisisFacade;
import entity.Administrador;
import entity.Analisis;
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
 * @author Merli
 */
@WebServlet(name = "ServeltAnalisisBorrar", urlPatterns = {"/ServeltAnalisisBorrar"})
public class ServeltAnalisisBorrar extends HttpServlet {

    @EJB
    private AnalisisFacade analisisFacade;

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
        
        Integer idAnalisis = null;
        
        try{
            idAnalisis = Integer.parseInt(request.getParameter("id"));
        } catch(Exception ex){
            
        }
        
        
        if(idAnalisis != null){
            Analisis thisAnalisis = analisisFacade.find(idAnalisis);
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            
            if(thisAnalisis != null && thisUsuario != null && 
                    (thisAnalisis.getAnalistaUsuarioId().getUsuarioId().equals(thisUsuario.getId()) || 
                    Autenticacion.tieneRol(request, response, Administrador.class))){
            
                analisisFacade.remove(thisAnalisis);

                response.sendRedirect("ServeltAnalisisListar");
            } else {
                Autenticacion.error(request, response, "No estás logeado, no tienes suficientes permisos o el análisis no ha sido encontrado.");
            }
 
            
        }
        
        
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
