package com.generation.farmaciasolidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.farmaciasolidaria.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> {

	public List <Produto>findAllByDescricaoContainingIgnoreCase(@Param("descricao")String descricao);

	
	
}
