package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.dto.AnalisisDTO;
import com.eventbookspring.eventbookspring.dto.CampoanalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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

    //Tipos de filtro de la tabla usuario
    private static final String FILTRONUMUSUARIOS = "numUsuarios";
    private static final String FILTROSEXO = "sexo";
    private static final String FILTROCIUDAD = "ciudad";
    private static final String FILTRONOMBRE ="nombre";
    private static final String FILTROAPELLIDO = "apellido";
    private static final String FILTROFECHAPORMES = "filtroFechaMeses";
    private static final String FILTROFECHAPORANYOS = "filtroFechaAnyos";

    //Tipos de filtro de la tabla evento
    private static final String FILTRONUMEVENTOS = "numEventos";
    private static final String FILTROTITULOEVENTO = "tituloEvento";
    private static final String FILTROFECHAEVENTOMESYANYOS = "fechaEventoMesesyAnyos";
    private static final String FILTROFECHAEVENTOANYOS = "fechaEventoAnyos";
    private static final String FILTROCOSTEEVENTO ="costeEvento";
    private static final String FILTROAFOROEVENTO = "aforoEvento";
    private static final String FILTROMAXENTRADASEVENTO = "maxEntradasEvento";
    private static final String FILTROASIENTOSFIJOSEVENTO = "asientosFijosEvento";


    private UsuarioRepository usuarioRepository;
    private AnalisisRepository analisisRepository;
    private TipoanalisisRepository tipoanalisisRepository;
    private CampoanalisisRepository campoanalisisRepository;
    private AnalistaRepository analistaRepository;
    private EventoRepository eventoRepository;


    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public void setAnalisisRepository(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    @Autowired
    public void setTipoanalisisRepository(TipoanalisisRepository tipoanalisisRepository) {
        this.tipoanalisisRepository = tipoanalisisRepository;
    }

    @Autowired
    public void setCampoanalisisRepository(CampoanalisisRepository campoanalisisRepository) {
        this.campoanalisisRepository = campoanalisisRepository;
    }

    @Autowired
    public void setAnalistaRepository(AnalistaRepository analistaRepository) {
        this.analistaRepository = analistaRepository;
    }

    @Autowired
    public void setEventoRepository(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }



    public List<TipoanalisisDTO> generarAnalisis(
            List<String> tipoUsuario,
            List<String> tipoFiltroUsuario,
            List<String> tipoFiltroEvento,
            String cadenaFechaInicial,
            String cadenaFechaFinal,
            Par<String, String> autoGenerado) throws ParseException {
        //Nota: La tabla de Usuarios realmente realiza un JOIN con los roles seleccionados. Si se seleccionan todos los roles
        //equivale a no realizar un join. Por otra parte, es posible guardar un analisis que no devuelva datos(quizas el
        //analista tiene interes de recordad (en un futuro) que en sus filtros aplicados no devolvieron nada).


        //------------FECHA-----------
        Date fechaInicial, fechaFinal;
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
        if(cadenaFechaInicial == null || cadenaFechaInicial.isEmpty())
            fechaInicial  = sdt.parse("0001/01/01");
        else
            fechaInicial = sdt.parse(cadenaFechaInicial.replace("-", "/"));

        if(cadenaFechaFinal == null || cadenaFechaFinal.isEmpty())
            fechaFinal  = sdt.parse("9999/12/31");
        else
            fechaFinal = sdt.parse(cadenaFechaFinal.replace("-", "/"));



        //---------OBTENER USUARIOS----------
        Set<Usuario> listaUsuarios = new HashSet<>();//Evitamos repeticiones
        String autoGeneradoAnalisisDe = "";
        String autoGeneradoTiposFiltros = "";
        if(tipoUsuario.contains(ADMINISTRADORES)){
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosAdministradores(fechaInicial, fechaFinal));
            autoGeneradoAnalisisDe+=" Administradores,";
        }
        if(tipoUsuario.contains(ANALISTAS)){
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosAnalistas(fechaInicial, fechaFinal));
            autoGeneradoAnalisisDe+=" Analistas,";
        }
        if(tipoUsuario.contains(USUARIOEVENTOS)){
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosEventos(fechaInicial, fechaFinal));
            autoGeneradoAnalisisDe+=" Usuario Eventos,";
        }
        if(tipoUsuario.contains(CREADOREVENTOS)){
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosCreadoresEventos(fechaInicial, fechaFinal));
            autoGeneradoAnalisisDe+=" Creadores de Eventos,";
        }
        if(tipoUsuario.contains(TELEOPERADORES)){
            listaUsuarios.addAll(this.usuarioRepository.getUsuariosTeleoperadores(fechaInicial, fechaFinal));
            autoGeneradoAnalisisDe+=" Teleoperadores,";
        }
        autoGeneradoAnalisisDe = autoGeneradoAnalisisDe.substring(0, autoGeneradoAnalisisDe.lastIndexOf(","));




        List<TipoanalisisDTO> listaTipos = new ArrayList<>();

        //--------BUSQUEDA POR FILTROS DE LA TABLA USUARIO---------

        if(!listaUsuarios.isEmpty() && tipoFiltroUsuario!=null && !tipoFiltroUsuario.isEmpty()){
            autoGeneradoTiposFiltros+=" (Tabla Usuario join Tablas de roles) -> ";
            if(tipoFiltroUsuario.contains(FILTRONUMUSUARIOS)){

                List<CampoanalisisDTO> caDtoLista = new ArrayList<>();
                CampoanalisisDTO caDto = new CampoanalisisDTO("Num. Usuarios", (double)listaUsuarios.size());
                caDtoLista.add(caDto);
                TipoanalisisDTO taDto = new TipoanalisisDTO("Número de Usuarios", caDtoLista);
                listaTipos.add(taDto);

                autoGeneradoTiposFiltros+=" número de usuarios totales,";
            }

            if(tipoFiltroUsuario.contains(FILTROSEXO)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupBySexo(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Sexos de los Usuarios");

                autoGeneradoTiposFiltros+=" sexos, ";
            }

            if(tipoFiltroUsuario.contains(FILTROCIUDAD)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCities(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Ciudades de los Usuarios");

                autoGeneradoTiposFiltros+=" ciudades, ";
            }

            if(tipoFiltroUsuario.contains(FILTRONOMBRE)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByName(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Nombres de los Usuarios");

                autoGeneradoTiposFiltros+=" nombres, ";
            }

            if(tipoFiltroUsuario.contains(FILTROAPELLIDO)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByLastName(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Apellidos de los Usuarios");
                autoGeneradoTiposFiltros+=" apellidos, ";
            }

            if(tipoFiltroUsuario.contains(FILTROFECHAPORANYOS)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCreatedYear(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Fecha Creacion de los Usuarios (Año)");
                autoGeneradoTiposFiltros+=" fecha por años, ";
            }

            if(tipoFiltroUsuario.contains(FILTROFECHAPORMES)){
                List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCreatedMonthYear(listaUsuarios);
                anyadirEnListaTipos(listaTipos, listaPar, "Fecha Creacion de los Usuarios (Mes / Año)");
                autoGeneradoTiposFiltros+=" fecha por meses-años, ";
            }

            autoGeneradoTiposFiltros = autoGeneradoTiposFiltros.substring(0, autoGeneradoTiposFiltros.lastIndexOf(",")) + ".";
        } else if(listaUsuarios.isEmpty()){
            autoGeneradoTiposFiltros+=" (Tabla Usuario join Tablas de roles) -> Sin resultados. ";
        }




        //--------BUSQUEDA POR FILTROS DE LA TABLA EVENTOS---------
        if(tipoFiltroEvento !=null && !tipoFiltroEvento.isEmpty()){
            autoGeneradoTiposFiltros+=" (Tabla Evento) -> ";
            if(tipoFiltroEvento.contains(FILTRONUMEVENTOS)){
                List<CampoanalisisDTO> caDtoLista = new ArrayList<>();
                CampoanalisisDTO caDto = new CampoanalisisDTO("Num. Eventos", (double)this.eventoRepository.count());
                caDtoLista.add(caDto);
                TipoanalisisDTO taDto = new TipoanalisisDTO("Número de Eventos", caDtoLista);
                listaTipos.add(taDto);

                autoGeneradoTiposFiltros+=" número de eventos totales,";
            }

            if(tipoFiltroEvento.contains(FILTROTITULOEVENTO)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupByTitulos();
                anyadirEnListaTipos(listaTipos, listaPar, "Títulos de los Eventos");

                autoGeneradoTiposFiltros+=" título, ";
            }

            if(tipoFiltroEvento.contains(FILTROFECHAEVENTOMESYANYOS)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupFechaDelEventoMesYAnyo();
                anyadirEnListaTipos(listaTipos, listaPar, "Fechas de los Eventos (Mes / Año)");

                autoGeneradoTiposFiltros+=" fecha mes-año, ";
            }

            if(tipoFiltroEvento.contains(FILTROFECHAEVENTOANYOS)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupFechaDelEventoAnyo();
                anyadirEnListaTipos(listaTipos, listaPar, "Fechas de los Eventos (Año)");

                autoGeneradoTiposFiltros+=" fecha año, ";
            }

            if(tipoFiltroEvento.contains(FILTROCOSTEEVENTO)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupCosteEvento();
                listaPar.add(this.eventoRepository.getNumEventosGroupCosteEventoSinEspecificar());
                anyadirEnListaTipos(listaTipos, listaPar, "Costes de los Eventos");

                autoGeneradoTiposFiltros+=" coste, ";
            }

            if(tipoFiltroEvento.contains(FILTROAFOROEVENTO)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupAforo();
                listaPar.add(this.eventoRepository.getNumEventosGroupAforoSinEspecificar());
                anyadirEnListaTipos(listaTipos, listaPar, "Aforo de los Eventos");

                autoGeneradoTiposFiltros+=" aforo, ";
            }

            if(tipoFiltroEvento.contains(FILTROMAXENTRADASEVENTO)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupMaxEntradas();
                listaPar.add(this.eventoRepository.getNumEventosGroupMaxEntradasSinEspecificar());
                anyadirEnListaTipos(listaTipos, listaPar, "Máximo de entradas por usuario de los Eventos");

                autoGeneradoTiposFiltros+=" max. entradas, ";
            }

            if(tipoFiltroEvento.contains(FILTROASIENTOSFIJOSEVENTO)){
                List<Par<?, ?>> listaPar = this.eventoRepository.getNumEventosGroupAsientosFijos();
                anyadirEnListaTipos(listaTipos, listaPar, "Asientos fijos de los eventos");

                autoGeneradoTiposFiltros+=" asientos fijos, ";
            }

            autoGeneradoTiposFiltros = autoGeneradoTiposFiltros.substring(0, autoGeneradoTiposFiltros.lastIndexOf(","));


        }




        System.out.println(autoGeneradoTiposFiltros);

        autoGenerado.setPrimerElem(autoGeneradoAnalisisDe);
        autoGenerado.setSegundoElem(autoGeneradoTiposFiltros);

        return listaTipos;
    }

    private void anyadirEnListaTipos(List<TipoanalisisDTO> listaTipos, List<Par<?,?>> listaPar, String nombreTabla) {
        List<CampoanalisisDTO> caDtoLista = new ArrayList<>();
        for(Par<?,?> p : listaPar){
            CampoanalisisDTO caDto = new CampoanalisisDTO(p.getPrimerElemYExtraElemLikeString(), p.getSegundoElemLikeDouble());
            caDtoLista.add(caDto);
        }
        TipoanalisisDTO taDto = new TipoanalisisDTO(nombreTabla, caDtoLista);
        listaTipos.add(taDto);
    }

    public void guardarAnalisis(List<TipoanalisisDTO> listaTablas, String descripcion, HttpSession session) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);
        Analisis nuevoAnalisis = new Analisis();
        nuevoAnalisis.setDescripcion(descripcion);
        nuevoAnalisis.setAnalistaUsuarioId(thisAnalista);
        this.analisisRepository.save(nuevoAnalisis);

        List<Tipoanalisis> listaTiposAnalisis = new ArrayList<>();
        for(TipoanalisisDTO thisTipoAnalisisDto : listaTablas){
            Tipoanalisis nuevoTipoAnalisis = new Tipoanalisis();
            nuevoTipoAnalisis.setNombre(thisTipoAnalisisDto.getNombre());
            nuevoTipoAnalisis.setAnalisisId(nuevoAnalisis);
            this.tipoanalisisRepository.save(nuevoTipoAnalisis);
            List<Campoanalisis> listaCamposAnalisis = new ArrayList<>();
            for(CampoanalisisDTO thisCampoAnalisisDto : thisTipoAnalisisDto.getCampoanalisisList()){
                CampoanalisisPK capk = new CampoanalisisPK();
                capk.setNombre(thisCampoAnalisisDto.getNombre());
                capk.setTipoanalisisId(nuevoTipoAnalisis.getId());

                Campoanalisis nuevoCampoanalisis = new Campoanalisis();
                nuevoCampoanalisis.setCampoanalisisPK(capk);
                nuevoCampoanalisis.setValor(thisCampoAnalisisDto.getValor());
                nuevoCampoanalisis.setTipoanalisis(nuevoTipoAnalisis);
                listaCamposAnalisis.add(nuevoCampoanalisis);
            }
            nuevoTipoAnalisis.setCampoanalisisList(listaCamposAnalisis);
            this.tipoanalisisRepository.save(nuevoTipoAnalisis);
            listaTiposAnalisis.add(nuevoTipoAnalisis);
        }
        nuevoAnalisis.setTipoanalisisList(listaTiposAnalisis);
        this.analisisRepository.save(nuevoAnalisis);
    }

    public List<AnalisisDTO> obtenerListaAnalisisLazy(HttpSession session) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        List<Analisis> listaAnalisis = this.analisisRepository.findAnalisisByAnalistaUsuarioId(thisAnalista);
        List<AnalisisDTO> listaAnalisisDto = new ArrayList<>();
        for(Analisis a : listaAnalisis){
            listaAnalisisDto.add(a.getAnalisisDtoLazy());
        }

        return listaAnalisisDto;
    }

    public void eliminarAnalisis(HttpSession session, Integer analisisId) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        Analisis thisAnalisis = this.comprobarOptional(this.analisisRepository.findById(analisisId));
        if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
            throw new AutenticacionException("Error: No puedes eliminar un análisis el cual no eres dueño");

        this.analisisRepository.delete(thisAnalisis);

    }

    public AnalisisDTO obtenerAnalisis(HttpSession session, Integer analisisId) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        Analisis thisAnalisis = this.comprobarOptional(this.analisisRepository.findById(analisisId));
        if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
            throw new AutenticacionException("Error: No puedes ver un análisis el cual no eres dueño");

        return thisAnalisis.getAnalisisDto();
    }

    public TipoanalisisDTO obtenerTipoanalisis(HttpSession session, Integer tipoanalisisId) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        Tipoanalisis thisTipoanalisis = this.comprobarOptional(this.tipoanalisisRepository.findById(tipoanalisisId));
        if(!thisTipoanalisis.getAnalisisId().getAnalistaUsuarioId().equals(thisAnalista))
            throw new AutenticacionException("Error: No puedes mostrar el menú de edición un analisis el cual no eres dueño");

        return thisTipoanalisis.getTipoanalisisDto();

    }

    @Transactional
    public AnalisisDTO editarTipoanalisis(HttpSession session, Integer tipoanalisisId, List<String> listaNombres, List<Double> listaValores) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        Tipoanalisis thisTipoanalisis = this.comprobarOptional(this.tipoanalisisRepository.findById(tipoanalisisId));
        if(!thisTipoanalisis.getAnalisisId().getAnalistaUsuarioId().equals(thisAnalista))
            throw new AutenticacionException("Error: No puedes editar los datos de un análisis el cual no eres dueño");

        this.campoanalisisRepository.deleteCampoanalisisByTipoanalisis(thisTipoanalisis);
        List<Campoanalisis> listaCampoanalisis = new ArrayList<>();
        for(int i=0;i<listaNombres.size();i++){
            CampoanalisisPK capk = new CampoanalisisPK();
            capk.setTipoanalisisId(tipoanalisisId);
            capk.setNombre(listaNombres.get(i));
            Campoanalisis ca = new Campoanalisis();
            ca.setValor(listaValores.get(i));
            ca.setCampoanalisisPK(capk);
            ca.setTipoanalisis(thisTipoanalisis);
            listaCampoanalisis.add(ca);
        }
        thisTipoanalisis.setCampoanalisisList(listaCampoanalisis);
        this.tipoanalisisRepository.save(thisTipoanalisis);

        return thisTipoanalisis.getAnalisisId().getAnalisisDto();

    }


    public void editarAnalisis(HttpSession session, AnalisisDTO thisAnalisisDtoEditado) throws AutenticacionException {
        Analista thisAnalista = this.obtenerAnalistaLogeado(session);

        Analisis thisAnalisis = this.comprobarOptional(this.analisisRepository.findById(thisAnalisisDtoEditado.getId()));
        if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
            throw new AutenticacionException("Error: No puedes editer un análisis el cual no eres dueño");

        //Por ahora lo unico modificable es la descripcion
        thisAnalisis.setDescripcion(thisAnalisisDtoEditado.getDescripcion());
        this.analisisRepository.save(thisAnalisis);
    }

    public void duplicarAnalisis(HttpSession session, AnalisisDTO thisAnalisisDtoDuplicado) throws AutenticacionException {
        AnalisisDTO analisisDtoOriginal = this.obtenerAnalisis(session, thisAnalisisDtoDuplicado.getId());
        this.guardarAnalisis(analisisDtoOriginal.getTipoanalisisList(), thisAnalisisDtoDuplicado.getDescripcion(), session);
    }


    private <T> T comprobarOptional(Optional<T> valorOptional){
        if(valorOptional.isPresent()){
            return valorOptional.get();
        } else{
            throw new NullPointerException("El análisis especificado no ha sido encontrado");
        }
    }


    private Analista obtenerAnalistaLogeado(HttpSession session) throws AutenticacionException {
        //Propaga una excepcion
        this.comprobarLogeadoYConRolAnalista(session);

        UsuarioDTO thisUsuario = Autenticacion.getUsuarioLogeado(session);
        return this.analistaRepository.getById(thisUsuario.getId());
    }

    public void comprobarLogeadoYConRolAnalista(HttpSession session) throws AutenticacionException {
        if(!Autenticacion.tieneRol(session, Analista.class))
            throw new AutenticacionException("Error: Necesitas estar logeado y/o poseer rol de Analista.");
    }

}
