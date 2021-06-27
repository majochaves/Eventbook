<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
    Document   : evento
    Created on : 19-Apr-2021, 15:30:15
    Author     : majochaves
--%>

<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.*" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crear evento</title>
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="/components/base/base.css">
    <script defer src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script defer src="/components/eventos/eventos.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>

</head>
<%
    String strError = (String) request.getAttribute("strError");
    EventoDTO evento = (EventoDTO) request.getAttribute("evento");
    String selectedAFSi = "";
    String selectedAFNo = "checked";
    boolean editar = evento.getTitulo() != null;
    String showConfig = "none";
    if (evento != null && (!(Autenticacion.tieneRol(request, response, Administrador.class) || (Autenticacion.tieneRol(request, response, Creadoreventos.class) && evento.getCreadoreventos().equals(Autenticacion.getUsuarioLogeado(request, response).getCreadoreventos()))))) {
        Autenticacion.error(request, response, Autenticacion.PERMISOS);
    }
    if (editar) {
        if (evento.getAsientosFijos() == 'S') {
            showConfig = "block";
            selectedAFSi = "checked";
            selectedAFNo = "";
        } else {
            selectedAFNo = "checked";
            selectedAFSi = "";
        }
    }
%>
<body>
<div class="page">
    <jsp:include page="header.jsp"/>
    <!-- Intro-->
    <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container">
            <div class="row justify-content-center">
                <h1><%= editar ? "Editar evento " + evento.getId() : "Crear un nuevo evento" %>
                </h1>
                <%if (strError != null) {%>
                <p><%=strError%>
                </p>
                <%}%>
            </div>
            <div class="row justify-content-center">
                <form:form method="POST" action="/eventoGuardar" modelAttribute="evento">
                    <form:hidden path="id"/>
                    <input type="hidden" name="creadorEventosId"
                           value="<%=Autenticacion.getUsuarioLogeado(session).getCreadoreventos()%>"/>
                    <div class="form-group">
                        <label for="titulo">Título:*</label>
                        <form:input path="titulo" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">Descripción:</label>
                        <form:textarea path="descripcion" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="fecha">Fecha:*</label>
                        <form:input path="fecha" type="date" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="fechaLimite">Fecha límite para reservar entradas:</label>
                        <form:input path="fechaLimite" type="date" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="costeEntrada">Coste de entrada:</label>
                        <form:input path="costeEntrada" type="number" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="aforo">Aforo del evento:</label>
                        <form:input path="aforo" type="number" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="maxEntradas">Máximo número de entradas por usuario:</label>
                        <form:input path="maxEntradas" type="number" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="etiquetaList">Etiquetas:</label>
                        <form:select class="form-control" path="etiquetaList" multiple="true" items="${etiquetaList}"
                                     itemLabel="descripcion"/>
                    </div>
                    <div class="form-group">
                        <label>Asientos fijos:</label>
                        <form:radiobutton path="asientosFijos" id="asientosFijosSi" value="S"
                                          checked="<%=selectedAFSi%>"/>Si
                        <form:radiobutton path="asientosFijos" id="asientosFijosNo" value="N"
                                          checked="<%=selectedAFNo%>"/>No
                    </div>
                    <div id="configuracionAsientos" style="display:<%=showConfig%>;">
                        <div class="form-group">
                            <label for="numFilas">Número de filas*:</label>
                            <form:input id="numFilas" class="form-control" path="numFilas" type="number"/>
                        </div>
                        <div class="form-group">
                            <label for="numAsientosFila">Número de asientos por fila*:</label>
                            <form:input id="numAsientosFila" class="form-control" path="numAsientosFila" type="number"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <a class="btn btn-danger" href="/eventos">Cancelar</a>
                        </div>
                        <div class="col-8">
                            <input class="btn btn-primary btn-block" type="submit"
                                   value="<%= editar ? "Confirmar cambios" : "Crear evento" %>"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp"/>
</div>
<div class="to-top int-arrow-up"></div>
</body>
</html>
