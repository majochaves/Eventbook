<%-- 
    Document   : usuario-crear
    Created on : 13-Apr-2021, 15:53:34
    Author     : josie
--%>

<%@page import="entity.Usuario"%>
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
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
    </head>
    <%
        String error = (String) request.getAttribute("error");
        Usuario usuarioEditar = (Usuario) request.getAttribute("usuarioEditar");
        boolean edicion = usuarioEditar != null;
        String rol = "";
        if (edicion) {
            if (usuarioEditar.getAdministrador() != null) {
                rol = "Administrador";
            } else if (usuarioEditar.getAnalista()!= null) {
                rol = "Analista";
            } else if (usuarioEditar.getTeleoperador()!= null) {
                rol = "Teleoperador";
            } else if (usuarioEditar.getUsuarioeventos() != null) {
                rol = "Usuario de eventos";
            } else if (usuarioEditar.getCreadoreventos()!= null) {
                rol = "Creador de eventos";
            }
        }
    %>
    <body>
        <jsp:include page="header.jsp" />
        
        <form action="ServletUsuarioGuardar" method="POST" style="margin-left: 1%;">
            <h1><%= edicion ? "Editar usuario: " + usuarioEditar.getUsername() : "Nuevo usuario" %></h1>
            <br/>
            
            <% 
                if (error != null || "".equals(error)) {
                    %>
                    <p style="color: #ec5482;"> <%= error %> </p>
                    <%
                }
            %>
            <table>
                <tr>
                    <td <%= edicion ? "" : "hidden" %>>ID <span style="color: #ec5482;">*</span></td>
                    <td><input type="text" name="id" maxlength="30" size="30" minlength="1" <%= edicion ? "disabled" : "enabled" %> <%= edicion ? "" : "hidden" %> value="<%= edicion ? usuarioEditar.getId() : "" %>"/></td>
                    <td><input type="text" name="id" maxlength="30" size="30" minlength="1" hidden value="<%= edicion ? usuarioEditar.getId() : "" %>"/></td>
                </tr>
                <tr>
                    <td>Usuario <span style="color: #ec5482;">*</span></td>
                    <td><input type="text" name="usuario" maxlength="30" size="30" minlength="1" required="required" value="<%= edicion ? usuarioEditar.getUsername() : "" %>"/></td>
                </tr>
                <tr>
                    <td>Contraseña <span style="color: #ec5482;">*</span>&nbsp&nbsp</td>
                    <td><input type="password" name="contrasena" maxlength="30" size="30" required="required" minlength="1"/></td>
                </tr>
                <tr>
                    <td>Nombre <span style="color: #ec5482;">*</span></td>
                    <td><input type="text" name="nombre" maxlength="30" size="30" required="required" value="<%= edicion ? usuarioEditar.getNombre(): "" %>"/></td>
                </tr>
                <tr>
                    <td>Apellidos <span style="color: #ec5482;">*</span></td>
                    <td><input type="text" name="apellidos" maxlength="30" size="30" required="required" value="<%= edicion ? usuarioEditar.getApellidos(): "" %>"/></td>
                </tr>
                <tr>
                    <td>Sexo <span style="color: #ec5482;">*</span></td>
                    
                    <td>
                        <input type="radio" id="male" name="sexo" value="hombre" checked="checked">
                        <label for="male">Hombre</label><br>
                        <input type="radio" id="female" name="sexo" value="mujer" <%= edicion && usuarioEditar.getSexo().equalsIgnoreCase("mujer") ? "checked" : "" %> >
                        <label for="female">Mujer</label><br/>
                    </td>
                </tr>
                <tr>
                    <td>Domicilio</td>
                    <td><input type="text" name="domicilio" maxlength="30" size="30" value="<%= edicion ? usuarioEditar.getDomicilio(): "" %>"/> </td>
                </tr>
                <tr>
                    <td>Ciudad</td>
                    <td><input type="text" name="ciudad" maxlength="30" size="30" value="<%= edicion ? usuarioEditar.getCiudadResidencia(): "" %>"/> </td>
                </tr>
                <tr>
                    <td>Rol <span style="color: #ec5482;">*</span></td>
                    <td>                
                        <%
                        if (edicion) {
                            %>
                            <select name="rol" disabled>
                           <option value="seleccionado"><%=rol%></option>
                           </select>
                           <%
                        } else {
                            %>
                           <select name="rol">
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
                    <td colspan="2">
                        <br/>
                       <input type="submit" value="<%= edicion ? "Confirmar cambios" : "Crear usuario" %>" class="btn btn-primary" href="https://www.templatemonster.com/intense-multipurpose-html-template.html" target="_blank"/>
                       <a style="margin-left: 2.5%" href="ServletUsuarioListar">Cancelar</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
