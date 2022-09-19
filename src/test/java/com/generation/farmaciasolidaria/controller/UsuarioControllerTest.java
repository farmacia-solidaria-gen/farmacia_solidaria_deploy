package com.generation.farmaciasolidaria.controller;

import com.generation.farmaciasolidaria.model.Usuario;
import com.generation.farmaciasolidaria.repository.UsuarioRepository;
import com.generation.farmaciasolidaria.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario( new Usuario(0L,"root", "root@root.com", "rootroot", " ", "123456789123","rua: root, 82"));
    }

    @Test
    @DisplayName("Cadastrar Um usuário")
    public void deveCriarUmUsuario() {

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
                new Usuario(0L,
                        "Vitoria dos santos",
                        "vivi@gmail.com",
                        "45678123",
                        "http://img.com/vicvi01.jpg",
                        "563248965321",
                        "Rua: finlay, 13"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuario/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        usuarioService.cadastrarUsuario(
                new Usuario(0L,
                        "Maria da silva",
                        "maria@gmail.com",
                        "87654321",
                        "http://img.com/maria50.jpg",
                        "568974256813",
                        "Rua: Jose Alves, 02"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
                new Usuario(0L,
                        "Maria da silva",
                        "maria@gmail.com",
                        "87654321",
                        "http://img.com/maria50.jpg",
                        "457896542312",
                        "Rua Machado Assis, 55"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuario/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<Usuario> usuariocadastrado = usuarioService.cadastrarUsuario(
                new Usuario(0L,
                        "Juliana Damaceno",
                        "ju_maceno@email.com",
                        "juliana321",
                        "https://ju.img.com/hkhn.jpg",
                        "123459875612",
                        "rua: Maria Lucia, 42"));

        Usuario usuarioUpdate = new Usuario(usuariocadastrado.get().getId(),
                "Juliana Damaceno",
                "juliana_dama@email.com",
                "juliana321",
                "https://ju.img.com/hkhn.jpg",
                "58745695245",
                "Rua: Lucas Figueiredo, 01");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuario/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

    }

    @Test
    @DisplayName("Listar todos usuarios")
    public void deveMostrarTodosUsuarios() {

        usuarioService.cadastrarUsuario(
                new Usuario(0L,
                        "Luisa Soares",
                        "lu_soares@email.com",
                        "lulu123",
                        "https://i.img.com/lulu90s.jpg",
                        "569636987546",
                        "Rua: aguia de aia, 58"));
        usuarioService.cadastrarUsuario(
                new Usuario(0L,
                        "Ricardo Marques",
                        "rick@email.com",
                        "rick123",
                        "https://rick.img/dsff.jpg",
                        "125425463987",
                        "Avenida Radial: 1001"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuario/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }


}


