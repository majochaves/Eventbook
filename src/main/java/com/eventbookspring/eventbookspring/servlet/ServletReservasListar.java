/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.EventoFacade;
import dao.ReservaFacade;
import dao.UsuarioeventosFacade;
import entity.Evento;
import entity.Usuario;
import entity.Usuarioeventos;
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
 * @author fcode
 */
@WebServlet(name = "ServletReservasListar", urlPatterns = {"/ServletReservasListar"})
public class ServletReservasListar extends HttpServlet {

    @EJB
    private EventoFacade eventoFacade;

    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;

    @EJB
    private ReservaFacade reservaFacade;

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
        String nav = "reserva";
        List<Evento> lista = null;
        String fechaFiltrado = request.getParameter("fechaFiltrado");
        Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");
        Usuarioeventos uE = this.usuarioeventosFacade.findByID(loggedUser.getId());

        if(fechaFiltrado == null){
            lista = this.eventoFacade.findEventosReservadosBy(uE.getUsuarioId());
        } else if (fechaFiltrado.equalsIgnoreCase("reciente")){
            lista = this.eventoFacade.findEventosReservadosByMostRecent(uE.getUsuarioId());
            request.setAttribute("filtro", fechaFiltrado);
        } else if (fechaFiltrado.equalsIgnoreCase("noReciente")){
            lista = this.eventoFacade.findEventosReservadosByLeastRecent(uE.getUsuarioId());
            request.setAttribute("filtro", fechaFiltrado);
        } else {
            lista = this.eventoFacade.findEventosReservadosBy(uE.getUsuarioId());
        }
        
        request.setAttribute("nav", nav);
        request.setAttribute("eventos", lista);
        
        RequestDispatcher rd = request.getRequestDispatcher("reservas-listar.jsp");
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
