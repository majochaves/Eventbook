/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.Analisis;

import clases.Autenticacion;
import clases.Tupla;
import dao.AdministradorFacade;
import dao.AnalistaFacade;
import dao.CreadoreventosFacade;
import dao.TeleoperadorFacade;
import dao.UsuarioFacade;
import dao.UsuarioeventosFacade;
import entity.Analista;
import entity.Usuario;
import entity.Usuarioeventos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "ServletAnalisisGenerar", urlPatterns = {"/ServletAnalisisGenerar"})
public class ServletAnalisisGenerar extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

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
    private static final String FILTROCIUDAD = "ciudad";
    private static final String FILTRONOMBRE ="nombre";
    private static final String FILTROAPELLIDO = "apellido";
    private static final String FILTROFECHAPORMES = "filtroFechaMeses";
    private static final String FILTROFECHAPORANYOS = "filtroFechaAnyos";
    
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
        
        //Miramos si el usuario esta logeado y si tiene rol de Analista.
        //Nota: Solo un Analista puede generar/guardar un Analisis, un Administrador que no tenga rol de analista no puede!
        if(Autenticacion.tieneRol(request, response, Analista.class)){
        
            List<String> tipoUsuario = null;
            List<String> tipoFiltro = null;
            boolean muestraError = false;

            //Comprobamos que al menos ha seleccionado una casilla de Filtro y una de Tipo de Usuairo
            try{
                tipoUsuario = Arrays.asList(request.getParameterValues("tipoUsuario"));
                tipoFiltro = Arrays.asList(request.getParameterValues("tipoFiltro"));
            } catch(NullPointerException e){
                muestraError = true;
                request.setAttribute("muestraError", muestraError);
                RequestDispatcher rd = request.getRequestDispatcher("analisisVerGenerado.jsp");
                rd.forward(request, response);
            }

            if(!muestraError){
                String cadenaFechaInicial = request.getParameter("fechaInicial");
                String cadenaFechaFinal = request.getParameter("fechaFinal");

                Date fechaInicial, fechaFinal;
                if(cadenaFechaInicial.length() == 0)
                    fechaInicial  = null;
                else
                    fechaInicial = Date.valueOf(cadenaFechaInicial);

                if(cadenaFechaFinal.length() == 0)
                    fechaFinal  = null;
                else
                    fechaFinal = Date.valueOf(cadenaFechaFinal);



                String AutoGeneradoAnalisisDe = "", AutoGeneradoTiposFiltros = "";
                if(tipoUsuario.contains(ANALISTAS))
                    AutoGeneradoAnalisisDe+=" Analistas,";
                if(tipoUsuario.contains(USUARIOEVENTOS))
                    AutoGeneradoAnalisisDe+=" Usuario Eventos,";
                if(tipoUsuario.contains(CREADOREVENTOS))
                    AutoGeneradoAnalisisDe+=" Creadores de Eventos,";
                if(tipoUsuario.contains(TELEOPERADORES))
                    AutoGeneradoAnalisisDe+=" Teleoperadores,";
                if(tipoUsuario.contains(ADMINISTRADORES))
                    AutoGeneradoAnalisisDe+=" Administradores,";
                AutoGeneradoAnalisisDe = AutoGeneradoAnalisisDe.substring(0, AutoGeneradoAnalisisDe.lastIndexOf(","));

                //Un conjunto de filas estará asociada a cierta columna en concreto (por eso el HashMap<columna, filas>)
                //Pero cada fila, debe tener un nombre Unico (en la BD el nombre es PK) por eso Set<String>
                //Pero cada fila es una tupla. ej: Masculino - 50, Femenino - 70...
                //Ej visual:
                //              | Ciudad | Valor |          Como se puede ver, las columnas SIEMPRE son una tupla (Elem, Valor)
                //              | Malaga |   15  |
                //              | Barcel |   20  |
                //              | Madrid |   22  |
                //
                //
                //Haremos un Map<NombreColumna, Map<Nombre, Valor>>
                Map<String, Map<String, Double>> listaFila = new HashMap<>();

                //Obtenemos todos los usuarios
                List<Usuario> listaUsuarios = usuarioFacade.getUsuarioByRoles(
                        tipoUsuario.contains(ANALISTAS),
                        tipoUsuario.contains(USUARIOEVENTOS),
                        tipoUsuario.contains(CREADOREVENTOS),
                        tipoUsuario.contains(TELEOPERADORES),
                        tipoUsuario.contains(ADMINISTRADORES),
                        fechaInicial,
                        fechaFinal
                );

                //Obtenemos el id de todos los usuarios
                List<Integer> listaUsuariosIds = new ArrayList<>();
                for(Usuario u : listaUsuarios)
                    listaUsuariosIds.add(u.getId());




                //------------------------------FILTROS----------------------------

                //Numero de usuarios totales
                if(tipoFiltro.contains(FILTRONUMUSUARIOS)){
                    Map<String, Double> m = new HashMap<>();
                    m.put("Num", new Double(listaUsuarios.size()));
                    listaFila.put("Numero de Usuarios", m);

                    AutoGeneradoTiposFiltros+=" numero de usuarios totales,";
                }

                //Sexo
                if(tipoFiltro.contains(FILTROSEXO)){
                    double nMasc = 0, nFem = 0;

                    for(Usuario u : listaUsuarios){
                        if(u.getSexo().equalsIgnoreCase("MASCULINO") || u.getSexo().equalsIgnoreCase("HOMBRE"))
                            nMasc++;
                        if(u.getSexo().equalsIgnoreCase("FEMENINO") || u.getSexo().equalsIgnoreCase("MUJER"))
                            nFem++;
                    }

                    Map<String, Double> m = new HashMap<>();
                    m.put("Masculino", nMasc);    
                    m.put("Femenino", nFem);
                    listaFila.put("Sexo", m);

                    AutoGeneradoTiposFiltros+=" generos, ";
                }

                //Ciudad
                if(tipoFiltro.contains(FILTROCIUDAD)){
                    Map<String, Double> listaCiudades = usuarioFacade.getNumUsersByCities(listaUsuariosIds);
                    listaFila.put("Ciudades", listaCiudades);

                    AutoGeneradoTiposFiltros+=" ciudades, ";
                }

                //Nombre
                if(tipoFiltro.contains(FILTRONOMBRE)){
                    Map<String, Double> listaNombres = usuarioFacade.getNumUsersByName(listaUsuariosIds);
                    listaFila.put("Nombres", listaNombres);

                    AutoGeneradoTiposFiltros+=" nombres, ";
                }

                //Apellido
                if(tipoFiltro.contains(FILTROAPELLIDO)){
                    Map<String, Double> listaApellidos = usuarioFacade.getNumUsersByLastName(listaUsuariosIds);
                    listaFila.put("Apellidos", listaApellidos);
                    AutoGeneradoTiposFiltros+=" apellidos, ";
                }

                //Fecha de creacion - Anyo
                if(tipoFiltro.contains(FILTROFECHAPORANYOS)){
                    Map<String, Double> listaFechasAnyos = usuarioFacade.getNumUsersByYears(listaUsuariosIds);
                    listaFila.put("Creado en (Año)", listaFechasAnyos);

                    AutoGeneradoTiposFiltros+=" fecha por años, ";
                }

                //Fecha de creacion - Mes y Anyo
                if(tipoFiltro.contains(FILTROFECHAPORMES)){
                    Map<String, Double> listaFechasMeses = usuarioFacade.getNumUsersByMonths(listaUsuariosIds);
                    listaFila.put("Creado en (Mes - Año)", listaFechasMeses);

                    AutoGeneradoTiposFiltros+=" fecha por meses-años, ";
                }

                AutoGeneradoTiposFiltros = AutoGeneradoTiposFiltros.substring(0, AutoGeneradoTiposFiltros.lastIndexOf(","));






                request.setAttribute("listaFila", listaFila);

                //Texto AutoGenerado
                request.setAttribute("AutoGeneradoAnalisisDe", AutoGeneradoAnalisisDe);
                request.setAttribute("AutoGeneradoTiposFiltros", AutoGeneradoTiposFiltros);

                RequestDispatcher rd = request.getRequestDispatcher("analisisVerGenerado.jsp");
                rd.forward(request, response);
            }
        } else {
            Autenticacion.error(request, response, "Solo un Analista tiene acceso a este apartado. Si eres Administrador, por favor, añadete el rol de Analista.");
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