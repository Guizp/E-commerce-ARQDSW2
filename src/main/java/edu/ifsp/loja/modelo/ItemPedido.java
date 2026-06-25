package edu.ifsp.loja.modelo;

public class ItemPedido {
	private int id;
	private int pedidoId;
	private int produtoId;
	private String produtoDescricao;
	private double precoUnitario;
	private int quantidade;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getPedidoId() { return pedidoId; }
	public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

	public int getProdutoId() { return produtoId; }
	public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

	public String getProdutoDescricao() { return produtoDescricao; }
	public void setProdutoDescricao(String produtoDescricao) { this.produtoDescricao = produtoDescricao; }

	public double getPrecoUnitario() { return precoUnitario; }
	public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

	public int getQuantidade() { return quantidade; }
	public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

	public double getSubtotal() { return precoUnitario * quantidade; }
}
