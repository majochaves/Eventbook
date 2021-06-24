package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.dto.MensajeDTO;
import com.eventbookspring.eventbookspring.dto.TeleoperadorDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.service.ChatService;
import com.eventbookspring.eventbookspring.service.MensajeService;
import com.eventbookspring.eventbookspring.service.TeleoperadorService;
import com.eventbookspring.eventbookspring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chat/")
public class ChatController {
    private ChatService chatService;
    private TeleoperadorService teleoperadorService;
    private UsuarioService usuarioService;
    private MensajeService mensajeService;


    @Autowired
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Autowired
    public void setTeleoperadorService(TeleoperadorService teleoperadorService) {
        this.teleoperadorService = teleoperadorService;
    }

    /*
             __            ___
            /  ` |__|  /\   |
            \__, |  | /~~\  |
            - Load chat from db -
     */
    @GetMapping("/{userID}/{user2ID}")
    public String chatUI(Model model, HttpSession session, @PathVariable("userID") Integer userID, @PathVariable("user2ID") Integer user2ID){
        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Coger lista de todos los chats (para mostrar filtro y búsqueda sobre ellos)
            List<Chat> chatList = this.chatService.findChatsByUserID(usuarioDTO.getId());
            model.addAttribute("chats", chatList);

            // Anyadir usuarios al modelo
            model.addAttribute("usuarioChat", this.usuarioService.findUsuarioByID(userID));
            model.addAttribute("usuarioChat2", this.usuarioService.findUsuarioByID(user2ID));

            // Coger lista de mensajes entre estos usuarios
            List<Par<Integer, Mensaje>> mensajes = this.mensajeService.getListOfMensajesByIDs(userID, user2ID);
            model.addAttribute("mensajesHistorial", mensajes);

            return "chat";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }



    /*
            __   __   ___      ___  ___
           /  ` |__) |__   /\   |  |__
           \__, |  \ |___ /~~\  |  |___
    */
    @GetMapping("/crear")
    public String crearChat(Model model, HttpSession session){
        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Coger lista de todos los operadores/admin
            List<TeleoperadorDTO> teleoperadorDTOList = this.teleoperadorService.findAll();

            // Si es privilegiado eliminar de la lista para evitar chats consigo mismo
            if(Autenticacion.tieneRol(session, Teleoperador.class) || Autenticacion.tieneRol(session, Administrador.class)){
                teleoperadorDTOList.remove(this.teleoperadorService.findByUsuarioId(usuarioDTO.getId()));
            }

            // Anyadir list al modelo
            model.addAttribute("listaOperadores", teleoperadorDTOList);

            return "chat-crear";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

    @PostMapping("/crear/guardar")
    public String guardarChat(Model model, HttpSession session, @RequestParam("teleoperador") Integer opId){
        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Guardar chat
            this.chatService.guardarChat(usuarioDTO.getId(), opId);

            return "redirect:/chat/";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

/*
        __   ___       __
       |__) |__   /\  |  \
       |  \ |___ /~~\ |__/
*/

    @GetMapping("/")
    public String listar(Model model, HttpSession session){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Mostrar vista con privilegios si los tiene
            if(Autenticacion.tieneRol(session, Teleoperador.class) || Autenticacion.tieneRol(session, Administrador.class)){
                model.addAttribute("allMessages", "Modo Usuario: mostrando tus chats - <a href='/chat/teleoperador'>Modo Operador</a>");
            } else {
                model.addAttribute("allMessages", "Modo Usuario: mostrando tus chats.");
            }

            model.addAttribute("chats", this.chatService.findChatsByUserID(usuarioDTO.getId()));

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

    @GetMapping("/teleoperador")
    public String listarTeleoperador(Model model, HttpSession session){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Mostrar vista con privilegios si los tiene
            if(Autenticacion.tieneRol(session, Teleoperador.class) || Autenticacion.tieneRol(session, Administrador.class)){
                model.addAttribute("allMessages", "Modo Teleoperador: mostrando todos los chats - <a href='/chat/'>Modo usuario</a>");
            } else { // Solo los teleoperadores tienen permisos
                throw new AutenticacionException("Solo los teleoperadores pueden ver esta página.");
            }

            // Anyadir lista de chats al modelo
            model.addAttribute("chats", this.chatService.findAll());

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }


    @PostMapping("/")
    public String listarConFiltro(Model model, HttpServletRequest request, HttpServletResponse response){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(request, response);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            String aBuscar = request.getParameter("aBuscar");
            String username = request.getParameter("username");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");

            List<Chat> chatsFiltrados = this.chatService.findChatsByUserID(usuarioDTO.getId());
            chatsFiltrados = FiltrarChats(aBuscar, username, nombre, apellidos, chatsFiltrados);

            // Mostrar vista con privilegios si los tiene
            if(Autenticacion.tieneRol(request, response, Teleoperador.class) || Autenticacion.tieneRol(request, response, Administrador.class)){
                model.addAttribute("allMessages", "Modo Teleoperador: mostrando todos los chats - <a href='/chat/'>Modo usuario</a>");
            } else { // Solo los teleoperadores tienen permisos
                throw new AutenticacionException("Solo los teleoperadores pueden ver esta página.");
            }

            request.setAttribute("chats", chatsFiltrados);

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }


    @PostMapping("/teleoperador")
    public String listarConFiltroTeleoperador(Model model, HttpServletRequest request, HttpServletResponse response){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(request, response);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            String aBuscar = request.getParameter("aBuscar");
            String username = request.getParameter("username");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");

            List<Chat> chatsFiltrados = this.chatService.findAll();
            chatsFiltrados = FiltrarChats(aBuscar, username, nombre, apellidos, chatsFiltrados);

            // Mostrar vista con privilegios si los tiene
            if(Autenticacion.tieneRol(request, response, Teleoperador.class) || Autenticacion.tieneRol(request, response, Administrador.class)){
                model.addAttribute("allMessages", "Modo Teleoperador: mostrando todos los chats - <a href='/chat/'>Modo usuario</a>");
            } else { // Solo los teleoperadores tienen permisos
                throw new AutenticacionException("Solo los teleoperadores pueden ver esta página.");
            }

            request.setAttribute("chats", chatsFiltrados);

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }


    private List<Chat> FiltrarChats(String aBuscar, String username, String nombre, String apellidos, List<Chat> chatsFiltrados) {
        if (aBuscar.equals("Teleoperador")){
            try{
                // FILTRO NOMBRE DE USUARIO
                chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getTeleoperador().getUsuario().getUsername().toLowerCase().contains(username.toLowerCase())).collect(Collectors.toList());

                // FILTRO NOMBRE
                chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getTeleoperador().getUsuario().getNombre().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());

                // FILTRO APELLIDOS
                chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getTeleoperador().getUsuario().getApellidos().toLowerCase().contains(apellidos.toLowerCase())).collect(Collectors.toList());
            } catch (Exception e){ }
        } else {
            // FILTRO NOMBRE DE USUARIO
            chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getUsuario().getUsername().toLowerCase().contains(username.toLowerCase())).collect(Collectors.toList());

            // FILTRO NOMBRE
            chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getUsuario().getNombre().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());

            // FILTRO APELLIDOS
            chatsFiltrados = chatsFiltrados.stream().filter(u -> u.getUsuario().getApellidos().toLowerCase().contains(apellidos.toLowerCase())).collect(Collectors.toList());

        }
        return chatsFiltrados;
    }


    /*
            __   ___       ___ ___  ___
           |  \ |__  |    |__   |  |__
           |__/ |___ |___ |___  |  |___
    */
    @GetMapping("/borrar/{userID}/{opID}")
    public String borrarChat(Model model, HttpSession session, @PathVariable("userID") Integer userID, @PathVariable("opID") Integer opID){
        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            this.chatService.borrarChat(userID, opID);

            return "redirect:/chat/";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }


    /*
              __            __      __            ___
         /\  /__` \ / |\ | /  `    /  ` |__|  /\   |
        /~~\ .__/  |  | \| \__,    \__, |  | /~~\  |
        - Async Chat -
     */

    // AsyncContext for message in real time
    private List<AsyncContext> contexts = new LinkedList<>();
    @GetMapping("/getMsg/{userID}/{user2ID}")
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10 * 60 * 1000);
        contexts.add(asyncContext);
    }

    @PostMapping("/sendMsg/{userID}/{user2ID}")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();
        ServletContext application = request.getServletContext();

        Chat chat;
        UsuarioDTO envia;
        UsuarioDTO recibe = new UsuarioDTO();
        recibe.setNombre("error");

        String message = request.getParameter("message").trim();
        String userTo = request.getParameter("userTo");

        String htmlMessage;

        // Message contains data
        if (!(message == null || message.contentEquals(""))) {

            // Current time
            Date currentTime = new Date();

            // Usuario envia es el logueado
            envia = Autenticacion.getUsuarioLogeado(request, response);

            // Usuario que recibe se consigue con request XHR
//            TODO antes era esto, quizás posible error - recibe = this.usuarioService.getUserByID(userTo);
            recibe = this.usuarioService.findUsuarioByID(Integer.valueOf(userTo));

            // Find if chat already exists
            chat = null;
            try {
                chat = this.chatService.findByChatPK(envia.getId(), recibe.getId());
                if(chat == null){ // El chat no existe
                    throw(new Exception());
                }
            } catch (Exception e) {
                throw new ServletException(e.getMessage());
            }


            // Compose message from data
            MensajeDTO msg = this.mensajeService.addMessage(currentTime, message, envia.getId(), chat);


            int idUserTo;
            if (msg.getChat().getTeleoperador().getUsuarioId() == msg.getUsuarioEmisorId()){
                idUserTo = msg.getChat().getUsuario().getId();
            } else {
                idUserTo = msg.getChat().getTeleoperador().getUsuarioId();
            }


            // HTML to be appended to the chat
            htmlMessage = "<li id=\""+ msg.getId() +"\" useridTo=\""+ idUserTo +"\" userid=\""+ msg.getUsuarioEmisorId()+"\"><div class='message-data'><span class='message-data-name'><i class='fa fa-circle online'></i>" + recibe.getNombre() + "</span><span class='message-data-time'>"+new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(currentTime)+"</span></div><div class='message my-message'>"+ message +"</div></li>";


            // Add message to chat
//            boolean add = this.chatService.addMsg(msg, chat);

//            if (!add){
//                throw new ServletException("No se pudo añadir el mensaje");
//            }


        } else { // Message had an error
            htmlMessage = "";
        }

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
                throw new ServletException(ex.getMessage());
            }
        }
    }


}
