<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%@ page import="java.util.List" %>  
<%@ page import="domain.Associacao" %>  
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="database.AssociacaoScript" %>

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
			<th></th>
		</tr>
		<%
			List<Associacao> assocs = AssociacaoScript.listar();
			assocs.sort(new Comparator<Associacao>() { @Override public int compare(Associacao a, Associacao b) { return a.get_nome().toUpperCase().compareTo(b.get_nome().toUpperCase()); } });
			for(Associacao assoc : assocs) {
		%>
	    	<tr>
	    		<td><%=assoc.get_matricula()%></td>
	    		<td><%=assoc.get_nome()%></td>
	    		<td>
	    			<form action="trataSession.jsp"/>
	    				<input type="hidden" name="proximaPagina" value="alterarFiliacaoAssociacao.jsp"/>
	    				
	    				<input type="hidden" name="asso_matricula" value="<%=assoc.get_matricula()%>"/>
	    				<input type="hidden" name="asso_nome" value="<%=assoc.get_nome() %>"/>
	    				<input type="hidden" name="asso_sigla" value="<%=assoc.get_sigla() %>"/>
	    				<input type="hidden" name="asso_endereco" value="<%=assoc.get_endereco() %>"/>
	    				<input type="hidden" name="asso_telefone" value="<%=assoc.get_telefone() %>"/>
	    				<input type="hidden" name="asso_oficio" value="<%=assoc.get_oficio() %>"/>
	    				<input type="hidden" name="asso_data" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(assoc.get_data()) %>"/>
	    				<input type="hidden" name="asso_data" value="<%=assoc.get_senha()%>"/>
	    				${button}
	    			</form>
	    		</td>
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