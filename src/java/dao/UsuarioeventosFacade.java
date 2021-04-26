/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Usuarioeventos;
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
public class UsuarioeventosFacade extends AbstractFacade<Usuarioeventos> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioeventosFacade() {
        super(Usuarioeventos.class);
    }
    
    public List<Integer> getAllId() {
        Query q;
        q = em.createQuery("SELECT u.id FROM Usuarioeventos u");
        return q.getResultList();
    }
    
}
