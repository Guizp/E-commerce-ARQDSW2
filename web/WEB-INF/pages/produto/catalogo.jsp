<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.controllers.produto.BuscarProdutoForm" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%
BuscarProdutoForm form = (BuscarProdutoForm)request.getAttribute("form");
<<<<<<< HEAD
List<ProdutoDTO> produtos = (List<ProdutoDTO>)request.getAttribute("produtos");
int total = (int)request.getAttribute("total");
int totalPag = (int) Math.ceil((double) total / form.getPageSize());
request.setAttribute("pageTitle", "Catalogo");
=======
request.setAttribute("pageTitle", "Catálogo");
>>>>>>> a3c7475e32dd79761ab884c1d4971bb1fb28e054
request.setAttribute("activePage", "catalogo");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<<<<<<< HEAD
<style>
.catalogo-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 1.25rem;
    margin-top: 1rem;
}
.produto-card {
    background: var(--color-surface);
    border: 1px solid var(--color-border);
    border-radius: var(--radius);
    display: flex;
    flex-direction: column;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0,0,0,0.06);
    transition: box-shadow 0.15s ease-in-out;
}
.produto-card:hover {
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}
.produto-card .card-img {
    width: 100%;
    height: 160px;
    object-fit: cover;
    background: #f0f3f2;
    display: flex;
    align-items: center;
    justify-content: center;
    color: var(--color-muted);
    font-size: 2.5rem;
}
.produto-card .card-img img {
    width: 100%;
    height: 160px;
    object-fit: cover;
}
.produto-card .card-body {
    padding: 1rem;
    display: flex;
    flex-direction: column;
    flex: 1;
}
.produto-card .card-title {
    font-weight: 600;
    font-size: 0.95rem;
    color: var(--color-text);
    margin-bottom: 0.5rem;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    min-height: 3.6em;
}
.produto-card .card-preco {
    font-size: 1.2rem;
    font-weight: 700;
    color: var(--color-primary-dark);
    margin-bottom: 0.75rem;
}
.produto-card .card-footer {
    padding: 0.75rem 1rem;
    border-top: 1px solid var(--color-border);
    background: #fafafa;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}
.produto-card .card-footer input[type="number"] {
    width: 65px;
    padding: 0.35rem 0.5rem;
    border: 1px solid var(--color-border);
    border-radius: 4px;
    font-size: 0.95rem;
}
.produto-card .card-footer button {
    flex: 1;
    padding: 0.45rem 0.5rem;
    font-size: 0.9rem;
}
</style>

<h1>Catalogo de produtos</h1>

<div class="card">
    <form id="search-form" method="get" action="<%= request.getContextPath() %>/produto/catalogo">
        <div class="form-row">
            <label for="descricao">Buscar:</label>
            <input type="text" name="descricao" id="descricao"
                   value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>">
        </div>
        <input type="hidden" name="page" value="<%= form.getPage() %>">
        <button type="submit" onclick="resetPage()">Buscar</button>
    </form>
</div>

<p class="subtitle">Total de produtos: <%= total %></p>

<div class="catalogo-grid">
    <% for (ProdutoDTO p : produtos) { %>
    <div class="produto-card">
        <div class="card-img">
            <% if (p.foto() != null) { %>
            <img src="<%= request.getContextPath() %>/uploads/produtos/<%= p.foto() %>"
                 alt="<%= p.descricao() %>">
            <% } else { %>
            📦
            <% } %>
        </div>
        <div class="card-body">
            <div class="card-title"><%= p.descricao() %></div>
            <div class="card-preco">R$ <%= String.format("%.2f", p.preco()) %></div>
        </div>
        <div class="card-footer">
            <form method="post" action="<%= request.getContextPath() %>/carrinho/adicionar"
                  style="display:flex; gap:0.5rem; width:100%; align-items:center;">
                <input type="hidden" name="produtoId" value="<%= p.id() %>">
                <input type="number" name="quantidade" value="1" min="1">
                <button type="submit">Adicionar</button>
            </form>
        </div>
    </div>
    <% } %>
</div>

<div class="pagination" style="margin-top:1.5rem;">
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
=======
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
>>>>>>> a3c7475e32dd79761ab884c1d4971bb1fb28e054
</div>

<script>
function gotoPage(page) {
<<<<<<< HEAD
    const form = document.querySelector('#search-form');
    form.querySelector('input[name="page"]').value = page;
    form.submit();
}
function movePage(offset) {
    gotoPage(<%= form.getPage() %> + offset);
}
function resetPage() {
    document.querySelector('#search-form input[name="page"]').value = 1;
=======
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
>>>>>>> a3c7475e32dd79761ab884c1d4971bb1fb28e054
}
</script>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
