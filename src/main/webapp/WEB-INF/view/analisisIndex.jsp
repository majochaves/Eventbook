<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/alert.css">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="/components/bootstrap/js/popper.js"></script>
        <script src="/components/bootstrap/js/bootstrap.min.js"></script>

        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>


    </head>
    <body>


    <div class="page">
        <jsp:include page="./header.jsp">
            <jsp:param name="nav" value="analisis"/>
        </jsp:include>
            <!--<b>EN DESARROLLO</b><br/>-->
            <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
                <div class="container">
                    <div class="row justify-content-md-center">
                        <img style="background-color: rgba(255,255,255,0.68); padding: 20px; border-radius: 30px;" src="/images/EventBookLogoAnalisis.svg"/>
                    </div>
                    <div class="row justify-content-md-center">
                        <div class="col-5">
                            <a class="btn btn-secondary btn-block mt-5" href="/analisis/listar"><i class="fa-chevron-left"></i>&nbsp; Ver mi lista de análisis</a>
                        </div>
                        <div class="col-5">
                            <a class="btn btn-primary btn-block mt-5" href="/analisis/crear/mostrar">Empezar a crear una estadística &nbsp;<i class="fa-chevron-right"></i></a>
                        </div>
                    </div>
                </div>
            </section>

        </div>
    </body>
</html>