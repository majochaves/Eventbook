/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.CreadoreventosFacade;
import dao.EventoFacade;
import entity.Creadoreventos;
import entity.Evento;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        Evento e;
        Creadoreventos creador;
        if(id == null || id.isEmpty()){
            e = new Evento();
            creador = creadorEventosFacade.find(7);
            e.setCreadoreventosId(creador);
        }else{
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
        
        
        //TITULO - Obligatorio
        e.setTitulo(titulo);
        
        //DESCRIPCION - Opcional
        if(!descripcion.isEmpty()){
            e.setDescripcion(descripcion);
        }
                
        //FECHA - Obligatorio
        e.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
        
        //FECHA LIMITE - Opcional
        if(!fechaLimite.isEmpty()){
            e.setFechaLimite(new SimpleDateFormat("yyyy-MM-dd").parse(fechaLimite));
        }
        
        //COSTE ENTRADA - Opcional
        if(!costeEntrada.isEmpty()){
            e.setCosteEntrada(new Double(costeEntrada));
        }
        
        //AFORO - Opcional
        if(!aforo.isEmpty()){
            e.setAforo(new Integer(aforo));
        }
        
        //MAX ENTRADAS - Opcional
        if(!maxEntradas.isEmpty()){
            e.setMaxEntradas(new Integer(maxEntradas));
        }
        
        //ASIENTOS_FIJOS - Opcional
        if(asientosFijos.equals("si")){
            e.setAsientosFijos('s');
            //NUM FILAS - Opcional
            if(numFilas != null){
                e.setNumFilas(new Integer(numFilas));
            }
            
            //NUM ASIENTOS POR FILA - Opcional
            if(numAsientosFila != null){
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
