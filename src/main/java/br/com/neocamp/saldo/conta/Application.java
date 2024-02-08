package br.com.neocamp.saldo.conta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.neocamp.saldo.conta.*")
@ComponentScan(basePackages = { "br.com.neocamp.saldo.conta.*" })
@EntityScan("br.com.neocamp.saldo.conta.*")
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}

