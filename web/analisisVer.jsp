<%-- 
    Document   : analisisVer
    Created on : 29-abr-2021, 1:32:30
    Author     : Merli
--%>

<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Ver Análisis</title>
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
        <script type="text/javascript" src="components/propios/chart.js"></script>
        <script type="text/javascript">
            google.charts.load("current", {packages:["corechart"]});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {
              var data = google.visualization.arrayToDataTable([
                ['Task', 'Hours per Day'],
                ['Work',     11],
                ['Eat',      2],
                ['Commute',  2],
                ['Watch TV', 2],
                ['Sleep',    7]
              ]);

              var options = {
                title: 'My Daily Activities',
                is3D: true,
              };

              var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
              chart.draw(data, options);
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-transparent">
            <div class="container">
                
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Análisis</h3>
                    </div>
                </div>
                
                <hr class="divider divider-sm mt-0" />
                
                <div class="row">
                    <%
                        Map<String, Map<String, Double>> listaTablas = (Map) request.getAttribute("listaTablas");
                        for(String nombreColumna : listaTablas.keySet()){
                    %>
                        <div class="col-md-6">
                            <%
                                if(nombreColumna.equalsIgnoreCase("Sexo")){
                            %>
                                <div id="piechart_3d" style="width: 600px; height: 300px;"></div>
                                <p>ENTRA</p>
                            <%
                                }
                            %>
                        </div>
                    <%
                        }
                    %>
                </div>
                
                
                
                
            </div>
        </div>
    </body>
</html>
