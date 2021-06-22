package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.TeleoperadorDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.Administrador;
import com.eventbookspring.eventbookspring.entity.Teleoperador;
import com.eventbookspring.eventbookspring.service.ChatService;
import com.eventbookspring.eventbookspring.service.TeleoperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/chat/")
public class ChatController {
    private ChatService chatService;
    private TeleoperadorService teleoperadorService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Autowired
    public void setTeleoperadorService(TeleoperadorService teleoperadorService) {
        this.teleoperadorService = teleoperadorService;
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
    private void checkLoginYPrivilegios(Model model, HttpSession session, UsuarioDTO usuarioDTO) throws AutenticacionException {

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
