/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Tupla;
import dao.AdministradorFacade;
import dao.AnalistaFacade;
import dao.CreadoreventosFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioeventosFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@WebServlet(name = "crearAnalisis", urlPatterns = {"/crearAnalisis"})
public class crearAnalisis extends HttpServlet {

    @EJB
    private TeleoperadorFacade teleoperadorFacade;

    @EJB
    private AdministradorFacade administradorFacade;

    @EJB
    private AnalistaFacade analistaFacade;

    @EJB
    private CreadoreventosFacade creadoreventosFacade;

    @EJB
    private UsuarioeventosFacade usuarioeventosFacade;
    
    
    //Tipos de usuario
    private static final String USUARIOEVENTOS = "usuarioEventos";
    private static final String CREADOREVENTOS = "creadorEventos";
    private static final String ANALISTAS = "analistas";
    private static final String TELEOPERADORES = "teleoperadores";
    private static final String ADMINISTRADORES = "administradores";
    //Tipos de filtro
    private static final String FILTRONUMUSUARIOS = "numUsuarios";
    private static final String FILTROSEXO = "sexo";
    //Anyos
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
        
        List<String> tipoUsuario = Arrays.asList(request.getParameterValues("tipoUsuario"));
        List<String> tipoFiltro = Arrays.asList(request.getParameterValues("tipoFiltro"));
        String anyo = request.getParameter("anyo");
        
        
        
        List<String> listaColumna = new ArrayList<>();
        //Asociaremos el nombre de una columna con varias filas(por eso el HashMap<columna, filas>)
        //Pero cada fila, debe tener un nombre Unico (en la BD el nombre es PK) por eso Set<String>
        //Pero cada fila es una tupla. ej: Masculino - 50, Femenino - 70...
        Map<String, Set<Tupla<String, Double>>> listaFila = new HashMap<>();
        
        
        //NUM USUARIOS
        if(tipoFiltro.contains(FILTRONUMUSUARIOS)){
            listaColumna.add(FILTRONUMUSUARIOS);
            double nUsuarios = 0;
            
            if(tipoUsuario.contains(USUARIOEVENTOS)){
                nUsuarios += usuarioeventosFacade.count();
            }
            if(tipoUsuario.contains(CREADOREVENTOS)){
                nUsuarios += creadoreventosFacade.count();
            }
            if(tipoUsuario.contains(ANALISTAS)){
                nUsuarios += analistaFacade.count();
            }
            if(tipoUsuario.contains(TELEOPERADORES)){
                nUsuarios += teleoperadorFacade.count();
            }
            if(tipoUsuario.contains(ADMINISTRADORES)){
                nUsuarios += administradorFacade.count();
            }
            
            Set<Tupla<String, Double>> c = new HashSet<>();
            Tupla<String, Double> t = new Tupla("NumUsuarios", nUsuarios);
            c.add(t);
            listaFila.put(FILTRONUMUSUARIOS, c);
        }
        
        
        //SEXO
        if(tipoFiltro.contains(FILTROSEXO)){
            listaColumna.add(FILTROSEXO);
            double masc = 0;
            double fem = 0;
            
            if(tipoUsuario.contains(USUARIOEVENTOS)){
                //...
            }
            //...
            Set<Tupla<String, Double>> c = new HashSet<>();
            Tupla<String, Double> t1 = new Tupla("Masculino", masc);
            Tupla<String, Double> t2 = new Tupla("Femenino", fem);
            c.add(t1);
            c.add(t2);
            listaFila.put(FILTROSEXO, c);
            
        }
        
        request.setAttribute("listaColumna", listaColumna);
        request.setAttribute("listaFila", listaFila);
        
        
        
        
        
        RequestDispatcher rd = request.getRequestDispatcher("crearAnalisis.jsp");
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
