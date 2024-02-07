package br.com.neocamp.saldo.conta.repository;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {
}
