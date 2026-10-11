package br.com.gabrielantunes.baozistore.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gabrielantunes.baozistore.dto.PedidoRequest;
import br.com.gabrielantunes.baozistore.model.Cliente;
import br.com.gabrielantunes.baozistore.model.Pedido;
import br.com.gabrielantunes.baozistore.model.Produto;
import br.com.gabrielantunes.baozistore.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteService clienteService,
            ProdutoService produtoService) {

        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public Pedido criar(PedidoRequest requisicao) {
        return salvarDados(new Pedido(), requisicao);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido não encontrado com o ID " + id + "."));
    }

    public Pedido atualizar(Long id, PedidoRequest requisicao) {
        Pedido pedido = buscarPorId(id);
        return salvarDados(pedido, requisicao);
    }

    public void excluir(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }

    private Pedido salvarDados(Pedido pedido, PedidoRequest requisicao) {
        Cliente cliente = clienteService.buscarPorId(requisicao.getClienteId());
        Produto produto = produtoService.buscarPorId(requisicao.getProdutoId());

        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O produto selecionado não está disponível em estoque.");
        }

        BigDecimal valorUnitario = produto.getPreco();
        BigDecimal valorTotal = valorUnitario.multiply(
                BigDecimal.valueOf(requisicao.getQuantidade()));

        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(requisicao.getQuantidade());
        pedido.setValorUnitario(valorUnitario);
        pedido.setValorTotal(valorTotal);

        return pedidoRepository.save(pedido);
    }
}