package edu.ifsp.loja.modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<ItemCarrinho> itens = new ArrayList<>();

    public void adicionar(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == produto.getId()) { // ajuste o getId() se necessário
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, quantidade));
    }

    public void remover(int idProduto) {
        itens.removeIf(item -> item.getProduto().getId() == idProduto);
    }

    public List<ItemCarrinho> getItens() { return itens; }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }
    
    public void limpar() { itens.clear(); }
}