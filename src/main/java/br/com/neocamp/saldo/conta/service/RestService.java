package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import br.com.neocamp.saldo.conta.dto.UsuarioTaskDTO;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPITasksException;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPIjsonplaceholderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://jsonplaceholder.typicode.com";

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public UsuarioJSONPlaceHolderDTO[] getUsersFromJSONPlaceHolder() {
        try {
            return restTemplate.getForObject(baseUrl + "/users", UsuarioJSONPlaceHolderDTO[].class);
        } catch (RestClientException e) {
            throw new ErroAcessarAPIjsonplaceholderException("Erro: " + e.getMessage());
        }
    }

    public UsuarioTaskDTO[] getUsersFromTasksApi() {
        try {
            ResponseEntity<UsuarioTaskDTO[]> response = restTemplate.getForEntity("http://localhost:8090/api/users", UsuarioTaskDTO[].class, 1);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new ErroAcessarAPITasksException("Erro: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new ErroAcessarAPITasksException(e.getMessage());
        }
    }
}
