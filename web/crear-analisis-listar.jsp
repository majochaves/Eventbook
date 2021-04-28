<%-- 
    Document   : crear-analisis-listar
    Created on : 28-abr-2021, 14:23:07
    Author     : Merli
--%>

<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Listar Análisis</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/tablas.css">
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-transparent">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Listar Analisis Realizado</h3>
                    </div>
                </div>
                
                <hr class="divider divider-sm mt-0" />
                <div class="row">
                <%
                    Map<String, Map<String, Double>> listaFila = (Map)request.getAttribute("listaFila");
                    if(listaFila != null){
                        for(String nombreColumna : listaFila.keySet()){
                %>
                        <div class="col-sm-6 pb-4">
                            <table class="table table-hover table-bordered">
                                <thead>
                                    <tr class="table-primary">
                                        <th cope="col"><%=nombreColumna%></th>
                                        <th scope="col">Valor</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        Map<String, Double> conjuntoDeFilas = listaFila.get(nombreColumna);
                                        for(String key : conjuntoDeFilas.keySet()){
                                    %>
                                            <tr>
                                                <td><%=key%></td>
                                                <td><%=conjuntoDeFilas.get(key)%></td>
                                            </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <br/>
                    <%
                        }
                    } else {
                        response.sendRedirect("crearAnalisis.jsp");
                    }
                    %>
           
                </div>
            </div>
                    
        </div>
    </body>
</html>
