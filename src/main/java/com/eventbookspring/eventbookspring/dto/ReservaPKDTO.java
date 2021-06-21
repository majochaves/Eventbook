package com.eventbookspring.eventbookspring.dto;

public class ReservaPKDTO {
    private int fila;
    private int asiento;
    private int eventoId;

    public ReservaPKDTO() {
    }

    public ReservaPKDTO(int fila, int asiento, int eventoId) {
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
        if (!(object instanceof ReservaPKDTO)) {
            return false;
        }
        ReservaPKDTO other = (ReservaPKDTO) object;
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
        return "entity.ReservaPK[ fila=" + fila + ", asiento=" + asiento + ", eventoId=" + eventoId + " ]";
    }
}
