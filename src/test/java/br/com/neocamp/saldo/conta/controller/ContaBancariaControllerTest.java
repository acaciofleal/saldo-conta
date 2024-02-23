package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.dto.TransferenciaRequestDTO;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ContaBancariaControllerTest {
    ContaBancariaService contaBancariaService;
    ContaBancariaController contaBancariaController;

    @BeforeEach
    public void setup() {
        contaBancariaService = mock(ContaBancariaService.class);
        contaBancariaController = new ContaBancariaController(contaBancariaService);
    };


    @Test
    public void buscarContasSaldoZero() {
       List<ContaBancaria> contas = new ArrayList<>();
       ContaBancaria conta1 = new ContaBancaria(1, "12345", "Corrente", "Ariane", 300.00);
       ContaBancaria conta2 = new ContaBancaria(2, "12346", "Corrente", "Layse", 0.00);
       contas.add(conta1);
       contas.add(conta2);
       when(contaBancariaService.buscarContas())
               .thenReturn(contas);

       ResponseEntity<List<ContaBancaria>> response = contaBancariaController.buscarContasSaldoZero();
       List<ContaBancaria> result = new ArrayList<>();
       result.add(conta2);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(result, response.getBody());


    }

    @DisplayName("Depositar com sucesso")
    @Test
    public void testDepositar() {
        when(contaBancariaService.depositar(anyInt(), anyDouble()))
                .thenReturn(new ContaBancaria());

        ResponseEntity<Object> response = contaBancariaController.depositar(2, 47.00);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Depositar com valor invalido")
    @Test
    public void testDepositarValorInvalido() {
        when(contaBancariaService.depositar(anyInt(), anyDouble()))
                .thenThrow(new ValorInvalidoException("Valor invalido"));

        ResponseEntity<Object> response = contaBancariaController.depositar(2, -47.00);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Depositar em conta inexistente")
    @Test
    public void testDepositarContaInexistente() {
        when(contaBancariaService.depositar(anyInt(), anyDouble()))
                .thenThrow(new ContaBancariaNotFoundException("Conta inexistente"));

        ResponseEntity<Object> response = contaBancariaController.depositar(2, -47.00);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    };

    @DisplayName("Transferir com sucesso")
    @Test
    public void testTransferir() {
        when(contaBancariaService.transferir(anyInt(), anyInt(), anyDouble()))
                .thenReturn(new ContaBancaria());

        ResponseEntity<Object> response = contaBancariaController.transferir(1, new TransferenciaRequestDTO(2, 45.00));

        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Transferencia realizada com sucesso!", response.getBody().toString());
    }

    @DisplayName("Transferir com alguma conta inexistente")
    @Test
    public void testTransferirContaInexistente() {
        when(contaBancariaService.transferir(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new ContaBancariaNotFoundException(""));

        ResponseEntity<Object> response = contaBancariaController.transferir(1, new TransferenciaRequestDTO(2, 45.00));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Transferir sem ter saldo suficiente")
    @Test
    public void testTransferirSaldoInsuficiente() {
        when(contaBancariaService.transferir(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new SaldoInsuficienteException(""));

        ResponseEntity<Object> response = contaBancariaController.transferir(1, new TransferenciaRequestDTO(2, 4500.00));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Saldo insuficiente", response.getBody());
    }

    @DisplayName("Transferir com valor invalido")
    @Test
    public void testTransferirValorInvalido() {
        when(contaBancariaService.transferir(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new ValorInvalidoException(""));

        ResponseEntity<Object> response = contaBancariaController.transferir(1, new TransferenciaRequestDTO(2, -45.00));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Valor invalido", response.getBody()


    }
}
