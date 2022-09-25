package com.github.myproject.vendas.service;

import java.util.Optional;

import com.github.myproject.vendas.dtos.PedidoDTO;
import com.github.myproject.vendas.enums.StatusPedido;
import com.github.myproject.vendas.model.Pedido;

public interface PedidoService {
	
	Pedido cadastrarPedido(PedidoDTO pedidoDTO);
	
	Optional<Pedido> relatorioPedido(Integer id);
	
	void atualizarStatus(Integer id, StatusPedido status);

}
