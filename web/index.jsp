<%--
  Created by IntelliJ IDEA.
  User: shezhao
  Date: 2/27/14
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
       <form action="/write" method="post">
           Name: <input type="text" name="name"/>
           <br>
           Message: <textarea name="message"></textarea>
           <br>
           <input type="submit" name="Submit"/>
       </form>
  </body>
</html>
