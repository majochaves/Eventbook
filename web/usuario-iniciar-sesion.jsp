<%-- 
    Document   : usuario-iniciar-sesion.jsp
    Created on : 06-May-2021, 17:58:45
    Author     : josie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        
        <title>Inicio de sesión</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="nav" value="iniciar"/>  
        </jsp:include>
        <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h1>Inicio de sesión</h1>
                    </div>
                </div>
                <p style="color: #ec5482;"> <%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %> </p>
                <img style="float: right; margin-right: 15%; margin-top: -10%; width: 25%; max-width: 499px;" src="images/agregar-usuario.svg"/>
                <form action="ServletUsuarioIniciarSesion" method="POST">
                    <table>
                    <tr>
                        <td>Usuario</td>
                        <td><input class="textf" type="text" name="usuario" maxlength="30" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Contraseña&nbsp&nbsp</td>
                        <td><input class="textf" type="password" name="contrasena" maxlength="30" size="30" required="required" minlength="1"/></td>
                    </tr>
                    <table/>
                    <input class="btn btn-primary" type="submit" value="Iniciar sesión"> 
                    <a style="margin-left: 2%" href="index.jsp">Cancelar</a>
                </form>
            </div>
        </div>
    </body>
</html>
