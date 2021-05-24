/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Teleoperador;
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
public class TeleoperadorFacade extends AbstractFacade<Teleoperador> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeleoperadorFacade() {
        super(Teleoperador.class);
    }
    
    
    public Teleoperador findByUsuarioID(Integer id){
        Query q = this.em.createNamedQuery("Teleoperador.findByUsuarioId");
        q.setParameter("usuarioId", id);
        List<Teleoperador> teleops = q.getResultList();
        
        return teleops.get(0);
    }
    
    
    
}
