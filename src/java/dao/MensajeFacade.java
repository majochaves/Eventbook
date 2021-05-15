/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Mensaje;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memoriasIT
 */
@Stateless
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @EJB
    private ChatFacade chatFacade;
    
    

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    
    
    public List<Pair<Integer, Mensaje>> getListOfMensajesByIDs( Integer to, Integer from){
        List<Pair<Integer, Mensaje>> res = new ArrayList<>();
        
        Query q = this.em.createQuery("SELECT m FROM Mensaje m WHERE "
                + "(m.chat.chatPK.teleoperadorId = :usuarioEmisorIdA AND m.chat.chatPK.usuarioId = :usuarioEmisorIdB) OR"
                + "(m.chat.chatPK.teleoperadorId = :usuarioEmisorIdB AND m.chat.chatPK.usuarioId = :usuarioEmisorIdA)"
                + "ORDER BY m.fecha");
        q.setParameter("usuarioEmisorIdA", to);
        q.setParameter("usuarioEmisorIdB", from);
        
        List<Mensaje> mensajes = q.getResultList();
        
        mensajes.forEach((msg) -> {
            if (to == msg.getUsuarioEmisorId()){
                res.add(new Pair(to, msg));
            } else {
                res.add(new Pair(from, msg));
            }
        });
        
        return res;
    }
    
}
