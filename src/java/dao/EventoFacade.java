/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evento;
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
public class EventoFacade extends AbstractFacade<Evento> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }
    
    public List<Evento> findEventByMostRecent(){
        
        Query q;
        List<Evento> lista = null;
        
        q = em.createQuery("SELECT e FROM Evento e ORDER BY e.fecha ASC");
        lista = q.getResultList();
        
        if(lista.isEmpty() || lista == null) return null;
        else return lista;
        
    }
    
    public List<Evento> findEventByLeastRecent(){
        Query q;
        List<Evento> lista = null;
        
        q = em.createQuery("SELECT e FROM Evento e ORDER BY e.fecha DESC");
        lista = q.getResultList();
        
        if(lista.isEmpty() || lista == null) return null;
        else return lista;
        
    }

    public List<Evento> findEventosReservadosBy(Integer usuarioId) {
        
        Query q;
        List<Evento> lista;
        
        q = em.createQuery("SELECT DISTINCT e FROM Evento e, Reserva r WHERE e.id = r.evento.id AND r.usuarioeventosId.usuarioId = :usuarioId");
        q.setParameter("usuarioId", usuarioId);
        lista = q.getResultList();
        
        if(lista == null) return null;
        else return lista;
        
    }
    
    public List<Evento> findEventosReservadosByMostRecent(Integer usuarioId) {
        
        Query q;
        List<Evento> lista;
        
        q = em.createQuery("SELECT DISTINCT e FROM Evento e, Reserva r WHERE e.id = r.evento.id AND r.usuarioeventosId.usuarioId = :usuarioId ORDER BY e.fecha asc");
        q.setParameter("usuarioId", usuarioId);
        lista = q.getResultList();
        
        if(lista == null) return null;
        else return lista;
        
    }
    
    public List<Evento> findEventosReservadosByLeastRecent(Integer usuarioId) {
        
        Query q;
        List<Evento> lista;
        
        q = em.createQuery("SELECT DISTINCT e FROM Evento e, Reserva r WHERE e.id = r.evento.id AND r.usuarioeventosId.usuarioId = :usuarioId ORDER BY e.fecha desc");
        q.setParameter("usuarioId", usuarioId);
        lista = q.getResultList();
        
        if(lista == null) return null;
        else return lista;
        
    }
    
    
    
}
