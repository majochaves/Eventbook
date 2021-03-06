<%-- 
    Document   : chat-crear
    Created on : 11-may-2021, 13:21:29
    Author     : memoriasIT
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Chat" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.ChatDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.TeleoperadorDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Teleoperador" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de chats</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <link rel="stylesheet" href="/components/base/tablas.css">
        <link rel="stylesheet" href="/components/base/modal.css">

        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>

        <script src="https://code.jquery.com/jquery-3.4.1.min.js"
                integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                crossorigin="anonymous"></script>
        <script src="https://cdn.usebootstrap.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>



        <link rel="stylesheet" href="/components/base/base.css">
    </head>
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);
        
//        boolean filtrado = request.getParameter("username") != null;
        List<Chat> chats = (List)request.getAttribute("chats");
        
        String allMessages = (String) request.getAttribute("allMessages");
        UsuarioDTO thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
        Boolean esTeleoperador = (thisUsuario.getTeleoperador() != null) || (thisUsuario.getAdministrador() != null);
        
    %>
    <body>
        <div class="page">
<%--            <jsp:include page="header.jsp">--%>
<%--                <jsp:param name="nav" value="chat"/>  --%>
<%--            </jsp:include>--%>


            <jsp:include page="header.jsp">
                <jsp:param name="nav" value="chat"/>
            </jsp:include>
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
                    <div style="float:left; margin-bottom: 1%;">
                        <p><%= allMessages %></p>
                        
                    </div>

                    <div style="float:right; margin-bottom: 1%;">
                        <a  class="shadow-sm badge badge-info" href="/chat/crear">Nuevo chat</a>
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
                                    <form action="" method="POST">
                                        <div class="form-group-sm">
                                            A buscar
                                            <select class="form-control" id="aBuscar" name="aBuscar">
                                                <option>Teleoperador</option>
                                                <option>Usuario</option>
                                            </select><br/>
                                        </div>
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
                                            <input type="submit" class="btn btn-primary" value="Buscar"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>


                    <br>
                    
                    <% if (!chats.isEmpty()) { %>

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

                                       <%
                                           Integer oneID;
                                           Integer otherID;
                                           if(!thisUsuario.getId().equals(chat.getChatPK().getTeleoperadorId())){
                                               oneID = chat.getChatPK().getUsuarioId();
                                               otherID = chat.getChatPK().getTeleoperadorId();
                                           } else {
                                               oneID = chat.getChatPK().getTeleoperadorId();
                                               otherID = chat.getChatPK().getUsuarioId();
                                           }

                                       %>

                                       <td class="align-middle text-center"> <a href="/chat/<%= otherID %>/<%= oneID %>" class="btn btn-success shadow-sm" >Chat</a> </td>
                                       <td class="align-middle text-center"> <a href="/chat/borrar/<%= chat.getChatPK().getUsuarioId() %>/<%= chat.getChatPK().getTeleoperadorId() %>" class="btn btn-danger" >X</a> </td>
                                   </tr>     
                                </tbody>
                            <%
                                }
                            %>
                        </table>
                        
                    <% } else {%>
                    
                        <h4>No hay chats para mostrar.</h4>

                    <% } %>
                </div>
            </div>
        </div>
    </body>
</html>
