package edu.ifsp.loja.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id;
	private int clienteId;
	private String clienteNome;
	private LocalDateTime dataPedido;
	private double total;
	private List<ItemPedido> itens = new ArrayList<>();

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getClienteId() { return clienteId; }
	public void setClienteId(int clienteId) { this.clienteId = clienteId; }

	public String getClienteNome() { return clienteNome; }
	public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

	public LocalDateTime getDataPedido() { return dataPedido; }
	public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }

	public List<ItemPedido> getItens() { return itens; }
	public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}
