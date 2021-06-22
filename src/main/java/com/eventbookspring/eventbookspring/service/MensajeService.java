package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.dto.MensajeDTO;
import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.Mensaje;
import com.eventbookspring.eventbookspring.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MensajeService {
    private MensajeRepository mensajeRepository;

    @Autowired
    public void setMensajeRepository(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public List<Par<Integer, Mensaje>> getListOfMensajesByIDs(Integer userID, Integer user2ID) {
        List<Mensaje> mensajes = this.mensajeRepository.getListOfMensajesByIDs(userID, user2ID);

        List<Par<Integer, Mensaje>> res = new ArrayList<>();
        for (Mensaje msg : mensajes) {
            if (userID == msg.getUsuarioEmisorId()){
                res.add(new Par(userID, msg));
            } else {
                res.add(new Par(user2ID, msg));
            }
        }

        return res;

    }

    public MensajeDTO addMessage(Date currentTime, String message, Integer id, Chat chat) {
        Mensaje msg = new Mensaje();
        msg.setFecha(currentTime);
        msg.setContenido(message);
        msg.setUsuarioEmisorId(id);
        msg.setChat(chat);
        this.mensajeRepository.save(msg);

        return msg.getDTO();

    }

    public void borrarMsg(Integer userID, Integer opID, Integer msgId) {
        this.mensajeRepository.delete(this.mensajeRepository.getById(msgId));
    }

    public void editarMsg(Integer msgId, String newContenido) {
        Mensaje msg = this.mensajeRepository.getById(msgId);
        msg.setContenido(newContenido);
        this.mensajeRepository.save(msg);
    }

    public MensajeDTO getMessageByID(Integer msgId) {
        return this.mensajeRepository.getById(msgId).getDTO();
    }
}
