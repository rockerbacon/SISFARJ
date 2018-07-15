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
		
		<div class="form-group">
			<label for="nomeAtleta">Nome</label>
			<input type="text" class="form-control" name="atleta_nome" id="nomeAtleta" value="<%=request.getSession().getAttribute("atleta_nome")%>"/>
		</div>
		<div class="form-group">
			<label for="dataEntrada">data entrada</label>
			<input type="text" class="form-control" name="atleta_associacao_entrada" id="entradaData" value="<%=request.getSession().getAttribute("atleta_associacao_data")%>"/>
		</div>
		<div class="form-group">
			<label for="numeroAtleta">Número</label>
			<input type="text" class="form-control" name="atleta_numero" id="numeroAtleta" value="<%=request.getSession().getAttribute("atleta_numero")%>"/>
		</div>
		<div class="form-group">
			<label for="dataOficio">data oficio</label>
			<input type="text" class="form-control" name="atleta_oficio_data" id="dataOficio" value="<%=request.getSession().getAttribute("atleta_oficio_data")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="atleta_nascimento_data" id="atletaNascimento" value="<%=request.getSession().getAttribute("atleta_nascimento_data")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="atleta_matricula" id="atletaMatricula" value="<%=request.getSession().getAttribute("atleta_matricula")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="atleta_indice" id="atletaIndice" value="<%=request.getSession().getAttribute("atleta_indice")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="matricula_associacao" id="matriculaAssociacao" value="<%=request.getSession().getAttribute("matricula_associacao")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="atleta_categoria" id="atletaCategoria" value="<%=request.getSession().getAttribute("atleta_categoria")%>"/>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" name="comprovante_pagamento" id="comprovantePagamento" value="<%=request.getSession().getAttribute("comprovante_pagamento")%>"/>
		</div>
		
		<button type="submit" class="btn btn-primary">Alterar</button>
	</form>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>