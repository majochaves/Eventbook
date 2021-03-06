/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import com.eventbookspring.eventbookspring.dto.TeleoperadorDTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
//import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "TELEOPERADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teleoperador.findAll", query = "SELECT t FROM Teleoperador t")
    , @NamedQuery(name = "Teleoperador.findByUsuarioId", query = "SELECT t FROM Teleoperador t WHERE t.usuarioId = :usuarioId")})
public class Teleoperador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID", nullable = false)
    private Integer usuarioId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teleoperador")
    private List<Chat> chatList;

    public Teleoperador() {
    }

    public Teleoperador(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

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

    @XmlTransient
    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teleoperador)) {
            return false;
        }
        Teleoperador other = (Teleoperador) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.getUsuarioId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Teleoperador[ usuarioId=" + usuarioId + " ]";
    }

    public TeleoperadorDTO getDTO() {
        TeleoperadorDTO teleoperadorDTO = new TeleoperadorDTO();
        teleoperadorDTO.setUsuarioId(this.getUsuarioId());
        teleoperadorDTO.setUsuario(this.getUsuario());
        teleoperadorDTO.setChatList(this.getChatList());

        return teleoperadorDTO;
    }
}
