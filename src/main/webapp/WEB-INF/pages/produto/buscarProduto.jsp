<%@ page import="edu.ifsp.loja.controllers.produto.BuscarProdutoForm" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%@ page import="java.util.List" %>

<%
BuscarProdutoForm form = (BuscarProdutoForm)request.getAttribute("form");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loja Web2</title>
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
</head>
<body>
	<h1>Buscar produtos</h1>
	<form id="search-form" method="get" action="<%= request.getContextPath() %>/produto/buscar">
		<label for="descricao">Descrição: </label>
		<input type="text" name="descricao" id="descricao" value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>">
		<br>

		<label for="preco-minimo">Preço Mínimo: </label>
		<input type="number" name="precoMinimo" id="preco-minimo" value="<%= form.getPrecoMinimo() %>">
		<br>
				
		<label for="preco-maximo">Preço Máximo: </label>
		<input type="number" name="precoMaximo" id="preco-maximo" value="<%= form.getPrecoMaximo() %>">
		<br>
		
		<label for="pagesize">Quantidade de Registros: </label>
		<input type="number" name="pageSize" id="pagesize" value="<%= form.getPageSize() %>">
		<br>
		
		<input type="hidden" name="page" value="<%= form.getPage() %>">
		
		<button type="submit" onclick="resetPage()">Buscar</button>
		<br>
	</form>
	
	<%
	if (request.getAttribute("produtos") != null) {
		List<ProdutoDTO> produtos = (List<ProdutoDTO>)request.getAttribute("produtos");
		int total = (int)request.getAttribute("total");
		int totalPag = (int) Math.ceil((double) total / form.getPageSize());
	%>
	<p>Total de registros: <%= total %></p>
	<br>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Descrição</th>
			<th>Preco</th>
		</tr>
		
		<% for (ProdutoDTO p : produtos) { %>
		<tr>
			<td><%= p.id() %></td>
			<td><%= p.descricao() %></td>
			<td><%= p.preco() %></td>
		</tr>
		<% } %>
		
	</table>
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
	<%
	} else {
	%>
	<p>Nenhum resultado encontrado para os critérios informados.</p>
	<%
	}
	%>
	
	
</body>
</html>