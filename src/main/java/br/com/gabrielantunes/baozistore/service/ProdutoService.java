package br.com.gabrielantunes.baozistore.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gabrielantunes.baozistore.model.Produto;
import br.com.gabrielantunes.baozistore.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criar(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado com o ID " + id + "."));
    }

    public Produto atualizar(Long id, Produto dadosAtualizados) {
        Produto produtoExistente = buscarPorId(id);

        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setPreco(dadosAtualizados.getPreco());
        produtoExistente.setEstoque(dadosAtualizados.getEstoque());

        return produtoRepository.save(produtoExistente);
    }

    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}