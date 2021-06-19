<%@ page import="com.eventbookspring.eventbookspring.dto.AnalisisDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Merli
  Date: 19/06/2021
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EventBook - Listar Mis Análisis</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="/components/base/base.css">
    <link rel="stylesheet" href="/components/base/tablas.css">
    <link rel="stylesheet" href="/components/base/modal.css">
    <!-- Esto esta roto <script src="components/jquery/jquery-3.4.1.min.js"></script>-->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="/components/bootstrap/js/popper.js"></script>
    <script src="/components/bootstrap/js/bootstrap.min.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>
</head>
<%
    List<AnalisisDTO> listaAnalisis = (List<AnalisisDTO>) request.getAttribute("listaAnalisis");
%>

<body>
<div class="page">
    <jsp:include page="header.jsp"/>

    <div class="section section-lg bg-transparent">
        <div class="container">

            <div class="row">
                <div class="col-sm-12">
                    <h3>Mis Análisis Realizados</h3>
                </div>
            </div>

            <hr class="divider divider-sm mt-0"/>

            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr class="table-secondary">
                            <th cope="col">Descripción</th>
                            <th scope="col" class="text-center">Acceder</th>
                            <th scope="col" class="text-center">Eliminar</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (AnalisisDTO thisAnalisis : listaAnalisis) {
                        %>
                        <tr>
                            <td><%=thisAnalisis.getDescripcion()%>
                            </td>

                            <td class="align-middle text-center"><a
                                    href="ServeltAnalisisVer?id=<%=thisAnalisis.getId()%>" class="btn btn-primary">Acceder</a>
                            </td>

                            <td class="align-middle text-center"><a
                                    href="ServeltAnalisisBorrar?id=<%=thisAnalisis.getId()%>"
                                    class="shadow-sm btn btn-danger">Eliminar</a></td>
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