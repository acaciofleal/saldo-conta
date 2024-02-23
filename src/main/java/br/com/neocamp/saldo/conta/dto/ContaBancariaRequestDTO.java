package br.com.neocamp.saldo.conta.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaRequestDTO {


    @NotNull(message = "Saldo não pode ser nulo")
    @DecimalMin(value = "50.0", message = "O saldo deve ser maior que 50")
    private Double saldo;


    @NotEmpty(message = "Numero da conta não pode ser nulo")
    @NotNull (message = "Numero da conta não pode ser nulo")
    private String numeroConta;

    @NotEmpty
    @NotNull
    private String tipo;

    @NotEmpty
    @NotNull
    private String titular;
}
