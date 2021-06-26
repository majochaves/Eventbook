<%@ page import="java.util.List" %>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.*" %><%--
    Document   : evento_list
    Created on : 20-Apr-2021, 12:09:51
    Author     : majochaves
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventos</title>
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script defer src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script defer src="/components/eventos/eventos.js"></script>
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        
    </head>
   <%
        List<EventoDTO> eventos = (List)request.getAttribute("eventoList");
        List<Etiqueta> etiquetaList = (List) request.getAttribute("etiquetaList");
        boolean puedeCrear = Autenticacion.tieneRol(request, response, Administrador.class, Creadoreventos.class);
    %>
    <body>
        <div class="page">
          <jsp:include page="header.jsp">
                <jsp:param name="nav" value="eventos"/>  
            </jsp:include>
          <!-- Intro-->
          <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
            <div class="container-fluid">
                <h1 class="text-center" data-animate='{"class":"fadeInUp"}'>
                Eventos
                </h1>
                <% if (!etiquetaList.isEmpty()){ %>
                <form method="get" action="/verEventosFiltrados">
                    <table>
                        <tr>
                            <td>Filtrar</td>
                            <td>
                                <select id="selectEventos" name="etiqueta">
                                        <%
                                    for(Etiqueta e:etiquetaList){
                                %>
                                    <option value="<%=e.getId()%>"><%=e.getDescripcion()%></option>
                                        <%
                                    }
                                %>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-secondary"><i class="fas fa-filter"></i></button>
                            </td>
                        </tr>
                    </table>
                </form>
                <%}%>

                <% if(puedeCrear){%>
                <a href="/crearEvento" class="btn btn-primary">Crear evento</a>
                <a href="/verEtiquetas" class="btn btn-warning">Administrar etiquetas</a>
                <%}%>

                <div class="row row-30 row-md-40 row-lg-50 justify-content-center">
                    <% if(!eventos.isEmpty()){
                        for(EventoDTO e : eventos){
                            System.out.println("Evento: " + e.getId());
                    %>
                    <div class="col-xs-6 col-md-4 col-xl-auto" data-animate='{"class":"fadeInUp"}'>
                            <!-- Layout-->
                            <article class="layout"><a class="layout-figure thumbnail-up-shadow" href="/verEvento/<%= e.getId() %>"><img src="/images/calendar.svg" alt="" width="336" height="450"/></a>
                                <div class="layout-title h6"><a class="layout-title-link" href="/verEvento/<%= e.getId() %>"><%=e.getTitulo()%></a>
                              </div>
                            </article>
                    </div>
                    <%}}else{%>
                    <p>No existen eventos en este momento.</p>
                    <%}%>
                </div>
            </div>
          </section>


          <jsp:include page="footer.jsp" />
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>
