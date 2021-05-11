<%-- 
    Document   : chat-crear
    Created on : 11-may-2021, 14:11:07
    Author     : memoriasIT
--%>

<%@page import="entity.Teleoperador"%>
<%@page import="java.util.List"%>
<%@page import="clases.Autenticacion"%>
<%@page import="entity.Administrador"%>
<%@page import="entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar un chat</title>
        
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
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);
        List<Teleoperador> listaTeleoperador = (List<Teleoperador>) request.getAttribute("listaOperadores");
        String error = (String) request.getAttribute("error");

    %>
    <body>
        <jsp:include page="header.jsp" />
        <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
            <div class="container">
                <form action="ServletChatGuardar" method="POST">
                    <table>
                        <tr>
                            <td>Teleoperador <span style="color: #ec5482;">*</span></td>
                            <td>                
                                <select class="textf" name="teleoperador">
                                    <% 
                                        for(Teleoperador teleoperador : listaTeleoperador){
                                    %>
                                        <option value="<%= teleoperador.getUsuario().getId() %>"><%= teleoperador.getUsuario().getNombre()%></option>
                                   <%
                                       }
                                   %>
                                </select>

                           </td>        
                        </tr>
                        <tr>            
                            <td colspan="2">
                                <br/>
                               <input type="submit" class="btn btn-primary" href="ServletChatGuardar" target="_blank"/>
                               <a style="margin-left: 2.5%" href="ServletChatListar">Cancelar</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
