package br.com.neocamp.saldo.conta.domain;

import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ContaBancaria - Testes")
class ContaBancariaTest {


//    @DisplayName("Teste saque sucesso - valor de saque 850")
//    @Test
//    public void testSaldo() {
//        ContaBancaria contaBancaria = criarContaBancaria();
//        contaBancaria.sacar(850);
//        assertEquals(150.00, contaBancaria.getSaldo());
//    }
//
//    @DisplayName("Teste saque - valor de saque 0")
//    @Test
//    public void testSaque0() {
//        ContaBancaria contaBancaria = criarContaBancaria();
//        assertThrows(IllegalArgumentException.class, () -> contaBancaria.sacar(0));
//    }
//
//    @DisplayName("Teste saque - valor de saque maior que o saldo disponivel")
//    @Test
//    public void testSaqueMaiorQueSaldo() {
//        ContaBancaria contaBancaria = criarContaBancaria();
//        assertThrows(SaldoInsuficienteException.class, () -> contaBancaria.sacar(1200));
//    }
//
//    @DisplayName("Teste saque - valor de saque negativo")
//    @Test
//    public void testSaqueValorNegativo() {
//        ContaBancaria contaBancaria = criarContaBancaria();
//        assertThrows(IllegalArgumentException.class, () -> contaBancaria.sacar(-100));
//    }

//    private ContaBancaria criarContaBancaria() {
//        return new ContaBancaria(12345, 1000.00);
//    }
}
