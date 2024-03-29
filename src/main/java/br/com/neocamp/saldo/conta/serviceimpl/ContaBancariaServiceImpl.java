package br.com.neocamp.saldo.conta.serviceimpl;

import br.com.neocamp.saldo.conta.util.Constantes;
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

    public ContaBancariaServiceImpl(ContaBancariaRepository contaBancariaRepository) {
        repository = contaBancariaRepository;
    }

    @Override
    public void sacar(Integer numeroConta, Double valor) throws SaldoInsuficienteException, ContaBancariaNotFoundException {

        Optional<ContaBancaria> conta = repository.findById(numeroConta);

        ContaBancaria contaBancaria;

        ValidaContaBancaria.validaSeContaExiste(conta, Constantes.ERROR_CONTA_INEXISTENTE);

        contaBancaria = conta.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);
        ValidaContaBancaria.validaSaldoSuficiente(valor, contaBancaria.getSaldo());

        contaBancaria.setSaldo(contaBancaria.getSaldo()-valor);
        repository.save(contaBancaria);
    }

    @Override
    public ContaBancaria depositar(Integer numeroConta, Double valor) {
        Optional<ContaBancaria> contaBancaria = repository.findById(numeroConta);
        ContaBancaria conta;
        
        ValidaContaBancaria.validaSeContaExiste(contaBancaria, Constantes.ERROR_CONTA_INEXISTENTE);
        conta = contaBancaria.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);
        conta.setSaldo(conta.getSaldo() + valor);
        repository.save(conta);
        return conta;
    }

    @Override
    public ContaBancaria transferir(Integer numContaOrigem, Integer numContaDestino, Double valor) {
        ValidaContaBancaria.validaContasDiferentes(numContaOrigem, numContaDestino);

        Optional<ContaBancaria> contaO = repository.findById(numContaOrigem);
        Optional<ContaBancaria> contaD = repository.findById(numContaDestino);
        ContaBancaria contaOrigem, contaDestino;

        ValidaContaBancaria.validaSeContaExiste(contaO, Constantes.ERROR_CONTA_ORIGEM_INEXISTENTE);
        ValidaContaBancaria.validaSeContaExiste(contaD, Constantes.ERROR_CONTA_DESTINO_INEXISTENTE);

        contaOrigem = contaO.get();
        contaDestino = contaD.get();

        ValidaContaBancaria.validaValorNuloOuNegativo(valor);

        ValidaContaBancaria.validaSaldoSuficiente(valor, contaOrigem.getSaldo());

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
    public ContaBancaria criarConta(ContaBancaria conta) {

        if (conta.getSaldo() < 50) {
            throw new IllegalArgumentException(Constantes.ERROR_VALOR_MINIMO_ABERTURA_CONTA);
        }
        return repository.save(conta);
    }





    @Override
    public ContaBancaria buscarConta(String numeroConta) {
        if (numeroConta == null) {
            throw new IllegalArgumentException(Constantes.ERROR_NUMERO_CONTA_NULO);
        }
        List<ContaBancaria> contas = repository.findByNumeroConta(numeroConta);
        if (!contas.isEmpty()) {
            return contas.get(0); // ou qualquer outra lógica para selecionar uma ContaBancaria da lista
        } else {
            throw new ContaBancariaNotFoundException("Conta bancária: " + numeroConta + " não foi encontrada." );
        }
    }

    @Override
    public List<ContaBancaria> buscarContas(String numeroConta, String tipo, String titular) {
        System.out.println(String.format("numero conta: %s. Tipo: %s. Titular: %s", numeroConta, tipo, titular));

        List<ContaBancaria> contas;

        if (numeroConta != null && tipo != null && titular != null) {
            //select * from contas where numero= and tipo= and titular=
            contas =  repository.findByNumeroContaAndTipoAndTitular(numeroConta, tipo, titular);
        } else if (numeroConta != null && tipo != null) {
            //select * from contas where numero= and tipo=
            contas = repository.findByNumeroContaAndTipo(numeroConta, tipo);
        } else if (numeroConta != null && titular != null) {
            //select * from contas where numero= and titular=
            contas = repository.findByNumeroContaAndTitular(numeroConta, titular);
        } else if (tipo != null && titular != null) {
            contas = repository.findByTipoAndTitular(tipo, titular);
        } else if (numeroConta != null) {
            //select * from contas where numero=
            contas = repository.findByNumeroConta(numeroConta);
        } else if (tipo != null) {
            //select * from contas where tipo=
            contas = repository.findByTipo(tipo);
        } else if (titular != null) {
            contas = repository.findByTitular(titular);
        } else {
            contas = repository.findAll();
        }

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
