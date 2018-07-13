<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.Date" %>  
<%@ page import="java.util.List,java.util.Set" %>  
<%@ page import="database.Associacao" %>  

<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ - Filiar associação</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>


	<%
	List<Associacao> assocs = Associacao.listar();

	for(Associacao assoc : assocs){
    %>
    	<form action="LoginRedirectServlet">
			<input type="hidden" name="afterLogin" value="/alterarFiliacaoAssociacao.jsp"/>
			<input type="hidden" name="accessLevel" value="0"/>
			<input type="hidden" name="matricula" value="<%=assoc.get_matricula()%>"/>
			<input type="hidden" name="nome" value="<%=assoc.get_nome()%>"/>
			<input type="hidden" name="sigla" value="<%=assoc.get_sigla()%>"/>
			<input type="hidden" name="endereco" value="<%=assoc.get_endereco()%>"/>
			<input type="hidden" name="telefone" value="<%=assoc.get_telefone()%>"/>
			<input type="hidden" name="oficio" value="<%=assoc.get_oficio()%>"/>
			<input type="hidden" name="data" value="<%=assoc.get_data()%>"/>
			<button  type="submit"> <%=assoc.get_nome()%></button>
		</form>
        

    <%
    }
    %>



<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>