package br.com.gabrielantunes.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabrielantunes.baozistore.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}