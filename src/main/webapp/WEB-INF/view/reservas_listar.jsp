<%-- 
    Document   : reservas-listar
    Created on : 19-may-2021, 19:21:49
    Author     : fcode
--%>

<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.entity.Evento" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis Reservas</title>
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/eventos/eventos.js"></script>
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        
    </head>
    <%
        Usuario usuario = (Usuario)request.getAttribute("usuario");
        String nav = (String) request.getAttribute("nav");
        String filtro = (String) request.getAttribute("filtro");
        List<EventoDTO> eventos = (List) request.getAttribute("eventos");
    %>
    <body>
        <div class="page">
          <jsp:include page="header.jsp">
                <jsp:param name="nav" value="<%= nav %>"/>  
            </jsp:include>
          <!-- Intro-->
          <section class="section section-lg bg-white d-flex min-vh-100">
            <div class="container-fluid">
                <h1 class="text-center" data-animate='{"class":"fadeInUp"}'>Mis Reservas</h1>
                <!--
                <form align="right" action="ServletReservasListar">
                    <table>
                        <tr>
                            <td>Filtrar</td>
                            <td>
                                <select name="fechaFiltrado">
                                    <option <%= (filtro != null && filtro.equalsIgnoreCase("reciente") ? "selected" : " ")%> value="reciente">Fecha más reciente</option>
                                    <option <%= (filtro != null && filtro.equalsIgnoreCase("noReciente") ? "selected" : " ")%> value="noReciente">Fecha menos reciente</option>
                                </select>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-secondary"><i class="fas fa-filter"></i></button>
                            </td>
                        </tr>
                    </table>
                </form> -->

                <table class="table mx-auto">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Nombre del evento</th>
                        <th scope="col">Numero de asientos</th>
                        <th scope="col">Fecha</th>
                        <th scope="col">Acción</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if(eventos != null){
                        for(EventoDTO e : eventos){
                    %>
                    <tr>
                        <td><%=e.getTitulo()%></td>
                        <td><%=e.getEntradasReservadas(usuario)%></td>
                        <td><%=new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(e.getFecha()))%></td>
                        <td><a href="/editarReserva/<%=e.getId() + "/"+ usuario.getId()%>">Ver</a></td>
                    </tr>
                    </tbody>
                    <%}}else{%>
                        <p>No tiene reservas.</p><br/>
                    <%}%>
                </table>
            </div>
          </section>
          <jsp:include page="footer.jsp" />
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>