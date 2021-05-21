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
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fcode
 */
@WebServlet(name = "ServletReservaBorrar", urlPatterns = {"/ServletReservaBorrar"})
public class ServletReservaBorrar extends HttpServlet {

    @EJB
    private ReservaFacade reservaFacade;

    @EJB
    private EventoFacade eventoFacade;

    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;

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
        
        Integer idEvento = new Integer(request.getParameter("id"));
        Usuario user = Autenticacion.getUsuarioLogeado(request, response);
        Usuarioeventos uE = this.usuarioeventosFacade.findByID(user.getId());
        Evento e = this.eventoFacade.find(idEvento);
        List<Reserva> listaReservas = this.reservaFacade.findReservasUser(uE.getUsuarioId(), idEvento);
        
        if(listaReservas == null){
           
        } else {
            for(int i = 0; i < listaReservas.size(); i++){
                e.getReservaList().remove(listaReservas.get(i));
                this.reservaFacade.remove(listaReservas.get(i));
            }
            this.eventoFacade.edit(e);
        }
        
        response.sendRedirect("index.jsp");
        
        
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
