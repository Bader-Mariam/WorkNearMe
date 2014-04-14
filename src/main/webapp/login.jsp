<%-- 
    Document   : login
    Created on : Mar 28, 2013, 12:28:39 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Work Near Me Login</title>
    </head>
    <body>
        <form action="Controller?action=login" method="post" id="loginForm">
            <div class="row requiredRow">
                <label for="username" id="username-ariaLabel">username</label>
                <input id="username" name="username" type="text" aria-labelledby="username-ariaLabel" class="required" />
            </div>
            <div class="row requiredRow">
                <label for="password" id="password-ariaLabel">password</label>
                <input id="password" name="password" type="password" aria-labelledby="password-ariaLabel" class="required"/>
            </div>
            <div class="row"> 
                <input id="remember" name="remember" type="checkbox" checked aria-labelledby="remember-ariaLabel"/>
                <span><label for="remember" id="remember-ariaLabel">Remember login</label></span>
            </div>
            <div class="row">
                <input type="submit" value="Submit" />
            </div>
        </form>
    </body>
</html>
