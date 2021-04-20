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
    </head>
    <%
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
        <h1>Nuevo usuario</h1>
        <form action="ServletUsuarioGuardar" method="POST">
            <table>
                <tr>
                    <td <%= edicion ? "" : "hidden" %>>ID:</td>
                    <td><input type="text" name="id" maxlength="30" size="30" minlength="1" <%= edicion ? "disabled" : "enabled" %> <%= edicion ? "" : "hidden" %> value="<%= edicion ? usuarioEditar.getId() : "" %>"/></td>
                    <td><input type="text" name="id" maxlength="30" size="30" minlength="1" hidden value="<%= edicion ? usuarioEditar.getId() : "" %>"/></td>
                </tr>
                <tr>
                    <td>Usuario:</td>
                    <td><input type="text" name="usuario" maxlength="30" size="30" minlength="1" required="required" value="<%= edicion ? usuarioEditar.getUsername() : "" %>" <%= edicion ? "disabled" : "enabled" %>/></td>
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
                <tr>
                    <td>Domicilio:</td>
                    <td><input type="text" name="domicilio" maxlength="30" size="30" value="<%= edicion ? usuarioEditar.getDomicilio(): "" %>"/> </td>
                </tr>
                <tr>
                    <td>Ciudad:</td>
                    <td><input type="text" name="ciudad" maxlength="30" size="30" value="<%= edicion ? usuarioEditar.getCiudadResidencia(): "" %>"/> </td>
                </tr>
                <tr>
                    <td>Rol de usuario:</td>
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
                       <input type="submit" value="<%= edicion ? "Confirmar cambios" : "Crear usuario" %>"/>
                       <a style="margin-left: 2.5%" href="ServletUsuarioListar">Cancelar</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
