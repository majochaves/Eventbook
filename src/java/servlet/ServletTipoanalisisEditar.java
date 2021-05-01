/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.CampoanalisisFacade;
import dao.TipoanalisisFacade;
import entity.Campoanalisis;
import entity.CampoanalisisPK;
import entity.Tipoanalisis;
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
@WebServlet(name = "ServletTipoanalisisEditar", urlPatterns = {"/ServletTipoanalisisEditar"})
public class ServletTipoanalisisEditar extends HttpServlet {

    @EJB
    private TipoanalisisFacade tipoanalisisFacade;

    @EJB
    private CampoanalisisFacade campoanalisisFacade;
    
    
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
        
        Integer idTipoAnalisis = Integer.parseInt(request.getParameter("id"));
        
        Tipoanalisis thisTipoanalisis = tipoanalisisFacade.find(idTipoAnalisis);
        
        int numFilas = thisTipoanalisis.getCampoanalisisList().size();
        
        for(int i=1; i<=numFilas; i++){
            //El nombre esta en la columna 1
            String nombre = request.getParameter("fila" + i + "col" + 1);
            CampoanalisisPK campoAnalisisPk = new CampoanalisisPK(nombre, idTipoAnalisis);
            Campoanalisis thisCampoAnalisis = campoanalisisFacade.find(campoAnalisisPk);
            
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
