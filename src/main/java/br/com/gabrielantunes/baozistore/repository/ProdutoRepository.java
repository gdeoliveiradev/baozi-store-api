package br.com.gabrielantunes.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabrielantunes.baozistore.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}