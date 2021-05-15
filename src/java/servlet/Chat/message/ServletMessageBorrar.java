/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Chat.message;

import clases.Autenticacion;
import dao.MensajeFacade;
import entity.Administrador;
import entity.Mensaje;
import entity.Teleoperador;
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
 * @author memoriasIT
 */
@WebServlet(name = "ServletMessageBorrar", urlPatterns = {"/ServletMessageBorrar"})
public class ServletMessageBorrar extends HttpServlet {

        @EJB
    private MensajeFacade mensajeFacade;
    
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
        
        String userID = request.getParameter("userID");
        Integer msgID = new Integer(request.getParameter("msgId"));
        Mensaje msg = this.mensajeFacade.getMessageByID(msgID);
        
        //Solo un teleoperador o un Administrador podrá editar un mensaje
        Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
        if(thisUsuario != null && 
                (Autenticacion.tieneRol(request, response, Administrador.class) || 
                (Autenticacion.tieneRol(request, response, Teleoperador.class)))){

            this.mensajeFacade.remove(msg);

            response.sendRedirect("ServletChatUI?userID=" + userID);
        } else {
            Autenticacion.error(request, response, "No estás logeado, no tienes suficientes permisos o el mensaje no ha sido encontrado.");
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
