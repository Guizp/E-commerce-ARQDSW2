package edu.ifsp.loja.controllers.pedido;

import java.io.IOException;

import edu.ifsp.loja.controllers.auth.UsuarioLogado;
import edu.ifsp.loja.modelo.Carrinho;
import edu.ifsp.loja.persistencia.DataAccessException;
import edu.ifsp.loja.service.PedidoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/pedido/checkout")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UsuarioLogado usuario = (UsuarioLogado) session.getAttribute("usuarioLogado");
		Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

		if (carrinho == null || carrinho.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/carrinho");
			return;
		}

		PedidoService service = new PedidoService();

		try {
			int pedidoId = service.finalizar(usuario.getId(), carrinho);

			// limpa o carrinho da sessao apos o pedido ser confirmado
			session.removeAttribute("carrinho");

			response.sendRedirect(request.getContextPath() + "/pedido/meus?sucesso=" + pedidoId);

		} catch (DataAccessException e) {
			// estoque insuficiente ou erro de banco: volta para o carrinho com mensagem
			request.setAttribute("carrinho", carrinho);
			request.setAttribute("erro", "Não foi possível finalizar o pedido. Verifique o estoque dos produtos.");
			request.getRequestDispatcher("/WEB-INF/pages/carrinho/carrinho.jsp").forward(request, response);
		}
	}
}
