<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoForm" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%
ProdutoForm form = (ProdutoForm) request.getAttribute("form");
String modo = (String) request.getAttribute("modo");
boolean editando = "editar".equals(modo);

request.setAttribute("pageTitle", editando ? "Editar Produto" : "Cadastrar Produto");
request.setAttribute("activePage", "produtos");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1><%= editando ? "Editar Produto" : "Cadastrar Produto" %></h1>

<div class="card">
	<form method="post"
		  action="<%= request.getContextPath() %>/produto/<%= editando ? "editar" : "cadastrar" %>"
		  enctype="multipart/form-data">

		<% if (editando) { %>
		<input type="hidden" name="id" value="<%= form.getId() %>">
		<% } %>

		<div class="form-row">
			<label for="descricao">Descrição:</label>
			<input type="text" name="descricao" id="descricao" required
				   value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>">
		</div>

		<div class="form-row">
			<label for="preco">Preço:</label>
			<input type="number" step="0.01" min="0" name="preco" id="preco" required
				   value="<%= form.getPreco() %>">
		</div>

		<% if (editando && form.getFoto() != null) { %>
		<div class="form-row">
			<label>Foto atual:</label>
			<img src="<%= request.getContextPath() %>/uploads/produtos/<%= form.getFoto() %>"
				 alt="Foto atual do produto"
				 style="max-width: 140px; border-radius: 4px; border: 1px solid var(--color-border);">
		</div>
		<% } %>

		<div class="form-row">
			<label for="foto"><%= editando ? "Substituir foto:" : "Foto do produto:" %></label>
			<input type="file" name="foto" id="foto" accept="image/*">
		</div>

		<button type="submit"><%= editando ? "Salvar alterações" : "Cadastrar" %></button>
		<a class="btn" href="<%= request.getContextPath() %>/produto/buscar"
		   style="background-color: var(--color-muted); margin-left: 0.5rem;">Cancelar</a>
	</form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
