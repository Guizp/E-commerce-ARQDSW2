<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="edu.ifsp.loja.controllers.cliente.BuscarClienteForm" %>
<%@ page import="edu.ifsp.loja.controllers.cliente.ClienteDTO" %>
<%@ page import="java.util.List" %>
<%
BuscarClienteForm dto = (BuscarClienteForm)request.getAttribute("dto");
request.setAttribute("pageTitle", "Clientes");
request.setAttribute("activePage", "clientes");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<h1>Buscar clientes</h1>

<div class="card">
	<form method="get" action="<%= request.getContextPath() %>/cliente/buscar">
		<div class="form-row">
			<label for="nome">Nome:</label>
			<input type="text" name="nome" id="nome" value="<%= dto.nome() %>">
		</div>
		<div class="form-row checkbox">
			<input type="checkbox" <%= dto.ativo() ? "checked" : "" %> name="ativo" id="ativo" value="true">
			<label for="ativo">Somente clientes ativos</label>
		</div>
		<button type="submit">Buscar</button>
	</form>
</div>

<%
if (request.getAttribute("clientes") != null) {
	List<ClienteDTO> clientes = (List<ClienteDTO>)request.getAttribute("clientes");
%>
<table>
	<tr>
		<th>ID</th>
		<th>Nome</th>
		<th>E-mail</th>
		<th>Ativo</th>
	</tr>
	<% for (ClienteDTO c : clientes) { %>
	<tr>
		<td><%= c.id() %></td>
		<td><%= c.nome() %></td>
		<td><%= c.email() %></td>
		<td><%= c.ativo() ? "Sim" : "Não" %></td>
	</tr>
	<% } %>
</table>
<%
} else {
%>
<p class="empty-state">Nenhum cliente encontrado para os critérios informados.</p>
<%
}
%>

<%@ include file="/WEB-INF/includes/footer.jspf" %>