<%-- 
    Document   : tipoAnalisisEditar
    Created on : 01-may-2021, 1:20:46
    Author     : Merli
--%>

<%@page import="entity.Campoanalisis"%>
<%@page import="entity.Tipoanalisis"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Editar tabla</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/tablas.css">
        <link rel="stylesheet" href="components/base/modal.css">
        <link rel="stylesheet" href="components/propios/tooltip.css">
        
        <script src="components/jquery/jquery-3.4.1.min.js"></script>
        <script src="components/bootstrap/js/popper.js"></script>
        <script src="components/bootstrap/js/bootstrap.min.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        <script src="components/propios/valoresNoRepetibles.js" defer></script>
        <script src="components/propios/anyadirFilaEnUnaTabla.js" defer></script>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });
        </script>
        
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />
            <div class="section section-lg bg-transparent">
                <div class="container">
                    
                    <%
                        Tipoanalisis tipoAnalisis = (Tipoanalisis) request.getAttribute("tipoAnalisis");
                    %>
                    
                    <div class="row">
                        <div class="col-sm-12">
                            <h3>Editar Análisis</h3>
                        </div>
                    </div>
                    
                    <hr class="divider divider-sm mt-0" />
                    
                    
                    

                    
                    <div class="row justify-content-md-center">
                        <div class="col-sm-6 pb-4">
                            <form method="POST" action="ServletTipoanalisisEditar?id=<%=tipoAnalisis.getId()%>">
                                <table id="tablaAnyadirFila" class="table table-sm table-hover table-bordered">

                                    <thead>
                                        <tr class="table-secondary">
                                            <th cope="col"><%=tipoAnalisis.getNombre()%></th> <!--Nombre de la columna-->
                                            <th scope="col">Valor</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            int i = 1;
                                            for(Campoanalisis thisCampoanalisis : tipoAnalisis.getCampoanalisisList()){//fila=icol1
                                        %>
                                                <tr>
                                                    <td>
                                                        <input class="form-control form-control-sm textoColumna1" type="text" name="nombres" value="<%=thisCampoanalisis.getCampoanalisisPK().getNombre()%>">
                                                    </td>
                                                    <td><input class="form-control form-control-sm" type="number" name="fila<%=i%>col2" value="<%=thisCampoanalisis.getValor()%>"></td>
                                                </tr>
                                        <%
                                                i++;
                                            }
                                        %>
                                    </tbody>
                                </table>
                                <div class="text-right">
                                    <button type="button" id="anyadirFila" class="shadow-sm badge badge-info">Añadir fila</button>
                                </div>
                            </form>
                            <br/>
                        </div>
                        
              
                    </div>
                                    
                    <div class="row justify-content-md-center">
                        <div class="col-8">
                            <span id="spanTooltip">
                                <button type="button" class="btn btn-primary btn-lg btn-block"  id="btnEnviar">Guardar</button>
                            </span>
                        </div>
                    </div>            
                                
                                
                                
                </div>
            </div>
        </div>
       
        
    </body>
    
</html>


