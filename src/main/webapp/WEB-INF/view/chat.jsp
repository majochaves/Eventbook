<%--
    Document   : index
    Created on : 16-mar-2021, 18:28:47
    Author     : guzman
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="com.eventbookspring.eventbookspring.clases.Autenticacion" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Usuario" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Chat" %>
<%@ page import="com.eventbookspring.eventbookspring.entity.Mensaje" %>
<%@ page import="com.eventbookspring.eventbookspring.dto.UsuarioDTO" %>
<%@ page import="com.eventbookspring.eventbookspring.clases.Par" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Chat</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
        <link rel="stylesheet" href="/components/chat/chat.css">

        <%
            // AUTENTICACION
            Autenticacion.autenticar(request, response, Autenticacion.PERMISOS, Usuario.class);

            // Lista de teleoperadores;
            List<Chat> chats = (List<Chat>) request.getAttribute("chats");
            UsuarioDTO usuarioChat = (UsuarioDTO) request.getAttribute("usuarioChat");
            UsuarioDTO usuarioChat2 = (UsuarioDTO) request.getAttribute("usuarioChat2");
            
            int userId1 = usuarioChat.getId();
            int userId2 = usuarioChat2.getId();

            UsuarioDTO thisUsuario = Autenticacion.getUsuarioLogeado(request, response);
            Boolean adminPriviledges = (thisUsuario.getTeleoperador() != null) || (thisUsuario.getAdministrador() != null);
            
            List<Par<Integer, Mensaje>> mensajes = (List<Par<Integer, Mensaje>>) request.getAttribute("mensajesHistorial");
        %>

    </head>
    <body>

        <!--RD Navbar-->
        <header class="section rd-navbar-wrap">
            <jsp:include page="header.jsp" />
        </header>
        <!-- Breadcrumb default-->
        <section class="section section-sm bg-transparent">
            <div class="container">
                <!-- Breadcrumb-->
                <ul class="breadcrumb">
                    <li class="breadcrumb-item"><a class="breadcrumb-link" href="/chat/">Chats</a></li>
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
                        <% for (Chat chat : chats) {
                                Usuario usuario;
                            if (adminPriviledges){
                                usuario = chat.getUsuario();
                            } else {
                                usuario = chat.getTeleoperador().getUsuario();
                            }
                        %>
                        <li class="clearfix">
                            <div class="about">
                                <a href="/chat/<%= usuario.getId() %>/<%= userId2 %>">
                                <div class="name"><%= usuario.getUsername()%></div>
                                <div class="status">
                                    <i class="fa fa-circle online"></i> online
                                </div>
                                </a>
                            </div>
                        </li>
                        <% }%>

                    </ul>
                </div>

                <div class="chat">
                    <div class="chat-header clearfix">


                        <div class="chat-about">
                            <div class="chat-with"><%= usuarioChat.getUsername()%> </div>
                            <div class="chat-num-messages"><%= usuarioChat.getNombre()%>, <%= usuarioChat.getApellidos()%></div>
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
                <span class="message-data-name" ><%= usuarioChat2.getNombre()%></span> <i class="fa fa-circle me"></i>
                
                <% if (adminPriviledges){ %>
                <span class="message-data-edit"><a href="ServletMessageEditar?msgId={{id}}&userID=<%= request.getParameter("userID") %>&user2ID=<%= userId2 %>"><i class="far fa-edit" style="padding-right: 4px;"></i>Edit<a></span>
                <span class="message-data-edit"><a href="ServletMessageBorrar?msgId={{id}}&userID=<%= request.getParameter("userID") %>&user2ID=<%= userId2 %>"><i class="far fa-trash" style="padding-right: 4px;"></i>Borrar<a></span>
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
                    <span class="message-data-edit"><a href="ServletMessageEditar?msgId={{id}}&userID=<%= request.getParameter("userID") %>&user2ID=<%= userId2 %>"><i class="far fa-edit" style="padding-right: 4px;"></i>Edit<a></span>
                        <span class="message-data-edit"><a href="ServletMessageBorrar?msgId={{id}}&userID=<%= request.getParameter("userID") %>&user2ID=<%= userId2 %>"><i class="far fa-trash" style="padding-right: 4px;"></i>Borrar<a></span>
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
                        idMsg: 0,
                        init: function () {
                            
                            <% if (thisUsuario.getId() != userId1 && thisUsuario.getId() != userId2){%>
                                $('#message-to-send').attr('disabled', true);
                            <%} %>
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
                            xmlhttp.open("POST", "/chat/sendMsg/<%= userId1%>/<%= userId2%>", false);
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

                                        
                                        // Benchmark of this only took 0.14 milliseconds lol
                                        HTMLMessage = document.implementation.createHTMLDocument("HTMLMessage");
                                        HTMLMessage.documentElement.innerHTML = xmlhttp.responseText;
                                        var messageElement = HTMLMessage.children[0].getElementsByTagName("li")[0];
                                        sender = messageElement.getAttribute("userid");
                                        var idOfMsg =  messageElement.getAttribute("id");
                                        var idUserTo =  messageElement.getAttribute("useridTo");
                                        var mensajeTxt = "";
                                        try {
                                            mensajeTxt= messageElement.getElementsByClassName("my-message")[0].innerText.trim();
                                        } catch (error) {
                                            console.error(error);
                                            
                                        }
//                                        console.log(messageElement);
//                                        console.log(idUserTo);
//                                        console.log("<%= thisUsuario.getId() %>");
                                        
                                        
                                        // Change data according to receiver or sender
                                        messageElement.getElementsByClassName("message-data-name")[0].innerHTML = "<i class='fa fa-circle online'></i>"+ "<%= usuarioChat.getNombre() %>";
                                        <% if (adminPriviledges){ %>
                                            var innerHtml = `<span class="message-data-edit"><a href="ServletMessageEditar?msgId=`+ idOfMsg +`&userID=<%= request.getParameter("userID") %>"><i class="far fa-edit" style="padding-right: 4px;"></i>Edit<a></span><span class="message-data-edit"><a href="ServletMessageBorrar?msgId=`+ idOfMsg +`&userID=<%= request.getParameter("userID") %>"><i class="far fa-trash" style="padding-right: 4px;"></i>Borrar<a></span>`;
                                            
                                            messageElement.getElementsByClassName("message-data")[0].innerHTML += innerHtml;
                                        <% } %>
                                        
                                      
                                        
                                        // Evitar hacer render de los mensajes que el usuario envía dos veces
                                        if (sender !== "<%= thisUsuario.getId() %>"){
//                                             $('.chat-history').find('ul').append(xmlhttp.responseText);

                                            // It belongs to this chat!
                                            if (idUserTo === "<%= thisUsuario.getId() %>" && sender === "<%= usuarioChat.getId() %>"){
                                                $('.chat-history').find('ul').append(messageElement);
                                            }
                                            
                                            const userId1 = <%= request.getParameter("userID") %>;
                                            const userId2 = <%= request.getParameter("user2ID") %>;                          
                                            if (idUserTo == userId2 && sender == userId1){ // Usuario izq
                                                console.log(messageElement);
                                                $('.chat-history').find('ul').append(messageElement);
                                            } else if(idUserTo == userId1 && sender == userId2){ // Usuario drcha
                                                console.log("mensaje2", messageElement);
                                                var template = Handlebars.compile($("#message-template").html());
                                                
                                                var context = {
                                                    messageOutput: mensajeTxt,
                                                    id: idOfMsg,
                                                    time: new Date().toLocaleString('en-GB', { hour12: true }).replaceAll("/", "-")
                                                };

                                                // Add message
                                                $('.chat-history').find('ul').append(template(context));
                                                // scroll to bottom
                                                $('.chat-history').scrollTop($('.chat-history')[0].scrollHeight);
                                                
                                                
                                            }
  
                                        } else {
                                            
                                            var message = $('#message-to-send').val().trim();
                                            if (message !== '') {
                                                var template = Handlebars.compile($("#message-template").html());
                                                
                                                var context = {
                                                    messageOutput: message,
                                                    id: idOfMsg,
                                                    time: new Date().toLocaleString('en-GB', { hour12: true }).replaceAll("/", "-")
                                                };


                                                $('.chat-history').find('ul').append(template(context));
//                                                this.scrollToBottom();
                                                $('#message-to-send').val('');
                                            }
                                        }
                                        
                                        // Scroll to bottom
                                        $('.chat-history').scrollTop($('.chat-history')[0].scrollHeight);
                                    }
                                };
                                xmlhttp.open("GET", "/chat/getMsg/", true);
                                xmlhttp.send();
                            }
                        },
                        render: function () {
                            this.scrollToBottom();
                            if (this.messageToSend.trim() !== '') {
                                var template = Handlebars.compile($("#message-template").html());
                                
                                var context = {
                                    messageOutput: this.messageToSend,
                                    id: this.idMsg,
                                    time: this.getCurrentTime()
                                };
                                
                                this.$chatHistoryList.append(template(context));
                                this.scrollToBottom();
                                this.$textarea.val('');
                            }

                        },

                        addMessage: function () {
                            this.messageToSend = this.$textarea.val();
                            var context = {
                                messageOutput: this.messageToSend,
                                id: this.idMsg,
                                time: this.getCurrentTime()
                            };

                            this.sendMessageToBackend(context);
//                            this.render();
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
                            return new Date().toLocaleString('en-GB', { hour12: true }).replaceAll("/", "-");
                        },
//                        getRandomItem: function (arr) {
//                            return arr[Math.floor(Math.random() * arr.length)];
//                        },
                        initHistorial: function(){
                            var templateEnviar = Handlebars.compile($("#message-template").html());
                            var templateResponse = Handlebars.compile( $("#message-response-template").html());
                            
                            <% for (Par<Integer, Mensaje> msg : mensajes){ %>
                                
                                var data = {
                                    messageOutput: "<%= msg.getSegundoElem().getContenido().trim() %>",
                                    id: "<%= msg.getSegundoElem().getId() %>",
                                    time: "<%= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa").format(msg.getSegundoElem().getFecha()) %>"
                                }; 
                                
                                
                            
                            <% if (msg.getPrimerElem().equals(userId2)){ %>
                                    this.$chatHistoryList.append(templateEnviar(data));            
                                <% } else if (msg.getPrimerElem().equals(userId1)){%>
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