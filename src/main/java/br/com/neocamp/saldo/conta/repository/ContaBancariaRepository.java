package br.com.neocamp.saldo.conta.repository;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {

 public Optional<ContaBancaria> findByNumeroConta(String numeroConta);

}
