<%-- 
    Document   : evento
    Created on : 19-Apr-2021, 15:30:15
    Author     : majochaves
--%>

<%@page import="java.text.SimpleDateFormat"%>
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
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        List<Etiqueta> etiquetas = (List)request.getAttribute("listaEtiquetas");
        
        Evento evento = (Evento) request.getAttribute("evento");
        
        String id="", titulo = "", descripcion = "", fecha = "", fecha_limite= "", asientosFijosNoChecked = "checked", asientosFijosSiChecked ="", showConfig="none";
        Double costeEntrada = -1.0;
        int aforo = -1, maxEntradas = -1, numFilas = -1, numAsientosFila = -1;
        
        if(evento != null){
            id = evento.getId().toString();
            titulo = evento.getTitulo();
            if(evento.getDescripcion()!=null){
                descripcion = evento.getDescripcion();
            }
            fecha = new SimpleDateFormat("yyy-MM-dd").format(evento.getFecha());
            if(evento.getFechaLimite() != null){
                fecha_limite = new SimpleDateFormat("yyy-MM-dd").format(evento.getFechaLimite());
            }
            if(evento.getCosteEntrada() != null){
                costeEntrada = evento.getCosteEntrada();
            }
            if(evento.getAforo() != null){
                aforo = evento.getAforo();
            }
            if(evento.getMaxEntradas() != null){
                maxEntradas = evento.getMaxEntradas();
            }
            if(evento.getAsientosFijos() != null){
                if(evento.getAsientosFijos() == 's'){
                    asientosFijosNoChecked = "";
                    asientosFijosSiChecked = "checked";
                    showConfig="block";
                }
                if(evento.getNumFilas() != null){
                    numFilas = evento.getNumFilas();
                }
                if(evento.getNumAsientosFila() != null){
                    numAsientosFila = evento.getNumAsientosFila();
                }
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
                <h1><%= evento != null ? "Editar evento": "Crear un nuevo evento" %></h1>
                <form action = "ServletEventoGuardar">
                    <input type="hidden" name="id" value="<%=id%>"/>
                    <div class="form-group">
                        <label for="titulo">Título:</label>
                        <input class="form-control" type="text" name="titulo" maxlength="30" size ="30" value="<%=titulo%>"/>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">Descripción:</label>
                        <textarea class="form-control" name="descripcion" rows="4" cols="50" value="<%=descripcion%>"><%=descripcion%></textarea>
                    </div>
                    <div class="form-group">
                        <label for="fecha">Fecha:</label>
                        <input class="form-control" type="date" name="fecha" value="<%=fecha%>"/>
                    </div>
                    <div class="form-group">
                        <label for="fecha_limite">Fecha límite para reservar entradas:</label>
                        <input class="form-control" type="date" name="fecha_limite" value="<%=fecha_limite%>"/>
                    </div>
                    <div class="form-group">
                        <label for="coste">Coste de entrada:</label>
                        <input class="form-control" type="number" name="coste" value="<%=(costeEntrada == -1) ? "": costeEntrada%>"/>
                    </div>
                    <div class="form-group">
                        <label for="aforo">Aforo del evento:</label>
                        <input class="form-control" type="number" name="aforo" value="<%=(aforo == -1) ? "": aforo%>"/>
                    </div>
                    <div class="form-group">
                        <label for="max_entradas">Máximo número de entradas por usuario:</label>
                        <input class="form-control" type="number" name="max_entradas" value="<%=(maxEntradas == -1) ? "": maxEntradas%>"/>
                    </div>
                    <div class="form-group">
                        <label for="etiqueta">Etiquetas:</label>
                        <%
                            if(etiquetas != null){
                        %>
                        <select multiple="multiple" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
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
                        <label for="asientosFijos">Asientos fijos:</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="asientos_fijos" id="asientosFijosSi" value="si" <%=asientosFijosSiChecked%>>
                            <label class="form-check-label" for="asientosFijosSi">Si</label>
                          </div>
                          <div class="form-check">
                            <input class="form-check-input" type="radio" name="asientos_fijos" id="asientosFijosNo" value="no"<%=asientosFijosNoChecked%>>
                            <label class="form-check-label" for="asientosFijosNo">No</label>
                        </div>
                    </div>
                    <div id="configuracionAsientos" style="display:<%=showConfig%>;">
                        <div class="form-group">
                            <label for="num_filas">Número de filas:</label>
                            <input class="form-control" type="number" name="num_filas" value="<%=(numFilas == -1) ? "": numFilas %>"/>
                        </div>
                        <div class="form-group">
                            <label for="num_asientos_fila">Número de asientos por fila:</label>
                            <input class="form-control" type="number" name="num_asientos_fila" value="<%=(numAsientosFila == -1) ? "": numAsientosFila %>"/>
                        </div>
                    </div>
                    <input class="btn btn-lg btn-primary" type="submit" value="<%= evento != null ? "Confirmar cambios" : "Crear evento" %>"/>
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
