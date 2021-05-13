<%-- 
    Document   : seleccionar_asientos
    Created on : 13-May-2021, 18:59:43
    Author     : majochaves
--%>

<%@page import="java.util.List"%>
<%@page import="entity.Evento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seleccionar asientos</title>
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="components/eventos/asientos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        Evento evento = (Evento)request.getAttribute("evento");
        int[][] matrizAsientos = (int[][])request.getAttribute("matrizAsientos");
        Integer numAsientos = (Integer)request.getAttribute("numAsientos");
        
    %>
    <body>
      <div class="page">
      <!--RD Navbar-->
      <jsp:include page="header.jsp" />
      <!-- Intro-->
      <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container-fluid">
                <h1>Selección de asientos para <%=evento.getTitulo()%></h1>
                <p>Numero de entradas: <%=numAsientos%></p>
                <form method="POST" action="ServletEventoReservarAsientos">
                    <input type="hidden" name="eventoId" value="<%=evento.getId()%>"/>
                    <input type="hidden" id="numAsientos" name="numAsientos" value="<%=new Integer(numAsientos)%>"/>
                    <table class="d-flex justify-content-center">
                        <% for(int f = 0; f < matrizAsientos.length; f++){ %>
                            <tr>
                                <td>Fila <%=f+1%></td>
                                <%for(int a = 0; a < matrizAsientos[0].length; a++){ %>
                                <td> 
                                    <%if(matrizAsientos[f][a] == 0){%> 
                                        <img src="images/AsientoDisponible.svg" height="25" width="25">
                                    <%}else{%>
                                    <img src="images/AsientoNoDisponible.svg" height="25" width="25">
                                    <%}%>
                                </td>
                                <%}%>
                            </tr>
                            <tr>
                                <td></td>
                                <%for(int a = 0; a < matrizAsientos[0].length; a++){ %>
                                <td> 
                                    Asiento <%= a+1%>
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

                                    <input type="checkbox" class="single-checkbox" name="asientosSeleccionados" value="<%=f+1%>:<%=a+1%>"/>
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
      
      <!-- Footer default extended-->
      <jsp:include page="footer.jsp" />
    </div>
    <div class="to-top int-arrow-up"></div>
    </body>
</html>
