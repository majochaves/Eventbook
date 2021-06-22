package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Chat;

import javax.persistence.*;
import java.util.Date;

public class MensajeDTO {
    private Integer id;
    private Date fecha;
    private String contenido;
    private int usuarioEmisorId;
    private Chat chat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getUsuarioEmisorId() {
        return usuarioEmisorId;
    }

    public void setUsuarioEmisorId(int usuarioEmisorId) {
        this.usuarioEmisorId = usuarioEmisorId;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
