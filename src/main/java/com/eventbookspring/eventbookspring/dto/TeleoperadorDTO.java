package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeleoperadorDTO that = (TeleoperadorDTO) o;
        return usuarioId.equals(that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }
}
