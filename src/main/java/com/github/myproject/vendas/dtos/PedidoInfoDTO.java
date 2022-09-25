package com.github.myproject.vendas.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoInfoDTO {
	
	private Integer id;
	private String cpf;
	private String nomeCliente;
	private String dataPedido;
	private String status;
	private BigDecimal total;
	private List<ItemPedidoInfoDTO> itemInfo;
	
}
