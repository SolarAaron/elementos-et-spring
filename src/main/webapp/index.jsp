<%@page import="slr.epoo.web.unused.mdl.*"%>
<%@page import="slr.epoo.web.ServiceConfig"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%!
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceConfig.class);
    IPersona p = ctx.getBean(IPersona.class);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hola mundo</h1>
        <%= p.ejecutarGracia()%>
    </body>
</html>
