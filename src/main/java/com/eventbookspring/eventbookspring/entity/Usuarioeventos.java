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
@Table(name = "USUARIOEVENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarioeventos.findAll", query = "SELECT u FROM Usuarioeventos u")
    , @NamedQuery(name = "Usuarioeventos.findByUsuarioId", query = "SELECT u FROM Usuarioeventos u WHERE u.usuarioId = :usuarioId")})
public class Usuarioeventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID", nullable = false)
    private Integer usuarioId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioeventosId")
    private List<Reserva> reservaList;

    public Usuarioeventos() {
    }

    public Usuarioeventos(Integer usuarioId) {
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
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
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
        if (!(object instanceof Usuarioeventos)) {
            return false;
        }
        Usuarioeventos other = (Usuarioeventos) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarioeventos[ usuarioId=" + usuarioId + " ]";
    }
    
}
