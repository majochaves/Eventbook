/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

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
@Table(name = "CREADOREVENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Creadoreventos.findAll", query = "SELECT c FROM Creadoreventos c")
    , @NamedQuery(name = "Creadoreventos.findByUsuarioId", query = "SELECT c FROM Creadoreventos c WHERE c.usuarioId = :usuarioId")})
public class Creadoreventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID", nullable = false)
    private Integer usuarioId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creadoreventosId")
    private List<Evento> eventoList;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public Creadoreventos() {
    }

    public Creadoreventos(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Creadoreventos)) {
            return false;
        }
        Creadoreventos other = (Creadoreventos) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.getUsuarioId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Creadoreventos[ usuarioId=" + usuarioId + " ]";
    }
    
}
