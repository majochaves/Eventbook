<%--
    Document   : filtroAnalisis
    Created on : 15-abr-2021, 2:36:56
    Author     : Merli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EventBook - Crear Análisis</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/alert.css">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="/components/bootstrap/js/popper.js"></script>
        <script src="/components/bootstrap/js/bootstrap.min.js"></script>

        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        <script src="/components/propios/botonesSeleccionarTodoAnalisis.js"></script>
    </head>

    <%
        Boolean muestraError = (Boolean)request.getAttribute("muestraError");
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
                            <h3>Realizar Análisis - Filtros</h3>

                        </div>
                    </div>

                    <hr class="divider divider-sm mt-0" />

                    <form method="POST" action="/analisis/crear/generar">
                        <div class="row">
                            <div class="col-sm-4">
                                <h5>Tipo de usuario</h5>
                                <button type="button" class="shadow-none badge badge-info mt-2" id="todoTipoUsuarioTablaUsuario">Seleccionar todo</button>
                                <div class="form-check mt-3">
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="usuarioEventos" checked="checked" />UsuarioEventos <br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="creadorEventos" />CreadorEventos<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="administradores" />Administradores<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="teleoperadores" />Teleoperadores<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoUsuario" value="analistas" />Analistas<br/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <h5>Tipo de análisis - Tabla Usuario</h5>
                                <button type="button" class="shadow-none badge badge-info mt-2" id="todoTablaUsuario">Seleccionar todo</button>
                                <div class="form-check mt-3">
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="numUsuarios" checked="checked" />Número de usuarios totales<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="sexo" />Sexo<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="ciudad" />Ciudad<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="nombre" />Nombre<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="apellido" />Apellido<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="filtroFechaMeses" />Fecha - Por meses<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroUsuario" value="filtroFechaAnyos" />Fecha - Por años<br/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <h5>Fecha de registro - Tabla Usuario</h5>
                                <input type="date" class="textf" name="fechaInicial" min="0001-01-01" max="9999-12-31" /> Fecha Inicial <br />
                                <input type="date" class="textf" name="fechaFinal" min="0001-01-01" max="9999-12-31" /> Fecha Final <br />
                            </div>
                        </div>


                        <div class="row justify-content-center">
                            <div class="col-sm-4">
                                <h5>Tipo de análisis - Tabla Evento</h5>
                                <button type="button" class="shadow-none badge badge-info mt-2" id="todoTablaEvento">Seleccionar todo</button>
                                <div class="form-check mt-3">
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="numEvento" checked="checked" />Número de eventos totales<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="tituloEvento" />Título<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="fechaEvento" />Fecha del evento<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="costeEvento" />Coste de entrada<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="aforoEvento" />Aforo<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="maxEntradasEvento" />Maximo de entradas<br/>
                                    <input class="form-check-input mt-2" type="checkbox" name="tipoFiltroEvento" value="asientosFijosEvento" />Asientos fijos<br/>
                                </div>
                            </div>
                        </div>



                        <div class="row justify-content-md-center">
                            <div class="col-8">
                                <button type="submit" class="btn btn-primary btn-lg btn-block">Realizar Análisis</button>
                            </div>
                        </div>
                    </form>
                    <%
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
        </div>

    </body>
</html>