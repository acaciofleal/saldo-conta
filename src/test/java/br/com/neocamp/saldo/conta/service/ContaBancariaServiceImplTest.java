package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.repository.ContaBancariaRepository;
import br.com.neocamp.saldo.conta.serviceimpl.ContaBancariaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}

























