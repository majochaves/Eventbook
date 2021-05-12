<%-- 
    Document   : evento_info
    Created on : 22-Apr-2021, 21:06:40
    Author     : majochaves
--%>

<%@page import="entity.ReservaPK"%>
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
        Integer numMaxEntradas = evento.getMaxEntradas();
        String maxentradas = " "+"máximo "+numMaxEntradas+" entradas.";
        List<ReservaPK> reservas = (List) request.getAttribute("reservas");
        
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
                <form method="POST" action="ServletEventoReservar">
                    <table>
                        <tr>
                            <td>Numero de Entradas </td>
                            <td><input type="text" name="numEntradas" value="" /><%=maxentradas%></td>
                            <input type="hidden" name="id" value="<%= evento.getId() %>" />
                        </tr>
                        <%
                          
                            if(evento.getAsientosFijos().equals('s')){
                                int filas = evento.getNumFilas();
                                int numAsientos = evento.getNumAsientosFila();
                        %>
                        <th>Seleccione sus entradas</th>
                        <table>
                            <%
                                for(int i = 0; i < filas; i++){
                            %>        
                                <tr>
                            <%        
                                    for(int j = 0; j < numAsientos; j++){  
                            %>        
                                        <td><input type="checkbox" <%= (reservas != null && reservas.contains(new ReservaPK(i+1, j+1, evento.getId())) ? "checked disabled" : " ")%> name="" value="<%= i %>+' '+<%= j %>" /></td>
                            <%            
                                    }
                            %>
                                </tr>
                            <%
                                }   
                            }
                        %>    
                    </table>
                    <input type="submit" class="btn btn-secondary" value="Aceptar" />
                </form>
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

