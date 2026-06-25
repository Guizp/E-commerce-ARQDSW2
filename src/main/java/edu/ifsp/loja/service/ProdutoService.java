package edu.ifsp.loja.service;

import java.util.List;

import edu.ifsp.loja.controllers.produto.BuscarProdutoForm;
import edu.ifsp.loja.controllers.produto.ProdutoDTO;
import edu.ifsp.loja.controllers.produto.ProdutoForm;
import edu.ifsp.loja.modelo.Produto;
import edu.ifsp.loja.persistencia.ProdutoDAO;

public class ProdutoService {
	private ProdutoDAO dao = new ProdutoDAO();

	public List<ProdutoDTO> search(BuscarProdutoForm form) {
		return dao.findPaged(form.getPage(), form.getPageSize(),
				form.getDescricao(), form.getPrecoMinimo(), form.getPrecoMaximo())
				.stream()
				.map(p -> new ProdutoDTO(p.getId(), p.getDescricao(), p.getPreco(), p.getFoto(), p.getEstoque()))
				.toList();
	}

	public int searchTotal(BuscarProdutoForm form) {
		return dao.total(form.getDescricao(), form.getPrecoMinimo(), form.getPrecoMaximo());
	}

	public ProdutoForm findById(int id) {
		Produto produto = dao.findById(id);
		ProdutoForm form = new ProdutoForm();
		form.setId(produto.getId());
		form.setDescricao(produto.getDescricao());
		form.setPreco(produto.getPreco());
		form.setFoto(produto.getFoto());
		form.setEstoque(produto.getEstoque());
		return form;
	}

	public void salvar(ProdutoForm form) {
		Produto produto = new Produto();
		produto.setId(form.getId() != null ? form.getId() : 0);
		produto.setDescricao(form.getDescricao());
		produto.setPreco(form.getPreco());
		produto.setFoto(form.getFoto());
		produto.setEstoque(form.getEstoque());

		if (form.getId() == null) {
			dao.insert(produto);
		} else {
			dao.update(produto);
		}
	}

	public String excluir(int id) {
		Produto produto = dao.findById(id);
		String foto = produto.getFoto();
		dao.delete(id);
		return foto;
	}
}
