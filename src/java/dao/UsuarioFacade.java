/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memoriasIT
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> getUsuarioByRoles(boolean analistas, boolean usuarioEventos, boolean creadorEventos, boolean teleoperadores, boolean administradores){
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<Usuario> l;
        
        if(analistas){
            l = em.createQuery("SELECT u FROM Analista a, Usuario u WHERE a.usuarioId=u.id").getResultList();
            if(l!=null)
                listaUsuarios.addAll(l);
        }
            
        if(usuarioEventos){
            l = em.createQuery("SELECT u FROM Usuarioeventos ua, Usuario u WHERE ua.usuarioId=U.id").getResultList();
            if(l!=null)
                listaUsuarios.addAll(l);
        }
        
        if(creadorEventos){
            l = em.createQuery("SELECT u FROM Creadoreventos c, Usuario u WHERE c.usuarioId=U.id").getResultList();
            if(l!=null)
                listaUsuarios.addAll(l);
        }
            
        if(teleoperadores){
            l = em.createQuery("SELECT u FROM Teleoperador t, Usuario u WHERE t.usuarioId=U.id").getResultList();
            if(l!=null)
                listaUsuarios.addAll(l);
        }
        
        if(administradores){
            l = em.createQuery("SELECT u FROM Administrador a, Usuario u WHERE a.usuarioId=U.id").getResultList();
            if(l!=null)
                listaUsuarios.addAll(l);
        }
        
        return listaUsuarios;
    }
    
    public Map<String, Integer> getNumUsersByGender(List<Usuario> listaUsuarios){
        Map<String, Integer> genderList = new HashMap<>();
        //Segun JPA especificacion COUNT siempre devuelve Long
        Long masculinos = (Long)em.createQuery("SELECT COUNT(u.sexo) FROM Usuario u WHERE u.sexo LIKE 'Masculino'").getSingleResult();
        Long femenino = (Long)em.createQuery("SELECT COUNT(u.sexo) FROM Usuario u WHERE u.sexo LIKE 'Femenino'").getSingleResult();
        genderList.put("Masculino", masculinos.intValue());
        genderList.put("Femenino", femenino.intValue());
        return genderList;
    }
}
