package br.com.gabrielantunes.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabrielantunes.baozistore.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}