package br.com.neocamp.saldo.conta.serviceimpl;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.repository.ContaBancariaRepository;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContaBancariaServiceImpl implements ContaBancariaService {

    @Autowired
    ContaBancariaRepository repository;

    @Override
    public void sacar(Integer numeroConta, Double valor) throws SaldoInsuficienteException, ContaBancariaNotFoundException {

        Optional<ContaBancaria> conta = repository.findById(numeroConta);
        ContaBancaria contaBancaria;

        if(conta.isPresent())  {
           contaBancaria = conta.get();
        } else {
            throw new ContaBancariaNotFoundException("Conta inexistente");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("A quantia deve ser maior que zero.");
        }

        if (valor > contaBancaria.getSaldo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo()-valor);
        repository.save(contaBancaria);
    }

    @Override
    public void depositar(Integer numeroConta, Double valor) {

    }

    @Override
    public Double consultarSaldo(Integer numeroConta) {
        return null;
    }

    private List<ContaBancaria> getContaBancarias() {
        List<ContaBancaria> contas = new ArrayList<>();
        ContaBancaria contaBancaria_Vitor = ContaBancaria.builder().numeroConta(1).saldo(1000.00).build();
        ContaBancaria contaBancaria_Jean = ContaBancaria.builder().numeroConta(2).saldo(2000.00).build();
        ContaBancaria contaBancaria_Rosana = ContaBancaria.builder().numeroConta(3).saldo(5000.00).build();
        ContaBancaria contaBancaria_Fabrycio = ContaBancaria.builder().numeroConta(4).saldo(00.00).build();
        ContaBancaria contaBancaria_Rafaela = ContaBancaria.builder().numeroConta(5).saldo(0.20).build();
        ContaBancaria contaBancaria_Ariane = ContaBancaria.builder().numeroConta(6).saldo(1000000.00).build();
        contas.add(contaBancaria_Ariane);
        contas.add(contaBancaria_Jean);
        contas.add(contaBancaria_Fabrycio);
        contas.add(contaBancaria_Rafaela);
        contas.add(contaBancaria_Vitor);
        contas.add(contaBancaria_Rosana);
        return contas;
    }
}
