package br.com.neocamp.saldo.conta.controller;

import br.com.neocamp.saldo.conta.util.TestUtils;
import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPITasksException;
import br.com.neocamp.saldo.conta.exception.ErroAcessarAPIjsonplaceholderException;
import br.com.neocamp.saldo.conta.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Instancia o springboot para testes
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Rest Usuario Controller - Testes")
class RestUsuarioControllerTest {
    private final String BASE_URI = "/v1/users";
    private final String API_TASKS_URI = "/task";

    // Responsavel por gerar uma requisicao real para o endpoint
    @Autowired
    private MockMvc mockMvc;

    // Faz a injecao de dependencia mockada
    @MockBean
    private RestService restService;

    @DisplayName("Testa o acesso à API jsonplaceholder no caso de um request bem sucedido")
    @Test
    public void testGetUsuarios_RequestBom() throws Exception {
        final UsuarioJSONPlaceHolderDTO[] usuarios = TestUtils.getArrayOfUsuarioJSONPlaceHolderDTO();
        when(restService.getUsersFromJSONPlaceHolder()).thenReturn(usuarios);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(BASE_URI)
                )
                .andReturn();

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(TestUtils.getJsonFromResource("UserMock1.json"), result.getResponse().getContentAsString());
    }

    @DisplayName("Testa o acesso à API jsonplaceholder no caso de um request com erro")
    @Test
    public void testGetUsuarios_RequestComErro() throws Exception {
        when(restService.getUsersFromJSONPlaceHolder()).thenThrow(new ErroAcessarAPIjsonplaceholderException(""));

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(BASE_URI)
                )
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }

    @DisplayName("Testa o acesso à API Tasks no caso de um request bem sucedido")
    @Test
    public void testGetUsuariosTasks_RequestBom() throws Exception {
        when(restService.getUsersFromTasksApi()).thenReturn(TestUtils.getArrayOfUsuarioTaskDTO());

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(BASE_URI + API_TASKS_URI)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(TestUtils.getJsonFromResource("UserMockTask.json"), result.getResponse().getContentAsString());
    }

    @DisplayName("Testa o acesso à API Tasks no caso de um request com erro")
    @Test
    public void testGetUsuariosTasks_RequestComErro() throws Exception {
        when(restService.getUsersFromTasksApi()).thenThrow(new ErroAcessarAPITasksException(""));

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(BASE_URI + API_TASKS_URI)
                )
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }
}