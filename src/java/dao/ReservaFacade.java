/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reserva;
import entity.ReservaPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memoriasIT
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }
    
    public List<ReservaPK> findReservaPKById(int id){
        
        Query q;
        List<ReservaPK> reservas;
                
        q = em.createQuery("SELECT r.reservaPK FROM Reserva r WHERE r.reservaPK.eventoId = :eventoId");
        q.setParameter("eventoId", id);
        reservas = q.getResultList();
        
        if(reservas == null) return null;
        else return reservas;
        
    }

    public Integer findNumEntradas(Integer usuarioId, Integer id) {
        Query q;
        List<Reserva> reservas;
        
        q = em.createQuery("SELECT r FROM Reserva r WHERE r.usuarioeventosId.usuarioId = :usuarioId AND r.evento.id = :id");
        q.setParameter("id", id);
        q.setParameter("usuarioId", usuarioId);
        
        reservas = q.getResultList();
        if(reservas == null || reservas.isEmpty()) return 0;
        else return reservas.size();
        
        
        
    }

    public List<Reserva> findReservasUser(Integer usuarioId, Integer idEvento) {
        Query q;
        List<Reserva> reservas;
        
        q = em.createQuery("SELECT r FROM Reserva r WHERE r.usuarioeventosId.usuarioId = :usuarioId AND r.evento.id = :idEvento");
        q.setParameter("usuarioId", usuarioId);
        q.setParameter("idEvento", idEvento);
        
        reservas = q.getResultList();
        if(reservas == null) return null;
        else return reservas;
        
    }
    
}
