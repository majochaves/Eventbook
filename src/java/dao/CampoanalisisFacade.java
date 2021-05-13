/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Campoanalisis;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memoriasIT
 */
@Stateless
public class CampoanalisisFacade extends AbstractFacade<Campoanalisis> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CampoanalisisFacade() {
        super(Campoanalisis.class);
    }
    
    public void deleteCampoanalisisByTipoanalisisId(Integer idTipoanalisis){
        Query q = em.createQuery("DELETE FROM Campoanalisis c WHERE c.campoanalisisPK.tipoanalisisId = :idTipoanalisis");
        q.setParameter("idTipoanalisis", idTipoanalisis);
        q.executeUpdate();
    }
    
}
