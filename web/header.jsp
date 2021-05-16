<%-- 
    Document   : header
    Created on : 21-Apr-2021, 12:29:13
    Author     : josie
--%>

      <%@page import="entity.Usuarioeventos"%>
<%@page import="entity.Creadoreventos"%>
<%@page import="entity.Analista"%>
<%@page import="clases.Autenticacion"%>
<%@page import="entity.Usuario"%>
<%@page import="entity.Administrador"%>

<%
    Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");
    String nav = (String) request.getParameter("nav");
    if(nav == null) nav = "";
%>

<header class="section rd-navbar-wrap">
        <nav class="rd-navbar">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="index.jsp"><img class="navbar-logo-default" src="images/EventbookLogoOscuro.svg" alt="Intense" width="161" height="27"/><img class="navbar-logo-inverse" src="images/EventbookLogoClaro.svg" alt="Intense" width="161" height="27"/></a></div>
              </div>
            </div>
            <div class="navbar-cell navbar-spacer"></div>
            <div class="navbar-cell navbar-sidebar">
              <ul class="navbar-navigation rd-navbar-nav">
                <li class="navbar-navigation-root-item <%= (nav.equals("inicio") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="index.jsp">Inicio</a>
                </li>
                
                <!-- Esto deberá comprobar en un futuro si el usuario logeado es Admin o Analista -->
                <%
                    if (Autenticacion.tieneRol(request, response, Administrador.class, Analista.class)) {
                        %>
                        <li class="navbar-navigation-root-item <%= (nav.equals("analisis") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="ServeltAnalisisIndex">Análisis</a>
                        </li>
                        <%
                    }
                    if (Autenticacion.tieneRol(request, response, Administrador.class)) {
                        %>
                    <li class="navbar-navigation-root-item <%= (nav.equals("administracion") ? "active" : "") %>" ><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Administración</a>
                    </li>
                        <%
                    }
                    %>
                    <% 
                    if (Autenticacion.tieneRol(request, response, Usuarioeventos.class, Administrador.class)) {
                    %>
                    <li class="navbar-navigation-root-item <%= (nav.equals("reservas") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Mis reservas</a>
                        </li>
                    <%
                    }
                    %>
                    <li class="navbar-navigation-root-item <%= (nav.equals("eventos") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="ServletEventoListar">Eventos</a>
                    </li>
                    <%
                    if (!Autenticacion.estaLogeado(request, response)) {
                        %>
                        <li class="navbar-navigation-root-item <%= (nav.equals("iniciar") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="usuario-iniciar-sesion.jsp">Iniciar sesión</a>
                        </li>
                        <li class="navbar-navigation-root-item <%= (nav.equals("registrar") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="usuario-registrar.jsp">Registrarse</a>
                        </li>
                        <%
                    } else {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletChatListar">Soporte Técnico</a>
                        </li>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioCerrarSesion">Cerrar sesión</a>
                        </li>
                        <%
                    }
                %>
              </ul>
            </div>
          </div>
        </nav>
      </header>