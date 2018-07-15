<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ - Alterar Locais de Competição</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
   <a class="navbar-brand" href="#">SISFARJ</a>      
</nav>

<div class="container" style="margin-top: 30px;">	
	<h4 class="mb-3">SISFARJ - Alterar Locais de Competição</h4>
	<form action="InvokerServlet" method="POST">
		<input type="hidden" name="command" value="AlterarLocaisCompeticaoCommand"/>
		
		<div class="form-group">
			<label for="NomeLocal">Nome</label>
			<input type="text" class="form-control" name="loca_nome" id="locaNome" value="<%=request.getSession().getAttribute("loca_nome")%>"/>
		</div>
		<div class="form-group">
			<label for="EnderecoLocal">Endereço</label>
			<input type="text" class="form-control" name="loca_endereco" id="EndLocal" value="<%=request.getSession().getAttribute("loca_endereco")%>"/>
		</div>
		<div class="form-group">
			<label>Piscina(s)</label>
			<select class="form-control" name="piscinasDisponiveis" >
				<option <%if(request.getSession().getAttribute("piscinasDisponiveis").equals("25")){%>selected<% } %> value="25">25 metros</option>
				<option <%if(request.getSession().getAttribute("piscinasDisponiveis").equals("50")){%>selected<% } %> value="50">50 metros</option>
				<option <%if(request.getSession().getAttribute("piscinasDisponiveis").equals("75")){%>selected<% } %> value="75">25 e 50 metros</option>
			</select>
		</div>

		<button type="submit" class="btn btn-primary">Alterar</button>
	</form>
</div>


<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>