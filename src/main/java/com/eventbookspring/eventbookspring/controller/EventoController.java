package com.eventbookspring.eventbookspring.controller;

import com.eventbookspring.eventbookspring.dto.EventoDTO;
import com.eventbookspring.eventbookspring.entity.*;
import com.eventbookspring.eventbookspring.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EventoController {

    private EventoService eventoService;
    private EtiquetaService etiquetaService;
    private CreadorEventosService creadoreventosService;
    private UsuarioService usuarioService;
    private ReservaService reservaService;

    @Autowired
    public void setReservaService(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Autowired
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    public void setCreadoreventosService(CreadorEventosService creadoreventosService) {
        this.creadoreventosService = creadoreventosService;
    }

    @Autowired
    public void setEtiquetaService(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @Autowired
    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/eventos")
    public String doEventoListar(Model model){
        List<EventoDTO> eventoList = null;
        eventoList = this.eventoService.listarEventos();
        model.addAttribute("eventoList", eventoList);
        return "evento_list";
    }

    @PostMapping("/eventoGuardar")
    public String doGuardar (Model model, @ModelAttribute("evento") EventoDTO evento, @RequestParam("creadorEventosId") Integer creadorId) throws ParseException {
        String strTo = null;
        String strError = "";
        EventoDTO eventoDTO = new EventoDTO();
        model.addAttribute("evento", eventoDTO);
        List<Etiqueta> etiquetaList = this.etiquetaService.listarEtiquetas();
        model.addAttribute("etiquetaList", etiquetaList);
        if(evento.getTitulo() == null || evento.getTitulo().isEmpty() ||
            evento.getFecha() == null || evento.getFecha().isEmpty() || (evento.getAsientosFijos() == 'S' &&
                (evento.getNumFilas() == null || evento.getNumAsientosFila() == null))){
            strTo = "evento";
            strError = "Error: Por favor rellene todos los campos obligatorios.";
            model.addAttribute("strError", strError);
        }else if(evento.getFechaLimite() != null && !evento.getFechaLimite().isEmpty() && (new SimpleDateFormat("yyyy-MM-dd").parse(evento.getFechaLimite()).compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(evento.getFecha())) == 1)){
            strError = "Error: La fecha límite para comprar entradas debe ser anterior a la fecha del evento.";
            model.addAttribute("strError", strError);
            strTo = "evento";
        }else if((evento.getAforo() != null && evento.getAforo() < 0) ||
                (evento.getCosteEntrada() != null && evento.getCosteEntrada() < 0) ||
                (evento.getMaxEntradas() != null && evento.getMaxEntradas() < 0) ||
                (evento.getNumFilas() != null && evento.getNumFilas() < 0) ||
                (evento.getNumAsientosFila() != null && evento.getNumAsientosFila() < 0)){
            strError = "Error: Por favor introduzca valores numéricos positivos.";
            model.addAttribute("strError", strError);
            strTo = "evento";
        }else{
            Creadoreventos creadoreventos = this.creadoreventosService.getUserById(creadorId);
            evento.setCreadoreventos(creadoreventos);
            Evento e = this.eventoService.guardarEvento(evento);
            strTo = "redirect:/verEvento/"+e.getId();
        }
        return strTo;
    }

    @GetMapping("/crearEvento")
    public String doCrearEvento(Model model){
        List<Etiqueta> etiquetaList = this.etiquetaService.listarEtiquetas();
        EventoDTO evento = new EventoDTO();
        model.addAttribute("evento", evento);
        model.addAttribute("etiquetaList", etiquetaList);
        return "evento";
    }

    @GetMapping("/verEvento/{id}")
    public String doVerEvento (@PathVariable("id") Integer id, Model model) {
        EventoDTO evento = this.eventoService.getEventoById(id);
        model.addAttribute("evento", evento);
        return "evento_info";
    }

    @GetMapping("/editarEvento/{id}")
    public String doEditarEvento(@PathVariable("id") Integer id, Model model) {
        EventoDTO evento = this.eventoService.getEventoById(id);
        model.addAttribute("evento", evento);
        List<Etiqueta> etiquetaList = this.etiquetaService.listarEtiquetas();
        model.addAttribute("etiquetaList", etiquetaList);
        return "evento";
    }

    @GetMapping("/borrarEvento/{id}")
    public String doBorrarEvento(@PathVariable("id") Integer id) {
        this.eventoService.borrarEvento(id);
        return "redirect:/eventos";
    }

    @GetMapping("/reservarEvento/{id}")
    public String doReservarEvento(@PathVariable("id") Integer id, Model model) {
        EventoDTO evento = this.eventoService.getEventoById(id);
        model.addAttribute("evento", evento);
        return "evento_reservar";
    }

    @PostMapping("/crearReserva")
    public String doCrearReserva(@RequestParam("id") Integer eventoId, @RequestParam("numEntradas") Integer numEntradas,
    @RequestParam("usuarioId") Integer usuarioId, @RequestParam("editar") String editar, Model model) {
        String strTo = null;
        String strError = "";
        EventoDTO evento = this.eventoService.getEventoById(eventoId);
        Usuario usuario = this.usuarioService.getUsuarioById(usuarioId);

        if(!editar.isEmpty()){
            List<Reserva> reservas = this.eventoService.getReservasDeUsuario(usuario, evento.getId());
            model.addAttribute("numEntradas", evento.getEntradasReservadas(usuario));
            model.addAttribute("usuario", usuario);
            model.addAttribute("evento", evento);
            model.addAttribute("reservas", reservas);
            model.addAttribute("editar", "editar");
        }

        int entradasReservadas = evento.getEntradasReservadas(usuario);

        if(evento.getMaxEntradas() != null && (evento.getMaxEntradas() - entradasReservadas) < numEntradas && editar.isEmpty()){
            if((evento.getMaxEntradas() - entradasReservadas) <= 0){
                strError = "Error: Ya no puedes reservar más entradas para este evento";
            }else{
                strError = "Error: Sólo puedes reservar " + (evento.getMaxEntradas() - entradasReservadas) + " entradas más para este evento.";
            }
            model.addAttribute("strError", strError);
            model.addAttribute("evento", evento);

            strTo = "evento_reservar";

        }else if(evento.getMaxEntradas() != null && numEntradas > evento.getMaxEntradas()){
            strError = "Error. Sólo se pueden reservar " + evento.getMaxEntradas() + " entradas";
            model.addAttribute("strError", strError);
            model.addAttribute("evento", evento);

            strTo = "evento_reservar";

        }else if(evento.getAsientosDisponibles() < numEntradas){
            strError = "Error: Sólo hay " + evento.getAsientosDisponibles() + " asientos disponibles.";
            model.addAttribute("strError", strError);
            model.addAttribute("evento", evento);

            strTo = "evento_reservar";

        }else{
            if(!editar.isEmpty()){
                List<Reserva> reservasUsuario = this.eventoService.getReservasDeUsuario(usuario, evento.getId());
                for(Reserva r: reservasUsuario){
                    this.eventoService.borrarReserva(eventoId, r);
                    this.reservaService.borrarReserva(r);
                }
            }
            if(evento.getAsientosFijos() == 'S'){
                int[][] matrizAsientos = evento.getMatrizAsientos();
                model.addAttribute("matrizAsientos", matrizAsientos);
                model.addAttribute("evento", evento);
                model.addAttribute("usuario", usuario);
                model.addAttribute("numEntradas", numEntradas);
                strTo = "seleccionar_asientos";
            }else{
                List<Reserva> listaReservas = evento.getReservaList();
                for(int i = 0; i < numEntradas; i++){
                    int numAsiento = 0;
                    if(listaReservas != null){
                        numAsiento = listaReservas.size() + 1;
                    }
                    Reserva reserva = this.reservaService.crearReserva(1, numAsiento, evento.getId(), usuario);
                    this.eventoService.agregarReserva(eventoId, reserva);
                }
                strTo = "redirect:/verEvento/" + eventoId;
            }
        }
        return strTo;
    }

    @PostMapping("reservarAsientos")
    public String doCrearReserva(@RequestParam("eventoId") Integer eventoId, @RequestParam("numEntradas") Integer numEntradas,
    @RequestParam("usuarioId") Integer usuarioId, @RequestParam("asientosSeleccionados")String[] asientosSeleccionados, Model model) {
        EventoDTO evento = this.eventoService.getEventoById(eventoId);
        Usuario usuario = this.usuarioService.getUsuarioById(usuarioId);

        for(int i = 0; i < asientosSeleccionados.length; i++){
            String filaAsiento = asientosSeleccionados[i];
            String fila = filaAsiento.substring( 0, filaAsiento.indexOf(":"));
            String asiento = filaAsiento.substring(filaAsiento.indexOf(":")+1, filaAsiento.length());
            System.out.println("i = " + i + " . filaAsiento = " + filaAsiento + ". fila = " + fila + ". asiento = " + asiento);
            Reserva reserva = this.reservaService.crearReserva(Integer.valueOf(fila), Integer.valueOf(asiento), evento.getId(), usuario);
            this.eventoService.agregarReserva(eventoId, reserva);
        }

        return "redirect:/verEvento/" + eventoId;
    }

    @GetMapping("verReservas/{usuarioId}")
    public String doVerReservas (@PathVariable("usuarioId") Integer usuarioId, Model model){
        Usuario u = this.usuarioService.getUsuarioById(usuarioId);
        List<EventoDTO> eventosUsuario = this.eventoService.getEventosDeUsuario(u);
        List<Etiqueta> etiquetaList = this.etiquetaService.listarEtiquetas();
        model.addAttribute("etiquetaList", etiquetaList);
        model.addAttribute("eventos", eventosUsuario);
        model.addAttribute("usuario", u);
        return "reservas_listar";
    }

    @GetMapping("verReservasFiltradas/{usuarioId}")
    public String doVerReservasFiltradas (@PathVariable("usuarioId") Integer usuarioId, @RequestParam("etiqueta") Integer etiqueta, Model model){
        Usuario u = this.usuarioService.getUsuarioById(usuarioId);
        List<EventoDTO> eventosUsuario = this.eventoService.getEventosDeUsuarioConFiltro(u, etiqueta);
        List<Etiqueta> etiquetaList = this.etiquetaService.listarEtiquetas();
        model.addAttribute("etiquetaList", etiquetaList);
        model.addAttribute("eventos", eventosUsuario);
        model.addAttribute("usuario", u);
        return "reservas_listar";

    }

    @GetMapping("editarReserva/{eventoId}/{usuarioId}")
    public String doEditarReserva (@PathVariable("eventoId") Integer eventoId, @PathVariable("usuarioId") Integer usuarioId, Model model){
        Usuario u = this.usuarioService.getUsuarioById(usuarioId);
        EventoDTO evento = this.eventoService.getEventoById(eventoId);
        List<Reserva> reservas = this.eventoService.getReservasDeUsuario(u, evento.getId());
        model.addAttribute("numEntradas", evento.getEntradasReservadas(u));
        model.addAttribute("usuario", u);
        model.addAttribute("evento", evento);
        model.addAttribute("reservas", reservas);
        model.addAttribute("editar", "editar");
        return "evento_reservar";
    }

    @GetMapping("eliminarReserva/{eventoId}/{usuarioId}")
    public String doEliminarReserva(@PathVariable("eventoId") Integer eventoId, @PathVariable("usuarioId") Integer usuarioId, Model model){
        Usuario u = this.usuarioService.getUsuarioById(usuarioId);
        EventoDTO evento = this.eventoService.getEventoById(eventoId);
        List<Reserva> reservas = this.eventoService.getReservasDeUsuario(u, evento.getId());
        for(Reserva r: reservas){
            this.eventoService.borrarReserva(eventoId, r);
            this.reservaService.borrarReserva(r);
        }
        return "redirect:/verReservas/"+u.getId();
    }
}
