<%-- 
    Document   : error
    Created on : Mar 29, 2013, 6:47:04 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="infoBean" scope="session" type="beans.InfoBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./CSS/baseCSS.css" />
    </head>
    <body>
        <%@include file="/WEB-INF/titleBar.jsp" %>
        <div id="errorFileDiv">
            <h1>404: File Not Found</h1>
            <a href="Controller">Return to home page</a>
        </div>
    </body>
</html>
