<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Date" %>  
<%@ page import="java.util.List,java.util.Set" %>  
<%@ page import="database.Local" %>  
<%@ page import="java.util.Comparator" %>

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
	
	
	<table style="width:100%">
		<tr>
			<th>Nome</th>
			<th>Endereco</th>
			<th>Tamanho Piscina</th>
		</tr>
		<%
		List<Local> local = Local.listar();
		local.sort(new Comparator<Local>(){ @Override public int compare(Local a, Local b){ return a.get_nome().compareTo(b.get_nome());} } );
	
		for(Local l:local){
	    %>
	    	<tr>
		    	<td><%=l.get_nome()%></td>
				<td><%=l.get_endereco()%></td>
				<td>
				<%if(l.get_tam_pisc() == 75){
					%>25 e 50
				<%	
				}else{
					%><%=l.get_tam_pisc() %>
				<%	
				}
				%>
				</td>
		    	<td>
		    		<form action="trataSession.jsp"/>
		    		<input type="hidden" name="proximaPagina" value="alterarLocaisDeCompeticao.jsp" />
		    			<input type="hidden" name="loca_nome" value="<%=l.get_nome() %>"/>
		    			<input type="hidden" name="loca_endereco" value="<%=l.get_endereco() %>"/>
		    			<input type="hidden" name="piscinasDisponiveis" value="<%=l.get_tam_pisc() %>"/>
		    			<%//request.getSession().setAttribute("loca_nome", l.get_nome()); %>
		    			<%//request.getSession().setAttribute("loca_endereco", l.get_endereco()); %>
		    			<%//request.getSession().setAttribute("piscinasDisponiveis", l.get_tam_pisc()); %>
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