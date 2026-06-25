<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.modelo.ItemPedido" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.ifsp.loja.controllers.auth.UsuarioLogado" %>
<%
List<ItemPedido> itens = (List<ItemPedido>) request.getAttribute("itens");
int pedidoId = (int) request.getAttribute("pedidoId");
request.setAttribute("pageTitle", "Pedido #" + pedidoId);
request.setAttribute("activePage", "pedidos");
double total = 0;
for (ItemPedido item : itens) {
	total += item.getSubtotal();
}
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<%
UsuarioLogado u = (UsuarioLogado) session.getAttribute("usuarioLogado");
%>

<h1>Pedido #<%= pedidoId %></h1>

<% if (itens == null || itens.isEmpty()) { %>
	<p class="empty-state">Nenhum item encontrado para este pedido.</p>
<% } else { %>
<table>
	<tr>
		<th>Produto</th>
		<th>Preco unit.</th>
		<th>Quantidade</th>
		<th>Subtotal</th>
	</tr>
	<% for (ItemPedido item : itens) { %>
	<tr>
		<td><%= item.getProdutoDescricao() %></td>
		<td>R$ <%= String.format("%.2f", item.getPrecoUnitario()) %></td>
		<td><%= item.getQuantidade() %></td>
		<td>R$ <%= String.format("%.2f", item.getSubtotal()) %></td>
	</tr>
	<% } %>
	<tr>
		<td colspan="3" style="text-align:right; font-weight:700;">Total:</td>
		<td style="font-weight:700;">R$ <%= String.format("%.2f", total) %></td>
	</tr>
</table>
<% } %>

<div style="margin-top:1rem;">
	<% if (u != null && u.isAdm()) { %>
	<a class="btn" href="<%= request.getContextPath() %>/pedido/todos"
	   style="background-color: var(--color-muted);">Voltar</a>
	<% } else { %>
	<a class="btn" href="<%= request.getContextPath() %>/pedido/meus"
	   style="background-color: var(--color-muted);">Voltar</a>
	<% } %>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>