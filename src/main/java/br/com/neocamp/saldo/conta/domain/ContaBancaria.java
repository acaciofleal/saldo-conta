package br.com.neocamp.saldo.conta.domain;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "contabancaria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaBancaria {
    @Id
    private Integer numeroConta;

    @Column
    private Double saldo;

}
