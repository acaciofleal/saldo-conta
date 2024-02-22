package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.dto.UsuarioTaskDTO;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPITasksException;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPIjsonplaceholderException;
import br.com.neocamp.saldo.conta.util.TestUtils;
import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import br.com.neocamp.saldo.conta.util.RestMockTemplateBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Rest Service - Testes")
class RestServiceTest {
    RestTemplate restTemplate;
    RestService restService;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        restService = new RestService(new RestMockTemplateBuilder(restTemplate));
    }

    @DisplayName("Teste para pegar usuarios com sucesso da API jsonplaceholder")
    @Test
    public void testGetUsersFromJSONPlaceHolder_RequestBom() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO());

        UsuarioJSONPlaceHolderDTO[] usuarios = restService.getUsersFromJSONPlaceHolder();

        assertEquals(TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO().length, usuarios.length);
        assertEquals(TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO()[0].getName(), usuarios[0].getName());
    }

    @DisplayName("Teste para pegar usuarios com erro REST da API jsonplaceholder")
    @Test
    public void testGetUsersFromJSONPlaceHolder_RequestComErro() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(new RestClientException(""));

        assertThrows(ErroAcessarAPIjsonplaceholderException.class, () -> restService.getUsersFromJSONPlaceHolder());
    }

    @DisplayName("Teste para pegar usuarios com sucesso da API Task")
    @Test
    public void testGetUsersFromTaskApi_RequestBom() {
        when(restTemplate.getForEntity(anyString(), any(), anyInt()))
                .thenReturn(new ResponseEntity<>(TestUtils.getArrayOfUsuarioTaskDTO(), HttpStatus.OK));

        UsuarioTaskDTO[] usuarios = restService.getUsersFromTasksApi();

        assertEquals(TestUtils.getArrayOfUsuarioTaskDTO().length, usuarios.length);
        assertEquals(TestUtils.getArrayOfUsuarioTaskDTO()[0].getNickName(), usuarios[0].getNickName());
    }

    @DisplayName("Teste para pegar usuarios com erro REST da API Task")
    @Test
    public void testGetUsersFromTaskApi_RequestComErro() {
        when(restTemplate.getForEntity(anyString(), any(), anyInt()))
                .thenThrow(new RestClientException(""));

        assertThrows(ErroAcessarAPITasksException.class, () -> restService.getUsersFromTasksApi());
    }

    @DisplayName("Teste para pegar usuarios com erro no na resposta da API Task")
    @Test
    public void testGetUsersFromTaskApi_RequestComErroNoBody() {
        when(restTemplate.getForEntity(anyString(), any(), anyInt()))
                .thenReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ErroAcessarAPITasksException.class, () -> restService.getUsersFromTasksApi());
    }
}