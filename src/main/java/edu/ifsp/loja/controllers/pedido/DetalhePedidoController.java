package edu.ifsp.loja.controllers.pedido;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.modelo.ItemPedido;
import edu.ifsp.loja.service.PedidoService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pedido/detalhe")
public class DetalhePedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pedidoId = Integer.parseInt(request.getParameter("id"));

		PedidoService service = new PedidoService();
		List<ItemPedido> itens = service.listarItens(pedidoId);

		request.setAttribute("itens", itens);
		request.setAttribute("pedidoId", pedidoId);
		ViewHelper.forward("pedido/detalhePedido.jsp", request, response);
	}
}
