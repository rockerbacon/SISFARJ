<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%@ page import="java.util.List" %>  
<%@ page import="database.Atleta" %>  
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
		List<Atleta> atleta2 = Atleta.listar();
		atleta2.sort(new Comparator<Atleta>() { @Override public int compare(Atleta a, Atleta b) { if(a.get_numero() < b.get_numero()) return a.get_numero(); else return b.get_numero(); } });
		for(Atleta atleta : atleta2) {
	    %>
	    	<tr>
	    		<td><%=atleta.get_numero()%></td>
	    		<td><%=atleta.get_nome()%></td>
	    		<td>
	    			<form action="alterarCadastroAtleta"/>
	    				<input type="hidden" name="atleta_nome" value="<%=atleta.get_nome() %>"/>
	    				<input type="hidden" name="atle_categoria" value="<%=atleta.get_categoria() %>"/>	    				
	    				<input type="hidden" name="atleta_numero" value="<%=atleta.get_numero()%>"/>
	    				<input type="hidden" name="atleta_indice" value="<%=atleta.get_indice() %>"/>
	    				<input type="hidden" name="data_oficio" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.get_oficio_data()) %>"/>
	    				<input type="hidden" name="data_entrada" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.get_associacao_data()) %>"/>
	    				<input type="hidden" name="data_nascimento" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(atleta.get_nascimento_data()) %>"/>
	    				<input type="hidden" name="atleta_matricula" value="<%=atleta.get_matricula() %>"/>
	    				<%request.getSession().setAttribute("atleta_nome", atleta.get_nome()); %>
						<%request.getSession().setAttribute("atleta_categoria", atleta.get_categoria());%>	    				
						<%request.getSession().setAttribute("atleta_numero", atleta.get_numero());%>
						<%request.getSession().setAttribute("atleta_indice", atleta.get_indice());%>				
						<%request.getSession().setAttribute("data_oficio", atleta.get_oficio_data()); %>
						<%request.getSession().setAttribute("data_entrada", atleta.get_associacao_data()); %>
						<%request.getSession().setAttribute("data_nascimento", atleta.get_nascimento_data()); %>
						<%request.getSession().setAttribute("atleta_matricula", atleta.get_matricula()); %>						
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
