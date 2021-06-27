<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
    Document   : evento
    Created on : 19-Apr-2021, 15:30:15
    Author     : majochaves
--%>

<%@ page import="com.eventbookspring.eventbookspring.entity.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crear Etiqueta</title>
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="/components/base/base.css">
    <script defer src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script defer src="/components/eventos/eventos.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>

</head>
<%
    String strError = (String)request.getAttribute("strError");
    Etiqueta etiqueta = (Etiqueta) request.getAttribute("etiqueta");
    boolean editar = etiqueta.getDescripcion() != null;
%>
<body>
<div class="page">
    <jsp:include page="header.jsp" />
    <!-- Intro-->
    <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container">
            <div class="row justify-content-center">
                <h1><%= editar ? "Editar etiqueta " + etiqueta.getDescripcion(): "Crear una nueva etiqueta" %></h1>
                <%if(strError != null){%>
                <p><%=strError%></p>
                <%}%>
            </div>
            <div class="row justify-content-center">
                <form:form method="POST" action="/etiquetaGuardar" modelAttribute="etiqueta">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <label for="descripcion">Descripcion:*</label>
                        <form:input path="descripcion" class="form-control" />
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <a class="btn btn-danger" href="/verEtiquetas">Cancelar</a>
                        </div>
                        <div class="ml-3 col-auto">
                            <input class="btn btn-primary btn-block" type="submit" value="<%= editar ? "Confirmar cambios" : "Crear etiqueta" %>"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </section>

    <jsp:include page="footer.jsp" />
</div>
</body>
</html>
