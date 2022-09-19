package com.generation.farmaciasolidaria.repository;

import com.generation.farmaciasolidaria.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoRepositoryTest {


    @Autowired
    private ProdutoRepository produtoRepository;


    @BeforeAll
    public void setup() {
        Produto p1 = new Produto();
        p1.setId(1L);
        p1.setNome("Pasta de dente");
        p1.setDescricao("Item de higiene pessoal");
        p1.setFabricante("xyz");
        p1.setQuantidade(123);
        p1.setPreco(12.00);

        Produto p2 = new Produto();
        p2.setId(2L);
        p2.setNome("Sabonete");
        p2.setDescricao("Item de higiene pessoal");
        p2.setFabricante("xyz");
        p2.setQuantidade(12);
        p2.setPreco(3.00);

        Produto p3 = new Produto();
        p3.setId(3L);
        p3.setNome("Batom");
        p3.setDescricao("Item de beleza");
        p3.setFabricante("xyz");
        p3.setQuantidade(12);
        p3.setPreco(12.00);

        produtoRepository.save(p1);
        produtoRepository.save(p2);
        produtoRepository.save(p3);


    }

    @Test
    public void findAllByDescricaoContainingIgnoreCaseTest() {
        List<Produto> produtos = produtoRepository.findAllByDescricaoContainingIgnoreCase("higiene pessoal");
        Assertions.assertTrue(produtos.size() == 2);

        List<Produto> produtos2 = produtoRepository.findAllByDescricaoContainingIgnoreCase("saude");
        Assertions.assertTrue(produtos2.size() == 0);

    }


}
