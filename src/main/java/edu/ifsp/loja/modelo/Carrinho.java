package edu.ifsp.loja.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrinho implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ItemCarrinho> itens = new ArrayList<>();

	public void adicionar(int produtoId, String descricao, double preco, String foto, int quantidade) {
		// se o produto ja existe no carrinho, aumenta a quantidade
		for (ItemCarrinho item : itens) {
			if (item.getProdutoId() == produtoId) {
				item.setQuantidade(item.getQuantidade() + quantidade);
				return;
			}
		}
		itens.add(new ItemCarrinho(produtoId, descricao, preco, foto, quantidade));
	}

	public void remover(int produtoId) {
		itens.removeIf(item -> item.getProdutoId() == produtoId);
	}

	public void atualizar(int produtoId, int novaQuantidade) {
		for (ItemCarrinho item : itens) {
			if (item.getProdutoId() == produtoId) {
				if (novaQuantidade <= 0) {
					remover(produtoId);
				} else {
					item.setQuantidade(novaQuantidade);
				}
				return;
			}
		}
	}

	public void limpar() {
		itens.clear();
	}

	public List<ItemCarrinho> getItens() {
		return itens;
	}

	public boolean isEmpty() {
		return itens.isEmpty();
	}

	public double getTotal() {
		return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
	}

	public int getQuantidadeTotal() {
		return itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
	}
}
