package com.github.myproject.vendas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.myproject.vendas.dtos.ItemPedidoDTO;
import com.github.myproject.vendas.dtos.PedidoDTO;
import com.github.myproject.vendas.dtos.ProdutoDTO;
import com.github.myproject.vendas.enums.StatusPedido;
import com.github.myproject.vendas.exception.OrderNotFounException;
import com.github.myproject.vendas.exception.RegrasException;
import com.github.myproject.vendas.model.Cliente;
import com.github.myproject.vendas.model.ItemPedido;
import com.github.myproject.vendas.model.Pedido;
import com.github.myproject.vendas.model.Produto;
import com.github.myproject.vendas.repository.ClienteRepository;
import com.github.myproject.vendas.repository.ItemPedidoRepository;
import com.github.myproject.vendas.repository.PedidoRepository;
import com.github.myproject.vendas.repository.ProdutosRepository;

@Service
public class PedidoServiceImpl implements PedidoService{
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutosRepository produtosRepository;	
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Override
	@Transactional
	public Pedido cadastrarPedido(PedidoDTO pedidoDTO) {
		
		Integer idCliente = pedidoDTO.getCliente();
		//Optional<Produto> idProduto = produtosRepository.findById(produto.getId_produto());	
		
		Produto produto = new Produto();
		Optional<Produto> idProduto = produtosRepository.findById(5);
		
		ItemPedido item =  new ItemPedido();
		Optional<ItemPedido> itemQtd = itemPedidoRepository.findById(item.getId_item_pedido());				
				
		Cliente cliente = clienteRepository.findById(idCliente)
		.orElseThrow( () -> new RegrasException("Código de Cliente Inválido"));
		
		Pedido pedido = new Pedido();
		
		pedido.setTotal(calcular(idProduto.get().getPreco_unitario(), itemQtd.get().getQuantidade()));
		pedido.setData_pedido(LocalDate.now());
		pedido.setCliente_id(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itemPedido = converterItens(pedido, pedidoDTO.getItens());
		
		/*
		 * var produto = new Produto(); var item = new ItemPedido();
		 * 
		 * BeanUtils.copyProperties(produtoDTO, produto);
		 * BeanUtils.copyProperties(itemDTO, item);
		 */
		
		pedidoRepository.save(pedido);
		itemPedidoRepository.saveAll(itemPedido);
		
		pedido.setItensPedido(itemPedido);
		
		return pedido;
	}

	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		
		if(itens.isEmpty()) {
			throw new RegrasException("Só poderá realizar um pedido com itens na lista.");
		}
		
		return itens.stream()
					.map(pedidoDTO ->{
						
						Integer id = pedidoDTO.getProduto();
						
						Produto produto = produtosRepository.findById(id)
						.orElseThrow(() -> new RegrasException("Códido do Produto Inválido: " + id));
						
						ItemPedido itemPedido = new ItemPedido();
						itemPedido.setQuantidade(pedidoDTO.getQuantidade());
						itemPedido.setPedido_id(pedido);
						itemPedido.setProduto_id(produto);
						
						return itemPedido;
						
					}).collect(Collectors.toList());	
	}

	@Override
	public Optional<Pedido> relatorioPedido(Integer id) {
		
		return pedidoRepository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizarStatus(Integer id, StatusPedido status) {
		
		pedidoRepository.findById(id).
		map(pedido -> {
			pedido.setStatus(status);
			return pedidoRepository.save(pedido);
		}).orElseThrow(() -> new OrderNotFounException("Pedido Não Encontrado!"));
		
	}	
	
	private BigDecimal calcular(BigDecimal preco, Integer quantidade) {
		
		BigDecimal qtd =  BigDecimal.valueOf(quantidade);
		  
		BigDecimal total = preco.multiply(qtd);		  
		
		return total; 
	}
	 
	 
		
}
