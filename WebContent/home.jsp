<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="home.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pagina inicial</title>
</head>
<body>
<div class="topbar"> 
	SISFARJ - Menu principal
</div>
<div>
	<form action="LoginRedirectServlet">
		<input type="hidden" name="afterLogin" value="/filiarAssoc.jsp"/>
		<input type="hidden" name="accessLevel" value="0"/>
		<button  type="submit"> Filiar associação</button>
	</form>
	<a href=""> Alterar filiação de associação</a><br>
	<form action="LoginRedirectServlet">
		<input type="hidden" name="afterLogin" value="ListarAssociacaoServlet"/>
		<input type="hidden" name="accessLevel" value="0"/>
		<button  type="submit"> Listar associação</button>
	</form>
	<a href=""> o</a><br>
	<form action="LoginRedirectServlet">
		<input type="hidden" name="afterLogin" value="/cadastroAtleta.jsp"/>
		<input type="hidden" name="accessLevel" value="0"/>
		<button  type="submit"> Cadastrar atleta</button>
	</form>
	<a href=""> Alterar cadastro de atleta</a><br>
	<a href=""> Transferir atleta</a><br>
	<a href=""> Inserir resultado do atleta</a><br>
	<a href=""> Inscrever atleta em competição</a><br>
	<a href=""> Identificar usuário</a><br>
	<a href=""> Incluir locais de competição</a><br>
	<a href=""> Alterar locais de competição</a><br>
	<a href=""> Listar locais de competição</a><br>
	<a href=""> Criar competição</a><br>
	<a href=""> Alterar competição</a><br>
	<a href=""> Inserir resultado do atleta</a><br>
	<a href=""> Listar competição</a><br>
	<a href=""> Listar balizamento competição</a><br>
	<a href=""> Listar pontuação competição</a><br>
	<a href=""> Listar pontuação final</a><br>
	
	
	
</div>

</body>
</html>