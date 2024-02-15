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
    private Integer numeroConta;

    @Column
    private Double saldo;

}
