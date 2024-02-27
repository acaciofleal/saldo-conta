package br.com.neocamp.saldo.conta.repository;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {

// public Optional<ContaBancaria> findByNumeroConta(String numeroConta);

 List<ContaBancaria> findByNumeroConta(String numeroConta);

 List<ContaBancaria> findByTipo(String tipo);

 List<ContaBancaria> findByTitular(String titular);

 List<ContaBancaria> findByNumeroContaAndTipo(String numeroConta, String tipo);

 List<ContaBancaria> findByNumeroContaAndTitular(String numeroConta, String titular);

 List<ContaBancaria> findByTipoAndTitular(String tipo, String titular);

 List<ContaBancaria> findByNumeroContaAndTipoAndTitular(String numeroConta, String tipo, String titular);

}
