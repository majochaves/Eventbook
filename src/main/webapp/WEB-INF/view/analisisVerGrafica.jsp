<%@ page import="com.eventbookspring.eventbookspring.dto.AnalisisDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.TipoanalisisDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.CampoanalisisDTO" %><%--
  Created by IntelliJ IDEA.
  User: Merli
  Date: 21/06/2021
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EventBook - Ver Análisis</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
    <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="/components/base/base.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="/components/bootstrap/js/popper.js"></script>
    <script src="/components/bootstrap/js/bootstrap.min.js"></script>
    <script src="/components/base/core.js"></script>
    <script src="/components/base/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        let randomBetween = (min, max) => min + Math.floor(Math.random() * (max - min + 1));
        let listaItems = [];
    </script>
</head>
<%
    AnalisisDTO thisAnalisisDto = (AnalisisDTO) request.getAttribute("thisAnalisisDto");
%>
<body>
    <div class="page">
        <jsp:include page="./header.jsp">
            <jsp:param name="nav" value="analisis"/>
        </jsp:include>

            <div class="row">
                <%
                    for (TipoanalisisDTO thisTipoanalisisDto : thisAnalisisDto.getTipoanalisisList()) {
                %>
                <div class="col-md-6 mb-5">
                    <div class="row justify-content-md-center" style="height:400px;">
                        <canvas id="myChart<%=thisTipoanalisisDto.getId()%>"></canvas>
                    </div>

                    <script>
                        listaItems.push({
                            idTipoanalisis: <%=thisTipoanalisisDto.getId()%>,
                            nombreTipoanalisis: '<%=thisTipoanalisisDto.getNombre()%>',
                            items: [],
                            valores: [],
                            listaColores: []
                        });

                        <%
                            for(CampoanalisisDTO thisCampoanalisisDto : thisTipoanalisisDto.getCampoanalisisList()){
                        %>
                            listaItems[listaItems.length - 1].items.push('<%=thisCampoanalisisDto.getNombre()%>');
                            listaItems[listaItems.length - 1].valores.push(<%=thisCampoanalisisDto.getValor()%>);
                            red = randomBetween(0, 255);
                            green = randomBetween(0, 255);
                            blue = randomBetween(0, 255);
                            rgb = `rgba(` + red + `, ` + green + `, ` + blue + `, 0.6)`;
                            listaItems[listaItems.length - 1].listaColores.push(rgb);
                        <%
                            }
                        %>
                    </script>


                    <div class="row mb-5">
                        <div class="col-12 text-center">
                            <div class="btn-group btn-group-toggle conjuntoBotones" data-toggle="buttons" id="<%=thisTipoanalisisDto.getId()%>">
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="option<%=thisTipoanalisisDto.getId()%>" value="ROSQUILLA" autocomplete="off" checked> Rosquilla
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="option<%=thisTipoanalisisDto.getId()%>" value="LINEA" autocomplete="off"> Línea
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="option<%=thisTipoanalisisDto.getId()%>" value="BARRA" autocomplete="off"> Barra
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="option<%=thisTipoanalisisDto.getId()%>" value="RADAR" autocomplete="off"> Radar
                                </label>
                            </div>
                        </div>
                    </div>

<%--                    <div class="row">--%>
<%--                        <div class="col-12">--%>
<%--                            <div class="btn-toolbar justify-content-center">--%>
<%--                                <div class="btn-group">--%>
<%--                                    <button type="button" class="btn btn-secondary" checked>Rosquilla</button>--%>
<%--                                    <button type="button" class="btn btn-secondary">Línea</button>--%>
<%--                                    <button type="button" class="btn btn-secondary">Otro mas</button>--%>
<%--                                    <button type="button" class="btn btn-secondary">Otro</button>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                </div>
                <%
                    }
                %>
            </div>


        <script src="/components/propios/generarGraficasAnalisis.js"></script>
    </div>
</body>
</html>
