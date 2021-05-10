/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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
@WebServlet(name = "ServletAnalisisEditar", urlPatterns = {"/ServletAnalisisEditar"})
public class ServletAnalisisEditar extends HttpServlet {

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
        
        String descripcion = null;
        Integer idAnalisis = null;
        
        try{
            descripcion = request.getParameter("descripcion");
            idAnalisis = Integer.parseInt(request.getParameter("id"));
        }catch(Exception ex){
            Autenticacion.error(request, response, "ID del Análisis escrito incorrectamente");
        }
        
        
        if(descripcion!=null && idAnalisis!=null){
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            Analisis thisAnalisis = analisisFacade.find(idAnalisis);
            
            
            //Solo el dueño (Analista) o un Administrador podrá editar un analsis
            if(thisUsuario != null && thisAnalisis != null && 
                    (Autenticacion.tieneRol(request, response, Administrador.class) || 
                    thisAnalisis.getAnalistaUsuarioId().getUsuarioId().equals(thisUsuario.getId()))){
                
                
                thisAnalisis.setDescripcion(descripcion);
                analisisFacade.edit(thisAnalisis);

                response.sendRedirect("ServeltAnalisisVer?id=" + idAnalisis);
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
