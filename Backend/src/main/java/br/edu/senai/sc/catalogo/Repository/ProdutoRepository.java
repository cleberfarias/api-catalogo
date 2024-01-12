package br.edu.senai.sc.catalogo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.senai.sc.catalogo.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findProdutoByNomeContaining(String nome);

    // Método atualizado para usar a sintaxe correta do Spring Data JPA para consultas personalizadas
    List<Produto> findByPrecoGreaterThanOrEqual(Double preco);

    // Método atualizado para usar a sintaxe correta do Spring Data JPA para consultas personalizadas
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.preco >= 1000.0")
    Long countByPrecoGreaterThanOrEqual();
}