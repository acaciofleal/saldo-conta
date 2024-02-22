package br.com.neocamp.saldo.conta.service;

import br.com.neocamp.saldo.conta.TestUtils;
import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Rest Service - Testes")
class RestServiceTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    RestService restService;
    @Test
    public void testGetUsersFromJSONPlaceHolder_RequestBom() {
        when(restTemplate.getForObject(anyString(), UsuarioJSONPlaceHolderDTO[].class))
                .thenReturn(TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO());

        UsuarioJSONPlaceHolderDTO[] usuarios = restService.getUsersFromJSONPlaceHolder();

        assertNotEquals(usuarios.length, TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO().length);
    }
}