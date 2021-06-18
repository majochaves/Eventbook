<%--
  Created by IntelliJ IDEA.
  User: Merli
  Date: 18/06/2021
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EventBook - Listar Análisis</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
    <link rel="icon" href="images/calendar-favicon.png" type="image/x-icon">
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="components/base/tablas.css">
    <link rel="stylesheet" href="components/base/modal.css">
    <link rel="stylesheet" href="/components/base/base.css">
    <!-- Esto esta roto <script src="/components/jquery/jquery-3.4.1.min.js"></script>-->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="/components/bootstrap/js/popper.js"></script>
    <script src="/components/bootstrap/js/bootstrap.min.js"></script>

    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>
</head>
<body>
<div class="page">
    <jsp:include page="header.jsp" />
    <div class="section section-lg bg-transparent">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <h3>Listar Análisis Realizado</h3>
                </div>
            </div>

            <hr class="divider divider-sm mt-0" />

            <p>
                <b>Autogenerado:</b>
                <u>Análisis de:</u><%=(String)request.getAttribute("AutoGeneradoAnalisisDe")%>
                ; <u>Usando un tipo de filtro:</u> <%=(String)request.getAttribute("AutoGeneradoTiposFiltros")%>
            </p>

            <div class="row">
                <%
                    Map<String, Map<String, Double>> listaTablas = (Map<String, Map<String, Double>>) request.getAttribute("listaTablas");
                    if(listaTablas != null){
                        HttpSession sesion = request.getSession(false);
                        sesion.setAttribute("analisislistaTablas", listaTablas);
                        for(String nombreColumna : listaTablas.keySet()){
                %>
                <div class="col-sm-6 pb-4">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr class="table-secondary">
                            <th cope="col"><%=nombreColumna%></th>
                            <th scope="col">Valor</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            Map<String, Double> conjuntoDeFilas = listaTablas.get(nombreColumna);
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
                        response.sendRedirect("/analisis/crear/mostrar");
                    }
                %>

            </div>

            <div class="row justify-content-md-center">
                <div class="col-8">
                    <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="modal" data-target="#abrirDialogoGuardar">Guardar Análisis</button>
                </div>
            </div>

        </div>
        <br/>
    </div>

    <!-- Bootstrap Modal (Ventana emergente) -->

    <div class="modal fade" id="abrirDialogoGuardar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form method="POST" action="ServletAnalisisGuardar">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Guardar Análisis</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="form-group">
                            <label for="desc" class="col-form-label">Nombre y/o descripción</label>
                            <textarea class="form-control" id="desc" name="descripcion"></textarea>
                        </div>
                        <div class="text-right">
                            <a href="#" class="badge badge-success" id="botonAutogenerar">Autogenerar descripción</a>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary mt-0" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary mt-0">Guardar</button>
                    </div>

                </div>
            </form>
        </div>
    </div>

    <!--Autogeneracion de descripcion-->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            let cajaDescripcion = document.getElementById('desc');
            let botonAutog = document.getElementById('botonAutogenerar');
            botonAutog.onclick = function() {
                cajaDescripcion.value = 'Analisis de:<%=(String)request.getAttribute("AutoGeneradoAnalisisDe")%>; Usando un tipo de filtro:<%=(String)request.getAttribute("AutoGeneradoTiposFiltros")%>';
            };
        });
    </script>
</div>
</body>
</html>
