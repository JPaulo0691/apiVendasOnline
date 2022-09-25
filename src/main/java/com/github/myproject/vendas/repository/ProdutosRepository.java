package com.github.myproject.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.myproject.vendas.model.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Integer>{

}
