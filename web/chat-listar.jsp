<%-- 
    Document   : chat-crear
    Created on : 11-may-2021, 13:21:29
    Author     : memoriasIT
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Teleoperador"%>
<%@page import="entity.Chat"%>
<%@page import="entity.Administrador"%>
<%@page import="clases.Autenticacion"%>
<%@page import="entity.Usuario"%>
<%@page import="entity.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de usuarios</title>
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
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);
        
//        boolean filtrado = request.getParameter("username") != null;
        List<Chat> chats = (List)request.getAttribute("chats");
    %>
    <body>
        <jsp:include page="header.jsp" />
    <div class="section section-lg bg-transparent">
        <div class="container">
            <br/>
            <div class="row">
                <div class="col-sm-12">
                    <h1>Chats abiertos</h1>
                </div>
            </div>
            <hr class="divider divider-sm mt-0" />
            <br/>
            
            <div style="float:right; margin-bottom: 1%;">
                <a  class="shadow-sm badge badge-info" href="ServletChatCrear">Nuevo chat</a>
                <button type="button" class="shadow-sm badge badge-warning" data-toggle="modal" data-target="#abrirDialogoEditar">Filtrar chat</button>
            </div>
            
                <!--Dialogo para editar-->
    <div class="modal fade" id="abrirDialogoEditar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">

                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Filtrar chats</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <form action="ServletUsuarioFiltrar" method="GET">
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
                        <th>Fecha</th>
                        <th>Teleoperador</th>
                        <th>Usuario</th>
                        <th></th>
                        <th></th>
                    </tr>        
                </thead>
                
                <%
                    for (Chat chat : chats) {
                        Teleoperador tele = chat.getTeleoperador();
                        Usuario usuario = chat.getUsuario();     
                %>   
                <tbody>
                   <tr>        
                       <td><%= new SimpleDateFormat("dd-M-yyyy hh:mm").format(chat.getFecha())  %></td>
                       <td><%= tele.getUsuario().getNombre() %></td>
                       <td><%= usuario.getNombre() %></td>
                       <td class="align-middle text-center"> <a href="ServletChatUI?opID=<%= chat.getChatPK().getTeleoperadorId() %>" class="btn" >Chat</a> </td>
                       <td class="align-middle text-center"> <a href="ServletChatBorrar?userID=<%= chat.getChatPK().getUsuarioId() %>&opID=<%= chat.getChatPK().getTeleoperadorId() %>" class="btn btn-danger" >X</a> </td>
                   </tr>     
                </tbody>
                <%
                    }
                %>
            </table>
        </div>
    </div>
    </body>
</html>
