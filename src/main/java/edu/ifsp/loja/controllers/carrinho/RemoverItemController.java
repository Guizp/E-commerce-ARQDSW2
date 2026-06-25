package edu.ifsp.loja.controllers.carrinho;

import java.io.IOException;

import edu.ifsp.loja.modelo.Carrinho;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/carrinho/remover")
public class RemoverItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int produtoId = Integer.parseInt(request.getParameter("produtoId"));

		HttpSession session = request.getSession(false);
		if (session != null) {
			Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
			if (carrinho != null) {
				carrinho.remover(produtoId);
			}
		}

		response.sendRedirect(request.getContextPath() + "/carrinho");
	}
}
