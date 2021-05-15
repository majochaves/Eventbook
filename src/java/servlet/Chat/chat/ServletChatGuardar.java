/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Chat.chat;

import clases.Autenticacion;
import dao.ChatFacade;
import dao.TeleoperadorFacade;
import entity.Chat;
import entity.ChatPK;
import entity.Usuario;
import java.io.IOException;
import entity.Teleoperador;
import java.util.Date;
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
@WebServlet(name = "ServletChatGuardar", urlPatterns = {"/ServletChatGuardar"})
public class ServletChatGuardar extends HttpServlet {

    @EJB
    private ChatFacade chatFacade;

    @EJB
    private TeleoperadorFacade teleoperadorFacade;

    
    
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
        
        // Usuario envia es el logueado
        Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
        
        // Receptor el teleoperador
        Integer opId = new Integer(request.getParameter("teleoperador"));
        Teleoperador teleoperador = this.teleoperadorFacade.findByUsuarioID(opId);
        
        // Create chat
        Chat chat = new Chat();
        ChatPK chatPK = new ChatPK(teleoperador.getUsuarioId(), thisUsuario.getId());
        chat.setChatPK(chatPK);
        chat.setFecha(new Date());
        chat.setTeleoperador(teleoperador);
        chat.setUsuario(thisUsuario);
        this.chatFacade.create(chat);
        
        // TODO hacer setchatlist con chat actual
//         teleoperador.setChatList(teleoperador.getChatList().add(current)););
        // usuario.setchatlist(usuario.listchat.append)
        
        RequestDispatcher rd = request.getRequestDispatcher("ServletChatListar");
        request.setAttribute("chatID", chat.getChatPK().getTeleoperadorId());
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
