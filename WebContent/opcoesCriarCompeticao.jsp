<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.LocalScript" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="database.ProvaScript" %>
<%@ page import="domain.Prova" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detalhes da Competicao</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>
<div class="container" style="margin-top: 30px;">	
	<h4 class="mb-3">SISFARJ - Detalhes da Competicao</h4>
	<form action="InvokerServlet" method="POST">
	
		<div class="form-group">
			<select class="custom-select" name="loca_nome">
			  <option selected>Local</option>
				<%
				List<LocalScript> locais = LocalScript.listar();
				locais.sort( new Comparator<LocalScript> () { @Override public int compare (LocalScript a, LocalScript b) {
					return a.get_nome().toUpperCase().compareTo(b.get_nome().toUpperCase());
				}});
				for (LocalScript local : locais) {	
				%>
				  <option value="<%=local.get_nome()%>"><%=local.get_nome()+" ("+(local.get_tam_pisc() < 75 ? new Short(local.get_tam_pisc()).toString()+"m" : "25 e 50m")+")"%></option>
				<%}%>
			</select>
		</div>
		
		<div class="form-group">
			<select class="custom-select" name="loca_tam_pisc">
				<option value="25">25</option>
				<option value="50">50</option>
			</select>
		</div>
		
		<div class="form-group">
			<select class="custom-select" name="prov_nome">
			  <option selected>Local</option>
				<%
				Prova<LocalScript> provas = ProvaScript.listar();
				provas.sort( new Comparator<ProvaScript> () { @Override public int compare (ProvaScript a, ProvaScript b) {
					return a.get_nome().toUpperCase().compareTo(b.get_nome().toUpperCase());
				}});
				for (ProvaScript prova : provas) {	
				%>
				  <option value="<%=prova.get_nome()%>"><%=prova.get_nome()%></option>
				<%}%>
			</select>
		</div>
		
		<button type="submit" class="btn btn-primary">Confirmar</button>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>