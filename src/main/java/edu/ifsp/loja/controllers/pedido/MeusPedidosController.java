package edu.ifsp.loja.controllers.pedido;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.controllers.auth.UsuarioLogado;
import edu.ifsp.loja.modelo.Pedido;
import edu.ifsp.loja.service.PedidoService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/pedido/meus")
public class MeusPedidosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UsuarioLogado usuario = (UsuarioLogado) session.getAttribute("usuarioLogado");

		PedidoService service = new PedidoService();
		List<Pedido> pedidos = service.listarPorCliente(usuario.getId());

		request.setAttribute("pedidos", pedidos);
		ViewHelper.forward("pedido/meusPedidos.jsp", request, response);
	}
}
