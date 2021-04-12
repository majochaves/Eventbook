/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "CAMPOANALISIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campoanalisis.findAll", query = "SELECT c FROM Campoanalisis c")
    , @NamedQuery(name = "Campoanalisis.findByNombre", query = "SELECT c FROM Campoanalisis c WHERE c.campoanalisisPK.nombre = :nombre")
    , @NamedQuery(name = "Campoanalisis.findByValor", query = "SELECT c FROM Campoanalisis c WHERE c.valor = :valor")
    , @NamedQuery(name = "Campoanalisis.findByTipoanalisisId", query = "SELECT c FROM Campoanalisis c WHERE c.campoanalisisPK.tipoanalisisId = :tipoanalisisId")})
public class Campoanalisis implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CampoanalisisPK campoanalisisPK;
    @Column(name = "VALOR")
    private Integer valor;
    @JoinColumn(name = "TIPOANALISIS_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipoanalisis tipoanalisis;

    public Campoanalisis() {
    }

    public Campoanalisis(CampoanalisisPK campoanalisisPK) {
        this.campoanalisisPK = campoanalisisPK;
    }

    public Campoanalisis(String nombre, int tipoanalisisId) {
        this.campoanalisisPK = new CampoanalisisPK(nombre, tipoanalisisId);
    }

    public CampoanalisisPK getCampoanalisisPK() {
        return campoanalisisPK;
    }

    public void setCampoanalisisPK(CampoanalisisPK campoanalisisPK) {
        this.campoanalisisPK = campoanalisisPK;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Tipoanalisis getTipoanalisis() {
        return tipoanalisis;
    }

    public void setTipoanalisis(Tipoanalisis tipoanalisis) {
        this.tipoanalisis = tipoanalisis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campoanalisisPK != null ? campoanalisisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campoanalisis)) {
            return false;
        }
        Campoanalisis other = (Campoanalisis) object;
        if ((this.campoanalisisPK == null && other.campoanalisisPK != null) || (this.campoanalisisPK != null && !this.campoanalisisPK.equals(other.campoanalisisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Campoanalisis[ campoanalisisPK=" + campoanalisisPK + " ]";
    }
    
}
