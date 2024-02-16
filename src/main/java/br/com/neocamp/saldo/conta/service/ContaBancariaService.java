package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;

import java.util.List;

public interface ContaBancariaService {
    public void sacar(Integer numeroConta, Double valor);
    public ContaBancaria depositar(Integer numeroConta, Double valor);
    public Double consultarSaldo(Integer numeroConta);

    ContaBancaria criarConta(Double valor);

    ContaBancaria buscarConta(Integer numeroConta);

    List<ContaBancaria> buscarContas();


    void excluirConta(Integer numeroConta);

    public ContaBancaria transferir(Integer numContaOrigem, Integer numContaDestino, Double valor);

}
