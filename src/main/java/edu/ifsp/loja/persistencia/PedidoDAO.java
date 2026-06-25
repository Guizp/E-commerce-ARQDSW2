package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.modelo.Carrinho;
import edu.ifsp.loja.modelo.ItemCarrinho;
import edu.ifsp.loja.modelo.ItemPedido;
import edu.ifsp.loja.modelo.Pedido;

public class PedidoDAO {

	/**
	 * Cria o pedido, seus itens e da baixa no estoque de cada produto em uma
	 * unica transacao JDBC. Se qualquer operacao falhar, tudo e revertido
	 * (ROLLBACK) e uma DataAccessException e lancada.
	 */
	public int inserirComTransacao(int clienteId, Carrinho carrinho) {
		Connection conn = null;
		try {
			conn = DatabaseConnector.getConnection();
			conn.setAutoCommit(false); // inicia a transacao

			// 1. Inserir o pedido
			double total = carrinho.getTotal();
			PreparedStatement psPedido = conn.prepareStatement(
					"INSERT INTO pedido (cliente_id, dt_criacao, total) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			psPedido.setInt(1, clienteId);
			psPedido.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			psPedido.setDouble(3, total);
			psPedido.executeUpdate();

			int pedidoId = 0;
			ResultSet rs = psPedido.getGeneratedKeys();
			if (rs.next()) {
				pedidoId = rs.getInt(1);
			}
			rs.close();
			psPedido.close();

			// 2. Inserir os itens e dar baixa no estoque
			for (ItemCarrinho item : carrinho.getItens()) {

				// 2a. Verificar estoque disponivel
				PreparedStatement psEstoque = conn.prepareStatement(
						"SELECT estoque FROM produto WHERE id = ?");
				psEstoque.setInt(1, item.getProdutoId());
				ResultSet rsEstoque = psEstoque.executeQuery();

				if (!rsEstoque.next()) {
					throw new SQLException("Produto não encontrado: " + item.getProdutoId());
				}
				int estoqueAtual = rsEstoque.getInt("estoque");
				rsEstoque.close();
				psEstoque.close();

				System.out.println("DEBUG checkout - produto: " + item.getProdutoId() 
					+ " estoque: " + estoqueAtual 
					+ " solicitado: " + item.getQuantidade());

				if (estoqueAtual < item.getQuantidade()) {
					throw new IllegalStateException(
						"Estoque insuficiente para o produto: " + item.getDescricao()
						+ " (disponível: " + estoqueAtual + ", solicitado: " + item.getQuantidade() + ")");
				}

				// 2b. Inserir item do pedido
				PreparedStatement psItem = conn.prepareStatement(
						"INSERT INTO pedido_item (pedido_id, produto_id, preco_unitario, quantidade) VALUES (?, ?, ?, ?)");
				psItem.setInt(1, pedidoId);
				psItem.setInt(2, item.getProdutoId());
				psItem.setDouble(3, item.getPreco());
				psItem.setInt(4, item.getQuantidade());
				psItem.executeUpdate();
				psItem.close();

				// 2c. Baixar estoque
				PreparedStatement psUpdate = conn.prepareStatement(
						"UPDATE produto SET estoque = estoque - ? WHERE id = ?");
				psUpdate.setInt(1, item.getQuantidade());
				psUpdate.setInt(2, item.getProdutoId());
				psUpdate.executeUpdate();
				psUpdate.close();
			}

			conn.commit(); // tudo certo: confirma a transacao
			conn.close();
			return pedidoId;

		} catch (IllegalStateException e) {
			// estoque insuficiente: rollback e relanca para o service tratar
			rollback(conn);
			throw new DataAccessException(e);
		} catch (SQLException e) {
			rollback(conn);
			throw new DataAccessException(e);
		}
	}

	public List<Pedido> findByCliente(int clienteId) {
		List<Pedido> pedidos = new ArrayList<>();

		try {
			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT p.id, p.cliente_id, c.nome AS cliente_nome, p.dt_criacao, p.total "
					+ "FROM pedido p "
					+ "JOIN cliente c ON c.id = p.cliente_id "
					+ "WHERE p.cliente_id = ? "
					+ "ORDER BY p.dt_criacao DESC");
			ps.setInt(1, clienteId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Pedido pedido = mapRow(rs);
				pedidos.add(pedido);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return pedidos;
	}

	public List<Pedido> findAll() {
		List<Pedido> pedidos = new ArrayList<>();

		try {
			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT p.id, p.cliente_id, c.nome AS cliente_nome, p.dt_criacao, p.total "
					+ "FROM pedido p "
					+ "JOIN cliente c ON c.id = p.cliente_id "
					+ "ORDER BY p.dt_criacao DESC");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pedidos.add(mapRow(rs));
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return pedidos;
	}

	public List<ItemPedido> findItensByPedido(int pedidoId) {
		List<ItemPedido> itens = new ArrayList<>();

		try {
			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT pi.id, pi.pedido_id, pi.produto_id, pr.descricao AS produto_descricao, "
					+ "pi.preco_unitario, pi.quantidade "
					+ "FROM pedido_item pi "
					+ "JOIN produto pr ON pr.id = pi.produto_id "
					+ "WHERE pi.pedido_id = ?");
			ps.setInt(1, pedidoId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ItemPedido item = new ItemPedido();
				item.setId(rs.getInt("id"));
				item.setPedidoId(rs.getInt("pedido_id"));
				item.setProdutoId(rs.getInt("produto_id"));
				item.setProdutoDescricao(rs.getString("produto_descricao"));
				item.setPrecoUnitario(rs.getDouble("preco_unitario"));
				item.setQuantidade(rs.getInt("quantidade"));
				itens.add(item);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return itens;
	}

	private Pedido mapRow(ResultSet rs) throws SQLException {
		Pedido pedido = new Pedido();
		pedido.setId(rs.getInt("id"));
		pedido.setClienteId(rs.getInt("cliente_id"));
		pedido.setClienteNome(rs.getString("cliente_nome"));
		pedido.setDataPedido(rs.getTimestamp("dt_criacao").toLocalDateTime());
		pedido.setTotal(rs.getDouble("total"));
		return pedido;
	}

	private void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException ex) {
				// ignora erro no rollback
			}
		}
	}
}
