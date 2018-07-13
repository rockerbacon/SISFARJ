<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<div class="container" style="margin-top: 30px;">	
	<h4 class="mb-3">SISFARJ - Filiar associação</h4>
	<form action="InvokerServlet" method="POST">
		<input type="hidden" name="command" value="FiliarAssociacaoCommand"/>
		<div class="form-group">
			<label for="nOficio">Número de ofício</label>
			<input type="text" class="form-control" name="nOficio" id="nOficio" placeholder="Número de ofício">
		</div>	
		<div class="form-group">
			<label for="Data">Data Ofício</label>
			<input type="date" class="form-control" name="dataOficio" id="dataOficio" placeholder="Data Ofício">
		</div>	
		<div class="form-group">
			<label for="nomeAssoc">Nome</label>
			<input type="text" class="form-control" name="nomeAssoc" id="nomeAssoc" placeholder="Nome">
		</div>
		<div class="form-group">
			<label for="siglaAssoc">Sigla</label>
			<input type="text" class="form-control" name="siglaAssoc" id="siglaAssoc" placeholder="Sigla">
		</div>
		<div class="form-group">
			<label for="enderecoAssoc">Endereço</label>
			<input type="text" class="form-control" name="enderecoAssoc" id="enderecoAssoc" placeholder="Endereço">
		</div>
		<div class="form-group">
			<label for="telAssoc">Telefone</label>
			<input type="text" class="form-control" name="telAssoc" id="telAssoc" placeholder="Telefone">
		</div>
		<div class="form-group">
			<label for="numComprovantePag">Número do comprovante de pagamento</label>
			<input type="text" class="form-control" name="numComprovantePag" id="numComprovantePag" placeholder="Número do comprovante de pagamento">
		</div>

		<button type="submit" value="Cadastrar" class="btn btn-primary">Cadastrar</button>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>