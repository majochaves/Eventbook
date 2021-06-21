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
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

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
            @RequestParam("tipoUsuario") List<String> tipoUsuario,
            @RequestParam("tipoFiltro") List<String> tipoFiltro,
            @RequestParam("fechaInicial") String cadenaFechaInicial,
            @RequestParam("fechaFinal") String cadenaFechaFinal) throws ParseException {

        String dest;
        if((tipoUsuario==null || tipoUsuario.isEmpty()) && (tipoFiltro==null || tipoFiltro.isEmpty())){
            model.addAttribute("muestraError", true);
            dest = "analisisMostrarCrear";  //Vuelve a la misma pagina con error lanzado
        } else {
            Par<String, String> autoGenerado = new Par<>("", "");
            try{
                this.analistaService.comprobarLogeadoYConRolAnalista(session);
                List<TipoanalisisDTO> listaTablas = this.analistaService.generarAnalisis(tipoUsuario,
                        tipoFiltro,
                        cadenaFechaInicial,
                        cadenaFechaFinal,
                        autoGenerado);

                session.setAttribute("listaTablas", listaTablas);
                model.addAttribute("listaTablas", listaTablas);
                model.addAttribute("AutoGeneradoAnalisisDe", autoGenerado.getPrimerElem());
                model.addAttribute("AutoGeneradoTiposFiltros", autoGenerado.getSegundoElem());
                dest = "analisisMostrarResultadosGenerados";
            }catch (AutenticacionException  | NullPointerException ex){
                return Autenticacion.getErrorJsp(model, ex.getMessage());
            }

        }

        return dest;
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



}
