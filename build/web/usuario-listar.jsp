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
        <title>JSP Page</title>
    </head>
    <%
        boolean filtrado = request.getParameter("username") != null;
        List<Usuario> usuarios = (List)request.getAttribute("usuarios");
        
    %>
    <body>
        <h1>Usuarios de EventBook</h1>

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
            <td> <a href="ServletUsuarioBorrar?id=<%= u.getId() %>">X</a> </td>
            <td> <a href="ServletUsuarioEditar?id=<%= u.getId() %>">Editar</a> </td>
        </tr>        
        <%
            }
        %>
        </table>
        <br/>
        <a href="ServletUsuarioCrear">Nuevo usuario ...</a>
        
    </body>
</html>
