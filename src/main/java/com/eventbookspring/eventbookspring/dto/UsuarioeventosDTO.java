package com.eventbookspring.eventbookspring.dto;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public class UsuarioeventosDTO {

    private Integer usuarioId;
    private UsuarioDTO usuario;
    private List<ReservaDTO> reservaList;

    public UsuarioeventosDTO() {
    }

    public UsuarioeventosDTO(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<ReservaDTO> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<ReservaDTO> reservaList) {
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
        if (!(object instanceof UsuarioeventosDTO)) {
            return false;
        }
        UsuarioeventosDTO other = (UsuarioeventosDTO) object;
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
