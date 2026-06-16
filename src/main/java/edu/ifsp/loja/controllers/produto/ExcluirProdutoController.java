package edu.ifsp.loja.controllers.produto;

import java.io.IOException;

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
		String foto = service.excluir(id);

		String uploadDir = getServletContext().getRealPath("/uploads/produtos");
		FileUploadUtil.excluir(uploadDir, foto);

		response.sendRedirect(request.getContextPath() + "/produto/buscar");
	}
}
