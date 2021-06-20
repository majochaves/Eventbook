package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.clases.Par;
import com.eventbookspring.eventbookspring.clases.Tupla;
import com.eventbookspring.eventbookspring.dto.AnalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;
import com.eventbookspring.eventbookspring.entity.Analista;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.service.AnalistaService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            return "analisisIndex";
        } else{
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }

    }

    //-----------------Creacion de Analisis-----------------
    @GetMapping("/crear/mostrar")
    public String  mostrarCrearAnalisis(Model model, HttpSession session){
        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            return "analisisMostrarCrear";
        } else{
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
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

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        String dest;
        if(thisAnalista != null) {
            if((tipoUsuario==null || tipoUsuario.isEmpty()) && (tipoFiltro==null || tipoFiltro.isEmpty())){
                model.addAttribute("muestraError", true);
                dest = "analisisMostrarCrear";  //Vuelve a la misma pagina con error lanzado
            } else {
                Par<String, String> autoGenerado = new Par<>("", "");
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
            }

            return dest;
        } else{
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }


    @PostMapping("/crear/guardar")
    public String guardarResultadosAnalisis(
            HttpSession session,
            Model model,
            @RequestParam("descripcion") String descripcion){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            List<TipoanalisisDTO> listaTablas =  (List<TipoanalisisDTO>) session.getAttribute("listaTablas");
            analistaService.guardarAnalisis(listaTablas, descripcion,thisAnalista);
            return "redirect:/analisis/";
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }

    }

    //-----------------Visualizacion de Analisis-----------------
    @GetMapping("/listar")
    public String listarAnalisis(Model model, HttpSession session){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {

            List<AnalisisDTO> listaAnalisis = this.analistaService.obtenerListaAnalisisLazy(thisAnalista);
            model.addAttribute("listaAnalisis", listaAnalisis);

            return "analisisListar";
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }

    @GetMapping("/ver/{id}")
    public String verAnalisis(Model model, HttpSession session, @PathVariable("id") Integer id){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            AnalisisDTO thisAnalisisDto = null;
            try {
                thisAnalisisDto = this.analistaService.obtenerAnalisis(thisAnalista, id);
            } catch (RuntimeException ex){
                return Autenticacion.getErrorJsp(model, ex.getMessage());
            }
            model.addAttribute("thisAnalisisDto", thisAnalisisDto);

            return "analisisVer";
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }

    //-----------------Eliminacion de Analisis-----------------
    @GetMapping("/eliminar/{id}")
    public String eliminarAnalisis(Model model, HttpSession session, @PathVariable("id") Integer id){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            try{
                this.analistaService.eliminarAnalisis(thisAnalista, id);
            } catch (RuntimeException ex){
                return Autenticacion.getErrorJsp(model, ex.getMessage());
            }

            return "redirect:/analisis/listar";
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }

    //-----------------Edicion de Analisis-----------------
    @GetMapping("/editar/tipoanalisis/mostrar/{id}")
    public String mostrarEditarTipoanalisis(Model model, HttpSession session, @PathVariable("id") Integer id){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            TipoanalisisDTO thisTipoanalisisDto = null;
            try{
                thisTipoanalisisDto = this.analistaService.obtenerTipoanalisis(thisAnalista, id);
            } catch (RuntimeException ex){
                return Autenticacion.getErrorJsp(model, ex.getMessage());
            }
            model.addAttribute("thisTipoanalisisDto", thisTipoanalisisDto);
            return "analisisEditarTipoanalisis";
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }

    @PostMapping("/editar/tipoanalisis/{id}")
    public String editarTipoanalisis(
            Model model,
            HttpSession session,
            @PathVariable("id") Integer id,
            @RequestParam("nombres") List<String> listaNombres,
            @RequestParam("valores") List<Double> listaValores){

        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            AnalisisDTO analisisDto = null;
            try{
                analisisDto = this.analistaService.editarTipoanalisis(thisAnalista, id, listaNombres, listaValores);
            } catch (RuntimeException ex){
                return Autenticacion.getErrorJsp(model, ex.getMessage());
            }

            return "redirect:/analisis/ver/" + analisisDto.getId();
        } else {
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }

    }


    private Analista obtenerAnalistaLogeado(HttpSession session){
        Analista thisAnalista = null;
        if(Autenticacion.tieneRol(session, Analista.class)){
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(session);
            thisAnalista = thisUsuario.getAnalista();
        }
        return thisAnalista;
    }

}
