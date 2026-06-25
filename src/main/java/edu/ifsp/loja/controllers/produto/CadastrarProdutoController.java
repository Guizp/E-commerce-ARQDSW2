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

@WebServlet("/produto/cadastrar")
@MultipartConfig
public class CadastrarProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("form", new ProdutoForm());
		request.setAttribute("modo", "cadastrar");
		ViewHelper.forward("produto/formProduto.jsp", request, response);
	}

	@Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProdutoForm form = new ProdutoForm();
            form.setDescricao(request.getParameter("descricao"));

            String precoStr = request.getParameter("preco").replace(",", ".");
            form.setPreco(Double.parseDouble(precoStr));

            String estoqueStr = request.getParameter("estoque");
            form.setEstoque(estoqueStr != null && !estoqueStr.isBlank() ? Integer.parseInt(estoqueStr) : 0);

            Part fotoPart = request.getPart("foto");
            String uploadDir = getServletContext().getRealPath("/uploads/produtos");
            String nomeArquivo = FileUploadUtil.salvar(fotoPart, uploadDir);
            form.setFoto(nomeArquivo);

            ProdutoService service = new ProdutoService();
            service.salvar(form);
            response.sendRedirect(request.getContextPath() + "/produto/buscar");
    }
}
