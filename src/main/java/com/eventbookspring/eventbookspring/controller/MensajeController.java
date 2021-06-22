package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.dto.MensajeDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mensaje/")
public class MensajeController {
    private MensajeService mensajeService;

    @Autowired
    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /*
              __   __       ___  ___
        |  | |__) |  \  /\   |  |__
        \__/ |    |__/ /~~\  |  |___
        - Update -
*/

    @GetMapping("/editarMsg/{userID}/{opID}/{msgId}")
    public String listar(Model model, HttpSession session,
                         @PathVariable("userID") Integer userID,
                         @PathVariable("opID") Integer opID,
                         @PathVariable("msgId") Integer msgId){

        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            MensajeDTO msg = this.mensajeService.getMessageByID(msgId);
            model.addAttribute("contenido", msg.getContenido());

            return "mensaje-editar/{userID}/{opID}";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

    @PostMapping("/editarMsg/guardar")
    public String guardarMensajeEditado(Model model, HttpSession session,
                                        @PathVariable("userID") Integer userID,
                                        @PathVariable("opID") Integer opID,
                                        @PathVariable("msgId") Integer msgId,
                                        @RequestParam("newContenido") String newContenido){

        try {
            // Comprobar login
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            // Modificar mensaje
            this.mensajeService.editarMsg(msgId, newContenido);

            // Volver al chat
            return "redirect:/chat/{userID}/{opID}/";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }


    /*
            __   ___       ___ ___  ___
           |  \ |__  |    |__   |  |__
           |__/ |___ |___ |___  |  |___
    */
    @GetMapping("/borrar/{userID}/{opID}/{msgId}")
    public String borrarChat(Model model, HttpSession session,
                             @PathVariable("userID") Integer userID,
                             @PathVariable("opID") Integer opID,
                             @PathVariable("msgId") Integer msgId){
        try {
            UsuarioDTO usuarioDTO = Autenticacion.getUsuarioLogeado(session);
            if (usuarioDTO == null){ // No ha hecho login
                throw new AutenticacionException("¿Has iniciado sesión?");
            }

            this.mensajeService.borrarMsg(userID, opID, msgId);

            return "redirect:/chat/{userID}/{opID}";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }
}
