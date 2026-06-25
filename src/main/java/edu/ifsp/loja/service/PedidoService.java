package edu.ifsp.loja.service;

import java.util.List;

import edu.ifsp.loja.modelo.Carrinho;
import edu.ifsp.loja.modelo.ItemPedido;
import edu.ifsp.loja.modelo.Pedido;
import edu.ifsp.loja.persistencia.PedidoDAO;

public class PedidoService {
	private PedidoDAO dao = new PedidoDAO();

	/**
	 * Finaliza o pedido: cria pedido + itens + baixa estoque em transacao unica.
	 * Retorna o ID do pedido criado.
	 */
	public int finalizar(int clienteId, Carrinho carrinho) {
		if (carrinho == null || carrinho.isEmpty()) {
			throw new IllegalArgumentException("O carrinho está vazio.");
		}
		return dao.inserirComTransacao(clienteId, carrinho);
	}

	public List<Pedido> listarPorCliente(int clienteId) {
		return dao.findByCliente(clienteId);
	}

	public List<Pedido> listarTodos() {
		return dao.findAll();
	}

	public List<ItemPedido> listarItens(int pedidoId) {
		return dao.findItensByPedido(pedidoId);
	}
}
