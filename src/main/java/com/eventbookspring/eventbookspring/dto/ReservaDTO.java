package com.eventbookspring.eventbookspring.dto;

import java.util.Date;

public class ReservaDTO {

    protected ReservaPKDTO reservaPK;
    private Date fecha;
    private EventoDTO evento;
    private UsuarioeventosDTO usuarioeventosId;

    public ReservaDTO() {
    }

    public ReservaDTO(ReservaPKDTO reservaPK) {
        this.reservaPK = reservaPK;
    }

    public ReservaDTO(int fila, int asiento, int eventoId) {
        this.reservaPK = new ReservaPKDTO(fila, asiento, eventoId);
    }

    public ReservaPKDTO getReservaPK() {
        return reservaPK;
    }

    public void setReservaPK(ReservaPKDTO reservaPK) {
        this.reservaPK = reservaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public UsuarioeventosDTO getUsuarioeventosId() {
        return usuarioeventosId;
    }

    public void setUsuarioeventosId(UsuarioeventosDTO usuarioeventosId) {
        this.usuarioeventosId = usuarioeventosId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservaPK != null ? reservaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaDTO)) {
            return false;
        }
        ReservaDTO other = (ReservaDTO) object;
        if ((this.reservaPK == null && other.reservaPK != null) || (this.reservaPK != null && !this.reservaPK.equals(other.reservaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reserva[ reservaPK=" + reservaPK + " ]";
    }

}
