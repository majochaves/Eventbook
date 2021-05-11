<%-- 
    Document   : evento
    Created on : 19-Apr-2021, 15:30:15
    Author     : majochaves
--%>

<%@page import="entity.Evento"%>
<%@page import="entity.Evento"%>
<%@page import="entity.Etiqueta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear evento</title>
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        List<Etiqueta> etiquetas = (List)request.getAttribute("listaEtiquetas");
        
        Evento eventoEditar = (Evento) request.getAttribute("eventoEditar");
        boolean edicion = eventoEditar != null;
        String fechaEvento = (String) request.getAttribute("eventoFecha");
        
        String fechaLimiteEvento = (String) request.getAttribute("eventoFechaLimite");
        if(fechaLimiteEvento == null){
            fechaLimiteEvento = "";
        }
        //Valores opcionales
        Double costeEntrada = -1.0;
        int aforo = -1, maxEntradas = -1, numFilas = -1, numAsientosFila = -1;
        if(edicion){
            
            if(eventoEditar.getCosteEntrada() != null){
                costeEntrada = eventoEditar.getCosteEntrada();
            }
            
            if(eventoEditar.getAforo()!= null){
                aforo = eventoEditar.getAforo();
            }
            
            if(eventoEditar.getMaxEntradas() != null){
                maxEntradas = eventoEditar.getMaxEntradas();
            }
            if(eventoEditar.getNumFilas()!= null){
                numFilas = eventoEditar.getNumFilas();
            }
            if(eventoEditar.getNumAsientosFila() != null){
                numAsientosFila = eventoEditar.getNumAsientosFila();
            }
        }
    %>
    <body>
           <div class="page">
      <!--RD Navbar-->
      <jsp:include page="header.jsp" />
      <!-- Intro-->
      <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container">
          <div class="row justify-content-center">
            <div data-animate='{"class":"fadeInUp"}'>
                <h1><%= edicion ? "Editar evento": "Crear un nuevo evento" %></h1>
                <form action = "ServletEventoGuardar">
                    <div class="form-group">
                        <label for="titulo">Título:</label>
                        <input class="form-control" type="text" name="titulo" maxlength="30" size ="30" value="<%= (edicion ? eventoEditar.getTitulo(): "" )%>"/>
                    </div>
                    <!--<div class="form-group">
                        <label for="descripcion">Descripción:</label>
                        <textarea class="form-control" name="descripcion" rows="4" cols="50" value="<%= (edicion ? eventoEditar.getDescripcion(): "" )%>"></textarea>
                    </div>
                    -->
                    <div class="form-group">
                        <label for="fecha">Fecha:</label>
                        <input class="form-control" type="date" name="fecha" value="<%= (edicion ? fechaEvento: "") %>">
                    </div>
                    <div class="form-group">
                        <label for="fecha_limite">Fecha límite para reservar entradas:</label>
                        <input class="form-control" type="date" name="fecha_limite" value="<%=( edicion ? fechaLimiteEvento: "" )%>>
                    </div>
                    <div class="form-group">
                        <label for="coste">Coste de entrada:</label>
                        <input class="form-control" type="number" name="coste" value="<%= (edicion ? ((costeEntrada == -1) ? "": costeEntrada): "")%>">
                    </div>
                    <div class="form-group">
                        <label for="aforo">Aforo del evento:</label>
                        <input class="form-control" type="text" name="aforo" value="<%=(edicion ? ((aforo == -1) ? "": aforo): "")%>">
                    </div>
                    <div class="form-group">
                        <label for="max_entradas">Máximo número de entradas por usuario:</label>
                        <input class="form-control" type="text" name="max_entradas" value="<%=(edicion ? ((maxEntradas == -1) ? "": maxEntradas): "") %>">
                    </div>
                    <div class="form-group">
                        <label for="etiqueta">Etiquetas:</label>
                        <%
                            if(etiquetas != null){
                        %>
                        <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
                            <%
                                for(Etiqueta et: etiquetas){
                            %>
                                <option value="<%=et.getId()%>"><%=et.getDescripcion()%></option>
                            <%
                                }
                            %>
                        </select>
                        <%
                            }
                        %>
                        <button class="btn btn-sm btn-primary" id="etiquetasBtn">+</button>
                    </div>
                    <div class="form-group" id="etiquetasDiv">
                    </div>     
                    <div class="form-group">
                        <div class="form-check">
                            <input id="asientosFijos" type="checkbox" class="form-check-input" name="asientos_fijos" value="<%= edicion ? eventoEditar.getAsientosFijos().toString(): "s" %>>
                            <label class="form-check-label">Asientos fijos:</label>
                        </div>
                    </div>
                    <div id="configuracionAsientos" style="display:none;">
                        <div class="form-group">
                            <label for="num_filas">Número de filas:</label>
                            <input class="form-control" type="text" name="num_filas" value="<%= (edicion ? ((numFilas == -1) ? "": numFilas): "")%>>
                        </div>
                        <div class="form-group">
                            <label for="num_asientos_fila">Número de asientos por fila:</label>
                            <input class="form-control" type="text" name="num_asientos_fila" value="<%= (edicion ? ((numAsientosFila == -1) ? "": numAsientosFila): "") %>">
                        </div>
                    </div>
                    <input class="btn btn-lg btn-primary" type="submit" value="<%= edicion ? "Confirmar cambios" : "Crear evento" %>"/>
                    <a style="margin-left: 2.5%" href="ServletEventoListar">Cancelar</a>
                </form>
            </div>
          </div>
        </div>
      </section>
      
      <!-- Footer default extended-->
      <jsp:include page="footer.jsp" />
    </div>
    <div class="to-top int-arrow-up"></div>
    </body>
</html>