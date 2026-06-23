package edu.ifsp.loja.modelo;

public class ItemCarrinho {
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