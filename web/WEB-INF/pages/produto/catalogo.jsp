<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.controllers.produto.BuscarProdutoForm" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%
BuscarProdutoForm form = (BuscarProdutoForm)request.getAttribute("form");
request.setAttribute("pageTitle", "Catálogo");
request.setAttribute("activePage", "catalogo");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Catálogo de produtos</h1>

<div class="card">
	<form id="search-form" method="get" action="<%= request.getContextPath() %>/produto/catalogo">
		<div class="form-row">
			<label for="descricao">Buscar:</label>
			<input type="text" name="descricao" id="descricao" value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>">
		</div>
		<input type="hidden" name="page" value="<%= form.getPage() %>">
		<button type="submit" onclick="resetPage()">Buscar</button>
	</form>
</div>

<%
List<ProdutoDTO> produtos = (List<ProdutoDTO>)request.getAttribute("produtos");
int total = (int)request.getAttribute("total");
int totalPag = (int) Math.ceil((double) total / form.getPageSize());
%>
<p class="subtitle">Total de produtos: <%= total %></p>

<div class="menu-grid">
	<% for (ProdutoDTO p : produtos) { %>
	<div class="card" style="text-align: center;">
		<% if (p.foto() != null) { %>
		<img src="<%= request.getContextPath() %>/uploads/produtos/<%= p.foto() %>" alt="Foto de <%= p.descricao() %>"
			 style="width: 100%; max-height: 160px; object-fit: cover; border-radius: 4px; margin-bottom: 0.75rem;">
		<% } %>
		<p style="font-weight: 600;"><%= p.descricao() %></p>
		<p style="color: var(--color-primary-dark); font-size: 1.2rem; font-weight: 700;">R$ <%= String.format("%.2f", p.preco()) %></p>
		<form method="post" action="<%= request.getContextPath() %>/carrinho/adicionar">
			<input type="hidden" name="produtoId" value="<%= p.id() %>">
			<input type="number" name="quantidade" value="1" min="1" style="width: 70px; margin-bottom: 0.5rem;">
			<br>
			<button type="submit">Adicionar ao carrinho</button>
		</form>
	</div>
	<% } %>
</div>

<div class="pagination">
	<% if (form.getPage() > 1) { %>
	<a href="#" onclick="gotoPage(1)">Primeira</a>
	<a href="#" onclick="movePage(-1)">Anterior</a>
	<% } %>

	<% for (int i = 1; i <= totalPag; i++) { %>
		<% if (i == form.getPage()) { %>
		<strong><%= i %></strong>
		<% } else { %>
		<a href="#" onclick="gotoPage(<%= i %>)"><%= i %></a>
		<% } %>
	<% } %>

	<% if (form.getPage() < totalPag) { %>
	<a href="#" onclick="movePage(1)">Próxima</a>
	<a href="#" onclick="gotoPage(<%= totalPag %>)">Última</a>
	<% } %>
</div>

<script>
function gotoPage(page) {
	const form = document.querySelector('#search-form');
	const pageInput = form.querySelector('input[name="page"]');
	pageInput.value = page;
	form.submit();
}
function movePage(offset) {
	const currentPage = <%= form.getPage() %>;
	gotoPage(currentPage + offset);
}
function resetPage() {
	const form = document.querySelector('#search-form');
	const pageInput = form.querySelector('input[name="page"]');
	pageInput.value = 1;
}
</script>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
