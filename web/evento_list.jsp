<%-- 
    Document   : evento_list
    Created on : 20-Apr-2021, 12:09:51
    Author     : majochaves
--%>

<%@page import="entity.Evento"%>
<%@page import="entity.Etiqueta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventos</title>
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
    </head>
    <%
        List<Evento> eventos = (List)request.getAttribute("lista");
    %>
    <body>
           <div class="page">
      <!--RD Navbar-->
      <header class="section rd-navbar-wrap">
        <nav class="rd-navbar bg-dark">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel bg-dark text-light">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="index.html"><img class="navbar-logo-default" src="images/EventbookLogo.svg" alt="Intense" width="161" height="36"/><img class="navbar-logo-inverse" src="images/logo-inverse-161x27.svg" alt="Intense" width="270" height="27"/></a></div>
              </div>
            </div>
            <div class="navbar-cell navbar-spacer"></div>
            <div class="navbar-cell navbar-sidebar text-light">
              <ul class="navbar-navigation bg-dark rd-navbar-nav text-light">
                <li class="navbar-navigation-root-item active"><a class="navbar-navigation-root-link text-light" href="index.html">Home</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link text-light" href="about-us.html">About us</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link text-light" href="ServletEventoCrear">Crear Evento</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link text-light" href="contact-us.html">Contact us</a>
                </li>
              </ul>
            </div>
            <div class="navbar-cell text-light">
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
                <div class="navbar-subpanel-item">
                  <button class="navbar-button int-cart novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","class":"navbar-cart-active","isolate":"[data-multi-switch]"}'></button>
                  <div class="navbar-cart">
                    <h5 class="navbar-cart-heading">Shopping cart</h5>
                    <div class="navbar-cart-item">
                      <div class="navbar-cart-item-left"><a class="thumbnail-small" href="#"><img src="images/products/product-03-80x103.jpg" alt="" width="80" height="103"/></a></div>
                      <div class="navbar-cart-item-body"><a class="navbar-cart-item-heading" href="#">Ombré vinyl backpack</a>
                        <div class="navbar-cart-item-price">$29</div>
                        <div class="navbar-cart-item-parameter">Size: S</div>
                        <div class="navbar-cart-item-parameter">Color: Black</div>
                        <div class="navbar-cart-item-parameter">Dimension: 80X120mm</div>
                        <div class="navbar-cart-item-parameter">Qty:
                          <input class="form-control navbar-cart-item-qty" type="number" value="1" name="qty" data-spinner='{"classes":{"ui-spinner":"navbar-cart-spinner"}}'/>
                        </div>
                      </div>
                      <div class="navbar-cart-item-right">
                        <button class="navbar-cart-remove int-trash novi-icon"></button>
                      </div>
                    </div>
                    <div class="navbar-cart-item">
                      <div class="navbar-cart-item-left"><a class="thumbnail-small" href="#"><img src="images/products/product-08-80x103.jpg" alt="" width="80" height="103"/></a></div>
                      <div class="navbar-cart-item-body"><a class="navbar-cart-item-heading" href="#">Phone case with chain</a>
                        <div class="navbar-cart-item-price">$34</div>
                        <div class="navbar-cart-item-parameter">Size: S</div>
                        <div class="navbar-cart-item-parameter">Color: Red</div>
                        <div class="navbar-cart-item-parameter">Dimension: 80X120mm</div>
                        <div class="navbar-cart-item-parameter">Qty:
                          <input class="form-control navbar-cart-item-qty" type="number" value="1" name="qty" data-spinner='{"classes":{"ui-spinner":"navbar-cart-spinner"}}'/>
                        </div>
                      </div>
                      <div class="navbar-cart-item-right">
                        <button class="navbar-cart-remove int-trash novi-icon"></button>
                      </div>
                    </div>
                    <div class="navbar-cart-line">
                      <div class="navbar-cart-line-name">Subtotal:</div>
                      <div class="navbar-cart-line-value">$63.00</div>
                    </div>
                    <div class="navbar-cart-line">
                      <div class="navbar-cart-line-name">Shipping:</div>
                      <div class="navbar-cart-line-value">Free</div>
                    </div>
                    <div class="navbar-cart-line">
                      <div class="navbar-cart-line-name">Taxes:</div>
                      <div class="navbar-cart-line-value">$0.00</div>
                    </div>
                    <div class="navbar-cart-line">
                      <div class="navbar-cart-line-name">Total:</div>
                      <div class="navbar-cart-total">$63</div>
                    </div>
                    <div class="navbar-cart-buttons">
                      <div class="navbar-cart-group"><a class="btn btn-dark btn-sm" href="#">Continue shopping</a><a class="btn btn-primary btn-sm" href="#"><span class="btn-icon int-check novi-icon"></span><span>Checkout</span></a></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </header>
      <!-- Intro-->
      <section class="section section-lg bg-gradient-animated text-center d-flex align-items-center min-vh-100">
        <div class="container">
          <div class="row justify-content-center">
            <div data-animate='{"class":"fadeInUp"}'>
                <h1>Eventos</h1>
                
            </div>
          </div>
        </div>
      </section>
      
      <!-- Footer default extended-->
      <footer class="section footer bg-800 text-400 context-dark">
        <div class="container">
          <div class="row row-30 justify-content-xxl-between">
            <div class="col-lg-5">
                    <!-- Logo-->
                    <div class="logo"><a class="logo-link" href="index.html"><img class="logo-image-default" src="images/logo-default-161x27.svg" alt="Intense" width="161" height="27"/><img class="logo-image-inverse" src="images/logo-inverse-161x27.svg" alt="Intense" width="161" height="27"/></a></div>
              <p class="small">Get the most for your website with Intense Free Multipurpose Website Template, valued by over 3,000 customers worldwide. With a variety of components, elements, and blocks, this responsive Bootstrap 4 template is definitely worth your attention.</p>
            </div>
            <div class="col-md-11 col-lg-7 col-xxl-6">
              <p class="small">178 West 27th Street, Suite 527, New York NY 10012, United States</p>
              <div class="row row-20">
                <div class="col-auto col-sm-4">
                  <div class="media media-xxs text-white">
                    <div class="media-left"><span class="icon icon-xs int-phone novi-icon"></span></div>
                    <div class="media-body">
                      <ul class="list list-contact">
                        <li class="list-contact-item"><a class="list-contact-link" href="tel:#">+1 234 567 8901</a></li>
                        <li class="list-contact-item"><a class="list-contact-link" href="tel:#">+1 234 567 8902</a></li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="col-auto col-sm-4">
                  <ul class="list list-xs small">
                    <li class="list-item">Mon-Fri: 8am - 6pm</li>
                    <li class="list-item">Sat-Sun: 8am - 4pm</li>
                    <li class="list-item">Holidays: closed</li>
                  </ul>
                </div>
                <div class="col-auto col-sm-4">
                  <ul class="list list-lg small text-white">
                    <li class="list-item"><a class="link link-contrast link-secondary" href="mailto:#">mail@demolink.org</a></li>
                    <li class="list-item"><a class="link link-contrast link-secondary" href="mailto:#">info@demolink.org</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <hr class="divider footer-divider">
          <div class="row row-15 align-items-center justify-content-between footer-panel">
            <div class="col-auto">
                    <!-- Copyright-->
                    <p class="rights"><span>&copy; 2019&nbsp;</span><span>Intense</span><span>. All rights reserved</span></p>
            </div>
            <div class="col-auto">
              <div class="group-30 d-flex text-white"><a class="icon icon-xs icon-social int-youtube novi-icon" href="#"></a><a class="icon icon-xs icon-social int-facebook novi-icon" href="#"></a><a class="icon icon-xs icon-social int-instagram novi-icon" href="#"></a><a class="icon icon-xs icon-social int-twitter novi-icon" href="#"></a><a class="icon icon-xs icon-social int-linkedin novi-icon" href="#"></a></div>
            </div>
          </div>
        </div>
      </footer>
    </div>
    <div class="to-top int-arrow-up"></div>
    </body>
</html>
