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
	<h4 class="mb-3">SISFARJ - Criar competição</h4>
	<form action="opcoesCriarCompeticao.jsp">
		<input type="hidden" name="button" value='<button type="sumbit">Selecionar</button>'/>
		<div class="form-group">
			<label for="exampleInputEmail1">Nome da Competição</label>
			<input type="text" class="form-control" name="comp_nome" placeholder="Nome da competição">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Data da Competição</label>
			<input type="text" class="form-control" name="comp_data" placeholder="Nome da competição">
		</div>
		<div>
			<button type="submit">Proximo</button>
		</div>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>