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
        <link rel="icon" href="images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
        <title>Registro</title>
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp">
                <jsp:param name="nav" value="registrar"/>  
            </jsp:include>
            <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-12">
                            <h1>Registro de usuario</h1>
                        </div>
                    </div>
                    
                    <p style="color: #ec5482;"> <%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %> </p>
                    <img style="float: right; margin-right: 15%; margin-top: 0%; width: 25%; max-width: 499px;" src="images/agregar-usuario.svg"/>
                    
                    <form action="ServletRegistrarUsuario" method="POST">
                        <table>
                            <tr>
                                <td>Usuario<span style="color: #ec5482;">*</span></td>
                                <td><input class="textf" type="text" name="usuario" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Contraseña<span style="color: #ec5482;">*</span>&nbsp&nbsp</td>
                                <td><input class="textf" type="password" name="pass" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Repetir Contraseña<span style="color: #ec5482;">*</span>&nbsp&nbsp</td>
                                <td><input class="textf" type="password" name="pass1" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Nombre<span style="color: #ec5482;">*</span></td>
                                <td><input class="textf" type="text" name="nombre" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Apellidos<span style="color: #ec5482;">*</span></td>
                                <td><input class="textf" type="text" name="apellidos" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Domicilio<span style="color: #ec5482;">*</span></td>
                                <td><input class="textf" type="text" name="domicilio" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Ciudad de Residencia<span style="color: #ec5482;">*</span></td>
                                <td><input class="textf" type="text" name="ciudad" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                            </tr>
                            <tr>
                                <td>Sexo<span style="color: #ec5482;">*</span></td>
                                <td>
                                    <select class="textf" name="sexo">
                                        <option>Hombre</option>
                                        <option>Mujer</option>
                                    </select>
                            </tr>
                            
                            <tr>
                                <td><a class="btn btn-danger mt-5" href="index.jsp">Cancelar</a></td>
                                <td><input class="btn btn-primary btn-block mt-5" type="submit" value="Siguiente"></td>
                            </tr>
                        </table>
                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
