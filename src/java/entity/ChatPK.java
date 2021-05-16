/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author memoriasIT
 */
@Embeddable
public class ChatPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "TELEOPERADOR_ID", nullable = false)
    private int teleoperadorId;
    @Basic(optional = false)
    @Column(name = "USUARIO_ID", nullable = false)
    private int usuarioId;

    public ChatPK() {
    }

    public ChatPK(int teleoperadorId, int usuarioId) {
        this.teleoperadorId = teleoperadorId;
        this.usuarioId = usuarioId;
    }

    public int getTeleoperadorId() {
        return teleoperadorId;
    }

    public void setTeleoperadorId(int teleoperadorId) {
        this.teleoperadorId = teleoperadorId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teleoperadorId;
        hash += (int) usuarioId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatPK)) {
            return false;
        }
        ChatPK other = (ChatPK) object;
        if (this.teleoperadorId != other.teleoperadorId) {
            return false;
        }
        if (this.usuarioId != other.usuarioId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ChatPK[ teleoperadorId=" + teleoperadorId + ", usuarioId=" + usuarioId + " ]";
    }
    
}
