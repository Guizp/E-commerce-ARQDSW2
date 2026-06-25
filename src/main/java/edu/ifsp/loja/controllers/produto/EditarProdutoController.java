package edu.ifsp.loja.controllers.produto;

import java.io.IOException;

import edu.ifsp.loja.service.ProdutoService;
import edu.ifsp.loja.util.FileUploadUtil;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/produto/editar")
@MultipartConfig
public class EditarProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		ProdutoService service = new ProdutoService();
		ProdutoForm form = service.findById(id);

		request.setAttribute("form", form);
		request.setAttribute("modo", "editar");
		ViewHelper.forward("produto/formProduto.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProdutoService service = new ProdutoService();

		int id = Integer.parseInt(request.getParameter("id"));
		ProdutoForm form = service.findById(id); // recupera os dados atuais, incluindo a foto

		form.setDescricao(request.getParameter("descricao"));
		form.setPreco(Double.parseDouble(request.getParameter("preco")));
		form.setEstoque(Integer.parseInt(request.getParameter("estoque")));

		String uploadDir = getServletContext().getRealPath("/uploads/produtos");
		Part fotoPart = request.getPart("foto");
		String novaFoto = FileUploadUtil.salvar(fotoPart, uploadDir);

		if (novaFoto != null) {
			// uma nova foto foi enviada: remove a antiga (se existir) e usa a nova
			FileUploadUtil.excluir(uploadDir, form.getFoto());
			form.setFoto(novaFoto);
		}
		// se nenhuma foto nova foi enviada, mantem a foto atual (form.getFoto())

		service.salvar(form);

		response.sendRedirect(request.getContextPath() + "/produto/buscar");
	}
}
