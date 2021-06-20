package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.clases.Tupla;
import com.eventbookspring.eventbookspring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //Consultas para obtencion de usuarios. Nota: Los administradores poseen todos los roles, por eso debemos descartar a los usuarios
    //que poseen rol de administrador

    @Query("SELECT u FROM Analista a, Usuario u WHERE a.usuarioId=u.id AND a.usuario NOT IN (SELECT u FROM Usuario u, Administrador a WHERE a.usuarioId=u.id) AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal")
    public Collection<Usuario> getUsuariosAnalistas(Date fechaInicial, Date fechaFinal);

    @Query("SELECT u FROM Usuarioeventos ua, Usuario u WHERE ua.usuarioId=u.id AND ua.usuario NOT IN (SELECT u FROM Usuario u, Administrador a WHERE a.usuarioId=u.id) AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal")
    public Collection<Usuario> getUsuariosEventos(Date fechaInicial, Date fechaFinal);

    @Query("SELECT u FROM Creadoreventos c, Usuario u WHERE c.usuarioId=u.id AND c.usuario NOT IN (SELECT u FROM Usuario u, Administrador a WHERE a.usuarioId=u.id) AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal")
    public Collection<Usuario> getUsuariosCreadoresEventos(Date fechaInicial, Date fechaFinal);

    @Query("SELECT u FROM Teleoperador t, Usuario u WHERE t.usuarioId=u.id AND t.usuario NOT IN (SELECT u FROM Usuario u, Administrador a WHERE a.usuarioId=u.id) AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal")
    public Collection<Usuario> getUsuariosTeleoperadores(Date fechaInicial, Date fechaFinal);

    @Query("SELECT u FROM Administrador a, Usuario u WHERE a.usuarioId=u.id AND u.fechaCreacion>= :fechaInicial AND u.fechaCreacion<= :fechaFinal")
    public Collection<Usuario> getUsuariosAdministradores(Date fechaInicial, Date fechaFinal);


    //Consultas para filtrar un conjunto de usuarios

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(u.sexo, COUNT(u.sexo))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY u.sexo")
    public List<Par<?, ?>> getNumUsuariosGroupBySexo(Set<Usuario> listaUsuarios);


    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(u.ciudadResidencia), COUNT(u.ciudadResidencia))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY UPPER(u.ciudadResidencia)")
    public List<Par<?, ?>> getNumUsuariosGroupByCities(Set<Usuario> listaUsuarios);


    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(u.nombre), COUNT(u.nombre))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY UPPER(u.nombre)")
    public List<Par<?, ?>> getNumUsuariosGroupByName(Set<Usuario> listaUsuarios);


    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(u.apellidos), COUNT(u.apellidos))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY UPPER(u.apellidos)")
    public List<Par<?,?>> getNumUsuariosGroupByLastName(Set<Usuario> listaUsuarios);


    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(YEAR(u.fechaCreacion), COUNT(u.fechaCreacion))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY YEAR(u.fechaCreacion)")
    public List<Par<?, ?>> getNumUsuariosGroupByCreatedYear(Set<Usuario> listaUsuarios);

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(MONTH(u.fechaCreacion), YEAR(u.fechaCreacion), COUNT(*))" +
            " FROM Usuario AS u WHERE u IN :listaUsuarios GROUP BY MONTH(u.fechaCreacion), YEAR(u.fechaCreacion)")
    public List<Par<?, ?>> getNumUsuariosGroupByCreatedMonthYear(Set<Usuario> listaUsuarios);




}