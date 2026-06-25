package edu.ifsp.loja.controllers.pedido;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.modelo.Pedido;
import edu.ifsp.loja.service.PedidoService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pedido/todos")
public class TodosPedidosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PedidoService service = new PedidoService();
		List<Pedido> pedidos = service.listarTodos();

		request.setAttribute("pedidos", pedidos);
		ViewHelper.forward("pedido/todosPedidos.jsp", request, response);
	}
}
