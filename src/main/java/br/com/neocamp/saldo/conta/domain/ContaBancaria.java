package br.com.neocamp.saldo.conta.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "contabancaria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numeroConta;

    @Column
    private Double saldo;

}
