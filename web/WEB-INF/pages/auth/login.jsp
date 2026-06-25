<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.controllers.auth.CadastroForm" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Entrar - E-Commerce</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
	<main class="container" style="max-width: 420px; margin-top: 4rem;">
		<h1 style="text-align: center;">E-Commerce</h1>
		<p class="subtitle" style="text-align: center;">Entre com sua conta para continuar</p>

		<div class="card">
			<% if (request.getAttribute("sucesso") != null) { %>
			<p style="color: var(--color-primary-dark); font-weight: 600; margin-bottom: 1rem;">
				<%= request.getAttribute("sucesso") %>
			</p>
			<% } %>
			<% if (request.getAttribute("erro") != null) { %>
			<p style="color: #b3413a; font-weight: 600; margin-bottom: 1rem;">
				<%= request.getAttribute("erro") %>
			</p>
			<% } %>

			<form method="post" action="<%= request.getContextPath() %>/login">
				<div class="form-row" style="flex-direction: column; align-items: stretch;">
					<label for="email">E-mail:</label>
					<input type="email" name="email" id="email" required autofocus
						   value="<%= request.getAttribute("emailDigitado") != null ? request.getAttribute("emailDigitado") : "" %>">
				</div>
				<div class="form-row" style="flex-direction: column; align-items: stretch;">
					<label for="senha">Senha:</label>
					<input type="password" name="senha" id="senha" required>
				</div>
				<button type="submit" style="width: 100%;">Entrar</button>
			</form>
		</div>

		<p style="text-align: center;">
			Não tem conta? <a href="<%= request.getContextPath() %>/cadastro">Cadastre-se</a>
		</p>
	</main>
</body>
</html>
