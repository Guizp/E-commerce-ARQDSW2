<%@ page import="edu.ifsp.loja.controllers.auth.UsuarioLogado" %>
<%
UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");

if (usuarioLogado == null) {
	response.sendRedirect(request.getContextPath() + "/login");
} else if (usuarioLogado.isAdm()) {
	response.sendRedirect(request.getContextPath() + "/produto/buscar");
} else {
	response.sendRedirect(request.getContextPath() + "/produto/catalogo");
}
%>