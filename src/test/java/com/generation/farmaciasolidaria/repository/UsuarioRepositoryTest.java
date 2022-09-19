package com.generation.farmaciasolidaria.repository;

import com.generation.farmaciasolidaria.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {

        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "Joao da silva", "joao@gmail.com", "12345678", "http://img.com/joao20.jpg", "565456987561","Rua: Carlota, 22"));
        usuarioRepository.save(new Usuario(0L, "Maria da silva", "maria@gmail.com", "87654321", "http://img.com/maria50.jpg","569874123658","Avenida Amaro, 56"));
        usuarioRepository.save(new Usuario(0L, "Fatima da silva", "fatima@gmail.com", "45671238", "http://img.com/fatimam20.jpg","89456356984","Estrada mogi, 55"));
        usuarioRepository.save(new Usuario(0L, "Vitoria dos santos", "vivi@gmail.com", "45678123", "http://img.com/vicvi01.jpg","8965356897","Rua: iva, 01"));

    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario() {
        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@gmail.com");
        assertTrue(usuario.get().getUsuario().equals("joao@gmail.com"));
    }

    @Test
    @DisplayName("Retorna 3 usuarios")
    public void deveRetornarTresUsuarios() {

        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("Joao da silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Maria da silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("Fatima da silva"));
    }

    @AfterAll
    public void end() {
        usuarioRepository.deleteAll();
    }
}

