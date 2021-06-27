<%-- 
    Document   : evento_info
    Created on : 22-Apr-2021, 21:06:40
    Author     : majochaves
--%>

<%@page import="java.util.List" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.EventoDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Reserva" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reservar en un Evento</title>
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="/components/base/base.css">
    <script src="/components/eventos/eventos.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>

</head>
<%
    EventoDTO evento = (EventoDTO) request.getAttribute("evento");
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    Integer numMaxEntradas = evento.getMaxEntradas();
    String maxentradas = " " + "máximo " + numMaxEntradas + " entradas.";
    if (evento.getMaxEntradas() == null) {
        maxentradas = "";
    }
    List<Reserva> reservas = (List) request.getAttribute("reservas");
    String error = (String) request.getAttribute("strError");
    Integer numEntradas = (Integer) request.getAttribute("numEntradas");
    String editar = (String) request.getAttribute("editar");
    if (editar == null) editar = "";
%>
<body>
<div class="page">
    <jsp:include page="header.jsp"/>

    <!-- Intro-->
    <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container">
            <%
                if (evento != null) {
            %>
            <h1><%=evento.getTitulo()%>
            </h1>
            <%
                if (error != null) {
            %>
            <p><%=error%>
            </p>
            <%
                }
                if (!editar.isEmpty() && evento.getAsientosFijos() == 'S') {
            %>
            <h3>Información de tu reserva</h3>
            <p>Asientos reservados: <%=evento.getEntradasReservadas(usuario)%>
            </p>
            <table>
                <tr>
                    <th>Fila</th>
                    <th>Asiento</th>
                </tr>
                <%
                    for (Reserva r : reservas) {
                %>
                <tr>
                    <td><%=r.getReservaPK().getFila()%>
                    </td>
                    <td><%=r.getReservaPK().getAsiento()%>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <%
                }
            %>
            <div class="row justify-content-center">
                <form method="POST" action="/crearReserva">
                    <input type="hidden" name="usuarioId"
                           value="<%=Autenticacion.getUsuarioLogeado(request, response).getId()%>"/>
                    <input type="hidden" name="editar" value="<%=editar%>"/>
                    <input type="hidden" name="id" value="<%= evento.getId() %>"/>
                    <div class="form-group">
                        <label for="numEntradas">Número de Entradas:*</label><br/>
                        <input type="text" id="numEntradas" name="numEntradas"
                               class="form-control" value="<%= (editar.equalsIgnoreCase("editar") ? numEntradas : "") %>"/><%=maxentradas%>
                    </div>

                    <%
                        if (editar.equalsIgnoreCase("editar")) {
                    %>
                    <p>Nota*: Si pulsas editar, se borrará tu reserva actual y no podrás volver atrás. </p>
                    <input type="submit" class="btn btn-secondary" value="Editar"/>
                    <a href="/eliminarReserva//<%=evento.getId() + "/"+ usuario.getId()%>"
                       class="btn btn-danger">Eliminar</a>
                    <%
                    } else {
                    %>
                    <input type="submit" class="btn btn-primary" value="Aceptar"/>
                    <%
                        }
                    %>
                </form>
            </div>
            <%
                }
            %>
        </div>
    </section>


    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>

