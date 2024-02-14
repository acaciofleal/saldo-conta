package br.com.neocamp.saldo.conta.serviceimpl;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
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
            throw new ValorInvalidoException("A quantia deve ser maior que zero.");
        }

        if (valor > contaBancaria.getSaldo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }

        contaBancaria.setSaldo(contaBancaria.getSaldo()-valor);
        repository.save(contaBancaria);
    }

    @Override
    public void depositar(Integer numeroConta, Double valor) {
        Optional<ContaBancaria> contaBancaria = repository.findById(numeroConta);
        ContaBancaria conta;

        if (contaBancaria.isPresent()) {
            conta = contaBancaria.get();
        } else {
            throw new ContaBancariaNotFoundException("Conta inexistente");
        }

        if (valor <= 0) {
            throw new ValorInvalidoException("A quantia deve ser maior que zero.");
        }
        conta.setSaldo(conta.getSaldo() + valor);
        repository.save(conta);
    }

    /**@Override
    public void transferir(Integer numContaOrigem, Double valor, Integer numContaDestino) {
        Optional<ContaBancaria> contaO = repository.findById(numContaOrigem);
        Optional<ContaBancaria> contaD = repository.findById(numContaDestino);
        ContaBancaria contaOrigem, contaDestino;

        if (contaO.isPresent() && contaD.isPresent()) {
            contaOrigem = contaO.get();
            contaDestino = contaD.get();
        } else if (contaO.isEmpty()) {
            throw new ContaBancariaNotFoundException("Conta de origem inexistente");
        } else {
            throw new ContaBancariaNotFoundException("Conta de destino inexistente");
        }

        if (valor <= 0) {
            throw new ValorInvalidoException("A quantia deve ser maior que zero.");
        }

        if (valor > contaOrigem.getSaldo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar .");
        }
    } */

    @Override
    public Double consultarSaldo(Integer numeroConta) {
        return null;
    }
}
