<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Enumeration" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%

 Enumeration <String> parametros = request.getParameterNames();
while (parametros.hasMoreElements()){
	String parametro = parametros.nextElement();
	request.getSession().setAttribute(parametro, request.getParameter(parametro));
}

%>
<% request.getRequestDispatcher(request.getParameter("proximaPagina")).forward(request, response);%>

</body>
</html>