/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.AdministradorFacade;
import dao.AnalistaFacade;
import dao.CreadoreventosFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioFacade;
import entity.Administrador;
import entity.Analista;
import entity.Creadoreventos;
import entity.Teleoperador;
import entity.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josie
 */
@WebServlet(name = "ServletUsuarioGuardar", urlPatterns = {"/ServletUsuarioGuardar"})
public class ServletUsuarioGuardar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private AdministradorFacade administradorFacade;
    
    @EJB
    private CreadoreventosFacade creadorEventosFacade;
    
    @EJB
    private TeleoperadorFacade teleoperadorFacade;
    
    @EJB
    private AnalistaFacade analistaFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuarioEditar = "".equals(request.getParameter("id")) ? null : usuarioFacade.find(Integer.parseInt(request.getParameter("id")));
        boolean edicion = usuarioEditar != null;

        String nombreUsuario = request.getParameter("usuario");
        if (!edicion) {
            for (Usuario u : usuarioFacade.findAll()) {
                if (u.getUsername().equalsIgnoreCase(nombreUsuario))
                    throw new RuntimeException("Username already exists.");
            }    
        }
        
        String contrasena = request.getParameter("contrasena");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String sexo = request.getParameter("sexo");
        String domicilio = request.getParameter("domicilio");
        String ciudad = request.getParameter("ciudad");
        String rol = request.getParameter("rol");
        Date fechaCreacion = new Date();
        
        Usuario u = edicion ? usuarioEditar : new Usuario();
        u.setUsername(edicion ? u.getUsername() : nombreUsuario);
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setPassword(contrasena);
        u.setSexo(sexo);
        u.setDomicilio(domicilio);
        u.setCiudadResidencia(ciudad);
        u.setFechaCreacion(fechaCreacion);
        
        if (!edicion) {
            usuarioFacade.create(u);
            switch(rol) {
                case "administrador":
                    Administrador administrador = new Administrador(u.getId());
                    u.setAdministrador(administrador);
                    administrador.setUsuario(u);
                    administradorFacade.create(administrador);
                    break;
                case "creador-eventos":
                    Creadoreventos creadorEventos = new Creadoreventos(u.getId());
                    u.setCreadoreventos(creadorEventos);
                    creadorEventos.setUsuario(u);
                    creadorEventosFacade.create(creadorEventos);
                    break;
                case "teleoperador":
                    Teleoperador teleoperador = new Teleoperador(u.getId());
                    u.setTeleoperador(teleoperador);
                    teleoperador.setUsuario(u);
                    teleoperadorFacade.create(teleoperador);
                    break;
                case "analista":
                    Analista analista = new Analista(u.getId());
                    u.setAnalista(analista);
                    analista.setUsuario(u);
                    analistaFacade.create(analista);
                    break;
                default:
                    break;
            }
        } else usuarioFacade.edit(u);

        response.sendRedirect("ServletUsuarioListar");
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
