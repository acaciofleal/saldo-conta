package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/conta-bancaria")
public class ContaBancariaController {

    @Autowired
    ContaBancariaService service;

    @PutMapping("/saque/{numeroConta}")
    public ResponseEntity<String> sacar(@PathVariable Integer numeroConta, @RequestParam Double valor) {
        try {
            service.sacar(numeroConta, valor);
            return ResponseEntity.ok("Saque realizado com sucesso!");
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/deposito/{numeroConta}")
    public ResponseEntity<String> depositar(@PathVariable Integer numeroConta, @RequestParam Double valor) {
        try {
            service.depositar(numeroConta, valor);
            return ResponseEntity.ok("Deposito realizado com sucesso!");
        } catch (ValorInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
