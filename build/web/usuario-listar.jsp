<%-- 
    Document   : lista-usuarios
    Created on : 13-Apr-2021, 15:37:38
    Author     : josie
--%>

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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta property="og:title" content="Template Monster Admin Template">
        <meta property="og:description" content="brevis, barbatus clabulares aliquando convertam de dexter, peritus capio. devatio clemens habitio est.">
        <meta property="og:image" content="http://digipunk.netii.net/images/radar.gif">
        <meta property="og:url" content="http://digipunk.netii.net">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <%
        boolean filtrado = request.getParameter("username") != null;
        List<Usuario> usuarios = (List)request.getAttribute("usuarios");
        
    %>
    <body>
        <jsp:include page="header.jsp" />

<%--        
        <form action="ServletUsuarioFiltrar" method="POST">
            <table>
                <tr>
                    <td>Usuario:</td>
                    <td><input type="text" name="usuario" maxlength="30" size="30" minlength="1" required="required" value="<%= filtrado ? request.getParameter("username") : "" %>"/></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="contrasena" maxlength="30" size="30" required="required" minlength="1"/></td>
                </tr>
                <tr>
                    <td>Nombre:</td>
                    <td><input type="text" name="nombre" maxlength="30" size="30" required="required" value="<%= edicion ? usuarioEditar.getNombre(): "" %>"/></td>
                </tr>
                <tr>
                    <td>Apellidos:</td>
                    <td><input type="text" name="apellidos" maxlength="30" size="30" required="required" value="<%= edicion ? usuarioEditar.getApellidos(): "" %>"/></td>
                </tr>
                <tr>
                    <td>Sexo:</td>
                    
                    <td>
                        <input type="radio" id="male" name="sexo" value="hombre" checked="checked" >
                        <label for="male">Hombre</label><br>
                        <input type="radio" id="female" name="sexo" value="mujer" <%= edicion && usuarioEditar.getSexo().equalsIgnoreCase("mujer") ? "checked" : "" %> >
                        <label for="female">Mujer</label><br/>
                    </td>
                </tr>
        </form>
--%>        
    <div style="margin-left: 1%;">
        <br/>
        <h1>Usuarios de EventBook</h1>
        <br/>
        <table style="width:70%; text-align: center">
            <tr>
                <th>ID</th>
                <th>USER</th>
                <th>NAME</th>
                <th>ROL</th>
                <th>BORRADO</th>
                <th>EDICIÓN</th>
            </tr>        

            <%
                for (Usuario u : usuarios) {

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
            <tr>
                <td><%= u.getId() %></td>            
                <td><%= u.getUsername() %></td>
                <td><%= u.getNombre() %></td>
                <td><%= rol %></td>
                <td> <a href="ServletUsuarioBorrar?id=<%= u.getId() %>" style="background-color: #e72660; color: white; padding-left: 10px; padding-right: 10px; border-radius: 10px;">X</a> </td>
                <td> <a href="ServletUsuarioEditar?id=<%= u.getId() %>" style="background-color: #43DD93; color: white; padding-left: 10px; padding-right: 10px; border-radius: 10px;">Editar</a> </td>
            </tr>        
            <%
                }
            %>
            </table>
            <br/>
            <a href="ServletUsuarioCrear">Nuevo usuario ...</a>
        </div>
    </body>
</html>
