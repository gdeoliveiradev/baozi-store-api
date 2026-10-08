package br.com.gabrielantunes.baozistore.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gabrielantunes.baozistore.model.Cliente;
import br.com.gabrielantunes.baozistore.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criar(Cliente cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado com o ID " + id + "."));
    }

    public Cliente atualizar(Long id, Cliente dadosAtualizados) {
        Cliente clienteExistente = buscarPorId(id);

        clienteExistente.setNome(dadosAtualizados.getNome());
        clienteExistente.setClienteDesde(dadosAtualizados.getClienteDesde());

        return clienteRepository.save(clienteExistente);
    }

    public void excluir(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}