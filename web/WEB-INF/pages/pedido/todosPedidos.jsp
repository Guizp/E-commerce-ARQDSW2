<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.modelo.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
request.setAttribute("pageTitle", "Todos os Pedidos");
request.setAttribute("activePage", "pedidos");
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Todos os pedidos</h1>

<% if (pedidos == null || pedidos.isEmpty()) { %>
	<p class="empty-state">Nenhum pedido realizado ainda.</p>
<% } else { %>
	<table>
		<tr>
			<th>#</th>
			<th>Cliente</th>
			<th>Data</th>
			<th>Total</th>
			<th>Detalhes</th>
		</tr>
		<% for (Pedido p : pedidos) { %>
		<tr>
			<td><%= p.getId() %></td>
			<td><%= p.getClienteNome() %></td>
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
