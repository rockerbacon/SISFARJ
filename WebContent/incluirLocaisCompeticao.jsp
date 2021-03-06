<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ - Incluir locais de competição</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>
<div class="container" style="margin-top: 30px;">	
	<h4 class="mb-3">SISFARJ - Incluir locais de competição</h4>
	<form action = "InvokerServlet" method ="post">
		<input type="hidden" name="command" value="Incluir locais de competicao"/>
		<div class="form-group">
			<label for="exampleInputEmail1">Nome</label>
			<input type="text" class="form-control" name="loca_nome" id="nomeCompeticao" placeholder="Nome">
		</div>		
		<div class="form-group">
			<label for="exampleInputEmail1">Endereço</label>
			<input type="text" class="form-control" name="loca_endereco" id="enderecoCompeticao" placeholder="Endereço">
		</div>
		<div class="form-group">
			<label>Piscina(s)</label>
			<select class="form-control" name="piscinasDisponiveis">
				<option value="25">25 metros</option>
				<option value="50">50 metros</option>
				<option value="75">25 e 50 metros</option>
			</select>
		</div>

		<button type="submit" value="Cadastrar" class="btn btn-primary">Cadastrar</button>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>