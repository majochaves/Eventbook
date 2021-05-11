<%-- 
    Document   : evento_list
    Created on : 20-Apr-2021, 12:09:51
    Author     : majochaves
--%>

<%@page import="entity.Evento"%>
<%@page import="entity.Etiqueta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventos</title>
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        List<Evento> eventos = (List)request.getAttribute("lista");
    %>
    <body>
      <div class="page">
      <!--RD Navbar-->
      <jsp:include page="header.jsp" />
      <!-- Intro-->
      <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container-fluid">
            <h1 class="text-center" data-animate='{"class":"fadeInUp"}'>
              Eventos
            </h1>
            <a href="ServletEventoCrear" class="btn btn-primary">Crear evento</a>
            <div class="row row-30 row-md-40 row-lg-50 justify-content-center">
                <% if(eventos != null){
                    for(Evento e : eventos){
                %>
                <div class="col-xs-6 col-md-4 col-xl-auto" data-animate='{"class":"fadeInUp"}'>
                        <!-- Layout-->
                        <article class="layout"><a class="layout-figure thumbnail-up-shadow" href="ServletEventoVer?id=<%= e.getId() %>"><img src="images/calendar.svg" alt="" width="336" height="450"/></a>
                            <div class="layout-title h6"><a class="layout-title-link" href="ServletEventoVer?id=<%= e.getId() %>"><%=e.getTitulo()%></a>
                          </div>
                        </article>
                </div>
                <%
                    }
                }
                %>
            </div>
        </div>
      </section>
      
      <!-- Footer default extended-->
      <jsp:include page="footer.jsp" />
    </div>
    <div class="to-top int-arrow-up"></div>
    </body>
</html>
