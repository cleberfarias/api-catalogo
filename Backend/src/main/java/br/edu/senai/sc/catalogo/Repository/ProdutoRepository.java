package br.edu.senai.sc.catalogo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.senai.sc.catalogo.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findProdutoByNomeContaining(String nome);

	@Query("SELECT p FROM Produto p WHERE p.preco >= 1000.0")
	List<Produto> listarProdutosPrecoMaiorOuIgual1000();

	@Query("SELECT COUNT(p) FROM Produto p WHERE p.preco >= 1000.0")
	Long contarProdutosPrecoMaiorOuIgual1000();
}
