package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Administrador;
import com.eventbookspring.eventbookspring.entity.Teleoperador;
import com.eventbookspring.eventbookspring.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat/")
public class ChatController {
    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

/*
        __   __   ___      ___  ___
       /  ` |__) |__   /\   |  |__
       \__, |  \ |___ /~~\  |  |___
*/

/*
        __   ___       __
       |__) |__   /\  |  \
       |  \ |___ /~~\ |__/
*/



    @GetMapping("/")
    public String listar(Model model, HttpSession session){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            checkLoginYPrivilegios(model, session, usuarioDTO);
            model.addAttribute("chats", this.chatService.findChatsByUserID(usuarioDTO.getId()));

            return "chat-listar";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

    private void checkLoginYPrivilegios(Model model, HttpSession session, UsuarioDTO usuarioDTO) throws AutenticacionException {
        if (usuarioDTO == null){ // No ha hecho login
            throw new AutenticacionException("¿Has iniciado sesión?");
        }

        // Mostrar vista con privilegios si los tiene
        if(Autenticacion.tieneRol(session, Teleoperador.class) || Autenticacion.tieneRol(session, Administrador.class)){
            model.addAttribute("allMessages", "Modo Usuario: mostrando tus chats - <a href='ServletChatListarTeleoperador'>Modo Operador</a>");
        } else {
            model.addAttribute("allMessages", "Modo Usuario: mostrando tus chats.");
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
}
