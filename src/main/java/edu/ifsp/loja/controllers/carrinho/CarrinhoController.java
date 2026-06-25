package edu.ifsp.loja.controllers.carrinho;

import java.io.IOException;

import edu.ifsp.loja.modelo.Carrinho;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/carrinho")
public class CarrinhoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

		if (carrinho == null) {
			carrinho = new Carrinho();
			session.setAttribute("carrinho", carrinho);
		}

		request.setAttribute("carrinho", carrinho);
		ViewHelper.forward("carrinho/carrinho.jsp", request, response);
	}
}
