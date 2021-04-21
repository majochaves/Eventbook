<%-- 
    Document   : header
    Created on : 21-Apr-2021, 12:29:13
    Author     : josie
--%>

      <%@page import="entity.Administrador"%>
<header class="section rd-navbar-wrap">
        <nav class="rd-navbar">
          <div class="navbar-container">
            <div class="navbar-cell">
              <div class="navbar-panel">
                <button class="navbar-switch int-hamburger novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","isolate":"[data-multi-switch]"}'></button>
                <div class="navbar-logo"><a class="navbar-logo-link" href="index.html"><img class="navbar-logo-default" src="images/logo-default-161x27.svg" alt="Intense" width="161" height="27"/><img class="navbar-logo-inverse" src="images/logo-inverse-161x27.svg" alt="Intense" width="161" height="27"/></a></div>
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
                    if (true || request.getSession().getAttribute("logged-user") instanceof Administrador) {
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
                <div class="navbar-subpanel-item">
                  <button class="navbar-button int-cart novi-icon" data-multi-switch='{"targets":".rd-navbar","scope":".rd-navbar","class":"navbar-cart-active","isolate":"[data-multi-switch]"}'></button>
                  <div class="navbar-cart">
                    <h5 class="navbar-cart-heading">Shopping cart</h5>
                    <div class="navbar-cart-item">
                      <div class="navbar-cart-item-left"><a class="thumbnail-small" href="#"><img src="images/products/product-03-80x103.jpg" alt="" width="80" height="103"/></a></div>
                      <div class="navbar-cart-item-body"><a class="navbar-cart-item-heading" href="#">Ombr� vinyl backpack</a>
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