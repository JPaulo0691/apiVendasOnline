package com.github.myproject.vendas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEM_PEDIDO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ITEM_PEDIDO")
	private Integer id_item_pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido_id;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto_id;
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
}
