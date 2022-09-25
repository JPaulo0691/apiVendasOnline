package com.github.myproject.vendas.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.github.myproject.vendas.model.Cliente;
import com.github.myproject.vendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	@Query(value = "SELECT * FROM PEDIDO P "
					+ "INNER JOIN CLIENTES C "
					+ "ON P.ID = C.ID ",
					nativeQuery = true)
	@Transactional(readOnly = true)
	List<Pedido> findByCliente(Cliente cliente_id);
	
	@Query(value = "SELECT * FROM PEDIDO P "
			+ "LEFT JOIN ITEM_PEDIDO IP "
			+ "ON P.ID_PEDIDO = IP.ID_PEDIDO "
			+ "WHERE P.ID_PEDIDO = :id ",
			nativeQuery = true)
	Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);

}
