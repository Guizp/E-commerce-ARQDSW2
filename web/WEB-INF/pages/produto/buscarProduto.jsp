<%@ page import="edu.ifsp.loja.controllers.produto.BuscarProdutoForm" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%
BuscarProdutoForm form = (BuscarProdutoForm)request.getAttribute("form");
request.setAttribute("pageTitle", "Produtos");
request.setAttribute("activePage", "produtos");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Buscar produtos</h1>

<div class="card">
	<form id="search-form" method="get" action="<%= request.getContextPath() %>/produto/buscar">
		<div class="form-row">
			<label for="descricao">Descrição:</label>
			<input type="text" name="descricao" id="descricao" value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>">
		</div>
		<div class="form-row">
			<label for="preco-minimo">Preço mínimo:</label>
			<input type="number" name="precoMinimo" id="preco-minimo" value="<%= form.getPrecoMinimo() %>">
		</div>
		<div class="form-row">
			<label for="preco-maximo">Preço máximo:</label>
			<input type="number" name="precoMaximo" id="preco-maximo" value="<%= form.getPrecoMaximo() %>">
		</div>
		<div class="form-row">
			<label for="pagesize">Registros por página:</label>
			<input type="number" name="pageSize" id="pagesize" value="<%= form.getPageSize() %>">
		</div>
		<input type="hidden" name="page" value="<%= form.getPage() %>">
		<button type="submit" onclick="resetPage()">Buscar</button>
	</form>
</div>

<div class="toolbar">
	<a class="btn" href="<%= request.getContextPath() %>/produto/cadastrar">Cadastrar novo produto</a>
</div>

<%
if (request.getAttribute("produtos") != null) {
	List<ProdutoDTO> produtos = (List<ProdutoDTO>)request.getAttribute("produtos");
	int total = (int)request.getAttribute("total");
	int totalPag = (int) Math.ceil((double) total / form.getPageSize());
%>
<p class="subtitle">Total de registros: <%= total %></p>

<table>
	<tr>
		<th>Foto</th>
		<th>ID</th>
		<th>Descrição</th>
		<th>Preço</th>
		<th>Estoque</th>
		<th>Ações</th>
	</tr>
	<% for (ProdutoDTO p : produtos) { %>
	<tr>
		<td>
			<% if (p.foto() != null) { %>
			<img class="thumb" src="<%= request.getContextPath() %>/uploads/produtos/<%= p.foto() %>" alt="Foto de <%= p.descricao() %>">
			<% } else { %>
			&mdash;
			<% } %>
		</td>
		<td><%= p.id() %></td>
		<td><%= p.descricao() %></td>
		<td>R$ <%= String.format("%.2f", p.preco()) %></td>
		<td><%= p.estoque() %></td>
		<td class="actions">
			<a class="btn btn-sm" href="<%= request.getContextPath() %>/produto/editar?id=<%= p.id() %>">Editar</a>
			<form method="post" action="<%= request.getContextPath() %>/produto/excluir" onsubmit="return confirm('Remover o produto \'<%= p.descricao() %>\'?');">
				<input type="hidden" name="id" value="<%= p.id() %>">
				<button type="submit" class="btn-sm btn-danger">Excluir</button>
			</form>
		</td>
	</tr>
	<% } %>
</table>

<div class="pagination">
	<% if (form.getPage() > 1) { %>
	<a href="#" onclick="gotoPage(1)">Primeira</a>
	<a href="#" onclick="movePage(-1)">Anterior</a>
	<% } %>

	<%
	int paginaAtual = form.getPage();
	int inicio = Math.max(1, paginaAtual - 4);
	int fim = Math.min(totalPag, paginaAtual + 5);
	if (inicio > 1) { %><span>...</span><% }
	for (int i = inicio; i <= fim; i++) {
		if (i == paginaAtual) { %>
		<strong><%= i %></strong>
		<% } else { %>
		<a href="#" onclick="gotoPage(<%= i %>)"><%= i %></a>
		<% }
	}
	if (fim < totalPag) { %><span>...</span><% } %>

	<% if (form.getPage() < totalPag) { %>
	<a href="#" onclick="movePage(1)">Proxima</a>
	<a href="#" onclick="gotoPage(<%= totalPag %>)">Ultima</a>
	<% } %>
	<span class="subtitle" style="margin-left:1rem;">Pagina <%= paginaAtual %> de <%= totalPag %></span>
</div>
<%
} else {
%>
<p class="empty-state">Nenhum resultado encontrado para os critérios informados.</p>
<%
}
%>

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
