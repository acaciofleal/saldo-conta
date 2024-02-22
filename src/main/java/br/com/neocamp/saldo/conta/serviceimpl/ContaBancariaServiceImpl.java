package br.com.neocamp.saldo.conta.serviceimpl;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.repository.ContaBancariaRepository;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import br.com.neocamp.saldo.conta.validador.ValidaContaBancaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        ValidaContaBancaria.validaSeContaExiste(conta, "Conta inexistente");

        contaBancaria = conta.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);
        ValidaContaBancaria.validaSaldoSuficiente(valor, contaBancaria.getSaldo(),"saldo insuficiente para operacao");

        contaBancaria.setSaldo(contaBancaria.getSaldo()-valor);
        repository.save(contaBancaria);
    }

    @Override
    public void depositar(Integer numeroConta, Double valor) {
        Optional<ContaBancaria> contaBancaria = repository.findById(numeroConta);
        ContaBancaria conta;

        ValidaContaBancaria.validaSeContaExiste(contaBancaria, "Conta inexistente");
        conta = contaBancaria.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);
        conta.setSaldo(conta.getSaldo() + valor);
        repository.save(conta);
    }

    @Override
    public ContaBancaria transferir(Integer numContaOrigem, Integer numContaDestino, Double valor) {
        Optional<ContaBancaria> contaO = repository.findById(numContaOrigem);
        Optional<ContaBancaria> contaD = repository.findById(numContaDestino);
        ContaBancaria contaOrigem, contaDestino;

        ValidaContaBancaria.validaSeContaExiste(contaO, "Conta de origem inexistente");
        ValidaContaBancaria.validaSeContaExiste(contaD, "Conta de destino inexistente");

        contaOrigem = contaO.get();
        contaDestino = contaD.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);

        ValidaContaBancaria.validaSaldoSuficiente(valor, contaOrigem.getSaldo(),"saldo insulficiente para operacao");

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        repository.save(contaOrigem);
        repository.save(contaDestino);

        return contaOrigem;
    }

    @Override
    public Double consultarSaldo(Integer numeroConta) {
        return null;
    }

    @Override
    public ContaBancaria criarConta(Double valor) {
        if (valor < 50) {
            throw new IllegalArgumentException("Valor minimo exigido para abertura de conta é R$50,00.");
        }
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setSaldo(valor);
        return repository.save(contaBancaria);

    }

    @Override
    public ContaBancaria buscarConta(Integer numeroConta) throws ContaBancariaNotFoundException {
        Optional<ContaBancaria> optionalConta = repository.findById(numeroConta);
        if (optionalConta.isPresent()) {
            return optionalConta.get();
        } else {
            throw new ContaBancariaNotFoundException("Conta bancária: " + numeroConta + " não foi encontrada." );
        }
    }

    @Override
    public List<ContaBancaria> buscarContas() {
        List<ContaBancaria> contas = repository.findAll();
        if (contas.isEmpty()) {
            throw new ContaBancariaNotFoundException("Nenhuma conta bancária foi criada no sistema.");
        }
        return contas;
    }

    @Override
    public void excluirConta(Integer numeroConta) throws ContaBancariaNotFoundException {
        Optional<ContaBancaria> optionalConta = repository.findById(numeroConta);
        if (optionalConta.isPresent()) {
            repository.delete(optionalConta.get());
        } else {
            throw new ContaBancariaNotFoundException("Conta bancária: " + numeroConta + " não foi encontrada." );
        }
    }

}
