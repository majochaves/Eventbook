<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.AnalisisDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.TipoanalisisDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.CampoanalisisDTO" %><%--
  Created by IntelliJ IDEA.
  User: Merli
  Date: 19/06/2021
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EventBook - Ver Análisis</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="/components/base/base.css">
    <link rel="stylesheet" href="/components/base/tablas.css">
    <link rel="stylesheet" href="/components/base/modal.css">

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="/components/bootstrap/js/popper.js"></script>
    <script src="/components/bootstrap/js/bootstrap.min.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>
</head>
<%
    AnalisisDTO thisAnalisisDto = (AnalisisDTO) request.getAttribute("thisAnalisisDto");
%>
<body>
<div class="page">
    <jsp:include page="./header.jsp">
        <jsp:param name="nav" value="analisis"/>
    </jsp:include>
    <div class="section section-lg bg-transparent">
        <div class="container">

            <div class="row">
                <div class="col-sm-12">
                    <h3>Análisis</h3>
                </div>
            </div>

            <hr class="divider divider-sm mt-0"/>

            <div class="row align-items-center">
                <div class="col-sm-10">
                    <p>
                        <b>Descripción: </b><%= thisAnalisisDto.getDescripcion() %>
                    </p>
                </div>

                <div class="col-sm-2 text-right">
                    <button type="button" class="shadow-sm badge badge-info" data-toggle="modal"
                            data-target="#abrirDialogoDuplicar">Duplicar
                    </button>
                    <button type="button" class="shadow-sm badge badge-warning" data-toggle="modal"
                            data-target="#abrirDialogoEditar">Modificar
                    </button>
                    <%
                        if(thisAnalisisDto.getTipoanalisisList()!=null && !thisAnalisisDto.getTipoanalisisList().isEmpty()){
                    %>
                    <a href="/analisis/ver/graficas/<%=thisAnalisisDto.getId()%>" class="shadow-sm badge badge-primary">Ver
                        gráficas</a>
                    <%
                        }
                    %>
                </div>
            </div>

            <div class="row">
                <%
                    for (TipoanalisisDTO thisTipoanalisisDto : thisAnalisisDto.getTipoanalisisList()) {
                %>
                <div class="col-sm-6 pb-4">
                    <table class="table table-sm table-hover table-bordered">

                        <thead>
                        <tr class="table-secondary">
                            <th cope="col"><%=thisTipoanalisisDto.getNombre()%>
                            </th> <!--Nombre de la columna-->
                            <th scope="col">Valor</th>
                        </tr>
                        </thead>

                        <tbody>
                        <%
                            for (CampoanalisisDTO thisCampoanalisisDto : thisTipoanalisisDto.getCampoanalisisList()) {
                        %>
                        <tr>
                            <td><%=thisCampoanalisisDto.getNombre()%>
                            </td>
                            <td><%=thisCampoanalisisDto.getValor()%>
                            </td>
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
                %>
            </div>


        </div>
    </div>


    <!-- Bootstrap Modal (Ventana emergente para EDITAR)  -->
    <div class="modal fade" id="abrirDialogoEditar" tabindex="-1" role="dialog" aria-labelledby="ventanaEditar"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form:form modelAttribute="thisAnalisisDto" action="/analisis/editar/analisis" method="POST">
                <%--            <form method="POST" action="ServletAnalisisEditar?id=<%=thisAnalisisDto.getId()%>">--%>
                <form:hidden path="id"/>
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="ventanaEditar">Editar Análisis</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="form-group pb-4">
                            <label for="desc" class="col-form-label">Descripción</label>
                                <%--                            <textarea class="form-control" name="descripcion"><%=thisAnalisisDto.getDescripcion()%></textarea>--%>
                            <form:textarea path="descripcion" class="form-control"/>
                        </div>

                        <%
                            for (TipoanalisisDTO thisTipoanalisisDto : thisAnalisisDto.getTipoanalisisList()) {
                        %>
                        <div class="row align-middle mt-4">
                            <div class="col-6">
                                <%=thisTipoanalisisDto.getNombre()%>
                            </div>
                            <div class="col-6 text-right">
                                <a href="/analisis/editar/tipoanalisis/mostrar/<%=thisTipoanalisisDto.getId()%>"
                                   class="shadow-none badge badge-warning">Editar tabla</a>
                            </div>
                        </div>

                        <hr class="divider divider-sm mt-0"/>
                        <%
                            }
                        %>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary mt-0" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary mt-0">Guardar</button>
                    </div>

                </div>
                <%--            </form>--%>
            </form:form>
        </div>
    </div>


    <!-- Bootstrap Modal (Ventana emergente para DUPLICAR) -->

    <div class="modal fade" id="abrirDialogoDuplicar" tabindex="-1" role="dialog" aria-labelledby="ventanaDuplicar"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form:form modelAttribute="thisAnalisisDto" action="/analisis/duplicar" method="POST">
                <form:hidden path="id"/>
                <%--            <form method="POST" action="ServletAnalisisDuplicar?id=<%=thisAnalisisDto.getId()%>">--%>
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="ventanaDuplicar">Duplicar Análisis</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="form-group">
                            <label for="desc" class="col-form-label">Nombre y/o descripción</label>
                                <%--                            <textarea class="form-control" id="desc" name="descripcion"></textarea>--%>
                            <form:textarea path="descripcion" id="desc" class="form-control"/>
                        </div>
                        <div class="text-right">
                            <a href="#" class="badge badge-success" id="botonAutogenerar">Autogenerar descripción</a>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary mt-0" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary mt-0">Duplicar</button>
                    </div>

                </div>
                <%--            </form>--%>
            </form:form>
        </div>
    </div>


    <!--Autogeneracion de descripcion-->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let cajaDescripcion = document.getElementById('desc');
            let botonAutog = document.getElementById('botonAutogenerar');
            botonAutog.onclick = function () {
                cajaDescripcion.value = 'Duplicado: <%= thisAnalisisDto.getDescripcion() %>';
            };
        });
    </script>

</div>
</body>
</html>
