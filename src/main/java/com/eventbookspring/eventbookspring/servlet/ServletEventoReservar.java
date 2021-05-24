
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.EventoFacade;
import dao.ReservaFacade;
import dao.UsuarioeventosFacade;
import entity.Evento;
import entity.Reserva;
import entity.ReservaPK;
import entity.Usuario;
import entity.Usuarioeventos;
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
    private UsuarioeventosFacade usuarioeventosFacade;

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
        String editar = (String) request.getParameter("editar");
        if(editar == null) editar = "";
        Integer id = new Integer(request.getParameter("id"));
        Integer numAsientos = new Integer(request.getParameter("numEntradas"));
        Usuario user = Autenticacion.getUsuarioLogeado(request, response);
        Usuarioeventos uE = this.usuarioeventosFacade.findByID(user.getId());
        Evento e = this.eventoFacade.find(id);
        int entradasReservadas = e.getEntradasReservadas(user);
        
        
        if(editar.equalsIgnoreCase("editar") && numAsientos == 0){
            request.setAttribute("id", id);
             RequestDispatcher rd = request.getRequestDispatcher("ServletReservaBorrar");
            rd.forward(request, response);
        } else if(editar.equalsIgnoreCase("editar") && numAsientos > e.getMaxEntradas()){
            int entradasPosibles = e.getMaxEntradas()-entradasReservadas;
            String error = "Error: Ya ha reservado "+entradasReservadas+" solo podría reservar "+entradasPosibles+" entradas más.";
            request.setAttribute("error", error);
            request.setAttribute("editar", editar);
            request.setAttribute("numEntradas", entradasReservadas);
            request.setAttribute("evento", e);
            request.setAttribute("reservas", e.getReservaList());
            RequestDispatcher rd = request.getRequestDispatcher("evento_reservar.jsp");
            rd.forward(request, response);
        } else if(numAsientos > e.getMaxEntradas() || entradasReservadas > e.getMaxEntradas()){
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
                List<Reserva> listaReservas = e.getReservaList();
                Integer numEntradas = this.reservaFacade.findNumEntradas(user.getUsuarioeventos().getUsuarioId(), id);
                if(editar.equalsIgnoreCase("editar") && numAsientos - numEntradas < 0){
                    int entradasEliminar = numEntradas - numAsientos;
                    int entradas = listaReservas.size();
                    for(int i = 1; i <= entradasEliminar; i++){
                        Reserva aux = listaReservas.get(entradas - i);
                        e.getReservaList().remove(aux);
                        this.reservaFacade.remove(aux);
                    }
                    this.eventoFacade.edit(e);
                } else {
                    numAsientos = numAsientos-entradasReservadas;
                    for(int i = 0; i < numAsientos; i++){
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
                }
                response.sendRedirect("ServletReservasListar");
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
