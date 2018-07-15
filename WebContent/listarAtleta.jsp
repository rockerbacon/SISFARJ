<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%@ page import="java.util.List" %>  
<%@ page import="database.AtletaScript" %>  
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.SimpleDateFormat" %>

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
		List<AtletaScript> atleta2 = AtletaScript.listar();
		atleta2.sort(new Comparator<AtletaScript>() { @Override public int compare(AtletaScript a, AtletaScript b) { if(a.getAtle_matricula() < b.getAtle_matricula()) return a.getAtle_matricula(); else return b.getAtle_matricula(); } });
		for(AtletaScript atleta : atleta2) {
	    %>
	    	<tr>
	    		<td><%=atleta.getAtle_numero()%></td>
	    		<td><%=atleta.getAtle_nome()%></td>
	    		<td>
	    			<form action="trataSession.jsp"/>
	    				<input type="hidden" name="proximaPagina" value="alterarCadastroAtleta.jsp" />
	    				<input type="hidden" name="atleta_nome" value="<%=atleta.getAtle_nome() %>"/>
	    				<input type="hidden" name="atle_categoria" value="<%=atleta.getAtle_categoria() %>"/>	    				
	    				<input type="hidden" name="atleta_numero" value="<%=atleta.getAtle_numero()%>"/>
	    				<input type="hidden" name="atleta_indice" value="<%=atleta.getAtle_indice() %>"/>
	    				<input type="hidden" name="atleta_oficio_data" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.getAtle_oficio_data()) %>"/>
	    				<input type="hidden" name="atleta_associacao_data" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.getAtle_associacao_data()) %>"/>
	    				<input type="hidden" name="atleta_nascimento_data" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.getAtle_nascimento_data()) %>"/>
	    				<input type="hidden" name="atleta_matricula" value="<%=atleta.getAtle_matricula() %>"/>
	    				<input type="hidden" name="matricula_associacao" value="<%=atleta.getAsso_matricula() %>"/>	    
	    				<input type="hidden" name="comprovante_pagamento" value="<%=atleta.getAtle_comprovante_pagamento() %>"/>						
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