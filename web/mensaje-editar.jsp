<%-- 
    Document   : mensaje-editar
    Created on : 15-may-2021, 13:44:23
    Author     : memoriasIT
--%>
<%@page import="entity.Teleoperador"%>
<%@page import="clases.Autenticacion"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar un chat</title>
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Teleoperador.class);
        String error = (String) request.getAttribute("error");

    %>
    <body>
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
            <div class="container">
                <form action="ServletMessageEditar" method="POST">
                    <input type="hidden" id="userID" name="userID" value="<%= request.getParameter("userID") %>">
                    <input type="hidden" id="msgId" name="msgId" value="<%= request.getParameter("msgId") %>">
                    <div class="form-group-sm">
                            Contenido:<input type="text" class="form-control" name="newContenido" value="<%= request.getAttribute("contenido") %>"><br/><br/>
                    </div>
                    <div class="align-middle text-center">
                            <input type="submit" class="btn btn-primary" value="Modificar"/>
                        </div>
                </form>
            </div>
        </div>
    </body>
</html>
