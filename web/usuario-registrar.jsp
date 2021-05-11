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
        
        <title>Registro</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-transparent container">
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <h1>Registro</h1>
                </div>
            </div>
            <hr class="divider divider-sm mt-0" />
            <br/>
            
            <%-- ERROR --%>
            <p style="color: #ec5482;"> <%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %> </p>
            <form action="ServletRegistrarUsuario" method="POST">
                <table>
                    <tr>
                        <td>Usuario</td>
                        <td><input type="text" name="usuario" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Contraseña&nbsp&nbsp</td>
                        <td><input type="password" name="pass" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Repetir Contraseña&nbsp&nbsp</td>
                        <td><input type="password" name="pass1" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Nombre</td>
                        <td><input type="text" name="nombre" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Apellidos</td>
                        <td><input type="text" name="apellidos" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Domicilio</td>
                        <td><input type="text" name="domicilio" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Ciudad de Residencia</td>
                        <td><input type="text" name="ciudad" maxlength="50" size="30" minlength="1" required="required" autocomplete="on" /></td>
                    </tr>
                    <tr>
                        <td>Sexo</td>
                        <td>
                            <select name="sexo">
                                <option>HOMBRE</option>
                                <option>MUJER</option>
                                <option>OTRO</option>
                            </select>
                    </tr>
                </table>    
                <input class="btn btn-primary" type="submit" value="Siguiente">
                <a style="margin-left: 2%" href="index.jsp">Cancelar</a>           
            </form>
        </div>
    </body>
</html>
