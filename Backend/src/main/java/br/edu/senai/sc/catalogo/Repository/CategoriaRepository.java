package br.edu.senai.sc.catalogo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.senai.sc.catalogo.entities.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findCategoriaByNomeContaining(String nome);

    // Método atualizado para usar a sintaxe correta do Spring Data JPA para consultas personalizadas
    List<Categoria> findByNomeContaining(String nome);