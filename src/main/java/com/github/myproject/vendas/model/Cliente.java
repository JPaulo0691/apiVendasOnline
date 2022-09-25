package com.github.myproject.vendas.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	@NotEmpty(message = "{nome.obrigatorio}")
	private String nome;
	
	@Column(nullable = false, unique = true, length = 11)
	@NotEmpty(message = "{cpf.obrigatorio}")
	@CPF
	private String cpf;
	
	@Column(nullable = false, unique = true, length = 50)
	@NotEmpty(message = "{email.obrigatorio}")
	@Email
	private String email;	
	
	// um cliente para muitos pedidos(OneToMany), quando uso o FetchType.EAGER, ele traz todas as relações 
	// nesse caso todos os pedido do cliente, deixando a consulta pesada, portanto não é recomendado.
	
	@JsonIgnore // ele vai dizer para o parser que ele deve ignorar esta propriedade
	@OneToMany(mappedBy = "cliente_id", fetch = FetchType.LAZY) 
	private Set<Pedido> pedidos;	
	
}
