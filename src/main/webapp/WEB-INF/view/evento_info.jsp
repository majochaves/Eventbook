<%-- 
    Document   : evento_info
    Created on : 22-Apr-2021, 21:06:40
    Author     : majochaves
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.*" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Evento</title>
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/eventos/eventos.js"></script>
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        
    </head>
    <%
        EventoDTO evento = (EventoDTO)request.getAttribute("evento");
        UsuarioDTO u = Autenticacion.getUsuarioLogeado(session);
    %>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />
            <!-- Intro-->
            <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
              <div class="container-fluid">
                  <% if(evento != null){
                  %>
                      <h1><%=evento.getTitulo()%></h1>
                      <%
                      if(evento.getDescripcion() != null){
                      %>
                          <p><%=evento.getDescripcion()%></p>
                      <%
                      }
                      %>
                      <p>Fecha: <%=new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(evento.getFecha()))%></p>
                      <%
                      if(evento.getFechaLimite() != null){
                      %>
                          <p>Fecha limite: <%=new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(evento.getFechaLimite()))%></p>
                      <%
                      }
                      if(evento.getCosteEntrada() != null){
                      %>
                          <p>Coste entrada: <%=evento.getCosteEntrada()%></p>
                      <%
                      }
                      if(evento.getAforo() != null){
                      %>
                          <p>Aforo: <%=evento.getAforo()%></p>
                      <%
                      }
                      if(evento.getMaxEntradas() != null){
                      %>
                          <p>Máximo número de entradas por usuario: <%=evento.getMaxEntradas()%></p>
                      <%
                      }
                      // SÓLO VISIBLE PARA CREADOR O ADMINISTRADOR
                      if(evento.getAsientosFijos() != null){
                          String aFijos = evento.getAsientosFijos() == 'S' ? "Si" : "No";
                      %>
                      <p>Asientos fijos: <%=aFijos%></p>
                      <%
                      }
                      if(evento.getNumFilas() != null){
                      %>
                      <p>Número de filas: <%=evento.getNumFilas()%></p>
                      <%
                      }
                      if(evento.getNumAsientosFila() != null){
                      %>
                      <p>Número de asientos por fila: <%=evento.getNumAsientosFila()%></p>
                      <%
                      }
                      if(Autenticacion.tieneRol(request, response, Administrador.class) || (Autenticacion.tieneRol(request, response, Creadoreventos.class) && evento.getCreadoreventos().getUsuarioId().equals(u.getCreadoreventos()))){
                      %>

                          <a href="/editarEvento/<%=evento.getId()%>" class="btn btn-warning">Editar</a>
                          <a href="/borrarEvento/<%=evento.getId()%>" class="btn btn-danger">Borrar</a>
                      <%
                      } 
                      if(Autenticacion.tieneRol(request, response, Administrador.class, Usuarioeventos.class, Administrador.class)){
                      %>
                          <a href="/reservarEvento/<%=evento.getId()%>" class="btn btn-primary">Reservar</a>
                      <%
                      }
                  }
                  %>
              </div>
            </section>

            <!-- Footer default extended-->
            <jsp:include page="footer.jsp" />
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>

