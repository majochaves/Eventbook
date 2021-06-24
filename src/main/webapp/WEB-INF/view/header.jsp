<%-- 
    Document   : header
    Created on : 21-Apr-2021, 12:29:13
    Author     : josie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
      <%@page import="com.eventbookspring.eventbookspring.entity.Usuarioeventos"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Creadoreventos"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Analista"%>
<%@page import="com.eventbookspring.eventbookspring.clases.Autenticacion"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Usuario"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Administrador"%>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>

<%
    UsuarioDTO loggedUser = Autenticacion.getUsuarioLogeado(session);

    String nav = (String) request.getParameter("nav");
    if(nav == null) nav = "";
%>

<header class="section rd-navbar-wrap">
        <nav class="rd-navbar">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="/"><img class="navbar-logo-default" src="/images/EventbookLogoOscuro.svg" alt="Intense" width="161" height="27"/><img class="navbar-logo-inverse" src="/images/EventbookLogoClaro.svg" alt="Intense" width="161" height="27"/></a></div>
              </div>
            </div>
            <div class="navbar-cell navbar-spacer"></div>
            <div class="navbar-cell navbar-sidebar">
              <ul class="navbar-navigation rd-navbar-nav">
                <li class="navbar-navigation-root-item <%= (nav.equals("inicio") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/">Inicio</a>
                </li>
                
                <!-- Esto deber� comprobar en un futuro si el usuario logeado es Admin o Analista -->
                <%
                    if (Autenticacion.tieneRol(request, response, Administrador.class, Analista.class)) {
                %>
                        <li class="navbar-navigation-root-item <%= (nav.equals("analisis") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/analisis/">Análisis</a>
                        </li>
                <%
                    }
                    if (Autenticacion.tieneRol(request, response, Administrador.class)) {
                %>
                    <li class="navbar-navigation-root-item <%= (nav.equals("administracion") ? "active" : "") %>" ><a class="navbar-navigation-root-link" href="/administracion">Administración</a>
                    </li>
                <%
                    }
                    if (Autenticacion.tieneRol(request, response, Administrador.class, Usuarioeventos.class)) {
                %>
                  <li class="navbar-navigation-root-item <%= (nav.equals("reserva") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/verReservas/<%=Autenticacion.getUsuarioLogeado(request,response).getId()%>">Mis reservas</a></li>
                <%
                    }
                %>
                  <li class="navbar-navigation-root-item <%= (nav.equals("eventos") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/eventos">Eventos</a>
                  </li>
                <%
                    if (!Autenticacion.estaLogeado(request, response)) {
                %>
                        <li class="navbar-navigation-root-item <%= (nav.equals("iniciar") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/login">Iniciar sesión</a>
                        </li>
                        <li class="navbar-navigation-root-item <%= (nav.equals("registrar") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/register">Registrarse</a>
                        </li>
                <%
                    } else {
                %>
                        <li class="navbar-navigation-root-item <%= (nav.equals("chat") ? "active" : "") %>"><a class="navbar-navigation-root-link" href="/chat/">Soporte Técnico</a>
                        </li>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="/logout">Cerrar sesión</a>
                        </li>
                <%
                    }
                %>
              </ul>
            </div>
          </div>
        </nav>
      </header>