<%-- 
    Document   : filtroAnalisis
    Created on : 15-abr-2021, 2:36:56
    Author     : Merli
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="clases.Tupla"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Crear Análisis</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/alert.css">
        <script src="components/jquery/jquery-3.4.1.min.js"></script>
        <script src="components/bootstrap/js/popper.js"></script>
        <script src="components/bootstrap/js/bootstrap.min.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <body>
        
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-transparent">
            <div class="container">
                
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Realizar Analisis - Filtros</h3>
                        
                    </div>
                </div>
                
                <hr class="divider divider-sm mt-0" />
                
                <form method="POST" action="crearAnalisis">
                    <div class="row">
                        <div class="col-sm-4">
                            <h5>Tipo de usuario</h5>
                            <div class="form-check">
                                <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="usuarioEventos" checked="checked" />UsuarioEventos <br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="creadorEventos" />CreadorEventos<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="administradores" />Administradores<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="teleoperadores" />Teleoperadores<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="analistas" />Analistas<br/><br/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <h5>Tipo de análisis - Tabla Usuario</h5>
                            <div class="form-check">
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="numUsuarios" checked="checked" />Numero de usuarios totales<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="sexo" />Sexo<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="ciudad" />Ciudad<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="nombre" />Nombre<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="apellido" />Apellido<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="filtroFechaMeses" />Fecha - Por meses<br/>
                                <input class="form-check-input mt-2" type="checkbox" name="tipoFiltro" value="filtroFechaAnyos" />Fecha - Por años<br/>
                            </div> 
                        </div>    
                        <div class="col-sm-4">
                            <h5>Año</h5>
                            <input type="date" name="fechaInicial" min="0001-01-01" max="9999-12-31" /> Fecha Inicial <br />
                            <input type="date" name="fechaFinal" min="0001-01-01" max="9999-12-31" /> Fecha Final <br />
                        </div>
                    </div>
                    
                    <div class="row justify-content-md-center">
                        <div class="col-8">
                            <button type="submit" class="btn btn-primary btn-lg btn-block">Realizar Análisis</button>
                        </div>
                    </div>
                </form>
                <%
                    Boolean muestraError = (Boolean)request.getAttribute("muestraError");
                    if(muestraError!=null && muestraError.booleanValue()){
                %>
                    <div class="row mt-6">
                        <div class="col-12">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>Error!</strong> Intenta seleccionar al menos un tipo de usuario y un tipo de análisis
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                  <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </div>
                    </div>
                <%
                    } 
                %>
            </div>
        </div>
    
    </body>
</html>
