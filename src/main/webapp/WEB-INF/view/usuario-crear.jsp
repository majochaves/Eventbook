<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
    Document   : usuarioDTO-crear
    Created on : 13-Apr-2021, 15:53:34
    Author     : josie
--%>

<%@page import="com.eventbookspring.eventbookspring.clases.Autenticacion"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Administrador"%>
<%@page import="com.eventbookspring.eventbookspring.entity.Usuario"%>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Creación de usuario</title>
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
    </head>
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Administrador.class);
        
        String error = (String) request.getAttribute("error");
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getAttribute("usuarioDTO");
        List<String> sexos = (List<String>) request.getAttribute("sexos");

        boolean edicion = usuarioDTO != null && usuarioDTO.getId() != null;
        String rol = "";
        if (edicion) {
            if (usuarioDTO.getAdministrador() != null) {
                rol = "Administrador";
            } else if (usuarioDTO.getAnalista()!= null) {
                rol = "Analista";
            } else if (usuarioDTO.getTeleoperador()!= null) {
                rol = "Teleoperador";
            } else if (usuarioDTO.getUsuarioeventos() != null) {
                rol = "Usuario de eventos";
            } else if (usuarioDTO.getCreadoreventos()!= null) {
                rol = "Creador de eventos";
            }
        }
    %>
    <body>
        <div class="page">
            
            <jsp:include page="header.jsp" />

            <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
                <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                <h1><%= edicion ? "Editar usuario: " + usuarioDTO.getUsername() : "Nuevo usuario" %></h1>
                            </div>
                        </div>
                        <img style="float: right; margin-right: 15%; width: 25%; max-width: 499px;" src="/images/agregar-usuario.svg"/>
                        <br/>
                        <%
                        if (error != null) {
                            %>
                            <p style="color: #ec5482;"> <%= error %> </p>
                            <%
                        }
                        %>

                    <form:form modelAttribute="usuarioDTO" action="/usuario-guardar" method="POST">
                        <form:hidden path="id" class="textf"/>

                        <table>
                            <tr>
                                <td <%= edicion ? "" : "hidden" %>>ID <span style="color: #ec5482;">*</span></td>
                                <td><input type="text" class="textf" name="id" disabled value="<%= edicion ? usuarioDTO.getId() : "" %>" <%=edicion ? "" : "hidden"%>/></td>
                            </tr>
                            <tr>
                                <td>Usuario <span style="color: #ec5482;">*</span></td>
                                <td><form:input path="username" class="textf" required="required" autocomplete="false"/></td>
                            </tr>
                            <tr>
                                <td>Contraseña <span style="color: #ec5482;">*</span>&nbsp&nbsp</td>
                                <td><form:password path="password" class="textf" required="required" autocomplete="false"/></td>
                            </tr>
                            <tr>
                                <td>Nombre  <span style="color: #ec5482;">*</span></td>
                                <td><form:input path="nombre" class="textf" required="required"/></td>
                            </tr>
                            <tr>
                                <td>Apellidos <span style="color: #ec5482;">*</span></td>
                                <td><form:input path="apellidos" class="textf" required="required"/></td>
                            </tr>
                            <tr>
                                <td>Sexo <span style="color: #ec5482;">*</span></td>
                                <td style="text-transform: capitalize;">
                                    <form:radiobuttons cssStyle="margin-left: 15px;" path="sexo" items="${sexos}" required="required"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Domicilio <span style="color: #ec5482;">*</span></td>
                                <td><form:input path="domicilio" class="textf" required="required"/></td>
                            </tr>
                            <tr>
                                <td>Ciudad <span style="color: #ec5482;">*</span></td>
                                <td><form:input path="ciudadResidencia" class="textf" required="required"/></td>
                            </tr>
                            <tr>
                                <td>Rol <span style="color: #ec5482;">*</span></td>
                                <td>
                                    <%
                                    if (edicion) {
                                        %>
                                        <select class="textf" name="rol" disabled>
                                       <option value="seleccionado"><%=rol%></option>
                                       </select>
                                       <%
                                    } else {
                                        %>
                                       <select class="textf" name="rol">
                                       <option value="administrador">Administrador</option>
                                       <option value="creador-eventos">Creador de eventos</option>
                                       <option value="teleoperador">Teleoperador</option>
                                       <option value="analista">Analista de eventos</option>
                                       </select>
                                       <%
                                    }
                                    %>

                               </td>
                            </tr>
                            
                            
                            <tr>
                                <td><a class="btn btn-danger mt-5" href="/administracion">Cancelar</a></td>
                                <td><input type="submit" class="btn btn-primary btn-block mt-5" value="<%= edicion ? "Confirmar cambios" : "Crear usuario" %>" /></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
                                   
        </div>
    </body>
</html>
