package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.dto.MensajeDTO;
import com.eventbookspring.eventbookspring.dto.TeleoperadorDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Administrador;
import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.Mensaje;
import com.eventbookspring.eventbookspring.entity.Teleoperador;
import com.eventbookspring.eventbookspring.service.ChatService;
import com.eventbookspring.eventbookspring.service.MensajeService;
import com.eventbookspring.eventbookspring.service.TeleoperadorService;
import com.eventbookspring.eventbookspring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            } else {
                throw new AutenticacionException("Solo los teleoperadores pueden ver esta página.");
            }

            model.addAttribute("chats", this.chatService.findAll());

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

/*
             __   __       ___  ___
       |  | |__) |  \  /\   |  |__
       \__/ |    |__/ /~~\  |  |___
*/
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
}
