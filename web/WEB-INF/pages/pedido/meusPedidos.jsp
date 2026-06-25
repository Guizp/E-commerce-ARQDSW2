<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.modelo.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
request.setAttribute("pageTitle", "Meus Pedidos");
request.setAttribute("activePage", "pedidos");
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
String pedidoSucesso = request.getParameter("sucesso");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Meus pedidos</h1>

<% if (pedidoSucesso != null) { %>
<div class="card" style="border-left: 4px solid var(--color-primary); margin-bottom:1rem;">
	<p style="color: var(--color-primary-dark); font-weight:600;">
		✅ Pedido #<%= pedidoSucesso %> finalizado com sucesso!
	</p>
</div>
<% } %>

<% if (pedidos == null || pedidos.isEmpty()) { %>
	<div class="card">
		<p class="empty-state">Você ainda não realizou nenhum pedido.</p>
		<a class="btn" href="<%= request.getContextPath() %>/produto/catalogo" style="margin-top:0.75rem; display:inline-block;">Ver catálogo</a>
	</div>
<% } else { %>
	<table>
		<tr>
			<th>#</th>
			<th>Data</th>
			<th>Total</th>
			<th>Detalhes</th>
		</tr>
		<% for (Pedido p : pedidos) { %>
		<tr>
			<td><%= p.getId() %></td>
			<td><%= p.getDataPedido().format(fmt) %></td>
			<td>R$ <%= String.format("%.2f", p.getTotal()) %></td>
			<td>
				<a class="btn btn-sm" href="<%= request.getContextPath() %>/pedido/detalhe?id=<%= p.getId() %>">Ver itens</a>
			</td>
		</tr>
		<% } %>
	</table>
<% } %>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
