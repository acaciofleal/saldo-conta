package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;

import java.util.List;

public interface ContaBancariaService {

    public void sacar(Integer numeroConta, Double valor);
    public ContaBancaria depositar(Integer numeroConta, Double valor);
    public Double consultarSaldo(Integer numeroConta);


    ContaBancaria criarConta(ContaBancaria conta);

    ContaBancaria buscarConta(String numeroConta);

    List<ContaBancaria> buscarContas(String numeroConta, String tipo, String titular);


    void excluirConta(Integer numeroConta);

    public ContaBancaria transferir(Integer numContaOrigem, Integer numContaDestino, Double valor);

}
