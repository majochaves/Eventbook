/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Analisis;

import clases.Autenticacion;
import dao.AnalisisFacade;
import dao.AnalistaFacade;
import entity.Administrador;
import entity.Analisis;
import entity.Analista;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Merli
 */
@WebServlet(name = "ServeltAnalisisListar", urlPatterns = {"/ServeltAnalisisListar"})
public class ServeltAnalisisListar extends HttpServlet {

    @EJB
    private AnalistaFacade analistaFacade;

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
        
        
        if(Autenticacion.tieneRol(request, response, Analista.class)){
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            
            Analista thisAnalista = thisUsuario.getAnalista();

            List<Analisis> listaAnalisis = analisisFacade.getAllAnalisis(thisAnalista);

            request.setAttribute("listaAnalisis", listaAnalisis);


            RequestDispatcher rd = request.getRequestDispatcher("analisisListar.jsp");
            rd.forward(request, response);
        
        } else {
            Autenticacion.error(request, response, "No estas logeado o no posees el rol de Analista");
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
