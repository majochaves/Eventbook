/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Analisis;

import clases.Autenticacion;
import dao.AnalisisFacade;
import dao.AnalistaFacade;
import dao.CampoanalisisFacade;
import dao.TipoanalisisFacade;
import entity.Administrador;
import entity.Analisis;
import entity.Analista;
import entity.Campoanalisis;
import entity.CampoanalisisPK;
import entity.Tipoanalisis;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Merli
 */
@WebServlet(name = "ServletAnalisisDuplicar", urlPatterns = {"/ServletAnalisisDuplicar"})
public class ServletAnalisisDuplicar extends HttpServlet {

    @EJB
    private CampoanalisisFacade campoanalisisFacade;

    @EJB
    private TipoanalisisFacade tipoanalisisFacade;

    @EJB
    private AnalistaFacade analistaFacade;

    @EJB
    private AnalisisFacade analisisFacade;
    
    
    
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
        
        Integer idAnalisis = null;
        String descripcion = null;
        
        try {
            idAnalisis = Integer.parseInt(request.getParameter("id"));
            descripcion = request.getParameter("descripcion");
        } catch(RuntimeException ex){
            Autenticacion.error(request, response, "ID del Análisis escrito incorrectamente");
        }
        
        if(idAnalisis!=null && descripcion!=null){
            Analisis AnalisisPadre = analisisFacade.find(idAnalisis);
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            
            
            
            if(AnalisisPadre!= null && thisUsuario!= null && 
                    (AnalisisPadre.getAnalistaUsuarioId().getUsuarioId().equals(thisUsuario.getId()) || 
                    Autenticacion.tieneRol(request, response, Administrador.class))){
                
                Analista thisAnalista = thisUsuario.getAnalista();
                
                //Creamos el nuevo analisis
                Analisis nuevoAnalisis = new Analisis();
                nuevoAnalisis.setAnalistaUsuarioId(thisAnalista);
                nuevoAnalisis.setDescripcion(descripcion);
                analisisFacade.create(nuevoAnalisis);
                
                //Creamos el Tipoanalisis (Una tabla en concreto del analisis)
                List<Tipoanalisis> listaTipoAnalisis = new ArrayList<>();
                for(Tipoanalisis tipoAnalisisPadre : AnalisisPadre.getTipoanalisisList()){
                    Tipoanalisis nuevoTipoAnalisis = new Tipoanalisis();
                    nuevoTipoAnalisis.setAnalisisId(nuevoAnalisis);
                    nuevoTipoAnalisis.setNombre(tipoAnalisisPadre.getNombre());
                    tipoanalisisFacade.create(nuevoTipoAnalisis);
                    
                    //Creamos el Campoanalisis (Cada fila de dicha tabla)
                    List<Campoanalisis> listaCampoAnalisis = new ArrayList<>();
                    for(Campoanalisis campoAnalisisPadre : tipoAnalisisPadre.getCampoanalisisList()){
                        CampoanalisisPK capk = new CampoanalisisPK();
                        capk.setNombre(campoAnalisisPadre.getCampoanalisisPK().getNombre());
                        capk.setTipoanalisisId(nuevoTipoAnalisis.getId());
                        
                        Campoanalisis nuevoCampoAnalisis = new Campoanalisis();
                        nuevoCampoAnalisis.setTipoanalisis(nuevoTipoAnalisis);
                        nuevoCampoAnalisis.setCampoanalisisPK(capk);
                        nuevoCampoAnalisis.setValor(campoAnalisisPadre.getValor());
                        campoanalisisFacade.create(nuevoCampoAnalisis);
                        listaCampoAnalisis.add(nuevoCampoAnalisis);
                    }
                    
                    nuevoTipoAnalisis.setCampoanalisisList(listaCampoAnalisis);
                    tipoanalisisFacade.edit(nuevoTipoAnalisis);
                    listaTipoAnalisis.add(nuevoTipoAnalisis);
                }
                
                nuevoAnalisis.setTipoanalisisList(listaTipoAnalisis);
                analisisFacade.edit(nuevoAnalisis);
                
                
                //Asociamos finalmente el nuevo analisis al Analista
                List<Analisis> listaAnalisis = thisAnalista.getAnalisisList();
                listaAnalisis.add(nuevoAnalisis);
                thisAnalista.setAnalisisList(listaAnalisis);
                analistaFacade.edit(thisAnalista);
                
                
            } else {
                Autenticacion.error(request, response, "No estás logeado, no tienes suficientes permisos o el análisis no ha sido encontrado.");
            }
            
            
        }
        
        
        
        
        response.sendRedirect("ServeltAnalisisListar");
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
