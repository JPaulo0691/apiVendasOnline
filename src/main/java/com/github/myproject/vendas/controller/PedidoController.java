package com.github.myproject.vendas.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.myproject.vendas.dtos.AtualizacaoStatusDTO;
import com.github.myproject.vendas.dtos.ItemPedidoInfoDTO;
import com.github.myproject.vendas.dtos.PedidoDTO;
import com.github.myproject.vendas.dtos.PedidoInfoDTO;
import com.github.myproject.vendas.enums.StatusPedido;
import com.github.myproject.vendas.model.ItemPedido;
import com.github.myproject.vendas.model.Pedido;
import com.github.myproject.vendas.service.PedidoService;

@RestController
@RequestMapping("/vendas/pedidos")
public class PedidoController {	
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping
	// tem que transformar o DTO pro modelo de dados 
	public ResponseEntity<String> cadastroPedido(@RequestBody PedidoDTO pedidoDTO){
		
		Pedido pedido = pedidoService.cadastrarPedido(pedidoDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Pedido de nr." + pedido.getId_pedido() + ",realizado.");
	}
	
	@GetMapping("{id}")
	public PedidoInfoDTO relatorio(@PathVariable Integer id) {
		return pedidoService.relatorioPedido(id)
			   .map(p -> converter(p))
			   .orElseThrow( () -> 
			   new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não Encontrado"));
				
	}
	
	private PedidoInfoDTO converter(Pedido pedido) {
		
		return 	PedidoInfoDTO.builder()
				.id(pedido.getId_pedido())
				.dataPedido(pedido.getData_pedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente_id().getCpf())
				.nomeCliente(pedido.getCliente_id().getNome())
				.status(pedido.getStatus().name())
				.total(pedido.getTotal())
				.itemInfo(converterItens(pedido.getItensPedido()))
				.build();
	}
	
	private List<ItemPedidoInfoDTO> converterItens(List<ItemPedido> itens){
		
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens.stream()
				.map(item -> 
					ItemPedidoInfoDTO.builder()
					.descricao(item.getProduto_id().getDescricao())
					.precoUnitario(item.getProduto_id().getPreco_unitario())
					.quantidade(item.getQuantidade())
					.build()
				).collect(Collectors.toList());
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Integer id,
			@RequestBody AtualizacaoStatusDTO atualizarDTO) {
		
		String novoStatus = atualizarDTO.getNovoStatus();
		pedidoService.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
		
	}

}
