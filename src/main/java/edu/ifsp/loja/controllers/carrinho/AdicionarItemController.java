package edu.ifsp.loja.controllers.carrinho;

import java.io.IOException;

import edu.ifsp.loja.modelo.Carrinho;
import edu.ifsp.loja.modelo.Produto;
import edu.ifsp.loja.persistencia.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/carrinho/adicionar")
public class AdicionarItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int produtoId = Integer.parseInt(request.getParameter("produtoId"));
		int quantidade = 1;
		String paramQtd = request.getParameter("quantidade");
		if (paramQtd != null && !paramQtd.isBlank()) {
			quantidade = Integer.parseInt(paramQtd);
		}

		// busca os dados atuais do produto para guardar no carrinho
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(produtoId);

		HttpSession session = request.getSession();
		Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
		if (carrinho == null) {
			carrinho = new Carrinho();
			session.setAttribute("carrinho", carrinho);
		}

		carrinho.adicionar(produto.getId(), produto.getDescricao(), produto.getPreco(), produto.getFoto(), quantidade);

		response.sendRedirect(request.getContextPath() + "/carrinho");
	}
}
