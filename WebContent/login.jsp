<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css">	
	<!-- Custom styles for this template -->
	<link href="css/signin.css" rel="stylesheet">
	<title>Login do usuário</title>
</head>
<body class="text-center">
    <form class="form-signin" data-op-form-id="0" action = "LoginServlet" method = "POST">
      <h1 class="h3 mb-3 font-weight-normal">Login no SISFARJ</h1>
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="user" name="matricula" id="inputUser" class="form-control" placeholder="Usuário" required="" autofocus="" data-op-id="0">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" name="senha" id="inputPassword" class="form-control" placeholder="Senha" required="">
      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
      <p class="mt-5 mb-3 text-muted">© 2018</p>
    </form>
</body>
</html>