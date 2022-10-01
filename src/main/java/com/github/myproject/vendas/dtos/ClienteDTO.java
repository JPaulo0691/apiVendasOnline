package com.github.myproject.vendas.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
	
	@NotEmpty(message = "{nome.obrigatorio}")
	private String nome;
	
	@NotEmpty(message = "{cpf.obrigatorio}")
	@CPF
	private String cpf;
	
	@NotEmpty(message = "{email.obrigatorio}")
	@Email
	private String email;

}
