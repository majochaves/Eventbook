/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Chat.chat;

import clases.Autenticacion;
import dao.ChatFacade;
import java.io.IOException;
import entity.Chat;
import entity.Usuario;
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
@WebServlet(name = "ServletChatBorrar", urlPatterns = {"/ServletChatBorrar"})
public class ServletChatBorrar extends HttpServlet {

    @EJB
    private ChatFacade chatFacade;

    
    
    
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
        
        
         try {
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            if (thisUsuario == null){
                request.setAttribute("error", "¿Has iniciado sesión?");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        
        
       
            Integer userID = new Integer(request.getParameter("userID"));
            Integer opID = new Integer(request.getParameter("opID"));

            Chat chat = this.chatFacade.findByChatPK(userID, opID);
            this.chatFacade.remove(chat);

            response.sendRedirect("ServletChatListar");
            
            
        } catch (Exception e) {
            request.setAttribute("error", "Hemos tenido un problema...");
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
