package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ContaBancariaControllerTest {
    ContaBancariaService contaBancariaService;
    ContaBancariaController contaBancariaController;

    @BeforeEach
    public void setup() {
        contaBancariaService = mock(ContaBancariaService.class);
        contaBancariaController = new ContaBancariaController(contaBancariaService);
    };

    @DisplayName("Depositar com sucesso")
    @Test
    public void depositar() {
        doNothing().when(contaBancariaService).depositar(anyInt(), anyDouble());

        ResponseEntity response = contaBancariaController.depositar(2, 47.00);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deposito realizado com sucesso!", response.getBody());
    };
}
