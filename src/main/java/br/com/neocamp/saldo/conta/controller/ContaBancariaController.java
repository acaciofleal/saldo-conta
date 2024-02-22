package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.dto.ContaBancariaRequestDTO;
import br.com.neocamp.saldo.conta.dto.TransferenciaRequestDTO;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/conta-bancaria")
public class ContaBancariaController {

    @Autowired
    ContaBancariaService service;

    @PostMapping()
    public ResponseEntity<ContaBancaria> criarConta(@RequestBody ContaBancariaRequestDTO contaBancariaRequestDTO) {
        ContaBancaria contaBancaria = service.criarConta(contaBancariaRequestDTO.getValor());
        return ResponseEntity.ok(contaBancaria);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaBancaria> buscarConta(@PathVariable Integer numeroConta) {
        ContaBancaria contaBancaria = service.buscarConta(numeroConta);
        if (contaBancaria != null) {
            return ResponseEntity.ok(contaBancaria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ContaBancaria>> buscarContas() {
        List<ContaBancaria> contas = service.buscarContas();
        if (!contas.isEmpty()) {
            return ResponseEntity.ok(contas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/saque/{numeroConta}")
    public ResponseEntity<String> sacar(@PathVariable Integer numeroConta, @RequestParam Double valor) {
        try {
            service.sacar(numeroConta, valor);
            return ResponseEntity.ok("Saque realizado com sucesso!");
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("conta inexistente");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente");
        }
    }


    @DeleteMapping("/{numeroConta}")
    public ResponseEntity<String> excluirConta(@PathVariable Integer numeroConta) {
        try {
            service.excluirConta(numeroConta);
            return ResponseEntity.ok("Conta excluída com sucesso!");
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.notFound().build();
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


    @PutMapping("transferir/{numContaOrigem}")
    public ResponseEntity<Object> transferir(@PathVariable Integer numContaOrigem, @RequestBody TransferenciaRequestDTO transferencia) {
        try {
            ContaBancaria conta = service.transferir(numContaOrigem, transferencia.getNumeroConta(), transferencia.getValor());
            return ResponseEntity.ok(conta);
        } catch (ContaBancariaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValorInvalidoException | SaldoInsuficienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contaZero")
    public ResponseEntity<List<ContaBancaria>> buscarContasSaldoZero() {
        List<ContaBancaria> valorDaContas = service.buscarContas();
        List<ContaBancaria> contasSaldoZero = new ArrayList<>();
        for (int i = 0; i < valorDaContas.size(); i++) {
            ContaBancaria elemento = valorDaContas.get(i);

            if (elemento.getSaldo()==0){
                contasSaldoZero.add(elemento);
            }
        }
        return ResponseEntity.ok(contasSaldoZero);
    }

}
