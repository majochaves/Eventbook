/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.UsuarioFacade;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josie
 */
@WebServlet(name = "ServletUsuarioIniciarSesion", urlPatterns = {"/ServletUsuarioIniciarSesion"})
public class ServletUsuarioIniciarSesion extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

    @PersistenceContext(unitName = "EventBookPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("usuario");
        Usuario u = null;
        
        List <Usuario> q = em
                .createNamedQuery("Usuario.findByUsername")
                .setParameter("username", username)
                .getResultList();
        
        if (!q.isEmpty()) {
            String password = request.getParameter("contrasena");
            u = q.iterator().next();
            if (u.getPassword().equals(password)) {
                // Se autentica al usuario
                
                request.getSession().setAttribute("logged-user", u);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }

        request.setAttribute("error", "El usuario o contraseña proporcionados son incorrectos");
        request.getRequestDispatcher("usuario-iniciar-sesion.jsp").forward(request, response);
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

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
