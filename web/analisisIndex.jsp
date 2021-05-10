<%-- 
    Document   : analisisIndex
    Created on : 28-abr-2021, 23:49:37
    Author     : Merli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Análisis</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/alert.css">
        <script src="components/jquery/jquery-3.4.1.min.js"></script>
        <script src="components/bootstrap/js/popper.js"></script>
        <script src="components/bootstrap/js/bootstrap.min.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />

            <b>EN DESARROLLO</b><br/>
            Debes ser Analista para poder acceder a todo. Si solo eres administrador como mucho podras ver, editar, eliminar pero NO CREAR.
            Esto es debido a en la BD, la tabla Analisis solo tiene relacion con la tabla Analista y no con Administrador.
            Por lo tanto, si solo eres admin no podrás crear estadisticas para ti pero podras visualizar estadisticas de otras personas.<br/><br/>

            Aun no has creado estadisticas! <a href="analisisGenerar.jsp">Empezar a crear una estadística</a><br/><br/>
            <a href="ServeltAnalisisListar">Ver mi lista de análisis</a>
        </div>
    </body>
</html>
