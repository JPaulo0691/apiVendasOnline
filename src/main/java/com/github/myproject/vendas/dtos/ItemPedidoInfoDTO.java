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
public class ItemPedidoInfoDTO {
	
	private String descricao;
	private BigDecimal precoUnitario;
	private Integer quantidade;
}
