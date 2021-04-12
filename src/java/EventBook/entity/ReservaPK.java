/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventBook.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Merli
 */
@Embeddable
public class ReservaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "FILA")
    private int fila;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASIENTO")
    private int asiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVENTO_ID")
    private int eventoId;

    public ReservaPK() {
    }

    public ReservaPK(int fila, int asiento, int eventoId) {
        this.fila = fila;
        this.asiento = asiento;
        this.eventoId = eventoId;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) fila;
        hash += (int) asiento;
        hash += (int) eventoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaPK)) {
            return false;
        }
        ReservaPK other = (ReservaPK) object;
        if (this.fila != other.fila) {
            return false;
        }
        if (this.asiento != other.asiento) {
            return false;
        }
        if (this.eventoId != other.eventoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProbandoBDEventBook.entity.ReservaPK[ fila=" + fila + ", asiento=" + asiento + ", eventoId=" + eventoId + " ]";
    }
    
}
