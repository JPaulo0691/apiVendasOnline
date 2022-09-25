package com.github.myproject.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.myproject.vendas.model.Cliente;
import com.github.myproject.vendas.service.ClientesService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vendas")
public class ClienteController {
	
	@Autowired
	ClientesService clientesService;
	
	@PostMapping("/cadastro")
	public ResponseEntity<Object> cadastrarCliente(@RequestBody @Validated Cliente cliente){
		
		if(clientesService.validarEmail(cliente.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um cliente com este email!");
		}
		
		clientesService.cadastrar(cliente);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com Sucesso!");
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> consultarLista(){			
		return ResponseEntity.status(HttpStatus.OK).body(clientesService.consultar());
	}
	
	@GetMapping("/listaordenada")
	public ResponseEntity<List<Cliente>> ordenarLista(){			
		return ResponseEntity.status(HttpStatus.OK).body(clientesService.ordenarNomeAsc());
	}	
	
	@GetMapping("/consultarnome/{nome}")
	public ResponseEntity<List<Cliente>> consultarClienteNome(@PathVariable(value = "nome") String nome){
		return ResponseEntity.status(HttpStatus.OK).body(clientesService.consultarPorNome(nome));		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cliente>> consultarPorId(@PathVariable(value = "id") Integer id){
		
		return ResponseEntity.status(HttpStatus.OK).body(clientesService.buscarPorId(id));
	}
	
	@PutMapping("/atualizarcadastro/{id}")
	public ResponseEntity<Object> atualizarCadastro(@PathVariable(value = "id")Integer id,
			@RequestBody Cliente cliente){
		
		ResponseEntity.status(HttpStatus.CREATED).body(clientesService.atualizar(id,cliente));
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Cadastrado atualizado com Sucesso");
	}
	
	@DeleteMapping("/deletarcadastro/{id}")
	public ResponseEntity<Object> deletarCadastro(@PathVariable(value = "id") Integer id){
		
		Optional<Cliente> deletarCliente = clientesService.buscarPorId(id);
		
		if(!deletarCliente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não existe ou já foi excluído");
		}
		
		clientesService.deletar(deletarCliente.get());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cadastro excluído com Sucesso");
	}

}
