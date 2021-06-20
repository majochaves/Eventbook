package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.clases.Tupla;
import com.eventbookspring.eventbookspring.dto.AnalisisDTO;
import com.eventbookspring.eventbookspring.dto.CampoanalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.repository.AnalisisRepository;
import com.eventbookspring.eventbookspring.repository.CampoanalisisRepository;
import com.eventbookspring.eventbookspring.repository.TipoanalisisRepository;
import com.eventbookspring.eventbookspring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AnalisisRepository analisisRepository;
    private TipoanalisisRepository tipoanalisisRepository;
    private CampoanalisisRepository campoanalisisRepository;


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



    public List<TipoanalisisDTO> generarAnalisis(
            List<String> tipoUsuario,
            List<String> tipoFiltro,
            String cadenaFechaInicial,
            String cadenaFechaFinal,
            Par<String, String> autoGenerado) throws ParseException {


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

        if(listaUsuarios.isEmpty())
            throw new NullPointerException("No se han encontrado usuarios para realizar un análisis. Prueba otro filtro o en otra fecha.");


        //--------BUSQUEDA POR FILTROS---------
        Map<String, Map<String, Double>> listaTablas = new HashMap<>();
        List<TipoanalisisDTO> listaTipos = new ArrayList<>();
        if(tipoFiltro.contains(FILTRONUMUSUARIOS)){

            List<CampoanalisisDTO> caDtoLista = new ArrayList<>();
            CampoanalisisDTO caDto = new CampoanalisisDTO("Num", (double)listaUsuarios.size());
            caDtoLista.add(caDto);
            TipoanalisisDTO taDto = new TipoanalisisDTO("Número de Usuarios", caDtoLista);
            listaTipos.add(taDto);

            autoGeneradoTiposFiltros+=" número de usuarios totales,";
        }

        if(tipoFiltro.contains(FILTROSEXO)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupBySexo(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Sexo");

            autoGeneradoTiposFiltros+=" sexos, ";
        }

        if(tipoFiltro.contains(FILTROCIUDAD)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCities(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Ciudad");

            autoGeneradoTiposFiltros+=" ciudades, ";
        }

        if(tipoFiltro.contains(FILTRONOMBRE)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByName(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Nombre");

            autoGeneradoTiposFiltros+=" nombres, ";
        }

        if(tipoFiltro.contains(FILTROAPELLIDO)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByLastName(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Apellido");
            autoGeneradoTiposFiltros+=" apellidos, ";
        }

        if(tipoFiltro.contains(FILTROFECHAPORANYOS)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCreatedYear(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Fecha Creacion (Año)");
            autoGeneradoTiposFiltros+=" fecha por años, ";
        }

        if(tipoFiltro.contains(FILTROFECHAPORMES)){
            List<Par<?, ?>> listaPar = this.usuarioRepository.getNumUsuariosGroupByCreatedMonthYear(listaUsuarios);
            anyadirEnListaTipos(listaTipos, listaPar, "Fecha Creacion (Mes / Año)");
            autoGeneradoTiposFiltros+=" fecha por meses-años, ";
        }

        autoGeneradoTiposFiltros = autoGeneradoTiposFiltros.substring(0, autoGeneradoTiposFiltros.lastIndexOf(","));
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

    public void guardarAnalisis(List<TipoanalisisDTO> listaTablas, String descripcion, Analista thisAnalista){
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

    public List<AnalisisDTO> obtenerListaAnalisisLazy(Analista thisAnalista){
        List<Analisis> listaAnalisis = this.analisisRepository.findAnalisisByAnalistaUsuarioId(thisAnalista);
        List<AnalisisDTO> listaAnalisisDto = new ArrayList<>();
        for(Analisis a : listaAnalisis){
            listaAnalisisDto.add(a.getAnalisisDtoLazy());
        }

        return listaAnalisisDto;

    }

    public void eliminarAnalisis(Analista thisAnalista, Integer analisisId){
        Optional<Analisis> thisAnalisisOpt = this.analisisRepository.findById(analisisId);
        if(thisAnalisisOpt.isPresent()){
            Analisis thisAnalisis = thisAnalisisOpt.get();
            if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
                throw new RuntimeException("Error: No puedes eliminar un analisis el cual no eres dueño");

            this.analisisRepository.delete(thisAnalisis);
        } else{
            throw new NullPointerException("El analisis especificado no ha sido encontrado");
        }
    }

    public AnalisisDTO obtenerAnalisis(Analista thisAnalista, Integer analisisId){
        Optional<Analisis> thisAnalisisOpt = this.analisisRepository.findById(analisisId);
        if(thisAnalisisOpt.isPresent()){
            Analisis thisAnalisis = thisAnalisisOpt.get();
            if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
                throw new RuntimeException("Error: No puedes ver un analisis el cual no eres dueño");

            return thisAnalisis.getAnalisisDto();
        } else{
            throw new NullPointerException("El analisis especificado no ha sido encontrado");
        }
    }

    public TipoanalisisDTO obtenerTipoanalisis(Analista thisAnalista, Integer tipoanalisisId){
        Optional<Tipoanalisis> thisTipoanalisisOpt = this.tipoanalisisRepository.findById(tipoanalisisId);
        if(thisTipoanalisisOpt.isPresent()){
            Tipoanalisis thisTipoanalisis = thisTipoanalisisOpt.get();
            if(!thisTipoanalisis.getAnalisisId().getAnalistaUsuarioId().equals(thisAnalista))
                throw new RuntimeException("Error: No puedes mostrar el menú de edición un analisis el cual no eres dueño");

            return thisTipoanalisis.getTipoanalisisDto();
        } else{
            throw new NullPointerException("El analisis especificado no ha sido encontrado");
        }
    }

    @Transactional
    public AnalisisDTO editarTipoanalisis(Analista thisAnalista, Integer tipoanalisisId, List<String> listaNombres, List<Double> listaValores){
        Optional<Tipoanalisis> thisTipoanalisisOpt = this.tipoanalisisRepository.findById(tipoanalisisId);
        if(thisTipoanalisisOpt.isPresent()){
            Tipoanalisis thisTipoanalisis = thisTipoanalisisOpt.get();
            if(!thisTipoanalisis.getAnalisisId().getAnalistaUsuarioId().equals(thisAnalista))
                throw new RuntimeException("Error: No puedes editar un analisis el cual no eres dueño");

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

        } else{
            throw new NullPointerException("El analisis especificado no ha sido encontrado");
        }
    }


    public void editarAnalisis(Analista thisAnalista, AnalisisDTO thisAnalisisDtoEditado){
        Optional<Analisis> thisAnalisisOpt = this.analisisRepository.findById(thisAnalisisDtoEditado.getId());
        if(thisAnalisisOpt.isPresent()){
            Analisis thisAnalisis = thisAnalisisOpt.get();
            if(!thisAnalisis.getAnalistaUsuarioId().equals(thisAnalista))
                throw new RuntimeException("Error: No puedes ver un análisis el cual no eres dueño");

            //Por ahora lo unico modificable es la descripcion
            thisAnalisis.setDescripcion(thisAnalisisDtoEditado.getDescripcion());
            this.analisisRepository.save(thisAnalisis);
        } else{
            throw new NullPointerException("El análisis especificado no ha sido encontrado");
        }
    }

    public void duplicarAnalisis(Analista thisAnalista, AnalisisDTO thisAnalisisDtoDuplicado){
        AnalisisDTO analisisDtoOriginal = this.obtenerAnalisis(thisAnalista, thisAnalisisDtoDuplicado.getId());
        this.guardarAnalisis(analisisDtoOriginal.getTipoanalisisList(), thisAnalisisDtoDuplicado.getDescripcion(), thisAnalista);
    }
}
