/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventBook.servelt;

import EventBook.dao.UsuarioFacade;
import EventBook.dao.UsuarioeventosFacade;
import EventBook.entity.Usuario;
import EventBook.entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Merli
 */
@WebServlet(name = "obtenerEstadisticas", urlPatterns = {"/obtenerEstadisticas"})
public class obtenerEstadisticas extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;
    
    
    private static final String USUARIOEVENTOS = "usuarioEventos";
    private static final String FILTRONUMUSUARIOS = "numUsuarios";
    private static final String ANYOTODOS = "todos";
    
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
            throws ServletException, IOException {
        String tipoUsuario = request.getParameter("tipoUsuario");
        String tipoFiltro = request.getParameter("tipoFiltro");
        String anyo = request.getParameter("anyo");
        
        List<List<String>> listaFila = null;    //Un conjunto de filas es un conjunto de listas
        List<String> listaColumna = null;
        if(tipoUsuario.equalsIgnoreCase(USUARIOEVENTOS)){
            if(tipoFiltro.equalsIgnoreCase(FILTRONUMUSUARIOS)){
                listaColumna = new ArrayList<>();
                listaFila = new ArrayList<>();
                
                listaColumna.add("Numero de usuarios");
                List<String> filaIndividual = Arrays.asList(String.valueOf(this.usuarioeventosFacade.count()));
                listaFila.add(filaIndividual);
                this.usuarioeventosFacade.findAll();
                
                
            } else{
                //List<Usuarioeventos> lista = this.usuarioeventosFacade.findAll();
            }
            
        }
        
        request.setAttribute("listaColumna", listaColumna);
        request.setAttribute("listaFila", listaFila);
   
        
        
        
        
        RequestDispatcher rd = request.getRequestDispatcher("filtroanalisis.jsp");
        rd.forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
