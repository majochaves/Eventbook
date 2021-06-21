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
    <style>
        .chart-container {
            position: relative;
            margin: auto;
            height: 50vh;
            width: 30%;
            margin-top: 5em;
        }
    </style>
    <script>
        let idTipoanalisis;
        let listaItems;
        let listaValores;
        let listaColores, red, green, blue;
        let data;
        let config;
        let myChart;
        const randomBetween = (min, max) => min + Math.floor(Math.random() * (max - min + 1));
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

        <%
            for (TipoanalisisDTO thisTipoanalisisDto : thisAnalisisDto.getTipoanalisisList()) {
        %>
            <div class="chart-container">
                <canvas id="myChart<%=thisTipoanalisisDto.getId()%>"></canvas>
            </div>

            <script>
                idTipoanalisis = <%=thisTipoanalisisDto.getId()%>;
                listaItems = [];
                listaValores = [];
                listaColores = [];
                <%
                    for(CampoanalisisDTO thisCampoanalisisDto : thisTipoanalisisDto.getCampoanalisisList()){
                %>
                    listaItems.push('<%=thisCampoanalisisDto.getNombre()%>');
                    listaValores.push(<%=thisCampoanalisisDto.getValor()%>);
                    red = randomBetween(0, 255);
                    green = randomBetween(0, 255);
                    blue = randomBetween(0, 255);
                    rgb = `rgb(` + red + `, ` + green + `, ` + blue + `)`;
                    listaColores.push(rgb);
                <%
                    }
                %>

                data = {
                    labels: listaItems,
                    datasets: [{
                        label: '<%=thisTipoanalisisDto.getNombre()%>',
                        backgroundColor: listaColores,
                        data: listaValores,
                    }]
                };

                config = {
                    type: 'line',
                    data,
                    options: {}
                };

                config = {
                    type: 'doughnut',
                    data: data,
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'top',
                            },
                            title: {
                                display: true,
                                text: '<%=thisTipoanalisisDto.getNombre()%>'
                            }
                        }
                    },
                };

                 myChart = new Chart(
                    document.getElementById('myChart<%=thisTipoanalisisDto.getId()%>'),
                    config
                );

            </script>
        <%
            }
        %>

    </div>
</body>
</html>
