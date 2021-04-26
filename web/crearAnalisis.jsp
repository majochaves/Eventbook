<%-- 
    Document   : filtroAnalisis
    Created on : 15-abr-2021, 2:36:56
    Author     : Merli
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="clases.Tupla"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Análisis</title>
    </head>
    <body>
        <h1>Realizar Analisis - Filtros</h1>
        <form method="POST" action="crearAnalisis">
            <h3>Tipo de usuario</h3>
            <input type="checkbox" name="tipoUsuario" value="usuarioEventos" checked="checked" />UsuarioEventos <br/>
            <input type="checkbox" name="tipoUsuario" value="creadorEventos" />CreadorEventos<br/>
            <input type="checkbox" name="tipoUsuario" value="administradores" />Administradores<br/>
            <input type="checkbox" name="tipoUsuario" value="teleoperadores" />Teleoperadores<br/>
            <input type="checkbox" name="tipoUsuario" value="analistas" />Analistas<br/><br/>
            <h3>Tipo de analisis</h3>
            <input type="checkbox" name="tipoFiltro" value="numUsuarios" checked="checked" />Numero de usuarios<br/><br/>
            <input type="checkbox" name="tipoFiltro" value="sexo" />Sexo<br/><br/>
            <h3>Año</h3>
            
            <input type="date" name="fechaInicial" min="0001-01-01" max="9999-12-31" /> Fecha Inicial <br />
            <input type="date" name="fechaFinal" min="0001-01-01" max="9999-12-31" /> Fecha Final <br />
            <input type="submit" value="Realizar Analisis" />
        </form>
        <br/>
        <br/>
        <%
            Map<String, Set<Tupla<String, Double>>> listaFila = (Map)request.getAttribute("listaFila");
            if(listaFila != null){
                for(String nombreColumna : listaFila.keySet()){
        %>
                    <table border="1">
                        <thead>
                            <tr>
                                <th><%=nombreColumna%></th>
                                <th>Valor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                Set<Tupla<String, Double>> conjuntoDeFilas = listaFila.get(nombreColumna);
                                for(Tupla<String, Double> filaIndividual : conjuntoDeFilas){
                            %>
                                    <tr>
                                        <td><%=filaIndividual.getPrimerElem()%></td>
                                        <td><%=filaIndividual.getSegundoElem()%></td>
                                    </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                    <br/>
                    <hr/>
            <%
                }
            }
            %>
    </body>
</html>
