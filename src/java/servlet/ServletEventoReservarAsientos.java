/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.EventoFacade;
import dao.ReservaFacade;
import entity.Evento;
import entity.Reserva;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author majochaves
 */
@WebServlet(name = "ServletEventoReservarAsientos", urlPatterns = {"/ServletEventoReservarAsientos"})
public class ServletEventoReservarAsientos extends HttpServlet {
    
    @EJB
    private EventoFacade eventoFacade;
    
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
        Integer id = new Integer(request.getParameter("eventoId"));
        Integer numAsientos = new Integer(request.getParameter("numAsientos"));
        String[] asientosSeleccionados = request.getParameterValues("asientosSeleccionados");
        
        Usuario user = Autenticacion.getUsuarioLogeado(request, response);
        Evento e = this.eventoFacade.find(id);
        
        for(int i = 0; i < numAsientos; i++){
            String filaAsiento = asientosSeleccionados[i];
            String fila = filaAsiento.substring( 0, filaAsiento.indexOf(":"));
            String asiento = filaAsiento.substring(filaAsiento.indexOf(":")+1, filaAsiento.length());
            Reserva reserva = new Reserva(new Integer(fila), new Integer(asiento), e.getId());
            reserva.setFecha(new Date());
            reserva.setUsuarioeventosId(user.getUsuarioeventos());
            this.reservaFacade.create(reserva);
            e.getReservaList().add(reserva);
            this.eventoFacade.edit(e);
        }
        response.sendRedirect("ServletEventoListar");
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