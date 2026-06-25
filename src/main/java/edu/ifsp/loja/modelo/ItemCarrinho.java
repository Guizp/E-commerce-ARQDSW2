package edu.ifsp.loja.modelo;

public class ItemCarrinho {
	private int produtoId;
	private String descricao;
	private double preco;
	private String foto;
	private int quantidade;

	public ItemCarrinho(int produtoId, String descricao, double preco, String foto, int quantidade) {
		this.produtoId = produtoId;
		this.descricao = descricao;
		this.preco = preco;
		this.foto = foto;
		this.quantidade = quantidade;
	}

	public int getProdutoId() {
		return produtoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getPreco() {
		return preco;
	}

	public String getFoto() {
		return foto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getSubtotal() {
		return preco * quantidade;
	}
}
