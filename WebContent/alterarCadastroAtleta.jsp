<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ - Alterar cadastro atleta</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>
<form action="InvokerServlet" method="POST">
		<input type="hidden" name="command" value="alterarCadastroAtletaCommand"/>
		<input type="hidden" name="asso_matricula" value="<%=request.getSession().getAttribute("atle_matricula")%>"/>
		<input type="hidden" name="atle_categoria" value="<%=request.getSession().getAttribute("atle_categoria") %>"/>	    				
		<input type="hidden" name="atleta_indice" value="<%=request.getSession().getAttribute("atleta_indice")%>"/>
		<input type="hidden" name="data_nascimento" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(request.getSession().getAttribute("data_nascimento")) %>"/>
		<input type="hidden" name="comprovante_pagamento" value="<%=request.getSession().getAttribute("comprovante_pagamento")%>"/>		
		<input type="hidden" name="atle_matricula" value="<%=request.getSession().getAttribute("atleta_matricula")%>"/>
		<%=request.getSession().getAttribute("atle_matricula")%>
		<%=request.getSession().getAttribute("atle_categoria") %>
		<%=request.getSession().getAttribute("atleta_indice")%>
		<%=request.getSession().getAttribute("comprovante_pagamento")%>
		<%=new SimpleDateFormat("dd/MM/yyyy").format(request.getSession().getAttribute("data_nascimento")) %>
		<%=request.getSession().getAttribute("atle_matricula")%>
		
		<div class="form-group">
			<label for="nomeAtleta">Nome</label>
			<input type="text" class="form-control" name="atleta_nome" id="nomeAtleta" value="<%=request.getSession().getAttribute("atleta_nome")%>"/>
		</div>
		<div class="form-group">
			<label for="dataEntrada">data entrada</label>
			<input type="text" class="form-control" name="data_entrada" id="entradaData" value="<%=request.getSession().getAttribute("data_entrada")%>"/>
		</div>
		<div class="form-group">
			<label for="numeroAtleta">Número</label>
			<input type="text" class="form-control" name="atleta_numero" id="numeroAtleta" value="<%=request.getSession().getAttribute("atleta_numero")%>"/>
		</div>
		<div class="form-group">
			<label for="dataOficio">data oficio</label>
			<input type="text" class="form-control" name="data_oficio" id="dataOficio" value="<%=request.getSession().getAttribute("data_oficio")%>"/>
		</div>
		<button type="submit" class="btn btn-primary">Alterar</button>
	</form>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>