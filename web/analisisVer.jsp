<%-- 
    Document   : analisisVer
    Created on : 29-abr-2021, 1:32:30
    Author     : Merli
--%>

<%@page import="entity.Campoanalisis"%>
<%@page import="entity.Tipoanalisis"%>
<%@page import="java.util.List"%>
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
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />
            <div class="section section-lg bg-transparent">
                <div class="container">

                    <div class="row">
                        <div class="col-sm-12">
                            <h3>Análisis</h3>
                        </div>
                    </div>
                    
                    <hr class="divider divider-sm mt-0" />
                    
                    <div class="row align-items-center">
                        <div class="col-sm-10">
                            <p>
                                <b>Descripción: </b><%=(String)request.getAttribute("descripcionAnalisis")%>
                            </p>
                        </div>
                            
                        <div class="col-sm-2">
                            <button type="button" class="shadow-sm badge badge-warning" data-toggle="modal" data-target="#abrirDialogoEditar">Modificar</button>
                        </div>
                    </div>
                            
                    <div class="row">
                        <%
                            List<Tipoanalisis> listaTiposAnalisis = (List) request.getAttribute("listaTiposAnalisis");
                            for(Tipoanalisis thisTipoDeAnalisis : listaTiposAnalisis){
                                
                        %>
                                <div class="col-sm-6 pb-4">
                                    <table class="table table-sm table-hover table-bordered">
                                        
                                        <thead>
                                            <tr class="table-secondary">
                                                <th cope="col"><%=thisTipoDeAnalisis.getNombre()%></th> <!--Nombre de la columna-->
                                                <th scope="col">Valor</th>
                                            </tr>
                                        </thead>
                                        
                                        <tbody>
                                            <%
                                                for(Campoanalisis thisCampoanalisis : thisTipoDeAnalisis.getCampoanalisisList()){
                                            %>
                                                    <tr>
                                                        <td><%=thisCampoanalisis.getCampoanalisisPK().getNombre()%></td>
                                                        <td><%=thisCampoanalisis.getValor()%></td>
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
                    
                    
                    
            <!--Dialogo para editar-->
                    <div class="modal fade" id="abrirDialogoEditar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <form method="POST" action="ServletAnalisisEditar?id=<%=request.getAttribute("idAnalisis")%>">
                                <div class="modal-content">

                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Editar Análisis</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <div class="modal-body">
                                        <div class="form-group pb-4">
                                            <label for="desc" class="col-form-label">Descripción</label>
                                            <textarea class="form-control" id="desc" name="descripcion"><%=(String)request.getAttribute("descripcionAnalisis")%></textarea>
                                        </div>
                                        
                                        <%
                                            for(Tipoanalisis thisTipoDeAnalisis : listaTiposAnalisis){
                                        %>
                                            <div class="row align-middle mt-4">
                                                <div class="col-6">
                                                    <%=thisTipoDeAnalisis.getNombre()%>
                                                </div>
                                                <div class="col-6 text-right">
                                                    <a href="ServletTipoanalisisVer?id=<%=thisTipoDeAnalisis.getId()%>" class="shadow-none badge badge-warning">Editar tabla</a>
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
                            </form>
                        </div>
                    </div>
                   




                </div>
            </div>
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>
