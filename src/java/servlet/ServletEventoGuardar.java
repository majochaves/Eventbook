/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.CreadoreventosFacade;
import dao.EtiquetaFacade;
import dao.EventoFacade;
import entity.Creadoreventos;
import entity.Etiqueta;
import entity.Evento;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author majochaves
 */
@WebServlet(name = "ServletEventoGuardar", urlPatterns = {"/ServletEventoGuardar"})
public class ServletEventoGuardar extends HttpServlet {
    
    @EJB
    private EventoFacade eventoFacade;
    
    @EJB
    private CreadoreventosFacade creadorEventosFacade;
    
    @EJB
    private EtiquetaFacade etiquetaFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        
        String id = request.getParameter("id");
        String usuarioId = request.getParameter("usuarioId");
        String error;
        
        Evento e;
        Creadoreventos creador;
        if(id == null || id.isEmpty()){
            System.out.print("Evento nuevo");
            e = new Evento();
            System.out.println("Id de usuario: " + usuarioId);
            creador = this.creadorEventosFacade.find(new Integer(usuarioId));
            e.setCreadoreventosId(creador);
        }else{
            System.out.println("Id de evento: " + id);
            e = this.eventoFacade.find(new Integer(id));
        }
        
        String titulo, descripcion, fecha, fechaLimite, costeEntrada, aforo, maxEntradas, 
            asientosFijos, numFilas, numAsientosFila;
        
        titulo = request.getParameter("titulo");
        descripcion = request.getParameter("descripcion");
        fecha = request.getParameter("fecha");
        fechaLimite = request.getParameter("fecha_limite");
        costeEntrada = request.getParameter("coste");
        aforo = request.getParameter("aforo");
        maxEntradas = request.getParameter("max_entradas");
        asientosFijos  = request.getParameter("asientos_fijos");
        numFilas = request.getParameter("num_filas");
        numAsientosFila = request.getParameter("num_asientos_fila");
        
        
        String[] etiquetas = request.getParameterValues("etiquetas");
        
        
        
        if(titulo.isEmpty() || fecha.isEmpty()){
            error = "Error: por favor rellene todos los campos obligatorios.";
            request.setAttribute("strError", error);
            RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
            rd.forward(request, response);
        }else{
            //TITULO - Obligatorio

            e.setTitulo(titulo);


            //DESCRIPCION - Opcional
            if(!descripcion.isEmpty() ){
                e.setDescripcion(descripcion);
            }

            //FECHA - Obligatorio
            e.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));

            //FECHA LIMITE - Opcional
            if(!fechaLimite.isEmpty()){
                e.setFechaLimite(new SimpleDateFormat("yyyy-MM-dd").parse(fechaLimite));
                if(e.getFecha().compareTo(e.getFechaLimite()) < 0){
                    error = "Error: fecha límite para comprar entradas tiene que ser anterior a la fecha del evento.";
                    request.setAttribute("strError", error);
                    RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                    rd.forward(request, response);
                }
            }
            //COSTE ENTRADA - Opcional
            if(!costeEntrada.isEmpty()){
                if(new Double(costeEntrada) < 0){
                    error = "Error: campos numéricos deben ser positivos.";
                    request.setAttribute("strError", error);
                    RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                    rd.forward(request, response);
                }
                e.setCosteEntrada(new Double(costeEntrada));
            }

            //AFORO - Opcional
            if(!aforo.isEmpty()){
                if(new Integer(aforo) < 0){
                    error = "Error: campos numéricos deben ser positivos.";
                    request.setAttribute("strError", error);
                    RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                    rd.forward(request, response);
                }
                e.setAforo(new Integer(aforo));
            }

            //MAX ENTRADAS - Opcional
            if(!maxEntradas.isEmpty()){
                if(new Integer(maxEntradas) < 0){
                    error = "Error: campos numéricos deben ser positivos.";
                    request.setAttribute("strError", error);
                    RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                    rd.forward(request, response);
                }
                if(!aforo.isEmpty()){
                    if(new Integer(aforo) < new Integer(maxEntradas)){
                        error = "Error: máximo número de entradas por usuario debe ser menor que el aforo.";
                        request.setAttribute("strError", error);
                        RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                        rd.forward(request, response);
                    }
                }
                e.setMaxEntradas(new Integer(maxEntradas));
            }

            if(etiquetas != null){
                for(int i = 0; i < etiquetas.length; i++){
                    Etiqueta etiqueta = this.etiquetaFacade.find(new Integer(etiquetas[i]));
                    etiqueta.getEventoList().add(e);
                    this.etiquetaFacade.edit(etiqueta);
                    e.getEtiquetaList().add(etiqueta);
                }
            }

            //ASIENTOS_FIJOS - Opcional
            if(asientosFijos.equals("si")){
                e.setAsientosFijos('s');
                //NUM FILAS - Opcional
                if(numFilas != null){
                    if(new Integer(numFilas) <= 0){
                        error = "Error: campos numéricos deben ser positivos.";
                        request.setAttribute("strError", error);
                        RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                        rd.forward(request, response);
                    }
                    e.setNumFilas(new Integer(numFilas));
                }

                //NUM ASIENTOS POR FILA - Opcional
                if(numAsientosFila != null){
                    if(new Integer(numAsientosFila) <= 0){
                        error = "Error: campos numéricos deben ser positivos.";
                        request.setAttribute("strError", error);
                        RequestDispatcher rd = request.getRequestDispatcher("ServletEventoCrear");
                        rd.forward(request, response);
                    }
                    e.setNumAsientosFila(Integer.parseInt(numAsientosFila));
                }
            }else{
                e.setAsientosFijos('n');
                e.setNumFilas(null);
                e.setNumAsientosFila(null);
            }

            if(id == null || id.isEmpty()){
                this.eventoFacade.create(e);
            }else{
                this.eventoFacade.edit(e);
            }

            response.sendRedirect("ServletEventoListar");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletEventoGuardar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletEventoGuardar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
