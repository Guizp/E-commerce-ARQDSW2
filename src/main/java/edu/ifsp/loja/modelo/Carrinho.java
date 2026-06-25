package edu.ifsp.loja.modelo;

<<<<<<< HEAD
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
=======
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
>>>>>>> a3c7475e32dd79761ab884c1d4971bb1fb28e054
