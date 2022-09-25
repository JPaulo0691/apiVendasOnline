package com.github.myproject.vendas.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.myproject.vendas.model.Cliente;
import com.github.myproject.vendas.repository.ClienteRepository;

@Service
public class ClientesService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente cadastrar(Cliente cliente) {		
		return clienteRepository.save(cliente);		
	}
	
	public List<Cliente> consultar(){
		
		return clienteRepository.findAll();
	}
	
	public Optional<Cliente> buscarPorId(Integer id){
		return clienteRepository.findById(id);
	}	
	
	public boolean validarEmail(String email) {
		return clienteRepository.existsByEmail(email);
	}
	
	public boolean existsCadastro(String cpf) {
		return clienteRepository.existsByCpf(cpf);
	}
	
	public List<Cliente> consultarPorNome(String nome){
		return clienteRepository.encontrarPorNome(nome);
	}
	
	public List<Cliente> ordenarNomeAsc(){
		return clienteRepository.ordenarPorNome();
	}
	
	 @Transactional
	 public Cliente atualizar(Integer id,Cliente cliente){
	  
	 Optional<Cliente> clientesOptional = clienteRepository.findById(id);
	  
	 var clientes = clientesOptional.get();
	  
	 clientes.setNome(cliente.getNome());
	 clientes.setEmail(cliente.getEmail());
	  
	  return clienteRepository.save(clientes); 
	  
	 }
	 
	 @Transactional
	 public void deletar(Cliente cliente) {		 
		 
		 clienteRepository.delete(cliente);
	 }
	 

}
