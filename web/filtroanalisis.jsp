<%-- 
    Document   : filtroanalisis
    Created on : 27-mar-2021, 15:27:38
    Author     : Merli
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Analsis</title>
        <link rel="stylesheet" href="ProbandoBDEventBook.css/filtroanalisis.css">
    </head>
    <body>
        <h3>Realizar Analisis - Filtros</h3>
        <form method="POST" action="obtenerEstadisticas">
            <div>
                <ul class="primerconjunto">
                    <li><input class="radios" type="radio" name="tipoUsuario" value="usuarioEventos" checked="checked" />UsuarioEventos</li>
                    <li><input class="radios" type="radio" name="tipoUsuario" value="creadorEventos" />CreadorEventos</li>
                    <li><input class="radios" type="radio" name="tipoUsuario" value="Administradores" />Administradores</li>
                    <li><input class="radios" type="radio" name="tipoUsuario" value="Teleoperadores" />Teleoperadores</li>
                    <li><input class="radios" type="radio" name="tipoUsuario" value="Analistas" />Analistas</li>
                </ul>
                <ul class="segundoconjunto">
                    <li><input type="radio" name="tipoFiltro" value="numUsuarios" checked="checked" />Numero de usuarios</li>
                </ul>
                <ul class="tercerconjunto">
                    <li><input type="radio" name="anyo" value="todos" checked="checked" />Todos los años</li>
                    <li><input type="radio" name="anyo" value="2021"/>2021</li>
                    <li><input type="radio" name="anyo" value="2020"/>2020</li>
                    <li><input type="radio" name="anyo" value="2019"/>2019</li>
                </ul>
            </div>
            <input class="btnEnviar" type="submit" value="Realizar Analisis" />
        </form>
        <br/>
        <br/>
        <%
            List<String> listaColumna = (List)request.getAttribute("listaColumna");
            List<List<String>> listaFila = (List)request.getAttribute("listaFila");
            if(listaColumna != null || listaFila != null){
        %>
                <table border="1">
                    <thead>
                        <tr>
                            <%
                                for(String nombreColumna : listaColumna){
                            %>
                                    <th><%=nombreColumna%></th>
                            <%
                                }
                            %>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for(List<String> filaIndividual : listaFila){
                        %>
                                <tr>
                                    <%
                                        for(String elemento : filaIndividual){
                                    %>
                                        <td><%=elemento%></td>
                                    <%
                                        }
                                    %>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
        <%
            }
        %>
        
    </body>
</html>
