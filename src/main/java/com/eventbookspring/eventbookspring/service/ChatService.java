package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.dto.MensajeDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.ChatRepository;
import com.eventbookspring.eventbookspring.repository.TeleoperadorRepository;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private ChatRepository chatRepository;
    private TeleoperadorRepository teleoperadorRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setChatRepository(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Autowired
    public void setTeleoperadorRepository(TeleoperadorRepository teleoperadorRepository) {
        this.teleoperadorRepository = teleoperadorRepository;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Chat> findChatsByUserID(Integer id) {
        return this.chatRepository.findChatsByUserID(id);
    }

    public List<Chat> findAll() {
        return this.chatRepository.findAll();
    }

    public void guardarChat(Integer userid, Integer opId) {
        Teleoperador op = this.teleoperadorRepository.findByUsuarioId(opId);
        Usuario usuario = this.usuarioRepository.getById(userid);

        Chat chat = new Chat();
        ChatPK chatPK = new ChatPK(opId, userid);
        chat.setChatPK(chatPK);
        chat.setFecha(new Timestamp(new Date().getTime()));
        chat.setTeleoperador(op);
        chat.setUsuario(usuario);

        this.chatRepository.save(chat);
    }

    public void borrarChat(Integer userID, Integer opID) {
        Chat chat = this.chatRepository.getByChatPK(userID, opID);
        this.chatRepository.delete(chat);
    }

    public Chat findByChatPK(Integer id, Integer id1) {
        return this.chatRepository.getByChatPK(id, id1);
    }

    public boolean addMsg(MensajeDTO msgDTO, Chat chat) {
        try {
            // Compose message
            Mensaje msg = new Mensaje();
            msg.setFecha(msgDTO.getFecha());
            msg.setContenido(msgDTO.getContenido());
            msg.setUsuarioEmisorId(msgDTO.getId());
            msg.setChat(chat);

            // Add message to chat
            chat.getMensajeList().add(msg);

            // Save entity
            this.chatRepository.save(chat);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
