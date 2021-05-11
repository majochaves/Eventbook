<%-- 
    Document   : header
    Created on : 21-Apr-2021, 12:29:13
    Author     : josie
--%>

      <%@page import="entity.Analista"%>
<%@page import="clases.Autenticacion"%>
<%@page import="entity.Usuario"%>
<%@page import="entity.Administrador"%>

<%
    Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");
%>
      
<header class="section rd-navbar-wrap">
        <nav class="rd-navbar">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="index.jsp"><img class="navbar-logo-default" src="images/logo-default-161x27.svg" alt="Intense" width="161" height="27"/><img class="navbar-logo-inverse" src="images/logo-inverse-161x27.svg" alt="Intense" width="161" height="27"/></a></div>
              </div>
            </div>
            <div class="navbar-cell navbar-spacer"></div>
            <div class="navbar-cell navbar-sidebar">
              <ul class="navbar-navigation rd-navbar-nav">
                <li class="navbar-navigation-root-item active"><a class="navbar-navigation-root-link" href="index.jsp">Inicio</a>
                </li>
                
                <!-- Esto deber� comprobar en un futuro si el usuario logeado es Admin o Analista -->
        
                <%
                    if (Autenticacion.tieneRol(request, response, Administrador.class, Analista.class)) {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServeltAnalisisIndex">An�lisis</a>
                        </li>
                        <%
                    }
                    if (Autenticacion.tieneRol(request, response, Administrador.class)) {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Administraci�n</a>
                        </li>
                        <%
                    }

                    if (!Autenticacion.estaLogeado(request, response)) {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="usuario-iniciar-sesion.jsp">Iniciar sesi�n</a>
                        </li>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Registrarse</a>
                        </li>
                        <%
                    } else {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioCerrarSesion">Cerrar sesi�n</a>
                        </li>
                        <%
                    }
                %>
              </ul>
            </div>
          </div>
        </nav>
      </header>