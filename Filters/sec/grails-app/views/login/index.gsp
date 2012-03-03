<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>

<body>
<g:if test="${message}">
    <p>${message}</p>
</g:if>
<g:form action="index" controller="login" method="POST">
    <p>Username: <g:textField name="username"/></p>
    <p>Password: <g:passwordField name="password"/></p>
    <g:submitButton name="Login" value="Login"/>
</g:form>
</body>
</html>