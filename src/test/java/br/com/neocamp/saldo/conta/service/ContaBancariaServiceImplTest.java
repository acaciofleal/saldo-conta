package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.MesmaContaException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.repository.ContaBancariaRepository;
import br.com.neocamp.saldo.conta.serviceimpl.ContaBancariaServiceImpl;
import br.com.neocamp.saldo.conta.util.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        when(contaBancariaRepository.save(any(ContaBancaria.class))).thenReturn(new ContaBancaria(1, "12345", "Corrente", "Ariane", 51.00));
        ContaBancaria contaBancaria = contaBancariaService.criarConta(new ContaBancaria(1, "12345", "Corrente", "Ariane", 51.00));

        assertEquals("12345", contaBancaria.getNumeroConta());
        assertEquals(51.00, contaBancaria.getSaldo());
    };

    @DisplayName("Transferência entre contas")
    @Test
    public void testTransferir() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, "12345", "Corrente", "Ariane", 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, "12346", "Corrente", "Layse", 300.00)));
        when(contaBancariaRepository.save(any(ContaBancaria.class)))
                .thenReturn(new ContaBancaria(1, "12345", "Corrente", "Ariane", 75.00));

        ContaBancaria contaBancaria = contaBancariaService.transferir(1,2, 25.00);

        assertEquals(  75.00, contaBancaria.getSaldo());
    }

    @DisplayName("Transferencia conta origem inexistente")
    @Test
    public void testTransferirContaOrigemInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new ContaBancaria(2, "12360", "Corrente", "Maria", 300.00)));

        ContaBancariaNotFoundException exception = assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.transferir(1,2, 50.00));
        assertEquals(Constantes.ERROR_CONTA_ORIGEM_INEXISTENTE, exception.getMessage());
    }

    @DisplayName("Transferencia conta destino inexistente")
    @Test
    public void testTransferirContaDestinoInexistente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00)))
                .thenReturn(Optional.empty());

        ContaBancariaNotFoundException exception = assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.transferir(1,2, 50.00));
        assertEquals(Constantes.ERROR_CONTA_DESTINO_INEXISTENTE, exception.getMessage());
    }

    @DisplayName("Transferencia para a mesma conta")
    @Test
    public void testTransferirMesmaConta() {
        MesmaContaException exception = assertThrows(MesmaContaException.class, () -> contaBancariaService.transferir(1,1, 50.00));
        assertEquals(Constantes.ERROR_CONTA_ORIGEM_DESTINO_IGUAIS, exception.getMessage());
    }

    @DisplayName("Transferencia valor de transferencia invalido")
    @Test
    public void testTransferirValorInvalido() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, "12347", "Corrente", "Mariana", 50.00)));

        assertThrows(ValorInvalidoException.class, () -> contaBancariaService.transferir(1,2, -50.00));
    }

    @DisplayName("Transferencia saldo da conta de origem insuficiente")
    @Test
    public void testTransferirSaldoInsuficiente() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00)))
                .thenReturn(Optional.of(new ContaBancaria(2, "12347", "Corrente", "Mariana", 50.00)));

        assertThrows(SaldoInsuficienteException.class, () -> contaBancariaService.transferir(1,2, 150.00));
    }

    @DisplayName("Depositar na conta com sucesso")
    @Test
    public void testDepositar() {
        when(contaBancariaRepository.findById(anyInt()))
                .thenReturn(Optional.of(new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00)));

        ContaBancaria conta = contaBancariaService.depositar(1, 0.1);
        assertEquals(100.1, conta.getSaldo());
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
                .thenReturn(Optional.of(new ContaBancaria(7, "12347", "Corrente", "Henrrique", 100.00)));

        assertThrows(ValorInvalidoException.class, () -> contaBancariaService.depositar(1, 0.0));
    }




    @DisplayName("Buscar conta com sucesso")
    @Test
    public void testBuscarConta() {

        String numeroContaTest = "12345";
        Double saldoTest = 200.00;

        when(contaBancariaRepository.findByNumeroConta(numeroContaTest))
                .thenReturn(List.of(new ContaBancaria(1, numeroContaTest, "Corrente", "Mariana", saldoTest)));

        ContaBancaria conta = contaBancariaService.buscarConta(numeroContaTest);
        assertEquals(numeroContaTest, conta.getNumeroConta());
        assertEquals(saldoTest, conta.getSaldo());
    }


    @DisplayName("Buscar todas as contas com sucesso")
    @Test
    public void buscarTodasAsContasComSucesso(){

        when(contaBancariaRepository.findAll())
                .thenReturn(List.of(new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00),
                        new ContaBancaria(2, "12355", "Corrente", "Alessandra", 300.00)));


      List<ContaBancaria> todasAsContas = contaBancariaService.buscarContas(null, null, null);

      assertNotNull(todasAsContas);
      assertEquals(todasAsContas.size(),2);



    }




    @DisplayName("Buscar conta inexistente")
    @Test
    public void testBuscarContaInexistente() {

        String numeroContaInexistenteTest = "1250";

        when(contaBancariaRepository.findByNumeroConta(numeroContaInexistenteTest))
                .thenReturn(List.of());

        assertThrows(ContaBancariaNotFoundException.class, () -> contaBancariaService.buscarConta(numeroContaInexistenteTest));
    }

    @DisplayName("Buscar conta com número de conta nulo")
    @Test
    public void testBuscarContaNumeroContaNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contaBancariaService.buscarConta(null));
        assertEquals(Constantes.ERROR_NUMERO_CONTA_NULO, exception.getMessage());
    }




    @DisplayName("Testar sacar com sucesso")
    @Test
    public void testSacarComSucesso(){

        Integer numeroConta = anyInt();

        ContaBancaria contaBancaria = new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00);

        Double valor = 40.00;

        Double saldoEsperado = 60.00;

        //simula o acesso ao banco de dados
        when(contaBancariaRepository.findById(numeroConta)).
                thenReturn(Optional.of(contaBancaria));

        when(contaBancariaRepository.save(contaBancaria)).thenReturn(contaBancaria);

        contaBancariaService.sacar(numeroConta, valor);

        assertEquals(contaBancaria.getSaldo(), saldoEsperado);

    }

    @DisplayName("Tentar sacar com saldo insuficiente")
    @Test
    public void tentarSacarComSaldoInsuficiente(){

        Integer numeroConta = anyInt();

        ContaBancaria contaBancaria = new ContaBancaria(1, "12345", "Corrente", "Mariana", 100.00);

        Double valor = 110.00;

        //simula o acesso ao banco de dados
        when(contaBancariaRepository.findById(numeroConta)).
                thenReturn(Optional.of(contaBancaria));

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> contaBancariaService.sacar(numeroConta, valor));
        assertEquals(Constantes.ERROR_SALDO_INSUFICIENTE, exception.getMessage());


    }





}