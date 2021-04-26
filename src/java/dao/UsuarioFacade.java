/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Usuario;
import java.sql.Date;
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
    
    /**
     * Devuelve una lista de usuarios con los roles seleccionados. 
     * Tambien puede filtar . En caso de no querer un filtro de fecha ponerlo a null.
     * @param fechaInicial Poner null si no se quiere seleccionar una fecha inicial
     * @param fechaFinal Poner null si no se quiere seleccionar una fecha final
     * @return 
     */
    public List<Usuario> getUsuarioByRoles(boolean analistas, boolean usuarioEventos, boolean creadorEventos, boolean teleoperadores, boolean administradores, Date fechaInicial, Date fechaFinal){
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<Usuario> l;
        Query q;
        if(fechaInicial == null)
            fechaInicial = Date.valueOf("0001-01-01");  //Date.valueOf(Long.MAX_VALUE); Esto peta
        if(fechaFinal == null)
            fechaFinal = Date.valueOf("9999-12-31");
        
        if(analistas){
            q = em.createQuery("SELECT u FROM Analista a, Usuario u WHERE a.usuarioId=u.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal");
            q.setParameter("fechaInicial", fechaInicial);
            q.setParameter("fechaFinal", fechaFinal);
            l = q.getResultList();
            
            if(l!=null)
                listaUsuarios.addAll(l);
        }
            
        if(usuarioEventos){
            q = em.createQuery("SELECT u FROM Usuarioeventos ua, Usuario u WHERE ua.usuarioId=U.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal");
            q.setParameter("fechaInicial", fechaInicial);
            q.setParameter("fechaFinal", fechaFinal);
            l = q.getResultList();
            
            if(l!=null)
                listaUsuarios.addAll(l);
        }
        
        if(creadorEventos){
            q = em.createQuery("SELECT u FROM Creadoreventos c, Usuario u WHERE c.usuarioId=U.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal");
            q.setParameter("fechaInicial", fechaInicial);
            q.setParameter("fechaFinal", fechaFinal);
            l = q.getResultList();
            
            if(l!=null)
                listaUsuarios.addAll(l);
        }
            
        if(teleoperadores){
            q = em.createQuery("SELECT u FROM Teleoperador t, Usuario u WHERE t.usuarioId=U.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal");
            q.setParameter("fechaInicial", fechaInicial);
            q.setParameter("fechaFinal", fechaFinal);
            l = q.getResultList();
            
            if(l!=null)
                listaUsuarios.addAll(l);
        }
        
        if(administradores){
            q = em.createQuery("SELECT u FROM Administrador a, Usuario u WHERE a.usuarioId=U.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal");
            q.setParameter("fechaInicial", fechaInicial);
            q.setParameter("fechaFinal", fechaFinal);
            l = q.getResultList();
            
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
