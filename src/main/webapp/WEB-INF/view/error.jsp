<%-- 
    Document   : error.jsp
    Created on : 06-May-2021, 18:29:36
    Author     : josie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String error = (String) request.getAttribute("error");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="icon" href="/images/calendar-favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="/components/base/base.css">
        <script src="/components/base/core.js"></script>
        <script src="/components/base/script.js"></script>
        <title>Error</title>
    </head>
    <body>
        <div class="page">
            <jsp:include page="header.jsp" />
            <div class="section section-lg d-flex align-items-center min-vh-100">
                <div class="container">
                    <br/>
                    <h1>Lo sentimos... :(</h1>
                    <%
                        if (error != null)
                            %>
                            <p id="errorMsg" style="color: #ec5482;"> <%= error %> </p>
                            <%
                    %>

                    <img style="float: right; margin-right: 10%; width: 30%; max-width: 499px;" src="images/monke.png"/>

                    <br/><br/>

                    <a class="btn btn-lg btn-secondary" href="/">Volver...</a>
                </div>
            </div>
        </div>
    </body>
</html>
