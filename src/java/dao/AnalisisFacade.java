/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Analisis;
import entity.Analista;
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
public class AnalisisFacade extends AbstractFacade<Analisis> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalisisFacade() {
        super(Analisis.class);
    }
    
    public List<Analisis> getAllAnalisis(Analista analista){
        Query q = em.createQuery("SELECT a FROM Analisis a WHERE a.analistaUsuarioId=:analista");
        q.setParameter("analista", analista);
        return q.getResultList();
    }
    
    
    
}
