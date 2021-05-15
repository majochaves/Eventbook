<%--
    Document   : index
    Created on : 16-mar-2021, 18:28:47
    Author     : guzman
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javafx.util.Pair"%>
<%@page import="entity.Mensaje"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Teleoperador"%>
<%@page import="java.util.List"%>
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
        <script src="components/base/core.js"></script>
        <script src="components/base/script.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
        <link rel="stylesheet" href="components/chat/chat.css">

        <%
            // AUTENTICACION
            Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);

            // Lista de teleoperadores;
            List<Teleoperador> teleoperadores = (List<Teleoperador>) request.getAttribute("teleoperadores");
            Usuario usuarioChat = (Usuario) request.getAttribute("usuarioChat");

            Usuario thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            Boolean adminPriviledges = (thisUsuario.getTeleoperador() != null) || (thisUsuario.getAdministrador() != null);
            
            List<Pair<Integer, Mensaje>> mensajes = (List<Pair<Integer, Mensaje>>) request.getAttribute("mensajesHistorial");
        %>

    </head>
    <body>

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
                </div>
            </nav>
        </header>
        <!-- Breadcrumb default-->
        <section class="section section-sm bg-transparent">
            <div class="container">
                <!-- Breadcrumb-->
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a class="breadcrumb-link" href="ServletChatListar">Chats</a></li>
                    <li class="breadcrumb-item"><span class="breadcrumb-text breadcrumb-active">Chat con <%= usuarioChat.getNombre() %></span></li>
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
                        <% for (Teleoperador op : teleoperadores) {%>
                        <li class="clearfix">
                            <div class="about">
                                <div class="name"><%= op.getUsuario().getNombre()%></div>
                                <div class="status">
                                    <i class="fa fa-circle online"></i> online
                                </div>
                            </div>
                        </li>
                        <% }%>

                    </ul>
                </div>

                <div class="chat">
                    <div class="chat-header clearfix">


                        <div class="chat-about">
                            <div class="chat-with"><%= usuarioChat.getNombre()%> </div>
                            <div class="chat-num-messages">already 1 902 messages</div>
                        </div>
                        <i class="fa fa-star"></i>
                    </div> <!-- end chat-header -->

                    <div class="chat-history">
                        <ul>


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
                <li class="clearfix" id="{{id}}">
                <div class="message-data align-right">
                
                <span class="message-data-time" >{{time}}</span> &nbsp; &nbsp;
                <span class="message-data-name" ><%= thisUsuario.getNombre()%></span> <i class="fa fa-circle me"></i>
                
                <% if (adminPriviledges){ %>
                <span class="message-data-edit"><a href="ServletMessageEditar?msgId={{id}}&userID=<%= request.getParameter("userID") %>"><i class="far fa-edit" style="padding-right: 4px;"></i>Edit<a></span>
                <span class="message-data-edit"><a href="ServletMessageBorrar?msgId={{id}}&userID=<%= request.getParameter("userID") %>"><i class="far fa-trash" style="padding-right: 4px;"></i>Borrar<a></span>
                    <% } %>
                </div>
                <div class="message other-message float-right">
                {{messageOutput}}
                </div>
                </li>
            </script>

            <script id="message-response-template" type="text/x-handlebars-template">
                <li id="{{id}}">
                <div class="message-data">
                <% if (adminPriviledges){ %>
                    <span class="message-data-edit"><a href="ServletMessageEditar?msgId={{id}}&userID=<%= request.getParameter("userID") %>"><i class="far fa-edit" style="padding-right: 4px;"></i>Edit<a></span>
                        <span class="message-data-edit"><a href="ServletMessageBorrar?msgId={{id}}&userID=<%= request.getParameter("userID") %>"><i class="far fa-trash" style="padding-right: 4px;"></i>Borrar<a></span>
                <% } %>
                
                <span class="message-data-name"><i class="fa fa-circle online"></i><%= usuarioChat.getNombre() %></span>
                <span class="message-data-time">{{time}}</span>
                </div>
                <div class="message my-message">
                {{messageOutput}}
                </div>
                </li>
            </script>


            <script>

                (function () {

                    var messagesWaiting = false;
                    var chat = {
                        messageToSend: '',
                        init: function () {
                            this.cacheDOM();
                            this.bindEvents();
                            this.render();
                            this.initHistorial();
                            setInterval(this.getMessages, 1000);
                        },
                        cacheDOM: function () {
                            this.$chatHistory = $('.chat-history');
                            this.$button = $('button');
                            this.$textarea = $('#message-to-send');
                            this.$chatHistoryList = this.$chatHistory.find('ul');
                        },
                        bindEvents: function () {
                            this.$button.on('click', this.addMessage.bind(this));
                            this.$textarea.on('keyup', this.addMessageEnter.bind(this));
                        },
                        sendMessageToBackend: function (context) {
                            var xmlhttp = new XMLHttpRequest();
                            xmlhttp.open("POST", "ServletChat", false);
                            xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                            xmlhttp.send("message=" + context.messageOutput + "&userTo=<%= usuarioChat.getId() %>");
                        },
                        getMessages: function () {
                            console.log("get messages");
                            if (!messagesWaiting) {
                                console.log("message");
                                messagesWaiting = true;
                                var xmlhttp = new XMLHttpRequest();
                                xmlhttp.onreadystatechange = function () {
                                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                                        messagesWaiting = false;

                                        rawData = xmlhttp.responseText;
                                        $('.chat-history').find('ul').append(xmlhttp.responseText);
                                        // TODO ARREGLAR ESTO NO FUNCIONA
                                        // this.scrollToBottom();
                                    }
                                };
                                xmlhttp.open("GET", "ServletChat", true);
                                xmlhttp.send();
                            }
                        },
                        render: function () {
                            this.scrollToBottom();
                            if (this.messageToSend.trim() !== '') {
                                var template = Handlebars.compile($("#message-template").html());
                                var context = {
                                    messageOutput: this.messageToSend,
                                    time: this.getCurrentTime()
                                };

                                this.sendMessageToBackend(context);
                                this.$chatHistoryList.append(template(context));
                                this.scrollToBottom();
                                this.$textarea.val('');
                            }

                        },

                        addMessage: function () {
                            this.messageToSend = this.$textarea.val();
                            this.render();
                        },
                        addMessageEnter: function (event) {
                            // enter was pressed
                            if (event.keyCode === 13) {
                                this.addMessage();
                            }
                        },
                        scrollToBottom: function () {
                            this.$chatHistory.scrollTop(this.$chatHistory[0].scrollHeight);
                        },
                        getCurrentTime: function () {
                            return new Date().toLocaleString().
                                    replace(/,/, "");
                        },
                        getRandomItem: function (arr) {
                            return arr[Math.floor(Math.random() * arr.length)];
                        },
                        initHistorial: function(){
                            var templateEnviar = Handlebars.compile($("#message-template").html());
                            var templateResponse = Handlebars.compile( $("#message-response-template").html());
                            
                            console.log("Size", "<%= mensajes.size() %>");
                            <% for (Pair<Integer, Mensaje> msg : mensajes){ %>
                                console.log("IMP MENSAJE");
                                console.log("<%= msg.getValue().getContenido() %>");
                                console.log("<%= msg.getValue().getUsuarioEmisorId() %>");
                                console.log("<%= msg.getKey() %>");
                                
                                console.log("this usuario id");
                                    console.log(<%= thisUsuario.getId() %>);
                                
                                var data = {
                                    messageOutput: "<%= msg.getValue().getContenido() %>",
                                    id: "<%= msg.getValue().getId() %>",
                                    time: "<%= new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(msg.getValue().getFecha()) %>"
                                }; 
                                
                                
                            <% if (msg.getKey().equals(thisUsuario.getId())){ %>  
                                    this.$chatHistoryList.append(templateEnviar(data));            
                                <% } else {%>   
                                    this.$chatHistoryList.append(templateResponse(data));    
                                <% } %>
                                this.scrollToBottom();
                            <% } %>
                        }

                    };

                    chat.init();
                    setInterval(chat.getMessages(), 1000);

                    var searchFilter = {
                        options: {valueNames: ['name']},
                        init: function () {
                            var userList = new List('people-list', this.options);
                            var noItems = $('<li id="no-items-found">No items found</li>');

                            userList.on('updated', function (list) {
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