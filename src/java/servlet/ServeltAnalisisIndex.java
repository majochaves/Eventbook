/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Autenticacion;
import dao.AnalisisFacade;
import dao.AnalistaFacade;
import dao.UsuarioFacade;
import entity.Administrador;
import entity.Analista;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Merli
 */
@WebServlet(name = "ServeltAnalisisIndex", urlPatterns = {"/ServeltAnalisisIndex"})
public class ServeltAnalisisIndex extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

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
        
        //Si solo tiene rol de administrador no podra acceder. Debe tener tambien rol de analista
        //Esto se debe a que los analisis tienen relacion con la tabla Analista y no con con la tabla Administrador
        if(Autenticacion.tieneRol(request, response, Administrador.class) && Autenticacion.tieneRol(request, response, Analista.class)){
            //Cambiar en un futuro: Mostrar el ultimo analisis realizado
            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            
            Analista thisAnalista = thisUsuario.getAnalista();
            
            RequestDispatcher rd = request.getRequestDispatcher("analisisIndex.jsp");
            rd.forward(request, response);
        } else {
            //Este metodo ya hace el requestDispatcher.forward()
            Autenticacion.error(request, response, "No estas logeado o no tienes suficientes permisos");
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
