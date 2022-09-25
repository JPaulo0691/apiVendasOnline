package com.github.myproject.vendas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.myproject.vendas.model.Produto;
import com.github.myproject.vendas.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoService produtoService;
	
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> cadastrar(@RequestBody @Valid Produto produto) {
		
		produtoService.cadastrarProdutos(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com Sucesso!");
	}
	
	@GetMapping("/consultar")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> consultar(){
		
		if(produtoService.getAllItens().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dados não Encontrados!");
		}
		return produtoService.getAllItens();
	}
	
	@PutMapping("/atualizarprodutos/{id}")
	public ResponseEntity<String> atualizar(@PathVariable @Valid Integer id, @RequestBody Produto produto) {
		
		if(produtoService.getById(id).isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Dados não encontrados");
		}	
		    produtoService.atualizar(id, produto);			
			return ResponseEntity.status(HttpStatus.CREATED).body("Produto atualizado com sucesso");			
	}
	
	@DeleteMapping("/deletarproduto/{id}")
	public ResponseEntity<String> deletar(@PathVariable(name = "id")Integer id) {
		
		Optional<Produto> produtos = produtoService.getById(id);
		
		if(produtos.isPresent()) {
		   produtoService.deleteItem(produtos.get());			
		   return ResponseEntity.status(HttpStatus.OK).body("Dados excluídos com sucesso");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body("Dados não existem ou já foram deletados");
		}		
	}	

}
