package edu.ifsp.loja.controllers.produto;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.service.ProdutoService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produto/catalogo")
public class CatalogoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramDescricao = request.getParameter("descricao");
		String paramPage = request.getParameter("page");
		String paramPageSize = request.getParameter("pageSize");

		BuscarProdutoForm form = new BuscarProdutoForm();
		if (paramDescricao != null) {
			form.setDescricao(paramDescricao);
		}
		if (paramPage != null && !paramPage.isBlank()) {
			form.setPage(Integer.parseInt(paramPage));
		}
		if (paramPageSize != null && !paramPageSize.isBlank()) {
			form.setPageSize(Integer.parseInt(paramPageSize));
		}

		ProdutoService service = new ProdutoService();
		List<ProdutoDTO> produtos = service.search(form);
		int total = service.searchTotal(form);

		request.setAttribute("produtos", produtos);
		request.setAttribute("total", total);
		request.setAttribute("form", form);
		ViewHelper.forward("produto/catalogo.jsp", request, response);
	}
}
