<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%@ page import="java.util.List" %>  
<%@ page import="database.Associacao" %>  
<%@ page import="java.util.Comparator" %>

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


	<table style="width:100%">
		<tr>
			<th>Matricula</th>
			<th>Nome</th>
		</tr>
		<%
		List<Associacao> assocs = Associacao.listar();
		assocs.sort(new Comparator<Associacao>() { @Override public int compare(Associacao a, Associacao b) { return a.get_nome().compareTo(b.get_nome()); } });
		for(Associacao assoc : assocs) {
	    %>
	    	<tr>
	    		<td><%=assoc.get_matricula()%></td>
	    		<td><%=assoc.get_nome()%></td>
	    	</tr>
	    <%
	    }
	    %>
    </table>



<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>