package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.AutenticacionException;
import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.dto.AnalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;
import com.eventbookspring.eventbookspring.service.AnalistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/analisis")
public class AnalistaController {

    private AnalistaService analistaService;

    @Autowired
    public void setAnalistaService(AnalistaService analistaService) {
        this.analistaService = analistaService;
    }



    @GetMapping("/")
    public String index(Model model, HttpSession session){
        try {
            this.analistaService.comprobarLogeadoYConRolAnalista(session);
            return "analisisIndex";
        } catch (AutenticacionException ex) {
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }


    //-----------------Creacion de Analisis-----------------
    @GetMapping("/crear/mostrar")
    public String  mostrarCrearAnalisis(Model model, HttpSession session){
        try {
            this.analistaService.comprobarLogeadoYConRolAnalista(session);
            return "analisisMostrarCrear";
        } catch (AutenticacionException ex) {
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @PostMapping("/crear/generar")
    public String generarResultadosAnalisis(
            Model model,
            HttpSession session,
            @RequestParam("tipoUsuario") Optional<List<String>> tipoUsuarioOpt,
            @RequestParam("tipoFiltroUsuario") Optional<List<String>> tipoFiltroUsuarioOpt,
            @RequestParam("tipoFiltroEvento") Optional<List<String>> tipoFiltroEventoOpt,
            @RequestParam("fechaInicial") String cadenaFechaInicial,
            @RequestParam("fechaFinal") String cadenaFechaFinal) throws ParseException {

        List<String> tipoFiltroEvento = null;
        List<String> tipoFiltroUsuario = null;
        List<String> tipoUsuario = null;

        if(tipoFiltroEventoOpt.isPresent())
            tipoFiltroEvento = tipoFiltroEventoOpt.get();
        if(tipoFiltroUsuarioOpt.isPresent())
            tipoFiltroUsuario = tipoFiltroUsuarioOpt.get();
        if(tipoUsuarioOpt.isPresent())
            tipoUsuario = tipoUsuarioOpt.get();

        //Devuelve a la misma pagina con un mensaje de error lanzado
        if(tipoUsuario==null && tipoFiltroUsuario==null && tipoFiltroEvento == null){
            model.addAttribute("muestraError", true);
            return "analisisMostrarCrear";
        } else if((tipoUsuario==null && tipoFiltroUsuario!=null) || (tipoUsuario!=null && tipoFiltroUsuario==null)){
            model.addAttribute("muestraError", true);
            return "analisisMostrarCrear";
        }



        Par<String, String> autoGenerado = new Par<>("", "");
        try{
            this.analistaService.comprobarLogeadoYConRolAnalista(session);
            List<TipoanalisisDTO> listaTablas = this.analistaService.generarAnalisis(tipoUsuario,
                    tipoFiltroUsuario,
                    tipoFiltroEvento,
                    cadenaFechaInicial,
                    cadenaFechaFinal,
                    autoGenerado);

            session.setAttribute("listaTablas", listaTablas);
            model.addAttribute("listaTablas", listaTablas);
            model.addAttribute("AutoGeneradoAnalisisDe", autoGenerado.getPrimerElem());
            model.addAttribute("AutoGeneradoTiposFiltros", autoGenerado.getSegundoElem());
            return "analisisMostrarResultadosGenerados";
        }catch (AutenticacionException  | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());

        }

    }

    @PostMapping("/crear/guardar")
    public String guardarResultadosAnalisis(
            HttpSession session,
            Model model,
            @RequestParam("descripcion") String descripcion){

        try {
            List<TipoanalisisDTO> listaTablas = (List<TipoanalisisDTO>) session.getAttribute("listaTablas");
            analistaService.guardarAnalisis(listaTablas, descripcion, session);
            return "redirect:/analisis/";
        } catch(AutenticacionException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }



    //-----------------Visualizacion de Analisis-----------------
    @GetMapping("/listar")
    public String listarAnalisis(Model model, HttpSession session){
        try {
            List<AnalisisDTO> listaAnalisis = this.analistaService.obtenerListaAnalisisLazy(session);
            model.addAttribute("listaAnalisis", listaAnalisis);
            return "analisisListar";

        } catch (AutenticacionException ex) {
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @GetMapping("/ver/{id}")
    public String verAnalisis(Model model, HttpSession session, @PathVariable("id") Integer id){
        try {
            AnalisisDTO thisAnalisisDto = this.analistaService.obtenerAnalisis(session, id);
            model.addAttribute("thisAnalisisDto", thisAnalisisDto);
            return "analisisVer";

        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @GetMapping("/ver/graficas/{id}")
    public String verAnalisisGrafica(Model model, HttpSession session, @PathVariable("id") Integer id){
        try {
            AnalisisDTO thisAnalisisDto = this.analistaService.obtenerAnalisis(session, id);
            model.addAttribute("thisAnalisisDto", thisAnalisisDto);
            return "analisisVerGrafica";

        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }

    //-----------------Eliminacion de Analisis-----------------
    @GetMapping("/eliminar/{id}")
    public String eliminarAnalisis(Model model, HttpSession session, @PathVariable("id") Integer id){
        try{
            this.analistaService.eliminarAnalisis(session, id);
            return "redirect:/analisis/listar";
        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }



    //-----------------Edicion de Tipoanalisis-----------------
    @GetMapping("/editar/tipoanalisis/mostrar/{id}")
    public String mostrarEditarTipoanalisis(Model model, HttpSession session, @PathVariable("id") Integer id){
        try{
            TipoanalisisDTO thisTipoanalisisDto = this.analistaService.obtenerTipoanalisis(session, id);
            model.addAttribute("thisTipoanalisisDto", thisTipoanalisisDto);
            return "analisisEditarTipoanalisis";
        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @PostMapping("/editar/tipoanalisis/{id}")
    public String editarTipoanalisis(
            Model model,
            HttpSession session,
            @PathVariable("id") Integer id,
            @RequestParam("nombres") List<String> listaNombres,
            @RequestParam("valores") List<Double> listaValores){

        try{
            AnalisisDTO analisisDto = this.analistaService.editarTipoanalisis(session, id, listaNombres, listaValores);
            return "redirect:/analisis/ver/" + analisisDto.getId();
        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }
    }



    //-----------------Edicion de Analisis-----------------
    @PostMapping("/editar/analisis")
    public String editarAnalisis(
            Model model,
            HttpSession session,
            @ModelAttribute("thisAnalisisDto") AnalisisDTO thisAnalisisDto){

        try{
            this.analistaService.editarAnalisis(session, thisAnalisisDto);
            return "redirect:/analisis/ver/" + thisAnalisisDto.getId();
        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @PostMapping("/duplicar")
    public String duplicarAnalisis(
            Model model,
            HttpSession session,
            @ModelAttribute("thisAnalisisDto") AnalisisDTO thisAnalisisDto){

        try{
            this.analistaService.duplicarAnalisis(session, thisAnalisisDto);
            return "redirect:/analisis/listar";
        } catch (AutenticacionException | NullPointerException ex){
            return Autenticacion.getErrorJsp(model, ex.getMessage());
        }

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String getMissingRequestParameterException(MissingServletRequestParameterException ex, Model model){
        String nombreVariable = ex.getParameterName();

            model.addAttribute("error", "Error: Alg??n campo esta vac??o. Debe completar: " + ex.getParameterName());
            return "error";
    }


}
