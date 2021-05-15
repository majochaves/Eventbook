/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Chat;
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
public class ChatFacade extends AbstractFacade<Chat> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChatFacade() {
        super(Chat.class);
    }
    
    public Chat findByChatPK(Integer userID, Integer opID){
        Query q;
       
        q = this.em.createQuery("SELECT c FROM Chat c WHERE  c.chatPK.teleoperadorId = :teleoperadorId AND c.chatPK.usuarioId = :usuarioId");
        q.setParameter("usuarioId", userID);
        q.setParameter("teleoperadorId", opID);
        
        return (Chat) q.getSingleResult();
    }
    
    
    public List<Chat> findChatsByUserID(Integer userID){
         Query q;
       
        q = this.em.createQuery("SELECT c FROM Chat c WHERE  c.chatPK.teleoperadorId = :userID OR c.chatPK.usuarioId = :userID");
        q.setParameter("userID", userID);
        
        return (List<Chat>) q.getResultList();
    }
}
