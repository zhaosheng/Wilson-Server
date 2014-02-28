<%@ page import="model.Message" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: shezhao
  Date: 2/27/14
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registered Messages</title>
</head>
<body>
     <%
         ArrayList<Message> messages = (ArrayList<Message>) session.getAttribute("msg");
         if ( messages == null) messages = new ArrayList<Message>();
         for (Message message : messages)
         {
             out.println(message);
     %>
     <br>
     <%
         }
     %>
</body>
</html>
