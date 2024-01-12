package br.edu.senai.sc.catalogo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.senai.sc.catalogo.Service.CategoriaService;
import br.edu.senai.sc.catalogo.entities.Categoria;
import br.edu.senai.sc.catalogo.entities.Produto;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

	private final CategoriaService categoriaService;

	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	// Lista Categoria
	@ApiOperation(value = "Listar todas as categorias")
	@GetMapping("/listarTodas")
	public ResponseEntity<List<Categoria>> listarTodasCategorias() {
		try {
			List<Categoria> categorias = categoriaService.buscarCategorias();
			return new ResponseEntity<>(categorias, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	// Contagem de Categorias
    @ApiOperation(value = "Contar o número de categorias")
    @GetMapping("/contarCategorias")
    public ResponseEntity<Long> contarCategorias() {
        try {
            // Funcionalidade:
            // Utiliza o método contarCategorias do categoriaService para obter o número total de categorias.
            Long count = categoriaService.contarCategorias();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception exception) {
            // Se ocorrer uma exceção, retorna 0 com status BAD_REQUEST.
            return new ResponseEntity<>(0L, HttpStatus.BAD_REQUEST);
        }
    }

	@ApiOperation(value = "Cadastrar categoria")
	@PostMapping
	public ResponseEntity<String> cadastrarCategoria(@RequestBody Categoria categoria) {
		try {
			categoriaService.salvarCategoria(categoria);
		} catch (Exception exception) {
			return new ResponseEntity<>("Erro ao cadastrar a Categoria", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Categoria cadastrada com sucesso!", HttpStatus.CREATED);
	}

	@ApiOperation(value = "Buscar todas as categoria")
	@GetMapping
	public ResponseEntity<List<Categoria>> buscarCategorias() {
		try {
			List<Categoria> categoria = categoriaService.buscarCategorias();
			return new ResponseEntity<>(categoria, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Buscar categoria por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarCategoria(@PathVariable("codigo") Long codigo) {
		try {
			Optional<Categoria> categoria = categoriaService.buscarCategoriaPorCodigo(codigo);
			if (Optional.ofNullable(categoria).isPresent()) {
				return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
			}
		} catch (Exception exception) {
			return new ResponseEntity<>(new Categoria(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new Categoria(), HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Buscar categoria por nome")
	@GetMapping("/nome")
	public ResponseEntity<List<Categoria>> buscarCategoria(@RequestParam("nome") String nome) {
		try {
			List<Categoria> categorias = categoriaService.buscarCategoriaPorNome(nome);
			return new ResponseEntity<>(categorias, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Apagar categoria")
	@DeleteMapping("/{codigo}")
	public ResponseEntity<String> excluirCategoria(@PathVariable("codigo") Long codigo) {
		try {
			categoriaService.excluirCategoria(codigo);
		} catch (Exception exception) {
			return new ResponseEntity<>("Erro ao excluir a Categoria", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Categoria excluida com sucesso", HttpStatus.OK);
	}

	@ApiOperation(value = "Alterar categoria")
	@PutMapping
	public ResponseEntity<String> alterarCategoria(@RequestBody Categoria categoria) {
		try {
			categoriaService.salvarCategoria(categoria);
		} catch (Exception exception) {
			return new ResponseEntity<>("Erro ao atualizar Categoria!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Categoria alterada com sucesso!", HttpStatus.OK);
	}

	@ApiOperation(value = "Alterar o nome da categoria")
	@PatchMapping("/{codigo}")
	public ResponseEntity<String> alterarNome(@RequestParam("nome") String nome, @PathVariable("codigo") Long codigo) {
		try {
			categoriaService.alterarNome(nome, codigo);
		} catch (Exception exception) {
			return new ResponseEntity<>("Erro ao alterar a Categoria!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Categoria alterada com sucesso!", HttpStatus.OK);
	}

	@ApiOperation(value = "Criar categoria Padaria e cadastrar produtos")
    @PostMapping("/criarCategoriaPadaria")
    public ResponseEntity<String> criarCategoriaPadaria() {
        try {
            // Funcionalidade:
            // Cria uma nova categoria "Padaria" e a salva utilizando o método salvarCategoria do categoriaService.
            Categoria padaria = new Categoria();
            padaria.setNome("Padaria");
            categoriaService.salvarCategoria(padaria);

            // Cria dois novos produtos associados à categoria "Padaria" e os salva utilizando o método salvarProduto do produtoService.
            Produto p1 = new Produto(null, "Pão Francês", "Pão tradicional francês", 2.5, 100L, padaria);
            Produto p2 = new Produto(null, "Bolo de Chocolate", "Bolo delicioso de chocolate", 15.0, 50L, padaria);

            produtoService.salvarProduto(p1);
            produtoService.salvarProduto(p2);

            // Se bem-sucedido, retorna uma mensagem de sucesso com status CREATED.
            return new ResponseEntity<>("Categoria Padaria criada e produtos cadastrados com sucesso!",
                    HttpStatus.CREATED);
        } catch (Exception exception) {
            // Se ocorrer uma exceção, retorna uma mensagem de erro com status BAD_REQUEST.
            return new ResponseEntity<>("Erro ao criar categoria Padaria e cadastrar produtos.",
                    HttpStatus.BAD_REQUEST);
        }
    }

	@ApiOperation(value = "Listar produtos da categoria Padaria")
	@GetMapping("/produtosCategoriaPadaria")
	public ResponseEntity<List<Produto>> listarProdutosCategoriaPadaria() {
		try {
			List<Produto> produtos = produtoService.listarProdutosCategoria("Padaria");
			return new ResponseEntity<>(produtos, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Contar produtos da categoria Padaria")
	@GetMapping("/contarProdutosCategoriaPadaria")
	public ResponseEntity<Long> contarProdutosCategoriaPadaria() {
		try {
			Long count = produtoService.contarProdutosCategoria("Padaria");
			return new ResponseEntity<>(count, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(0L, HttpStatus.BAD_REQUEST);
		}
	}

}
