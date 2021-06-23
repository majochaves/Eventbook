package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    //NOTA IMPORTANTE: Debido a que utilizamos Derby, nos vemos limitados a las funcionalidades que puede proporcionar la BD
    //Por ello, hay algunas funciones que se repite. Un ejemplo de estas funcionalidades limitadas, seria que derby NO PERMITE
    //realizar un CAST de INTEGER a VARCHAR(Segun la documentacion de Apache Derby)


    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(UPPER(e.titulo), COUNT(e.titulo)) FROM Evento e GROUP BY UPPER(e.titulo)")
    public List<Par<?, ?>> getNumEventosGroupByTitulos();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(MONTH(e.fecha), YEAR(e.fecha), COUNT(e.fecha)) FROM Evento e GROUP BY YEAR(e.fecha), MONTH(e.fecha)")
    public List<Par<?, ?>> getNumEventosGroupFechaDelEventoMesYAnyo();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(YEAR(e.fecha), COUNT(e.fecha)) FROM Evento e GROUP BY YEAR(e.fecha)")
    public List<Par<?, ?>> getNumEventosGroupFechaDelEventoAnyo();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(e.asientosFijos, COUNT(e.asientosFijos)) FROM Evento e GROUP BY e.asientosFijos")
    public List<Par<?, ?>> getNumEventosGroupAsientosFijos();



    //-------Consultas que pueden ser simplificadas si hacemos uso de otro SGBD------

    //    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(COALESCE(e.costeEntrada, 0), COUNT(COALESCE(e.costeEntrada, 0))) FROM Evento e GROUP BY COALESCE(e.costeEntrada, 0)")
    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(e.costeEntrada, COUNT(e.costeEntrada)) FROM Evento e WHERE e.costeEntrada IS NOT NULL GROUP BY e.costeEntrada")
    public List<Par<?, ?>> getNumEventosGroupCosteEvento();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par('-Sin especificar', COUNT(*)) FROM Evento e WHERE e.costeEntrada IS NULL")
    public Par<?, ?> getNumEventosGroupCosteEventoSinEspecificar();



    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(e.aforo, COUNT(e.aforo)) FROM Evento e WHERE e.aforo IS NOT NULL GROUP BY e.aforo")
    public List<Par<?, ?>> getNumEventosGroupAforo();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par('-Sin especificar', count(*)) FROM Evento e WHERE e.aforo IS NULL")
    public Par<?, ?> getNumEventosGroupAforoSinEspecificar();



    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par(e.maxEntradas, COUNT(e.maxEntradas)) FROM Evento e WHERE e.maxEntradas IS NOT NULL GROUP BY e.maxEntradas")
    public List<Par<?, ?>> getNumEventosGroupMaxEntradas();

    @Query("SELECT new com.eventbookspring.eventbookspring.clases.Par('-Sin especificar', COUNT(*)) FROM Evento e WHERE e.maxEntradas IS NULL")
    public Par<?, ?> getNumEventosGroupMaxEntradasSinEspecificar();




}