package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.clases.Tupla;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import com.eventbookspring.eventbookspring.repository.UsuarioeventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnalistaService {

    //Tipos de usuario
    private static final String USUARIOEVENTOS = "usuarioEventos";
    private static final String CREADOREVENTOS = "creadorEventos";
    private static final String ANALISTAS = "analistas";
    private static final String TELEOPERADORES = "teleoperadores";
    private static final String ADMINISTRADORES = "administradores";

    //Tipos de filtro
    private static final String FILTRONUMUSUARIOS = "numUsuarios";
    private static final String FILTROSEXO = "sexo";
    private static final String FILTROCIUDAD = "ciudad";
    private static final String FILTRONOMBRE ="nombre";
    private static final String FILTROAPELLIDO = "apellido";
    private static final String FILTROFECHAPORMES = "filtroFechaMeses";
    private static final String FILTROFECHAPORANYOS = "filtroFechaAnyos";



    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, Map<String, Double>> generarAnalisis(
            List<String> tipoUsuario,
            List<String> tipoFiltro,
            String cadenaFechaInicial,
            String cadenaFechaFinal) throws ParseException {


        //------------FECHA-----------
        Date fechaInicial, fechaFinal;
        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        if(cadenaFechaInicial == null || cadenaFechaInicial.isEmpty())
            fechaInicial  = sdt.parse("01/01/0001");
        else
            fechaInicial = sdt.parse(cadenaFechaInicial);

        if(cadenaFechaFinal == null || cadenaFechaFinal.isEmpty())
            fechaFinal  = sdt.parse("31/12/9999");
        else
            fechaFinal = sdt.parse(cadenaFechaFinal);



        //---------OBTENER USUARIOS----------
        Set<Usuario> listaUsuarios = new HashSet<>();//Evitamos repeticiones
        if(tipoUsuario.contains(ADMINISTRADORES))
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosAdministradores(fechaInicial, fechaFinal));
        if(tipoUsuario.contains(ANALISTAS))
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosAnalistas(fechaInicial, fechaFinal));
        if(tipoUsuario.contains(USUARIOEVENTOS))
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosEventos(fechaInicial, fechaFinal));
        if(tipoUsuario.contains(CREADOREVENTOS))
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosCreadoresEventos(fechaInicial, fechaFinal));
        if(tipoUsuario.contains(TELEOPERADORES))
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosTeleoperadores(fechaInicial, fechaFinal));




        //--------BUSQUEDA POR FILTROS---------
        Map<String, Map<String, Double>> listaTablas = new HashMap<>();
        if(tipoFiltro.contains(FILTRONUMUSUARIOS)){
            Map<String, Double> m = new HashMap<>();
            m.put("Num", (double) listaUsuarios.size());
            listaTablas.put("Numero de Usuarios", m);
        }

        if(tipoFiltro.contains(FILTROSEXO)){
            List<Par> listaPar = this.usuarioRepository.getNumUsuariosGroupBySexo(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            for(Par p : listaPar){
                m.put(p.getCadena(), (double) p.getValor());
            }
            listaTablas.put("Sexo", m);
        }

        if(tipoFiltro.contains(FILTROCIUDAD)){
            List<Par> listaPar = this.usuarioRepository.getNumUsuariosGroupByCities(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            for(Par p : listaPar){
                m.put(p.getCadena(), (double) p.getValor());
            }
            listaTablas.put("Ciudad", m);
        }

        if(tipoFiltro.contains(FILTRONOMBRE)){
            List<Par> listaPar = this.usuarioRepository.getNumUsuariosGroupByName(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            for(Par p : listaPar){
                m.put(p.getCadena(), (double) p.getValor());
            }
            listaTablas.put("Nombre", m);
        }

        if(tipoFiltro.contains(FILTROAPELLIDO)){
            List<Par> listaPar = this.usuarioRepository.getNumUsuariosGroupByLastName(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            for(Par p : listaPar){
                m.put(p.getCadena(), (double) p.getValor());
            }
            listaTablas.put("Apellido", m);
        }

        if(tipoFiltro.contains(FILTROFECHAPORANYOS)){
            List<Tupla<String, Double>> listaTupla = this.usuarioRepository.getNumUsuariosGroupByCreatedYear(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            sdt = new SimpleDateFormat("yyyy");
            for(Tupla<String, Double> t : listaTupla){
                m.put(sdt.format(t.getPrimerElem()), t.getSegundoElem());
            }
            listaTablas.put("Fecha Creacion (Año)", m);
        }

        if(tipoFiltro.contains(FILTROFECHAPORMES)){
            List<Tupla<String, Double>> listaTupla = this.usuarioRepository.getNumUsuariosGroupByCreatedMonthYear(listaUsuarios);
            Map<String, Double> m = new HashMap<>();
            for(Tupla<String, Double> t : listaTupla){
                m.put(t.getPrimerElem(), t.getSegundoElem());
            }
            listaTablas.put("Fecha Creacion (Mes - Año)", m);
        }

        return listaTablas;

    }


}
