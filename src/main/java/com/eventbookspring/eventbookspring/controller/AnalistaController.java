package com.eventbookspring.eventbookspring.controller;


import com.eventbookspring.eventbookspring.clases.Autenticacion;
import com.eventbookspring.eventbookspring.entity.Analista;
import com.eventbookspring.eventbookspring.entity.Usuario;
import com.eventbookspring.eventbookspring.service.AnalistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class AnalistaController {


    @Autowired
    private AnalistaService analistaService;

    public void setAnalistaService(AnalistaService analistaService) {
        this.analistaService = analistaService;
    }

    @GetMapping("/analisis")
    public String index(Model model, HttpSession session){
        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            return "analisisIndex";
        } else{
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }

    }

    @GetMapping("/analisis/crear/mostrar")
    public String  mostrarCrearAnalisis(Model model, HttpSession session){
        Analista thisAnalista = obtenerAnalistaLogeado(session);
        if(thisAnalista != null) {
            return "analisisMostrarCrear";
        } else{
            return Autenticacion.getErrorJsp(model, "Necesitas estar logeado y poseer rol de Analista");
        }
    }

    @PostMapping("/analisis/crear/generar")
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
                Map<String, Map<String, Double>> listaTablas = this.analistaService.generarAnalisis(tipoUsuario, tipoFiltro, cadenaFechaInicial, cadenaFechaFinal);
                model.addAttribute("listaTablas", listaTablas);
                model.addAttribute("AutoGeneradoAnalisisDe", "aunNada");
                model.addAttribute("AutoGeneradoTiposFiltros", "aunNada");
                dest = "analisisMostrarResultadosGenerados";
            }

            return dest;
        } else{
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
