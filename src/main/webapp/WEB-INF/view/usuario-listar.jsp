<%-- 
    Document   : lista-usuarios
    Created on : 13-Apr-2021, 15:37:38
    Author     : josie
--%>

<%@page import="com.eventbookspring.eventbookspring.entity.Administrador"%>
<%@page import="com.eventbookspring.eventbookspring.clases.Autenticacion"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Usuario"%>
<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de usuarios</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <link rel="icon" href="images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <link rel="stylesheet" href="components/base/tablas.css">
        <link rel="stylesheet" href="components/base/modal.css">
        <script src="components/jquery/jquery-3.4.1.min.js"></script>
        <script src="components/bootstrap/js/popper.js"></script>
        <script src="components/bootstrap/js/bootstrap.min.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Administrador.class);
        
        boolean filtrado = request.getParameter("username") != null;
        List<UsuarioDTO> usuarios = (List)request.getAttribute("usuarios");
    %>
    <body>
        <div class="page">
            <jsp:include page="header.jsp">
                <jsp:param name="nav" value="administracion"/>  
            </jsp:include>
        
            <div class="section section-lg bg-transparent">
                <div class="container">
                    <br/>
                    <div class="row">
                        <div class="col-sm-12">
                            <h1>Usuarios de EventBook</h1>
                        </div>
                    </div>
                    <hr class="divider divider-sm mt-0" />
                    <br/>

                    <div style="float:right; margin-bottom: 1%;">
                        <a  class="shadow-sm badge badge-info" href="/usuario-crear">Nuevo usuario</a>
                        <button type="button" class="shadow-sm badge badge-warning" data-toggle="modal" data-target="#abrirDialogoFiltrar">Filtrar usuarios</button>
                    </div>

                    
                <!--Dialogo para filtar-->
                    <div class="modal fade" id="abrirDialogoFiltrar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Filtrado de usuarios</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>

                                <div class="modal-body">
                                    <form action="/usuario-filtrar" method="GET">
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>
                                                    <h3> Rol </h3>
                                                    <div class="form-check">
                                                        <input class="form-check-input mt-2" type="checkbox" name="rol" value="usuarioEventos" checked="checked" />UsuarioEventos <br/>
                                                        <input class="form-check-input mt-2" type="checkbox" name="rol" value="creadorEventos" checked="checked"/>CreadorEventos<br/>
                                                        <input class="form-check-input mt-2" type="checkbox" name="rol" value="administradores" checked="checked"/>Administradores<br/>
                                                        <input class="form-check-input mt-2" type="checkbox" name="rol" value="teleoperadores" checked="checked"/>Teleoperadores<br/>
                                                        <input class="form-check-input mt-2" type="checkbox" name="rol" value="analistas" checked="checked"/>Analistas<br/><br/>
                                                    </div>  
                                                </td>
                                                <td>
                                                    <h3> Sexo </h3>
                                                    <div class="form-check">
                                                        <input class="form-check-input mt-2" type="checkbox" name="sexo" value="hombre" checked="checked" />Hombre<br/>
                                                        <input class="form-check-input mt-2" type="checkbox" name="sexo" value="mujer" checked="checked" />Mujer<br/><br/>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>

                                        <div class="form-group-sm">
                                            Nombre de usuario<input type="text" class="form-control" name="username"><br/><br/>
                                        </div>

                                        <div class="form-group-sm">
                                            Nombre<input type="text" class="form-control" name="nombre"><br/><br/>
                                        </div>

                                        <div class="form-group-sm">
                                            Apellidos<input type="text" class="form-control" name="apellidos"><br/><br/>
                                        </div>

                                        <div class="form-group-sm">
                                            Domicilio<input type="text" class="form-control" name="domicilio"><br/><br/>
                                        </div>

                                        <div class="form-group-sm">
                                            Ciudad<input type="text" class="form-control" name="ciudad"><br/><br/>
                                        </div>

                                        <div class="align-middle text-center">
                                            <input type="submit" class="btn btn-primary" value="Aplicar"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
            
                    <br>
                    
                    <table class="table table-hover table-bordered">
                        <thead>
                            <tr class="table-secondary">
                                <th>ID</th>
                                <th>USUARIO</th>
                                <th>NOMBRE COMPLETO</th>
                                <th>SEXO</th>
                                <th>CIUDAD</th>
                                <th>ROL</th>
                                <th>EDICIÓN</th>
                                <th>ELIMINACIÓN</th>
                            </tr>        
                        </thead>

                        <%
                            for (UsuarioDTO u : usuarios) {

                                String rol = "???";
                                if (u.getAdministrador() != null) {
                                    rol = "Administrador";
                                } else if (u.getAnalista()!= null) {
                                    rol = "Analista";
                                } else if (u.getTeleoperador()!= null) {
                                    rol = "Teleoperador";
                                } else if (u.getUsuarioeventos() != null) {
                                    rol = "Usuario de Eventos";
                                } else if (u.getCreadoreventos()!= null) {
                                    rol = "Creador de Eventos";
                                }
                        %>   
                            <tbody>
                               <tr>
                                   <td><%= u.getId() %></td>            
                                   <td><%= u.getUsername() %></td>
                                   <td><%= u.getNombre() + " " + u.getApellidos() %></td>
                                   <td><%= u.getSexo().substring(0, 1).toUpperCase() + u.getSexo().substring(1) %></td>
                                   <td><%= u.getCiudadResidencia() %></td>
                                   <td><%= rol %></td>
                                   <td class="align-middle text-center"> <a href="/usuario-editar/<%= u.getId() %>" class="btn btn-primary">Editar</a> </td>
                                   <td class="align-middle text-center"> <a href="/usuario-borrar/<%= u.getId() %>" class="btn btn-danger" ><i class="fas fa-trash"></i></a> </td>
                               </tr>     
                            </tbody>
                        <%
                            }
                        %>
                    </table>
                </div>
            </div>
                    
                    
        </div>
    </body>
</html>
