<%-- 
    Document   : evento_info
    Created on : 22-Apr-2021, 21:06:40
    Author     : majochaves
--%>

<%@page import="entity.Usuarioeventos"%>
<%@page import="entity.Administrador"%>
<%@page import="entity.Creadoreventos"%>
<%@page import="clases.Autenticacion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="entity.Evento"%>
<%@page import="entity.Etiqueta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Evento</title>
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        Evento evento = (Evento)request.getAttribute("evento");
    %>
    <body>
      <div class="page">
      <!--RD Navbar-->
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
                <p>Fecha: <%=new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha())%></p>
                <%
                if(evento.getFechaLimite() != null){
                %>
                    <p>Fecha limite: <%=new SimpleDateFormat("dd/MM/yyyy").format(evento.getFechaLimite())%></p>
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
                    <p>Aforo: <%=evento.getMaxEntradas()%></p>
                <%
                }
                // SÓLO VISIBLE PARA CREADOR O ADMINISTRADOR
                if(evento.getAsientosFijos() != null){
                    String aFijos = evento.getAsientosFijos().equals('s') ? "Si" : "No";
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
                if(Autenticacion.tieneRol(request, response, Creadoreventos.class, Administrador.class)){
                %>
                    <a href="ServletEventoEditar?id=<%=evento.getId()%>" class="btn btn-secondary">Editar</a>
                    <a href="ServletEventoBorrar?id=<%=evento.getId()%>" class="btn btn-danger">Borrar</a>
                <%
                } 
                if(Autenticacion.tieneRol(request, response, Administrador.class, Usuarioeventos.class, Administrador.class)){
                %>
                    <a href="ServletEventoDatos?id=<%=evento.getId()%>" class="btn btn-secondary">Reservar</a>
                <%
                }
                %>
                
            <%
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

