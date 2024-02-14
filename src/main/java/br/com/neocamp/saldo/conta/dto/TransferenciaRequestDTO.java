package br.com.neocamp.saldo.conta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaRequestDTO {
    Integer numeroConta;
    Double valor;
}
