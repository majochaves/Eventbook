<%-- 
    Document   : index
    Created on : 16-mar-2021, 18:28:47
    Author     : guzman
--%>

<%@page import="entity.Usuario"%>
<%@page import="clases.Autenticacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Chat</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="components/base/base.css">
        <script src="components/eventos/eventos.js"></script>
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>
        
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
        <link rel="stylesheet" href="components/chat/chat.css">
        
        <%
        // AUTENTICACION
        Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);
        
        %>
        
    </head>
    <body onload="getMessages();">
    
      <!--RD Navbar-->
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
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="index.html">Home</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="about-us.html">About us</a>
                </li>
                <li class="navbar-navigation-root-item active"><a class="navbar-navigation-root-link" href="typography.html">Typography</a>
                </li>
                <li class="navbar-navigation-root-item"><a class="navbar-navigation-root-link" href="contact-us.html">Contact us</a>
                </li>
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
      <!-- Breadcrumb default-->
      <section class="section section-sm bg-transparent">
        <div class="container">
                <!-- Breadcrumb-->
                <ul class="breadcrumb">
                  <li class="breadcrumb-item"><a class="breadcrumb-link" href="index.html">Home</a></li>
                  <li class="breadcrumb-item"><span class="breadcrumb-text breadcrumb-active">Typography</span></li>
                </ul>
        </div>
      </section>
      <!-- Start chat-->
      <section class="section section-lg bg-transparent">
           <div class="container ChatContainer clearfix">
    <div class="people-list" id="people-list">
      <div class="search">
        <input type="text" placeholder="search" />
        <i class="fa fa-search"></i>
      </div>
      <ul class="list">
        <li class="clearfix">

          <div class="about">
            <div class="name">Vincent Porter</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Aiden Chavez</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> left 7 mins ago
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Mike Thomas</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Erica Hughes</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Ginger Johnston</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Tracy Carpenter</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> left 30 mins ago
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Christian Kelly</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> left 10 hours ago
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Monica Ward</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Dean Henry</div>
            <div class="status">
              <i class="fa fa-circle offline"></i> offline since Oct 28
            </div>
          </div>
        </li>
        
        <li class="clearfix">
          
          <div class="about">
            <div class="name">Peyton Mckinney</div>
            <div class="status">
              <i class="fa fa-circle online"></i> online
            </div>
          </div>
        </li>
      </ul>
    </div>
    
    <div class="chat">
      <div class="chat-header clearfix">
        
        
        <div class="chat-about">
          <div class="chat-with">Chat with Vincent Porter</div>
          <div class="chat-num-messages">already 1 902 messages</div>
        </div>
        <i class="fa fa-star"></i>
      </div> <!-- end chat-header -->
      
      <div class="chat-history">
        <ul>
          <li class="clearfix">
            <div class="message-data align-right">
              <span class="message-data-time" >10:10 AM, Today</span> &nbsp; &nbsp;
              <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
              
            </div>
            <div class="message other-message float-right">
              Hi Vincent, how are you? How is the project coming along?
            </div>
          </li>
          
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:12 AM, Today</span>
            </div>
            <div class="message my-message">
              Are we meeting today? Project has been already finished and I have results to show you.
            </div>
          </li>
          
          <li class="clearfix">
            <div class="message-data align-right">
              <span class="message-data-time" >10:14 AM, Today</span> &nbsp; &nbsp;
              <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
              
            </div>
            <div class="message other-message float-right">
              Well I am not sure. The rest of the team is not here yet. Maybe in an hour or so? Have you faced any problems at the last phase of the project?
            </div>
          </li>
          
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:20 AM, Today</span>
            </div>
            <div class="message my-message">
              Actually everything was fine. I'm very excited to show this to our team.
            </div>
          </li>
          
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:31 AM, Today</span>
            </div>
            <i class="fa fa-circle online"></i>
            <i class="fa fa-circle online" style="color: #AED2A6"></i>
            <i class="fa fa-circle online" style="color:#DAE9DA"></i>
          </li>
          <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
              <span class="message-data-time">10:31 AM, Today</span>
            </div>
            <i class="fa fa-circle online"></i>
            <i class="fa fa-circle online" style="color: #AED2A6"></i>
            <i class="fa fa-circle online" style="color:#DAE9DA"></i>
          </li>
        </ul>
        
      </div> <!-- end chat-history -->
      
      <div class="chat-message clearfix">
        <textarea name="message-to-send" id="message-to-send" placeholder ="Type your message" rows="3"></textarea>
                
        <i class="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
        <i class="fa fa-file-image-o"></i>
        
        <button>Send</button>

      </div> <!-- end chat-message -->
      
    </div> <!-- end chat -->
    
  </div> <!-- end container -->

<script id="message-template" type="text/x-handlebars-template">
  <li class="clearfix">
    <div class="message-data align-right">
      <span class="message-data-time" >{{time}}, Today</span> &nbsp; &nbsp;
      <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
      {{messageOutput}}
    </div>
  </li>
</script>

<script id="message-response-template" type="text/x-handlebars-template">
  <li>
    <div class="message-data">
      <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
      <span class="message-data-time">{{time}}, Today</span>
    </div>
    <div class="message my-message">
      {{response}}
    </div>
  </li>
</script>

          
          
          
          
        <div class="container">
          <h1>SHOUT-OUT!</h1>
        <form>
            <table>
                <tr>
                    <td>Your name:</td>
                    <td><input type="text" id="name" name="name"/></td>
                </tr>
                <tr>
                    <td>Your shout:</td>
                    <td><input type="text" id="message" name="message" /></td>
                </tr>
                <tr>
                    <td><input type="button" onclick="postMessage();" value="SHOUT" /></td>
                </tr>
            </table>
        </form>
        <h2> Current Shouts </h2>
        <div id="content">
            <% if (application.getAttribute("messages") != null) {%>
            <%= application.getAttribute("messages")%>
            <% }%>
        </div>
        <script>
            function postMessage() {
                var xmlhttp = new XMLHttpRequest();
                //xmlhttp.open("POST", "shoutServlet?t="+new Date(), false);
                xmlhttp.open("POST", "ServletChat", false);
                xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var nameText = escape(document.getElementById("name").value);
                var messageText = escape(document.getElementById("message").value);
                document.getElementById("message").value = "";
                xmlhttp.send("name="+nameText+"&message="+messageText);
            }
            var messagesWaiting = false;
            function getMessages(){
                if(!messagesWaiting){
                    messagesWaiting = true;
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            messagesWaiting = false;
                            var contentElement = document.getElementById("content");
                            contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;
                        }
                    }
                    //xmlhttp.open("GET", "shoutServlet?t="+new Date(), true);
                    xmlhttp.open("GET", "ServletChat", true);
                    xmlhttp.send();
                }
            }
            setInterval(getMessages, 1000);
        </script>
        
        
        
        <script>
            (function(){

                var chat = {
                  messageToSend: '',
                  messageResponses: [
                    'Why did the web developer leave the restaurant? Because of the table layout.',
                    'How do you comfort a JavaScript bug? You console it.',
                    'An SQL query enters a bar, approaches two tables and asks: "May I join you?"',
                    'What is the most used language in programming? Profanity.',
                    'What is the object-oriented way to become wealthy? Inheritance.',
                    'An SEO expert walks into a bar, bars, pub, tavern, public house, Irish pub, drinks, beer, alcohol'
                  ],
                  init: function() {
                    this.cacheDOM();
                    this.bindEvents();
                    this.render();
                  },
                  cacheDOM: function() {
                    this.$chatHistory = $('.chat-history');
                    this.$button = $('button');
                    this.$textarea = $('#message-to-send');
                    this.$chatHistoryList =  this.$chatHistory.find('ul');
                  },
                  bindEvents: function() {
                    this.$button.on('click', this.addMessage.bind(this));
                    this.$textarea.on('keyup', this.addMessageEnter.bind(this));
                  },
                  render: function() {
                    this.scrollToBottom();
                    if (this.messageToSend.trim() !== '') {
                      var template = Handlebars.compile( $("#message-template").html());
                      var context = { 
                        messageOutput: this.messageToSend,
                        time: this.getCurrentTime()
                      };

                      this.$chatHistoryList.append(template(context));
                      this.scrollToBottom();
                      this.$textarea.val('');

                      // responses
                      var templateResponse = Handlebars.compile( $("#message-response-template").html());
                      var contextResponse = { 
                        response: this.getRandomItem(this.messageResponses),
                        time: this.getCurrentTime()
                      };

                      setTimeout(function() {
                        this.$chatHistoryList.append(templateResponse(contextResponse));
                        this.scrollToBottom();
                      }.bind(this), 1500);

                    }

                  },

                  addMessage: function() {
                    this.messageToSend = this.$textarea.val()
                    this.render();         
                  },
                  addMessageEnter: function(event) {
                      // enter was pressed
                      if (event.keyCode === 13) {
                        this.addMessage();
                      }
                  },
                  scrollToBottom: function() {
                     this.$chatHistory.scrollTop(this.$chatHistory[0].scrollHeight);
                  },
                  getCurrentTime: function() {
                    return new Date().toLocaleTimeString().
                            replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
                  },
                  getRandomItem: function(arr) {
                    return arr[Math.floor(Math.random()*arr.length)];
                  }

                };

                chat.init();

                var searchFilter = {
                  options: { valueNames: ['name'] },
                  init: function() {
                    var userList = new List('people-list', this.options);
                    var noItems = $('<li id="no-items-found">No items found</li>');

                    userList.on('updated', function(list) {
                      if (list.matchingItems.length === 0) {
                        $(list.list).append(noItems);
                      } else {
                        noItems.detach();
                      }
                    });
                  }
                };

                searchFilter.init();

              })();
              </script>
        
        </div>
      </section>
     
    </div>
    <div class="to-top int-arrow-up"></div>
        
        
    </body>
</html>