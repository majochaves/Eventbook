<%-- 
    Document   : analisisListar
    Created on : 29-abr-2021, 0:22:56
    Author     : Merli
--%>

<%@page import="entity.Analisis"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Listar Mis Análisis</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/tablas.css">
        <link rel="stylesheet" href="components/base/modal.css">
        <script src="components/jquery/jquery-3.4.1.min.js"></script>
        <script src="components/bootstrap/js/popper.js"></script>
        <script src="components/bootstrap/js/bootstrap.min.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />

            <div class="section section-lg bg-transparent">
                <div class="container">

                    <div class="row">
                        <div class="col-sm-12">
                            <h3>Mis Análisis Realizados</h3>
                        </div>
                    </div>

                    <hr class="divider divider-sm mt-0" />

                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-hover table-bordered">
                                <thead>
                                    <tr class="table-secondary">
                                        <th cope="col">Descripción</th>
                                        <th scope="col" class="text-center">Acceder</th>
                                        <th scope="col" class="text-center">Duplicar</th>
                                        <th scope="col" class="text-center">Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<Analisis> listaAnalisis = (List)request.getAttribute("listaAnalisis");
                                        for(Analisis esteAnalisis : listaAnalisis){
                                    %>
                                            <tr>
                                                <td><%=esteAnalisis.getDescripcion()%></td>
                                                <td class="align-middle text-center"><a href="ServeltAnalisisVer?id=<%=esteAnalisis.getId()%>" class="btn btn-primary">Acceder</a></td>
                                                <td class="align-middle text-center"><a href="#" class="btn btn-warning">Duplicar</a></td>
                                                <td class="align-middle text-center"><a href="ServeltAnalisisBorrar?id=<%=esteAnalisis.getId()%>" class="shadow-sm btn btn-danger">Eliminar</a></td>
                                            </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <br/>
                    <br/>
                </div>
            </div>
        </div>   
    </body>
</html>
