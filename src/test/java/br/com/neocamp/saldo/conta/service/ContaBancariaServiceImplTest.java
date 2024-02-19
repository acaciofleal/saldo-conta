package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.MesmaContaException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.repository.ContaBancariaRepository;
import br.com.neocamp.saldo.conta.serviceimpl.ContaBancariaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@DisplayName("Conta Bancária Service Implements - Testes")
public class ContaBancariaServiceImplTest {
    ContaBancariaRepository contaBancariaRepository;
    ContaBancariaServiceImpl contaBancariaService;
    @BeforeEach
    public void setup() {
        contaBancariaRepository = mock(ContaBancariaRepository.class);
        contaBancariaService = new ContaBancariaServiceImpl(contaBancariaRepository);
    }


    @DisplayName("Criar conta com sucesso")
    @Test
    public void testCriarConta() {
        when(contaBancariaRepository.save(any(ContaBancaria.class))).thenReturn(new ContaBancaria(1, 51.00));
        ContaBancaria contaBancaria = contaBancariaService.criarConta(51.00);

        assertEquals(1, contaBancaria.getNumeroConta());
        assertEquals(51.00, contaBancaria.getSaldo());
    };

    @DisplayName("Transferência entre contas")
    @Test
    public void testTransferir() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, 300.00)));
        when(contaBancariaRepository.save(any(ContaBancaria.class)))
                .thenReturn(new ContaBancaria(1, 100.00));

        ContaBancaria contaBancaria = contaBancariaService.transferir(1, 2, 25.00);

        assertEquals(75.00 ,contaBancaria.getSaldo());
    }

    @DisplayName("Transferencia conta origem inexistente")
    @Test
    public void testTransferirContaOrigemInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new ContaBancaria(2, 300.00)));

        ContaBancariaNotFoundException exception = assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.transferir(1,2, 50.00));
        assertEquals("Conta de origem inexistente", exception.getMessage());
    }

    @DisplayName("Transferencia conta destino inexistente")
    @Test
    public void testTransferirContaDestinoInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 300.00)))
                .thenReturn(Optional.empty());

        ContaBancariaNotFoundException exception = assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.transferir(1,2, 50.00));
        assertEquals("Conta de destino inexistente", exception.getMessage());
    }

    @DisplayName("Transferencia para a mesma conta")
    @Test
    public void testTransferirMesmaConta() {
        MesmaContaException exception = assertThrows(MesmaContaException.class, () -> contaBancariaService.transferir(1,1, 50.00));
        assertEquals("Contas iguais, impossivel fazer transferencia", exception.getMessage());
    }

    @DisplayName("Transferencia valor de transferencia invalido")
    @Test
    public void testTransferirValorInvalido() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, 300.00)));

        assertThrows(ValorInvalidoException.class, () -> contaBancariaService.transferir(1,2, -50.00));
    }

    @DisplayName("Transferencia saldo da conta de origem insuficiente")
    @Test
    public void testTransferirSaldoInsuficiente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, 300.00)));

        assertThrows(SaldoInsuficienteException.class, () -> contaBancariaService.transferir(1,2, 150.00));
    }

    @DisplayName("Depositar na conta com sucesso")
    @Test
    public void testDepositar() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)));

        ContaBancaria conta = contaBancariaService.depositar(1, 200.0);
        assertEquals(300.0, conta.getSaldo());
    }

    @DisplayName("Depositar em uma conta inexistente")
    @Test
    public void testDepositarContaInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.depositar(1, 200.0));
    }

    @DisplayName("Depositar em uma conta inexistente")
    @Test
    public void testDepositarValorInvalido() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)));

        assertThrows(ValorInvalidoException.class, () -> contaBancariaService.depositar(1, 0.0));
    }


    @DisplayName("Buscar conta com sucesso")
    @Test
    public void testBuscarConta() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, 100.00)));

        ContaBancaria conta = contaBancariaService.buscarConta(1);
        assertEquals(1, conta.getNumeroConta());
        assertEquals(100.0, conta.getSaldo());
    }



    @DisplayName("Buscar conta inexistente")
    @Test
    public void testBuscarContaInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.buscarConta(1));
    }

    @DisplayName("Buscar conta com número de conta nulo")
    @Test
    public void testBuscarContaNumeroContaNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contaBancariaService.buscarConta(null));
        assertEquals("Número da conta não pode ser nulo", exception.getMessage());
    }


}