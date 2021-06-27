<%--
    Document   : reservas-listar
    Created on : 19-may-2021, 19:21:49
    Author     : fcode
--%>

<%@page import="java.util.List" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Etiqueta" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Mis Reservas</title>
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <script src="/components/eventos/eventos.js"></script>

    <link rel="stylesheet" href="components/base/base.css">
    <link rel="stylesheet" href="components/base/tablas.css">
    <link rel="stylesheet" href="components/base/modal.css">
    <script src="components/jquery/jquery-3.4.1.min.js"></script>
    <script src="components/bootstrap/js/popper.js"></script>
    <script src="components/bootstrap/js/bootstrap.min.js"></script>
    <script src="components/base/core.js"></script>
    <script src="components/base/script.js"></script>

</head>
<%
    List<Etiqueta> etiquetaList = (List) request.getAttribute("etiquetaList");
%>
<body>
<div class="page">

    <jsp:include page="header.jsp">
        <jsp:param name="nav" value="eventos"/>
    </jsp:include>
    <!-- Intro-->
    <section class="section section-lg bg-white d-flex min-vh-100">
        <div class="container-fluid text-center">
            <h1 class="text-center" data-animate='{"class":"fadeInUp"}'>Etiquetas</h1>
            <a href="/crearEtiqueta" class="btn btn-warning mb-3">Crear etiqueta</a>
            <div class="row justify-content-center">
                <div class="col-6">
                    <div class="main-containter">
                        <% if (!etiquetaList.isEmpty()) { %>
                        <table class="table table-hover table-bordered">
                            <tr class="table-secondary">
                                <th scope="col">Nombre de la Etiqueta</th>
                                <th scope="col">Editar</th>
                                <th scope="col">Borrar</th>
                            </tr>
                            <tbody>
                            <%
                                for (Etiqueta e : etiquetaList) {
                            %>
                            <tr>
                                <td><%=e.getDescripcion()%>
                                </td>
                                <td><a class="btn btn-primary" href="/editarEtiqueta/<%=e.getId()%>">Editar</a></td>
                                <td><a class="btn btn-danger" href="/borrarEtiqueta/<%=e.getId()%>">Borrar</a></td>
                            </tr>
                            </tbody>
                            <%
                                }
                            } else {
                            %>
                            <p>No hay etiquetas.</p><br/>
                            <%}%>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </section>
    <jsp:include page="footer.jsp"/>
</div>
<div class="to-top int-arrow-up"></div>
</body>
</html>