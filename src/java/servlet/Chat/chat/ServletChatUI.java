/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Chat.chat;

import clases.Autenticacion;
import dao.ChatFacade;
import dao.MensajeFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioFacade;
import entity.Chat;
import entity.Mensaje;
import entity.Teleoperador;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    
    @EJB
    private ChatFacade chatFacade;

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Usuario autenticado?
        Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
        if (thisUsuario == null){
            request.setAttribute("error", "¿Has iniciado sesión?");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
       
        // Get teleoperadores of chats we have
//        List<Teleoperador> teleoperadores = new ArrayList();
//        for (Chat chat : chatFacade.findChatsByUserID(thisUsuario.getId())){
//            teleoperadores.add(chat.getTeleoperador());
//        }
//        request.setAttribute("teleoperadores", teleoperadores);
        request.setAttribute("chats", chatFacade.findChatsByUserID(thisUsuario.getId()));
        
        String userID = request.getParameter("userID");
        Usuario user = this.usuarioFacade.getUserByID(userID);
        request.setAttribute("usuarioChat", user);
       
        
        List<Pair<Integer, Mensaje>> mensajes = this.mensajeFacade.getListOfMensajesByIDs(new Integer(userID), thisUsuario.getId());
        System.out.println(mensajes);
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
