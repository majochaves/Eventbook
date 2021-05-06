<%-- 
    Document   : header
    Created on : 21-Apr-2021, 12:29:13
    Author     : josie
--%>

      <%@page import="entity.Usuario"%>
<%@page import="entity.Administrador"%>

<%
    Usuario loggedUser = (Usuario) request.getSession().getAttribute("logged-user");
%>
      
<header class="section rd-navbar-wrap">
        <nav class="rd-navbar">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="index.jsp"><img class="navbar-logo-default" src="images/logo-default-161x27.svg" alt="Intense" width="161" height="27"/><img class="navbar-logo-inverse" src="images/logo-inverse-161x27.svg" alt="Intense" width="161" height="27"/></a></div>
              </div>
            </div>
            <div class="navbar-cell navbar-spacer"></div>
            <div class="navbar-cell navbar-sidebar">
              <ul class="navbar-navigation rd-navbar-nav">
                <li class="navbar-navigation-root-item active"><a class="navbar-navigation-root-link" href="index.jsp">Home</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="about-us.html">About us</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="typography.html">Typography</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="contact-us.html">Contact us</a>
                </li>
                <%
                    if (true || loggedUser != null && loggedUser.getAdministrador() != null) {
                        %>
                        <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Administration</a>
                        </li>
                        <%
                    }
                %>
                
              </ul>
            </div>
            <div class="navbar-cell">
              <div class="navbar-subpanel">
                <div class="navbar-subpanel-item">
                  <div class="navbar-search">
                    <div class="navbar-search-container">
                      <form class="navbar-search-form">
                        <input class="navbar-search-input" type="text" placeholder="Enter search terms..." autocomplete="off" name="s"/>
                        <button class="navbar-search-btn int-search novi-icon"></button>
                        <button class="navbar-search-close search-switch int-close novi-icon" type="button" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","class":"navbar-search-active","isolate":"[data-multi-switch]:not(.search-switch)"}'></button>
                      </form>
                    </div>
                  </div>
                  <div class="navbar-search-results">No results</div>
                  <button class="navbar-button search-switch int-search novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","class":"navbar-search-active","isolate":"[data-multi-switch]:not(.search-switch)"}'></button>
                </div>
                <%
                    if (true || loggedUser == null) {
                        %>
                        <div class="navbar-subpanel-item">
                            <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Log in</a></li>
                        </div>
                        <div class="navbar-subpanel-item">
                            <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="ServletUsuarioListar">Sign in</a></li>
                        </div>
                        <%
                    }
                %>
              </div>
            </div>
          </div>
        </nav>
      </header>