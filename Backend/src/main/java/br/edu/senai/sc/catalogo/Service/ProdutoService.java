package br.edu.senai.sc.catalogo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.senai.sc.catalogo.Repository.CategoriaRepository;
import br.edu.senai.sc.catalogo.Repository.ImagemRepository;
import br.edu.senai.sc.catalogo.Repository.ProdutoRepository;
import br.edu.senai.sc.catalogo.entities.Categoria;
import br.edu.senai.sc.catalogo.entities.Imagem;
import br.edu.senai.sc.catalogo.entities.Produto;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ImagemRepository imagemRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
            ImagemRepository imagemRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.imagemRepository = imagemRepository;
    }

    public Produto salvarProduto(Produto produto) {
        produtoRepository.save(produto);
        return produto;
    }

    public List<Produto> buscarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorCodigo(Long codigo) {
        return produtoRepository.findById(codigo);
    }

    public List<Produto> buscarProdutoPorNome(String nome) {
        return produtoRepository.findProdutoByNomeContaining(nome);
    }

    public void excluirProduto(Long codigo) {
        Optional<Produto> optionalProduto = buscarProdutoPorCodigo(codigo);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();

            if (produto.getCategoria() != null) {
                removeCategoria(produto);
            }

            if (!"Sem imagem".equals(produto.getIdImagem())) {
                removeImagem(produto);
            }

            produtoRepository.deleteById(codigo);
        }
    }

    public void alterarNome(String nome, Long codigo) {
        Optional<Produto> optionalProduto = produtoRepository.findById(codigo);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(nome);
            produtoRepository.save(produto);
        }
    }

    public Produto addCategoria(Long codigoProduto, Long codigoCategoria) {
        Optional<Produto> optionalProduto = produtoRepository.findById(codigoProduto);
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(codigoCategoria);

        if (optionalProduto.isPresent() && optionalCategoria.isPresent()) {
            Produto produto = optionalProduto.get();
            Categoria categoria = optionalCategoria.get();

            produto.setCategoria(categoria);
            categoria.addProduto(produto);

            produtoRepository.save(produto);
            categoriaRepository.save(categoria);
        }

        return optionalProduto.orElse(null);
    }

    public Produto removeCategoria(Produto produto) {
        Categoria categoria = produto.getCategoria();

        if (categoria != null) {
            produto.setCategoria(null);
            categoria.removeProduto(produto);

            produtoRepository.save(produto);
            categoriaRepository.save(categoria);
        }

        return produto;
    }

    public Produto addImagem(Long codigoProduto, String codigoImagem) {
        Optional<Produto> optionalProduto = produtoRepository.findById(codigoProduto);
        Optional<Imagem> optionalImagem = imagemRepository.findById(codigoImagem);

        if (optionalProduto.isPresent() && optionalImagem.isPresent()) {
            Produto produto = optionalProduto.get();
            Imagem imagem = optionalImagem.get();

            produto.setImagem(imagem);
            imagem.setProduto(produto);

            produtoRepository.save(produto);
            imagemRepository.save(imagem);
        }

        return optionalProduto.orElse(null);
    }

    public Produto removeImagem(Produto produto) {
        String codigoImagem = produto.getIdImagem();

        if (!"Sem imagem".equals(codigoImagem)) {
            produto.setImagem(null);
            produtoRepository.save(produto);
            imagemRepository.deleteById(codigoImagem);
        }

        return produto;
    }

    public List<Produto> listarProdutosPrecoMaiorOuIgual1000() {
        return produtoRepository.listarProdutosPrecoMaiorOuIgual1000();
    }

    public Long contarProdutosPrecoMaiorOuIgual1000() {
        return produtoRepository.contarProdutosPrecoMaiorOuIgual1000();
    }

    public void removeImagem(Long codigoProduto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(codigoProduto);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();

            if (!"Sem imagem".equals(produto.getIdImagem())) {
                removeImagem(produto);
            }
        }
    }

    public List<Produto> listarProdutosCategoria(String nomeCategoria) {
        return produtoRepository.listarProdutosCategoria(nomeCategoria);
    }

    public Long contarProdutosCategoria(String nomeCategoria) {
        return produtoRepository.contarProdutosCategoria(nomeCategoria);
    }
}
