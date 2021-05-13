/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.ChatFacade;
import dao.MensajeFacade;
import dao.UsuarioFacade;
import entity.Chat;
import entity.Mensaje;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import entity.Usuario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/async-servlet/async-servlets.html
 */
@WebServlet(urlPatterns = {"/ServletChat"}, asyncSupported=true)
public class ServletChat extends HttpServlet {

    @EJB
    private ChatFacade chatFacade;
    
    
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private MensajeFacade mensajeFacade;
    
    private List<AsyncContext> contexts = new LinkedList<>();
    private static final Logger LOG = Logger.getLogger(ServletChat.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10 * 60 * 1000);
        contexts.add(asyncContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();
        ServletContext application = request.getServletContext();
        
        Chat chat;  
        Usuario envia;
        Usuario recibe = new Usuario();
        recibe.setNombre("error");
        
        String message = request.getParameter("message");
        String userTo = request.getParameter("userTo");
        
        String htmlMessage;
        
//         Message contains data
        if (!(message == null || message.contentEquals(""))) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(message);
            
            // Usuario envia es el logueado
            envia = Autenticacion.getUsuarioLogeado(request, response);
            
            // Usuario que recibe se consigue con request XHR
            recibe = this.usuarioFacade.getUserByID(userTo);
            
            // HTML to be appended to the chat
            htmlMessage = "<li><div class='message-data'><span class='message-data-name'><i class='fa fa-circle online'></i>" + recibe.getNombre() + "</span><span class='message-data-time'>"+new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date())+"</span></div><div class='message my-message'>"+ message +"</div></li>";
            
            // Find if chat already exists
//            chat = this.chatFacade.findByTwoUsers(usuario a, usuario b);
//            if(chat == null){
//                
//            }
//            
//            // Compose message from data
//            Mensaje msg = new Message();
//            msg.setFecha(fecha);
//            msg.setContenido(name);
//            msg.setUsuarioEmisorId(0);
//            
//            // Add message to chat
//            chat.getMensajeList().add();

        


            
        } else { // Message had an error
            htmlMessage = "";
        }
        
        
        
        
        System.out.println(htmlMessage);

        if (application.getAttribute("messages") == null) {
            application.setAttribute("messages", htmlMessage);
        } else {
            String currentMessages = (String) application.getAttribute("messages");
            application.setAttribute("messages", htmlMessage + currentMessages);
        }
        
        for (AsyncContext asyncContext : asyncContexts) {
            try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
                // Add message to website
                writer.println(htmlMessage);
                writer.flush();
                asyncContext.complete();
                

            } catch (Exception ex) {
                LOG.severe("Se ha producido la siguiente excepcion: " + ex.getMessage());
            }
        }

        /*
        // Create entity for db
        Mensaje msg = new Mensaje();
//        msg.setChat(chat); TODO: Add chat rooms
        msg.setContenido(message);
        msg.setFecha(new Date());
//        msg.setUsuarioEmisorId(usuario); TODO : GET USUARIO
        
        // Save to db
        mensajeFacade.create(msg);
*/
        
        

    }
}

