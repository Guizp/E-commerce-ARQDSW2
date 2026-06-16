package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.modelo.Produto;

public class ProdutoDAO {
	public Produto findById(int id) {
		Produto produto = new Produto();
		
		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, descricao, preco, foto FROM produto WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setFoto(rs.getString("foto"));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		return produto;
	}

	public List<Produto> findAll() {
		List<Produto> produtos = new ArrayList<>();
		
		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			Statement stmt = conn.createStatement();
			String sql = "SELECT id, descricao, preco, foto FROM produto;";
			ResultSet rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getDouble("preco"));
				p.setFoto(rs.getString("foto"));
				produtos.add(p);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		
		return produtos;
	}
	
	public List<Produto> findPaged(
			int page, int pageSize, 
			String descricao, double precoMinimo, double precoMaximo) {

		List<Produto> produtos = new ArrayList<>();
		
		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, descricao, preco, foto "
					+ "FROM produto "
					+ "WHERE descricao LIKE ? "
					+ "	AND preco BETWEEN ? AND ? "
					+ "ORDER BY id "
					+ "LIMIT ?, ?;");
			
			ps.setString(1, "%" + descricao + "%");
			ps.setDouble(2, precoMinimo);
			ps.setDouble(3, precoMaximo);
			ps.setInt(4, (page - 1) * pageSize);
			ps.setInt(5, pageSize);
						
			ResultSet rs = ps.executeQuery();
		
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getDouble("preco"));
				p.setFoto(rs.getString("foto"));
				produtos.add(p);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		
		return produtos;
		
	}
	
	public int total(
			String descricao, double precoMinimo, double precoMaximo) {

		int total = 0;
		
		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(
					"SELECT count(*) "
					+ "FROM produto "
					+ "WHERE descricao LIKE ? "
					+ "	AND preco BETWEEN ? AND ? ");
			
			ps.setString(1, "%" + descricao + "%");
			ps.setDouble(2, precoMinimo);
			ps.setDouble(3, precoMaximo);
						
			ResultSet rs = ps.executeQuery();
			
			
			if (rs.next()) {
			    total = rs.getInt(1);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		
		
		return total;
		
	}

	public int insert(Produto produto) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO produto (descricao, preco, foto) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getPreco());
			ps.setString(3, produto.getFoto());
			ps.executeUpdate();

			int id = 0;
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			ps.close();
			conn.close();

			return id;

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(Produto produto) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE produto SET descricao = ?, preco = ?, foto = ? WHERE id = ?");

			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getPreco());
			ps.setString(3, produto.getFoto());
			ps.setInt(4, produto.getId());
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(int id) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement("DELETE FROM produto WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

}
