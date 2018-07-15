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
		<input type="hidden" name="command" value="Alterar Filiacao de Associacao"/>
		<input type="hidden" name="paginaRedirecionamento" value="alterarFiliacaoAssociacao.jsp"/>
		
		<input type="hidden" name="asso_matricula" value="<%=request.getSession().getAttribute("asso_matricula")%>"/>
		<div class="form-group">
			<label for="nomeAssoc">Nome</label>
			<input type="text" class="form-control" name="asso_nome" id="nomeAssoc" value="<%=request.getSession().getAttribute("asso_nome")%>"/>
		</div>
		<div class="form-group">
			<label for="siglaAssoc">Sigla</label>
			<input type="text" class="form-control" name="asso_sigla" id="siglaAssoc" value="<%=request.getSession().getAttribute("asso_sigla")%>"/>
		</div>
		<input type="hidden" name="asso_endereco" value="<%=request.getSession().getAttribute("asso_endereco")%>"/>
		<div class="form-group">
			<label for="telAssoc">Telefone</label>
			<input type="text" class="form-control" name="asso_telefone" id="telAssoc" value="<%=request.getSession().getAttribute("asso_telefone")%>"/>
		</div>
		<div class="form-group">
			<label for="telAssoc">Telefone</label>
			<input type="text" class="form-control" name="asso_oficio" id="telAssoc" value="<%=request.getSession().getAttribute("asso_oficio")%>"/>
		</div>
		<div class="form-group">
			<label for="Data">Data Ofício</label>
			<input type="text" class="form-control" name="asso_data" id="dataOficio" value="<%=request.getSession().getAttribute("asso_data")%>"/>
		</div>
		<input type="hidden" name="asso_senha" value="<%=request.getSession().getAttribute("asso_senha") %>"/>

		<button type="submit" class="btn btn-primary">Alterar</button>
	</form>
</div>

<script src="js/jquery-3.2.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>