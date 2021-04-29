/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.AnalisisFacade;
import dao.AnalistaFacade;
import dao.CampoanalisisFacade;
import dao.TipoanalisisFacade;
import entity.Analisis;
import entity.Analista;
import entity.Campoanalisis;
import entity.CampoanalisisPK;
import entity.Tipoanalisis;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Merli
 */
@WebServlet(name = "ServletAnalisisGuardar", urlPatterns = {"/ServletAnalisisGuardar"})
public class ServletAnalisisGuardar extends HttpServlet {

    @EJB
    private CampoanalisisFacade campoanalisisFacade;

    @EJB
    private TipoanalisisFacade tipoanalisisFacade;

    @EJB
    private AnalisisFacade analisisFacade;

    @EJB
    private AnalistaFacade analistaFacade;
    
    
    
    
    
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
        HttpSession sesion = request.getSession(false);
        Map<String, Map<String, Double>> listaFila = (Map)sesion.getAttribute("analisisListaFila");
        String descripcion = request.getParameter("descripcion");
        
        Analista thisAnalista = analistaFacade.find(1);         //ESTO HAY QUE CAMBIARLO CUANDO HAYA LOGIN
        
        Analisis thisAnalisis = new Analisis();
        thisAnalisis.setDescripcion(descripcion);
        thisAnalisis.setAnalistaUsuarioId(thisAnalista);
        analisisFacade.create(thisAnalisis);                    //Posteriormente debemos meter la lista de Tipos
        
        List<Tipoanalisis> listaTipoanalisis = new ArrayList<>();
        for(String nombreColumna : listaFila.keySet()){
            
            Tipoanalisis ta = new Tipoanalisis();
            ta.setAnalisisId(thisAnalisis);
            ta.setNombre(nombreColumna);
            tipoanalisisFacade.create(ta);                      //Posteriormente debemos meter la lista de campos
            
            List<Campoanalisis> listaCampoanalisis = new ArrayList<>();
            Map<String, Double> filas = listaFila.get(nombreColumna);
            for(String nombreFila: filas.keySet()){
                Campoanalisis ca = new Campoanalisis();
                CampoanalisisPK capk = new CampoanalisisPK();
                capk.setNombre(nombreFila);
                capk.setTipoanalisisId(ta.getId());
                ca.setCampoanalisisPK(capk);
                ca.setValor(filas.get(nombreFila).intValue());  //ESTO HAY QUE CAMBIARLO EN LA BD Y EN LA ENTITY
                ca.setTipoanalisis(ta);
                campoanalisisFacade.create(ca);
                listaCampoanalisis.add(ca);
            }
            ta.setCampoanalisisList(listaCampoanalisis);        //Metemos la la lista de campos
            tipoanalisisFacade.edit(ta);
            listaTipoanalisis.add(ta);
        }
        thisAnalisis.setTipoanalisisList(listaTipoanalisis);    //Metemos la lista de Tipos
        analisisFacade.edit(thisAnalisis);
        
        
        //thisAnalista = analistaFacade.find(1);
        //thisAnalisis = analisisFacade.find(thisAnalista.getAnalisisList().get(0).getId());
        //Tipoanalisis ta = tipoanalisisFacade.find(thisAnalisis.getTipoanalisisList().get(0).getId());
        
        
        response.sendRedirect("ServeltAnalisisIndex");
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
