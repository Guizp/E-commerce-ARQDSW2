package edu.ifsp.loja.controllers.auth;

/**
 * Representa o usuario autenticado, guardado na sessao HTTP.
 * Mantido simples (sem referencia a entidades de persistencia) para nao
 * acoplar a camada de sessao/view ao modelo de dados.
 */
public class UsuarioLogado {
	private int id;
	private String nome;
	private String email;
	private String perfil;

	public UsuarioLogado(int id, String nome, String email, String perfil) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getPerfil() {
		return perfil;
	}

	public boolean isAdm() {
		return "adm".equals(perfil);
	}
}
