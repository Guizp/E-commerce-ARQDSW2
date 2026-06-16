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
		List<ProdutoDTO> produtos = dao.findPaged(
				form.getPage(), form.getPageSize(), 
				form.getDescricao(), form.getPrecoMinimo(), form.getPrecoMaximo())
				.stream()
				.map(p -> new ProdutoDTO(p.getId(), p.getDescricao(), p.getPreco(), p.getFoto()))
				.toList();		
		return produtos;
	}
	
	public int searchTotal(BuscarProdutoForm form) {
		int total = dao.total(form.getDescricao(), form.getPrecoMinimo(), form.getPrecoMaximo());
				
		return total;
	}

	public ProdutoForm findById(int id) {
		Produto produto = dao.findById(id);

		ProdutoForm form = new ProdutoForm();
		form.setId(produto.getId());
		form.setDescricao(produto.getDescricao());
		form.setPreco(produto.getPreco());
		form.setFoto(produto.getFoto());

		return form;
	}

	public void salvar(ProdutoForm form) {
		Produto produto = new Produto();
		produto.setId(form.getId() != null ? form.getId() : 0);
		produto.setDescricao(form.getDescricao());
		produto.setPreco(form.getPreco());
		produto.setFoto(form.getFoto());

		if (form.getId() == null) {
			dao.insert(produto);
		} else {
			dao.update(produto);
		}
	}

	/**
	 * Remove o produto do banco e devolve o nome do arquivo de foto associado
	 * (para que o controller possa apagar o arquivo do disco, se houver).
	 */
	public String excluir(int id) {
		Produto produto = dao.findById(id);
		String foto = produto.getFoto();
		dao.delete(id);
		return foto;
	}

}
