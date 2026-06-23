package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.modelo.Cliente;

public class ClienteDAO {

	public Cliente findById(int id) {
		Cliente cliente = new Cliente();

		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, nome, email, ativo, senha, perfil FROM cliente WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				cliente = mapRow(rs);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return cliente;
	}

	/**
	 * Busca um cliente pelo e-mail (usado no login e na validacao de cadastro duplicado).
	 * Retorna null se nao encontrado.
	 */
	public Cliente findByEmail(String email) {
		Cliente cliente = null;

		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, nome, email, ativo, senha, perfil FROM cliente WHERE email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				cliente = mapRow(rs);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return cliente;
	}

	public List<Cliente> find(String nome, boolean ativo) {
		List<Cliente> resultado = new ArrayList<>();

		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, nome, email, ativo, senha, perfil FROM cliente WHERE nome LIKE ? AND ativo = ?");
			ps.setString(1, "%" + nome + "%");
			ps.setBoolean(2, ativo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				resultado.add(mapRow(rs));
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return resultado;
	}

	public int insert(Cliente cliente) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO cliente (nome, email, ativo, senha, perfil) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setBoolean(3, cliente.isAtivo());
			ps.setString(4, cliente.getSenha());
			ps.setString(5, cliente.getPerfil());
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

	public void update(Cliente cliente) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE cliente SET nome = ?, email = ?, ativo = ?, perfil = ? WHERE id = ?");

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setBoolean(3, cliente.isAtivo());
			ps.setString(4, cliente.getPerfil());
			ps.setInt(5, cliente.getId());
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	/**
	 * Atualiza apenas a senha (usado em "alterar senha", separado do update geral
	 * para nao sobrescrever a senha sempre que os dados cadastrais forem editados).
	 */
	public void updateSenha(int id, String novaSenhaHash) {
		try {

			Connection conn = DatabaseConnector.getConnection();

			PreparedStatement ps = conn.prepareStatement("UPDATE cliente SET senha = ? WHERE id = ?");
			ps.setString(1, novaSenhaHash);
			ps.setInt(2, id);
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

			PreparedStatement ps = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private Cliente mapRow(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("nome"));
		cliente.setEmail(rs.getString("email"));
		cliente.setAtivo(rs.getBoolean("ativo"));
		cliente.setSenha(rs.getString("senha"));
		cliente.setPerfil(rs.getString("perfil"));
		return cliente;
	}
}
