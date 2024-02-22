package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import br.com.neocamp.saldo.conta.dto.UsuarioTaskDTO;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPITasksException;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPIjsonplaceholderException;
import br.com.neocamp.saldo.conta.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/v1/users")
public class RestUsuarioController {
    private RestService restService;
    public RestUsuarioController(RestService restService) { this.restService = restService; }

    @GetMapping
    public ResponseEntity<UsuarioJSONPlaceHolderDTO[]> getUsuarios() {
        try {
            UsuarioJSONPlaceHolderDTO[] usuarios = restService.getUsersFromJSONPlaceHolder();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarios);
        } catch (ErroAcessarAPIjsonplaceholderException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/task")
    public ResponseEntity<Object> getUsuariosTasks() {
        try {
            UsuarioTaskDTO[] usuarios = restService.getUsersFromTasksApi();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (ErroAcessarAPITasksException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
