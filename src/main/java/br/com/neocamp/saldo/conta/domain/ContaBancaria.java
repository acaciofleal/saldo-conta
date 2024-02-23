package br.com.neocamp.saldo.conta.domain;

import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "contabancaria")
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column (name = "numero_conta")
    private String numeroConta;


    @Column
    private String tipo;

    @Column
    private String titular;


    @Column
    private Double saldo;

}
