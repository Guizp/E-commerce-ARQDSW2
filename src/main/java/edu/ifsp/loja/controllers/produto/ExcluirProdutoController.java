package edu.ifsp.loja.controllers.produto;

import java.io.IOException;

import edu.ifsp.loja.persistencia.DataAccessException;
import edu.ifsp.loja.service.ProdutoService;
import edu.ifsp.loja.util.FileUploadUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produto/excluir")
public class ExcluirProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		ProdutoService service = new ProdutoService();

		try {
			String foto = service.excluir(id);
			String uploadDir = getServletContext().getRealPath("/uploads/produtos");
			FileUploadUtil.excluir(uploadDir, foto);
		} catch (DataAccessException e) {
			// produto vinculado a pedidos existentes — nao pode ser excluido
			response.sendRedirect(request.getContextPath()
					+ "/produto/buscar?erro=Produto+não+pode+ser+excluído+pois+está+vinculado+a+pedidos.");
			return;
		}

		response.sendRedirect(request.getContextPath() + "/produto/buscar");
	}
}
