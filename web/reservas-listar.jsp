<%-- 
    Document   : reservas-listar
    Created on : 19-may-2021, 19:21:49
    Author     : fcode
--%>

<%@page import="java.util.List"%>
<%@page import="entity.Evento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis Reservas</title>
        <link rel="icon" href="images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        String nav = (String) request.getAttribute("nav");
        String filtro = (String) request.getAttribute("filtro");
        List<Evento> eventos = (List) request.getAttribute("eventos");
    %>
    <body>
        <div class="page">
          <jsp:include page="header.jsp">
                <jsp:param name="nav" value="<%= nav %>"/>  
            </jsp:include>
          <!-- Intro-->
          <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
            <div class="container-fluid">
                <h1 class="text-center" data-animate='{"class":"fadeInUp"}'>
                Mis Reservas
                </h1>
                
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
                </form>
                                    
                                    
                <div class="row row-30 row-md-40 row-lg-50 justify-content-center">
                    <%  if(eventos != null){
                            for(Evento e : eventos){
                    %>
                    <div class="col-xs-6 col-md-4 col-xl-auto" data-animate='{"class":"fadeInUp"}'>
                            <!-- Layout-->
                            <article class="layout"><a class="layout-figure thumbnail-up-shadow" href="ServletReservaMostrar?id=<%= e.getId() %>"><img src="images/calendar.svg" alt="" width="336" height="450"/></a>
                                <div class="layout-title h6"><a class="layout-title-link" href="ServletReservaMostrar?id=<%= e.getId() %>"><%=e.getTitulo()%></a>
                              </div>
                            </article>
                    </div>
                    <%
                            }
                        } else {
                        %>
                            <p>No tiene reservas.</p>                    
                            <br/> 
                        <%                        
                        }
                    %>
                </div>                   
          </section>
          <jsp:include page="footer.jsp" />
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>