package com.github.myproject.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.myproject.vendas.enums.StatusPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PEDIDO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Integer id_pedido;	
	
	@ManyToOne(fetch = FetchType.LAZY) // muitos pedidos para um cliente
	@JoinColumn(name = "id")
	private Cliente cliente_id;
	
	@Column(name = "data_pedido")
	private LocalDate data_pedido;
	
	@Column(name = "total", length = 20, precision = 20, scale = 2)
	private BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private StatusPedido status;
	
	@OneToMany(mappedBy = "pedido_id")
	private List<ItemPedido> itensPedido;

	
}
