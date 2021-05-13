/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.Tupla;
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
    

    /*public Map<String, Integer> getNumUsersByGender(List<Usuario> listaUsuarios){
        Map<String, Integer> genderList = new HashMap<>();
        //Segun JPA especificacion COUNT siempre devuelve Long
        Long masculinos = (Long)em.createQuery("SELECT COUNT(u.sexo) FROM Usuario u WHERE u.sexo LIKE 'Masculino'").getSingleResult();
        Long femenino = (Long)em.createQuery("SELECT COUNT(u.sexo) FROM Usuario u WHERE u.sexo LIKE 'Femenino'").getSingleResult();
        genderList.put("Masculino", masculinos.intValue());
        genderList.put("Femenino", femenino.intValue());
        return genderList;
    }*/
    
    /**
     * Devuelve el numero de usuarios que existen en cada ciudad
     * @param listaUsuariosId
     * @return Map <Ciudad, NumeroDeUsuarios>
     */
    public Map<String, Double> getNumUsersByCities(List<Integer> listaUsuariosId){
        Map<String, Double> listaCiudades = new HashMap<>();
        if(listaUsuariosId!= null && !listaUsuariosId.isEmpty()){
            Query q = em.createQuery("SELECT UPPER(u.ciudadResidencia), COUNT(u) FROM Usuario u WHERE u.id IN :listaUsuariosId GROUP BY UPPER(u.ciudadResidencia)");
            q.setParameter("listaUsuariosId", listaUsuariosId);
            
            List<Object []> resultado = q.getResultList();
            for(Object[] o : resultado)
                listaCiudades.put((String)o[0], ((Long)o[1]).doubleValue());
        }
        
        return listaCiudades;
    }
    
    /**
     * Devuelve el numero de usuarios que existen por cada nombre
     * @param listaUsuariosId
     * @return Map <Nombre, NumeroDeUsuarios>
     */
    public Map<String, Double> getNumUsersByName(List<Integer> listaUsuariosId){
        Map<String, Double> listaNombres = new HashMap<>();
        if(listaUsuariosId!= null && !listaUsuariosId.isEmpty()){
            Query q = em.createQuery("SELECT UPPER(u.nombre), COUNT(u) FROM Usuario u WHERE u.id IN :listaUsuariosId GROUP BY UPPER(u.nombre)");
            q.setParameter("listaUsuariosId", listaUsuariosId);
            
            List<Object []> resultado = q.getResultList();
            for(Object[] o : resultado)
                listaNombres.put((String)o[0], ((Long)o[1]).doubleValue());
        }
        
        return listaNombres;
    }
    
    /**
     * Devuelve el numero de usuarios que existen por cada apellido
     * @param listaUsuariosId
     * @return 
     */
    public Map<String, Double> getNumUsersByLastName(List<Integer> listaUsuariosId){
        Map<String, Double> listaApellidos = new HashMap<>();
        if(listaUsuariosId!= null && !listaUsuariosId.isEmpty()){
            Query q = em.createQuery("SELECT UPPER(u.apellidos), COUNT(u) FROM Usuario u WHERE u.id IN :listaUsuariosId GROUP BY UPPER(u.apellidos)");
            q.setParameter("listaUsuariosId", listaUsuariosId);
            
            List<Object []> resultado = q.getResultList();
            for(Object[] o : resultado)
                listaApellidos.put((String)o[0], ((Long)o[1]).doubleValue());
        }
        
        return listaApellidos;
    }
    
    /**
     * Devuelve el numero de usuarios que existen por cada anyo
     * @param listaUsuariosId
     * @return Map<Anyo, numeroDeUsuarios>
     */
    public Map<String, Double> getNumUsersByYears(List<Integer> listaUsuariosId){
        Map<String, Double> listaAnyos = new HashMap<>();
        if(listaUsuariosId!= null && !listaUsuariosId.isEmpty()){
            
            Query q = em.createQuery("SELECT EXTRACT(YEAR FROM u.fechaCreacion), COUNT(u) FROM Usuario u WHERE u.id IN :listaUsuariosId GROUP BY EXTRACT(YEAR FROM u.fechaCreacion)");
            q.setParameter("listaUsuariosId", listaUsuariosId);
            
            List<Object []> resultado = q.getResultList();
            for(Object[] o : resultado)
                listaAnyos.put(((Integer)o[0]).toString(), ((Long)o[1]).doubleValue());
        }
        
        return listaAnyos;
    }
    
    /**
     * Devuelve el numero de usuarios que existen por cada mes-anyo
     * @param listaUsuariosId
     * @return Map<Mes-Anyo, numeroDeUsuarios>
     */
    public Map<String, Double> getNumUsersByMonths(List<Integer> listaUsuariosId){
    Map<String, Double> listaMesesAnyos = new HashMap<>();
    if(listaUsuariosId!= null && !listaUsuariosId.isEmpty()){

        Query q = em.createQuery("SELECT EXTRACT(YEAR FROM u.fechaCreacion), EXTRACT(MONTH FROM u.fechaCreacion), COUNT(u) FROM Usuario u WHERE u.id IN :listaUsuariosId GROUP BY EXTRACT(YEAR FROM u.fechaCreacion), EXTRACT(MONTH FROM u.fechaCreacion)");
        q.setParameter("listaUsuariosId", listaUsuariosId);

        List<Object []> resultado = q.getResultList();
        for(Object[] o : resultado){
            String mesYAnyo = (Integer)o[1] + " - " + (Integer)o[0];
            listaMesesAnyos.put(mesYAnyo, ((Long)o[2]).doubleValue());
        }
            
    }

    return listaMesesAnyos;
    }
    
    
    
    public Usuario getUserByID(String userID){
        Query q = this.em.createNamedQuery("Usuario.findById");
        q.setParameter("id", new Integer(userID));
        
        List<Usuario> resultado = q.getResultList();
        return resultado.get(0);
    }
    
}
