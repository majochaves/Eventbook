/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.MensajeFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioFacade;
import entity.Mensaje;
import entity.Teleoperador;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author memoriasIT
 */
@WebServlet(name = "ServletChatUI", urlPatterns = {"/ServletChatUI"})
public class ServletChatUI extends HttpServlet {

    @EJB
    private MensajeFacade mensajeFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TeleoperadorFacade teleoperadorFacade;

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Teleoperador> teleoperadores = this.teleoperadorFacade.findAll();
        request.setAttribute("teleoperadores", teleoperadores);
        
        String userID = request.getParameter("userID");
        Usuario user = this.usuarioFacade.getUserByID(userID);
        request.setAttribute("usuarioChat", user);
        
        Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
        
        List<Pair<Integer, Mensaje>> mensajes = this.mensajeFacade.getMapOfMensajesByIDs(new Integer(userID), thisUsuario.getId());
        request.setAttribute("mensajesHistorial", mensajes);
        
        RequestDispatcher rd = request.getRequestDispatcher("chat.jsp");
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
