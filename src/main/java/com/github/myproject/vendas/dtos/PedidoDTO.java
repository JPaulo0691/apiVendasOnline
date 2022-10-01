package com.github.myproject.vendas.dtos;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.github.myproject.vendas.customAnotations.NotEmptyList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	
	@NotNull(message = "{codigo-cliente.obrigatorio}")
	private Integer cliente;
	@NotEmptyList(message = "{itens-pedido.obrigatorio}")
	private List<ItemPedidoDTO> itens;

}
