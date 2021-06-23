<%-- 
    Document   : chat-crear
    Created on : 11-may-2021, 14:11:07
    Author     : memoriasIT
--%>

<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.TeleoperadorDTO" %>
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
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
    </head>
    <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);
        List<TeleoperadorDTO> listaTeleoperador = (List<TeleoperadorDTO>) request.getAttribute("listaOperadores");
        String error = (String) request.getAttribute("error");

    %>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />
            <div class="section section-lg bg-gradient-animated d-flex align-items-center min-vh-100">
                <div class="container">
                    <form action="/chat/crear/guardar" method="POST">
                        <table>
                            <tr>
                                <td>Teleoperador <span style="color: #ec5482;">*</span></td>
                                <td>                
                                    <select class="textf" name="teleoperador">
                                        <% 
                                            for(TeleoperadorDTO teleoperador : listaTeleoperador){
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
                                   <input type="submit" class="btn btn-primary" value="Crear Chat con Teleoperador" href="ServletChatGuardar" target="_blank"/>
                                   <a style="margin-left: 2.5%" href="/chat/">Cancelar</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
