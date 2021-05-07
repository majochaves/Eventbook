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
import dao.UsuarioeventosFacade;
import entity.Administrador;
import entity.Analista;
import entity.Creadoreventos;
import entity.Teleoperador;
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    
    @EJB
    private UsuarioeventosFacade usuarioEventosFacade;
    
    private void crearAdministrador(Usuario u) {
        Administrador administrador = new Administrador(u.getId());
        u.setAdministrador(administrador);
        administrador.setUsuario(u);
        administradorFacade.create(administrador);
    }
    
    private void crearUsuarioEventos(Usuario u) {
        Usuarioeventos usuarioEventos = new Usuarioeventos(u.getId());
        u.setUsuarioeventos(usuarioEventos);
        usuarioEventos.setUsuario(u);
        usuarioEventosFacade.create(usuarioEventos);
    }
    
    private void crearAnalista(Usuario u) {
        Analista analista = new Analista(u.getId());
        u.setAnalista(analista);
        analista.setUsuario(u);
        analistaFacade.create(analista);
    }
    
    private void crearCreadorEventos(Usuario u) {
        Creadoreventos creadorEventos = new Creadoreventos(u.getId());
        u.setCreadoreventos(creadorEventos);
        creadorEventos.setUsuario(u);
        creadorEventosFacade.create(creadorEventos);        
    }
    
    private void crearTeleoperador(Usuario u) {
        Teleoperador teleoperador = new Teleoperador(u.getId());
        u.setTeleoperador(teleoperador);
        teleoperador.setUsuario(u);
        teleoperadorFacade.create(teleoperador);       
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuarioEditar = "".equals(request.getParameter("id")) ? null : usuarioFacade.find(Integer.parseInt(request.getParameter("id")));
        boolean edicion = usuarioEditar != null;

        String nombreUsuario = request.getParameter("usuario");
        for (Usuario u : usuarioFacade.findAll()) {
            if ((edicion && !usuarioEditar.equals(u) && u.getUsername().equalsIgnoreCase(nombreUsuario)) || (!edicion && u.getUsername().equalsIgnoreCase(nombreUsuario))) {
                request.setAttribute("error", "El nombre de usuario '" + nombreUsuario +  "' ya está en uso");
                request.setAttribute("usuarioEditar", usuarioEditar);
                request.getRequestDispatcher("usuario-crear.jsp").forward(request, response);
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
        u.setUsername(nombreUsuario);
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setPassword(contrasena);
        u.setSexo(sexo);
        u.setDomicilio(domicilio);
        u.setCiudadResidencia(ciudad);
        u.setFechaCreacion(fechaCreacion);
        
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventBookPU");
        //EntityManager em = emf.createEntityManager();
        
        if (!edicion) {
            usuarioFacade.create(u);
            switch(rol) {
                case "administrador":
                    crearAdministrador(u);
                    break;
                case "creador-eventos":
                    crearCreadorEventos(u);
                    break;
                case "teleoperador":
                    crearTeleoperador(u);
                    break;
                case "analista":
                    crearAnalista(u);
                    break;
                default:
                    break;
            }
            
        }
        usuarioFacade.edit(u);

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
