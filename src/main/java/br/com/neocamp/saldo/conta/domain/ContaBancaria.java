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
    private Integer id;

    @Column(name = "numero_conta")
    private String numeroConta;

    @Column
    private String tipo;

    @Column
    private String titular;

    @Column
    private Double saldo;

}
