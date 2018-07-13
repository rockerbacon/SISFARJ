<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Date" %>  
<%@ page import="java.util.List,java.util.Set" %>  
<%@ page import="database.Local" %>  

<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ - Listar Locais de Competicao</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>
	nome | endereço | piscina<br>
	
	<%
	List<Local> local = Local.listar();

	for(Local l:local){
    %>
			<%=l.get_nome()%> |
			<%=l.get_endereco()%> |
			<%=l.get_tam_pisc()%> <br>
		
        

    <%
    }
    %>




<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>