package br.com.neocamp.saldo.conta.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "contabancaria")
@Entity
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
