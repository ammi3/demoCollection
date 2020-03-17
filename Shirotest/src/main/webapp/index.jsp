<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h2>Hello World!</h2>
    <a href="login.html">退出登录</a>
    <shiro:hasPermission name="menu:list">
        <a href="#">menu</a>
    </shiro:hasPermission>
    <br/>
    <shiro:hasRole name="admin">
        <a href="#">role</a>
    </shiro:hasRole>
</body>
</html>
