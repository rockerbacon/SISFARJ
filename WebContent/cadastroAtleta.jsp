<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login do usuário</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>
<div class="container" style="margin-top: 30px;">	
	<h4 class="mb-3">SISFARJ - Cadastrar atleta</h4>
	<form action="InvokerServlet" method="POST">
		<input type="hidden" name="command" value="Cadastrar atleta"/>
		<input type="hidden" name="paginaRedirecionamento" value="cadastroAtleta.jsp"/>
		<div class="form-group">
			<label for="exampleInputEmail1">Número de ofício</label>
			<input type="text" class="form-control" name="numero" id="numero"  placeholder="Número de ofício">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Data Ofício</label>
			<input type="date" class="form-control" name="data_oficio" id="dataOficio" placeholder="Data Ofício">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Nome</label>
			<input type="text" class="form-control" name="nome" id="nome" placeholder="Nome">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Data de nascimento</label>
			<input type="date" class="form-control" name="data_nascimento" id="dtNasc" placeholder="Data de nascimento">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Data de entrada</label>
			<input type="date" class="form-control" name="data_entrada" id="dtEntrada" placeholder="Data de entrada">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Matrícula da associacao</label>
			<input type="text" class="form-control" name="matricula_atleta" id="matricula_atleta" placeholder="Matrícula da Associacao">
		</div>
		<div class="form-group">
			<select class="custom-select" name="categoria">
			  <option selected>Categoria</option>
			  <option value="masculina">Masculina</option>
			  <option value="feminina">Feminina</option>
			</select>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Comprovante Pagamento</label>
			<input type="text" class="form-control" name="comprovante_pagamento" id="comprovante_pagamento" placeholder="Comprovante">
		</div>

		<button type="submit" class="btn btn-primary">Enviar</button>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>