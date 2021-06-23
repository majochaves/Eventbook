package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

public class TeleoperadorDTO {
    private Integer usuarioId;
    private Usuario usuario;
    private List<Chat> chatList;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
}
