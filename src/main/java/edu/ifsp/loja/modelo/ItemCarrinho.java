package edu.ifsp.loja.modelo;

public class ItemCarrinho {
<<<<<<< HEAD
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
=======
    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    
    public double getSubtotal() {
        // Ajuste o método de preço conforme sua classe Produto (ex: getPreco())
        return this.produto.getPreco() * this.quantidade; 
    }
}
>>>>>>> a3c7475e32dd79761ab884c1d4971bb1fb28e054
