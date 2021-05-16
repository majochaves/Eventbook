<%-- 
    Document   : evento
    Created on : 19-Apr-2021, 15:30:15
    Author     : majochaves
--%>

<%@page import="entity.Creadoreventos"%>
<%@page import="entity.Administrador"%>
<%@page import="clases.Autenticacion"%>
<%@page import="entity.Usuario"%>
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
        <link rel="icon" href="images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        List<Etiqueta> etiquetas = (List)request.getAttribute("listaEtiquetas");
        
        Evento evento = (Evento) request.getAttribute("evento");
        String strError = (String) request.getAttribute("strError");
        
        Usuario u = (Usuario)request.getSession().getAttribute("logged-user");

        String id="", titulo = "", descripcion = "", fecha = "", fecha_limite= "", asientosFijosNoChecked = "checked", asientosFijosSiChecked ="", showConfig="none";
        Double costeEntrada = -1.0;
        int aforo = -1, maxEntradas = -1, numFilas = -1, numAsientosFila = -1;
        
        if(evento != null && (!(Autenticacion.tieneRol(request, response, Administrador.class) || (Autenticacion.tieneRol(request, response, Creadoreventos.class) && evento.getCreadoreventosId().equals(Autenticacion.getUsuarioLogeado(request, response).getCreadoreventos()))))) {
            Autenticacion.error(request, response, Autenticacion.PERMISOS);
        }
        
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
            <jsp:include page="header.jsp" />
            <!-- Intro-->
            <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
              <div class="container">
                <div class="row justify-content-center">
                  <div data-animate='{"class":"fadeInUp"}'>
                      <h1><%= evento != null ? "Editar evento " + id: "Crear un nuevo evento" %></h1>
                      <%if(strError != null){%>
                          <p><%=strError%></p>
                      <%}%>
                      <form action = "ServletEventoGuardar" method="POST">
                          <input type="text" name="usuarioId" value="<%=u.getId()%>" hidden/>
                          <input type="text" name="id" value="<%=id%>" hidden/>
                          <div class="form-group">
                              <label for="titulo">Título:*</label>
                              <input class="form-control" type="text" name="titulo" maxlength="30" size ="30" value="<%=titulo%>"/>
                          </div>
                          <div class="form-group">
                              <label for="descripcion">Descripción:</label>
                              <textarea class="form-control" name="descripcion" rows="4" cols="50" value="<%=descripcion%>"><%=descripcion%></textarea>
                          </div>
                          <div class="form-group">
                              <label for="fecha">Fecha:*</label>
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
                              <label for="etiquetas">Etiquetas:</label>
                              <%
                                  if(etiquetas != null){
                              %>
                              <select name="etiquetas" multiple="multiple" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
                                  <%
                                      for(Etiqueta et: etiquetas){
                                          String strSelected = "";
                                          if(evento.getEtiquetaList().contains(et)){
                                              strSelected = "selected";
                                          }
                                  %>
                                      <option value="<%=et.getId()%>" <%=strSelected%>><%=et.getDescripcion()%></option>
                                  <%
                                      }
                                  %>
                              </select>
                              <%
                                  }
                              %>
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
                              
                          <div class="row">
                                <div class="col-4">
                                    <a class="btn btn-danger" href="ServletEventoListar">Cancelar</a>
                                </div>
                                <div class="col-8">
                                    <input class="btn btn-primary btn-block" type="submit" value="<%= evento != null ? "Confirmar cambios" : "Crear evento" %>"/>
                                </div>
                            
                          </div>
                      </form>
                  </div>
                </div>
              </div>
            </section>

            
            <jsp:include page="footer.jsp" />
        </div>
        <div class="to-top int-arrow-up"></div>
    </body>
</html>
