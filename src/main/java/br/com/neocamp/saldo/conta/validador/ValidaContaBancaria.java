package br.com.neocamp.saldo.conta.validador;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.MesmaContaException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.util.Constantes;

import java.util.Optional;

public class ValidaContaBancaria {
    public static void validaSeContaExiste(Optional<ContaBancaria> contaBancaria, String mensagem) {
        if (!contaBancaria.isPresent()) {
            throw new ContaBancariaNotFoundException(mensagem);
        }
    }

    public static void validaValorNuloOuNegativo(Double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException(Constantes.ERROR_VALOR_NULO_OU_NEGATIVO);
        }
    }

    public static void validaSaldoSuficiente(Double valor, Double saldo) {
        if (valor > saldo) {
            throw new SaldoInsuficienteException(Constantes.ERROR_SALDO_INSUFICIENTE);
        }
    }

    public static void validaContasDiferentes(Integer contaOrigem, Integer contaDestino) {
        if (contaOrigem == contaDestino) {
            throw new MesmaContaException(Constantes.ERROR_CONTA_ORIGEM_DESTINO_IGUAIS);
        }
    }
}
