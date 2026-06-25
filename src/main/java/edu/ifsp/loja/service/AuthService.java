package edu.ifsp.loja.service;

import edu.ifsp.loja.controllers.auth.CadastroForm;
import edu.ifsp.loja.controllers.auth.UsuarioLogado;
import edu.ifsp.loja.modelo.Cliente;
import edu.ifsp.loja.persistencia.ClienteDAO;
import edu.ifsp.loja.util.SenhaUtil;

public class AuthService {
	private ClienteDAO dao = new ClienteDAO();

	
	public UsuarioLogado autenticar(String email, String senha) {
		Cliente cliente = dao.findByEmail(email);

		if (cliente == null || !cliente.isAtivo()) {
			return null;
		}

		if (!SenhaUtil.confere(senha, cliente.getSenha())) {
			return null;
		}

		return new UsuarioLogado(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getPerfil());
	}

	/**
	 * Cadastra um novo cliente (perfil "cliente" por padrao). Lanca
	 * IllegalArgumentException se o e-mail ja estiver em uso.
	 */
	public void cadastrar(CadastroForm form) {
		if (dao.findByEmail(form.getEmail()) != null) {
			throw new IllegalArgumentException("Já existe uma conta cadastrada com este e-mail.");
		}

		Cliente cliente = new Cliente();
		cliente.setNome(form.getNome());
		cliente.setEmail(form.getEmail());
		cliente.setAtivo(true);
		cliente.setSenha(SenhaUtil.hash(form.getSenha()));
		cliente.setPerfil("cliente");

		dao.insert(cliente);
	}
}
