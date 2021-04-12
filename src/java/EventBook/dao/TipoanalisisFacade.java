/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventBook.dao;

import EventBook.entity.Tipoanalisis;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Merli
 */
@Stateless
public class TipoanalisisFacade extends AbstractFacade<Tipoanalisis> {

    @PersistenceContext(unitName = "ProbandoBDEventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoanalisisFacade() {
        super(Tipoanalisis.class);
    }
    
}
