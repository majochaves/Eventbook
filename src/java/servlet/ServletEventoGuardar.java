/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.EventoFacade;
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
        System.out.println("EVENTO");
        String titulo, descripcion, str;
        Date fecha, fechaLimite;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Evento evento = new Evento();
        
        //TITULO - Obligatorio
        titulo = request.getParameter("titulo");
        evento.setTitulo(titulo);
        
        //DESCRIPCION - Opcional
        /*
        descripcion = request.getParameter("descripcion");
        if(!descripcion.isEmpty()){
            evento.setDescripcion(descripcion);
        }
        */
                
        //FECHA - Obligatorio
        str = request.getParameter("fecha");
        fecha = format.parse(str);
        evento.setFecha(fecha);
        
        //FECHA LIMITE - Opcional
        /*
        str = request.getParameter("fecha_limite");
        if(!str.isEmpty()){
            fechaLimite = format.parse(str);
            evento.setFechaLimite(fechaLimite);
        }
        
        //COSTE ENTRADA - Opcional
        str = request.getParameter("coste");
        if(!str.isEmpty()){
            evento.setCosteEntrada(Double.parseDouble(str));
        }
        
        //AFORO - Opcional
        str = request.getParameter("aforo");
        if(!str.isEmpty()){
            evento.setAforo(Integer.parseInt(str));
        }
        
        //MAX ENTRADAS - Opcional
        str = request.getParameter("max_entradas");
        if(!str.isEmpty()){
            evento.setMaxEntradas(Integer.parseInt(str));
        }
        
        //ASIENTOS_FIJOS - Opcional
        str = request.getParameter("asientos_fijos");
        if(str != null){
            evento.setAsientosFijos('s');
            //NUM FILAS - Opcional
            str = request.getParameter("num_filas");
            if(str != null){
                evento.setNumFilas(Integer.parseInt(str));
            }
            
            //NUM ASIENTOS POR FILA - Opcional
            str = request.getParameter("num_asientos_fila");
            if(str != null){
                evento.setNumAsientosFila(Integer.parseInt(str));
            }
        }else{
            evento.setAsientosFijos('n');
        }
        */
        
        this.eventoFacade.create(evento);
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
