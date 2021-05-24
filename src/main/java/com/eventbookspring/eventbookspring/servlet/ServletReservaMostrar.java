/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.EventoFacade;
import dao.ReservaFacade;
import dao.UsuarioFacade;
import dao.UsuarioeventosFacade;
import entity.Evento;
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fcode
 */
@WebServlet(name = "ServletReservaMostrar", urlPatterns = {"/ServletReservaMostrar"})
public class ServletReservaMostrar extends HttpServlet {

    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;

    @EJB
    private ReservaFacade reservaFacade;

    @EJB
    private EventoFacade eventoFacade;

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
        
        Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");
        Usuarioeventos uE = this.usuarioeventosFacade.findByID(loggedUser.getId());
        
        Integer id = new Integer(request.getParameter("id"));
        Evento evento = this.eventoFacade.find(id);
        Integer numEntradas = this.reservaFacade.findNumEntradas(uE.getUsuarioId(), id);
        String url = "evento_reservar.jsp";
        
        request.setAttribute("numEntradas", numEntradas);
        request.setAttribute("editar", "editar");
        request.setAttribute("evento", evento);
        
        
        
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
        
        
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
