package br.com.neocamp.saldo.conta.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RestMockTemplateBuilder extends RestTemplateBuilder {
    private RestTemplate mock;
    public RestMockTemplateBuilder(RestTemplate mock) {
        this.mock = mock;
    }
    public RestTemplate build() {
        return mock;
    }
}
