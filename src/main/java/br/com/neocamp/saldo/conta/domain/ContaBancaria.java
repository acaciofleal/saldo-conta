package br.com.neocamp.saldo.conta.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "contabancaria")
@Entity
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
