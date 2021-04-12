/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "CHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c")
    , @NamedQuery(name = "Chat.findByFecha", query = "SELECT c FROM Chat c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Chat.findByTeleoperadorId", query = "SELECT c FROM Chat c WHERE c.chatPK.teleoperadorId = :teleoperadorId")
    , @NamedQuery(name = "Chat.findByUsuarioId", query = "SELECT c FROM Chat c WHERE c.chatPK.usuarioId = :usuarioId")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChatPK chatPK;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "TELEOPERADOR_ID", referencedColumnName = "USUARIO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Teleoperador teleoperador;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    private List<Mensaje> mensajeList;

    public Chat() {
    }

    public Chat(ChatPK chatPK) {
        this.chatPK = chatPK;
    }

    public Chat(int teleoperadorId, int usuarioId) {
        this.chatPK = new ChatPK(teleoperadorId, usuarioId);
    }

    public ChatPK getChatPK() {
        return chatPK;
    }

    public void setChatPK(ChatPK chatPK) {
        this.chatPK = chatPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Teleoperador getTeleoperador() {
        return teleoperador;
    }

    public void setTeleoperador(Teleoperador teleoperador) {
        this.teleoperador = teleoperador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatPK != null ? chatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.chatPK == null && other.chatPK != null) || (this.chatPK != null && !this.chatPK.equals(other.chatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Chat[ chatPK=" + chatPK + " ]";
    }
    
}
