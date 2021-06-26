<%-- 
    Document   : seleccionar_asientos
    Created on : 13-May-2021, 18:59:43
    Author     : majochaves
--%>

<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.entity.Evento" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seleccionar asientos</title>
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="/components/base/base.css">
        <script defer src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script defer src="/components/eventos/asientos.js"></script>
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        
    </head>
    <%
        EventoDTO evento = (EventoDTO)request.getAttribute("evento");
        int[][] matrizAsientos = (int[][])request.getAttribute("matrizAsientos");
        Integer numEntradas = (Integer)request.getAttribute("numEntradas");
        String editar = (String) request.getAttribute("editar");
        if(editar == null) editar = "";
        int asientos = 0;
    %>
    <body>
        <div class="page">

            <jsp:include page="header.jsp" />

            <!-- Intro-->
            <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
                <div class="container-fluid">
                        <h1>Selección de asientos para <%=evento.getTitulo()%></h1>
                        <p>Numero de entradas: <%=numEntradas%></p>
                        
                        <form method="POST" action="/reservarAsientos">
                            <input type="hidden" name="usuarioId" value="<%=Autenticacion.getUsuarioLogeado(request, response).getId()%>"/>
                            <input type="hidden" name="eventoId" value="<%=evento.getId()%>"/>
                            <input type="hidden" id="numEntradas" name="numEntradas" value="<%=new Integer(numEntradas)%>"/>
                            <table class="justify-content-center mx-auto">
                                <% for(int f = 0; f < matrizAsientos.length; f++){ %>
                                    <tr>
                                        <td class="px-2">Fila <%=f+1%></td>
                                        <%for(int a = 0; a < matrizAsientos[0].length; a++){ %>
                                        <td class="px-3">
                                            <%if(matrizAsientos[f][a] == 0){%> 
                                                <img src="images/AsientoDisponible.svg" height="25" width="25">
                                            <%}else{%>
                                            <img src="images/AsientoNoDisponible.svg" height="25" width="25">
                                            <%}%>
                                        </td>
                                        <%}%>
                                    </tr>
                                    <tr>
                                        <td class="px-2"></td>
                                        <%for(int a = 0; a < matrizAsientos[0].length; a++){ %>
                                        <td class="px-3">
                                            <%= a+1%>
                                        </td>
                                        <%}%>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <%for(int a = 0; a < matrizAsientos[0].length; a++){ %>
                                        <td> 
                                            <%
                                                if(matrizAsientos[f][a] == 0){ 
                                            %>

                                            <input type="checkbox" class="single-checkbox" name="asientosSeleccionados" value="<%=(f+1)+":"+(a+1)%>"/>
                                            <%}%>
                                        </td>
                                        <%}%>
                                    </tr>
                                <% } %>
                            </table>
                            <input id="btn-enviar" type="submit" class="btn btn-secondary" value="Enviar" style="display:none;"/>
                        </form>
                </div>
            </section>

            <jsp:include page="footer.jsp" />
            
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>
