package com.github.myproject.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.myproject.vendas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	boolean existsByEmail(String email);
	boolean existsByCpf(String CPF);

	@Query(value = "SELECT * FROM CLIENTES WHERE NOME LIKE %:NOME%", nativeQuery = true) 
	List<Cliente> encontrarPorNome(@Param("NOME") String nome);	 
	
	@Query(value = "SELECT * "
			+ "FROM CLIENTES C ORDER BY C.NOME", nativeQuery = true)
	List<Cliente> ordenarPorNome();
	
	/*
	 * @Query(value = "SELECT * " + "FROM CLIENTES C " + "LEFT JOIN FETCH " +
	 * "C.PEDIDOS WHERE C.ID = :ID") Cliente
	 * buscarClientePorPedido(@Param("ID")Integer id);
	 */
	
}
