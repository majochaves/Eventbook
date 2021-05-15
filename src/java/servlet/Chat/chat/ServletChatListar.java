/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Chat.chat;

import clases.Autenticacion;
import dao.ChatFacade;
import dao.UsuarioFacade;
import entity.Usuario;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josie
 */
@WebServlet(name = "ServletChatListar", urlPatterns = {"/ServletChatListar"})
public class ServletChatListar extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private ChatFacade chatFacade;

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            if (thisUsuario == null){
                request.setAttribute("error", "¿Has iniciado sesión?");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

            request.setAttribute("allMessages", "Modo Usuario: mostrando tus chats");
            request.setAttribute("chats", chatFacade.findChatsByUserID(thisUsuario.getId()));
            request.getRequestDispatcher("chat-listar.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Hemos encontrado un error");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
