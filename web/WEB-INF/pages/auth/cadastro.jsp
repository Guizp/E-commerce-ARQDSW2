<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.controllers.auth.CadastroForm" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%
CadastroForm form = (CadastroForm) request.getAttribute("form");
if (form == null) {
	form = new CadastroForm();
}
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Criar conta - E-Commerce</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
	<main class="container" style="max-width: 420px; margin-top: 4rem;">
		<h1 style="text-align: center;">Criar conta</h1>
		<p class="subtitle" style="text-align: center;">Cadastre-se para comprar na nossa loja</p>

		<div class="card">
			<% if (request.getAttribute("erro") != null) { %>
			<p style="color: #b3413a; font-weight: 600; margin-bottom: 1rem;">
				<%= request.getAttribute("erro") %>
			</p>
			<% } %>

			<form method="post" action="<%= request.getContextPath() %>/cadastro">
				<div class="form-row" style="flex-direction: column; align-items: stretch;">
					<label for="nome">Nome:</label>
					<input type="text" name="nome" id="nome" required autofocus
						   value="<%= StringUtil.emptyIfNull(form.getNome()) %>">
				</div>
				<div class="form-row" style="flex-direction: column; align-items: stretch;">
					<label for="email">E-mail:</label>
					<input type="email" name="email" id="email" required
						   value="<%= StringUtil.emptyIfNull(form.getEmail()) %>">
				</div>
				<div class="form-row" style="flex-direction: column; align-items: stretch;">
					<label for="senha">Senha:</label>
					<input type="password" name="senha" id="senha" required minlength="6">
				</div>
				<button type="submit" style="width: 100%;">Criar conta</button>
			</form>
		</div>

		<p style="text-align: center;">
			Já tem conta? <a href="<%= request.getContextPath() %>/login">Entrar</a>
		</p>
	</main>
</body>
</html>
