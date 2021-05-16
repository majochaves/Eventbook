
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
import entity.ReservaPK;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "ServletEventoReservar", urlPatterns = {"/ServletEventoReservar"})
public class ServletEventoReservar extends HttpServlet {

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
        
        Integer id = new Integer(request.getParameter("id"));
        Integer numAsientos = new Integer(request.getParameter("numEntradas"));
        
        Usuario user = Autenticacion.getUsuarioLogeado(request, response);
        Evento e = this.eventoFacade.find(id);
        int entradasReservadas = e.getEntradasReservadas(user);
        if(numAsientos > e.getMaxEntradas() || entradasReservadas >= e.getMaxEntradas()){
            String error = "Error: Sólo se pueden reservar " + e.getMaxEntradas() + " por usuario.";
            request.setAttribute("error", error);
            request.setAttribute("evento", e);
            request.setAttribute("reservas", e.getReservaList());
            RequestDispatcher rd = request.getRequestDispatcher("evento_reservar.jsp");
            rd.forward(request, response);
        } else if(e.asientosDisponibles() < numAsientos){
            String error = "Error: Sólo hay " + e.asientosDisponibles() + " asientos disponibles. ";
            request.setAttribute("error", error);
            request.setAttribute("evento", e);
            request.setAttribute("reservas", e.getReservaList());
            RequestDispatcher rd = request.getRequestDispatcher("evento_reservar.jsp");
            rd.forward(request, response);
        }else{
            if(e.getAsientosFijos() == 's'){
                int[][] matrizAsientos = e.matrizAsientos();
                request.setAttribute("matrizAsientos", matrizAsientos);
                request.setAttribute("evento", e);
                request.setAttribute("numAsientos", numAsientos);
                request.setAttribute("usuario", user);
                RequestDispatcher rd = request.getRequestDispatcher("seleccionar_asientos.jsp");
                rd.forward(request, response);
            }else{
                for(int i = 0; i < numAsientos; i++){
                    List<Reserva> listaReservas = e.getReservaList();
                    int numAsiento = 0;
                    if(listaReservas != null){
                        numAsiento = listaReservas.size() + 1;
                    }
                    Reserva reserva = new Reserva(1, numAsiento, e.getId());
                    reserva.setFecha(new Date());
                    reserva.setUsuarioeventosId(user.getUsuarioeventos());
                    this.reservaFacade.create(reserva);
                    e.getReservaList().add(reserva);
                    this.eventoFacade.edit(e);
                }
                response.sendRedirect("ServletEventoListar");
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
