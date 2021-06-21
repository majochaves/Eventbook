<%@ page import="com.eventbookspring.eventbookspring.dto.TipoanalisisDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.CampoanalisisDTO" %><%--
  Created by IntelliJ IDEA.
  User: Merli
  Date: 20/06/2021
  Time: 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EventBook - Editar tabla</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="/components/base/base.css">
    <link rel="stylesheet" href="/components/base/tablas.css">
    <link rel="stylesheet" href="/components/base/modal.css">
    <link rel="stylesheet" href="/components/propios/tooltip.css">

    <!-- Esto esta roto <script src="components/jquery/jquery-3.4.1.min.js"></script>-->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="/components/bootstrap/js/popper.js"></script>
    <script src="/components/bootstrap/js/bootstrap.min.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>
    <script src="/components/propios/valoresNoRepetibles.js" defer></script>
    <script src="/components/propios/anyadirFilaEnUnaTabla.js" defer></script>
    <script src="/components/propios/eliminarFilaEnUnaTabla.js" defer></script>
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>

</head>
<%
    TipoanalisisDTO thisTipoanalisisDto = (TipoanalisisDTO) request.getAttribute("thisTipoanalisisDto");
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
                        <h3>Editar Análisis</h3>
                    </div>
                </div>

                <hr class="divider divider-sm mt-0"/>


                <form method="POST" action="/analisis/editar/tipoanalisis/<%=thisTipoanalisisDto.getId()%>">
                    <div class="row justify-content-md-center">
                        <div class="col-sm-6 pb-4">

                            <table id="tablaAnyadirFila" class="table table-sm table-hover table-bordered">

                                <thead>
                                <tr class="table-secondary">
                                    <th cope="col"><%=thisTipoanalisisDto.getNombre()%>
                                    </th> <!--Nombre de la columna-->
                                    <th scope="col">Valor</th>
                                    <th scope="col">Eliminar</th>
                                </tr>
                                </thead>

                                <tbody>
                                <%
                                    for (CampoanalisisDTO thisCampoanalisisDto : thisTipoanalisisDto.getCampoanalisisList()) {
                                %>
                                <tr>
                                    <td>
                                        <input class="form-control form-control-sm textoColumna1" type="text" name="nombres"
                                               value="<%=thisCampoanalisisDto.getNombre()%>">
                                    </td>
                                    <td><input class="form-control form-control-sm" type="number" name="valores"
                                               value="<%=thisCampoanalisisDto.getValor()%>"></td>
                                    <td class="text-center"><button type="button" class="shadow-sm badge badge-danger eliminarFila">Eliminar fila
                                    </button></td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                            <div class="text-right mr-4">
                                <button type="button" id="anyadirFila" class="shadow-sm badge badge-info">Añadir fila
                                </button>
                            </div>

                            <br/>
                        </div>


                    </div>

                    <div class="row justify-content-md-center">
                        <div class="col-8">
                            <span id="spanTooltip">
                                <button type="submit" class="btn btn-primary btn-lg btn-block" id="btnEnviar">Guardar</button>
                            </span>
                        </div>
                    </div>
                </form>


            </div>
        </div>

    </div>
</body>
</html>
