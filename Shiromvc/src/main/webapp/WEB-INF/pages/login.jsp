<%--
  Created by IntelliJ IDEA.
  User: 75821
  Date: 2020/1/16
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/login.html" method="post">
        用户名：<input type="text" name="username"/>
        密码：<input type="password" name="password"/>
        <input type="submit" value="登录"/>${error}
    </form>
</body>
</html>
