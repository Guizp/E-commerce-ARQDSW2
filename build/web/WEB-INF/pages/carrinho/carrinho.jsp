<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.modelo.Carrinho" %>
<%@ page import="edu.ifsp.loja.modelo.ItemCarrinho" %>
<%@ page import="java.util.List" %>
<%
Carrinho carrinho = (Carrinho) request.getAttribute("carrinho");
request.setAttribute("pageTitle", "Carrinho");
request.setAttribute("activePage", "carrinho");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Carrinho de compras</h1>

<% if (request.getAttribute("erro") != null) { %>
<p style="color:#b3413a; font-weight:600;"><%= request.getAttribute("erro") %></p>
<% } %>

<% if (carrinho == null || carrinho.isEmpty()) { %>
	<div class="card">
		<p class="empty-state">Seu carrinho está vazio.</p>
		<a class="btn" href="<%= request.getContextPath() %>/produto/catalogo" style="margin-top:0.75rem; display:inline-block;">Ver catálogo</a>
	</div>
<% } else { %>
	<table>
		<tr>
			<th>Foto</th>
			<th>Produto</th>
			<th>Preço unit.</th>
			<th>Quantidade</th>
			<th>Subtotal</th>
			<th>Ação</th>
		</tr>
		<% for (ItemCarrinho item : carrinho.getItens()) { %>
		<tr>
			<td>
				<% if (item.getFoto() != null) { %>
				<img class="thumb" src="<%= request.getContextPath() %>/uploads/produtos/<%= item.getFoto() %>" alt="foto">
				<% } else { %>&mdash;<% } %>
			</td>
			<td><%= item.getDescricao() %></td>
			<td>R$ <%= String.format("%.2f", item.getPreco()) %></td>
			<td>
				<form method="post" action="<%= request.getContextPath() %>/carrinho/atualizar" style="display:flex; gap:0.4rem; align-items:center;">
					<input type="hidden" name="produtoId" value="<%= item.getProdutoId() %>">
					<input type="number" name="quantidade" value="<%= item.getQuantidade() %>" min="1" style="width:70px;">
					<button type="submit" class="btn-sm">OK</button>
				</form>
			</td>
			<td>R$ <%= String.format("%.2f", item.getSubtotal()) %></td>
			<td>
				<form method="post" action="<%= request.getContextPath() %>/carrinho/remover" onsubmit="return confirm('Remover item?');">
					<input type="hidden" name="produtoId" value="<%= item.getProdutoId() %>">
					<button type="submit" class="btn-sm btn-danger">Remover</button>
				</form>
			</td>
		</tr>
		<% } %>
	</table>

	<div class="card" style="margin-top:1rem; display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:1rem;">
		<span style="font-size:1.2rem; font-weight:700;">
			Total: R$ <%= String.format("%.2f", carrinho.getTotal()) %>
		</span>
		<div style="display:flex; gap:0.75rem;">
			<a class="btn" href="<%= request.getContextPath() %>/produto/catalogo"
			   style="background-color: var(--color-muted);">Continuar comprando</a>
			<form method="post" action="<%= request.getContextPath() %>/pedido/checkout">
				<button type="submit" class="btn">Finalizar pedido</button>
			</form>
		</div>
	</div>
<% } %>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
