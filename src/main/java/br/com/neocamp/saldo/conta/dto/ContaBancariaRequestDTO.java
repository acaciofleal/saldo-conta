package br.com.neocamp.saldo.conta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaRequestDTO {

    @NotNull(message = "O saldo nao debe ser nulo")
    @DecimalMin(value = "50.0", message = "O saldo deve ser maior que 50")
    private Double saldo;

    @NotEmpty(message = "o numero de conta nao pode ser vacio")
    @NotNull(message = "voce tem que mandar o numero de conta")
    @JsonProperty("numero_conta")
    private String numeroConta;

    @NotEmpty
    @NotNull
    private String tipo;

    @NotEmpty
    @NotNull
    private String titular;
}
