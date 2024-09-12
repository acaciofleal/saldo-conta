package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.dto.ContaBancariaRequestDTO;
import br.com.neocamp.saldo.conta.dto.TransferenciaRequestDTO;
import br.com.neocamp.saldo.conta.exception.ContaBancariaNotFoundException;
import br.com.neocamp.saldo.conta.exception.MesmaContaException;
import br.com.neocamp.saldo.conta.exception.SaldoInsuficienteException;
import br.com.neocamp.saldo.conta.exception.ValorInvalidoException;
import br.com.neocamp.saldo.conta.service.ContaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static br.com.neocamp.saldo.conta.mapper.ContaBancariaMapper.mapToDomain;

@Slf4j
@RestController
@RequestMapping(value = "/v1/conta-bancaria")
public class ContaBancariaController {

    @Autowired //Significa que o Spring vai injetar a dependência
    ContaBancariaService service;

    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        service = contaBancariaService;
    }

    @PostMapping()
    public ResponseEntity<Object> criarConta(@RequestBody @Valid ContaBancariaRequestDTO contaBancariaRequestDTO) {
        var contaBancaria = mapToDomain(contaBancariaRequestDTO);

        try {
            ContaBancaria novaContaBancaria = service.criarConta(contaBancaria);
            return new ResponseEntity<>(novaContaBancaria, HttpStatus.CREATED);
        } catch (MesmaContaException mce ) {
            return ResponseEntity.badRequest().body(mce.getMessage());
        }
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaBancaria> buscarConta(@PathVariable String numeroConta) {
        var contaBancaria = service.buscarConta(numeroConta);
        if (contaBancaria.isPresent()) {
            return ResponseEntity.ok(contaBancaria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping()
    public ResponseEntity<List<ContaBancaria>> buscarContas(@RequestParam(required = false) String numeroConta,
                                                            @RequestParam(required = false) String tipo,
                                                            @RequestParam(required = false) String titular) {
        List<ContaBancaria> contas = service.buscarContas(numeroConta, tipo, titular);
        if (!contas.isEmpty()) {
            return ResponseEntity.ok(contas);
        } else {
            //return new ResponseEntity<>("conta nao encontrada", HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contaZero")
    public ResponseEntity<List<ContaBancaria>> buscarContasSaldoZero() {
        List<ContaBancaria> valorDaContas = service.buscarContas(null, null, null);
        List<ContaBancaria> contasSaldoZero = new ArrayList<>();
        for (int i = 0; i < valorDaContas.size(); i++) {
            ContaBancaria elemento = valorDaContas.get(i);

            if (elemento.getSaldo()==0){
                contasSaldoZero.add(elemento);
            }
        }
        return ResponseEntity.ok(contasSaldoZero);
    }




    @PutMapping("/saque/{numeroConta}") // Aqui a buscar conta é o numero da conta
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
    public ResponseEntity<Object> depositar(@PathVariable Integer numeroConta, @RequestParam Double valor) {
        try {
            ContaBancaria conta = service.depositar(numeroConta, valor);
            return ResponseEntity.ok(conta);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (ContaBancariaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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





}