package br.com.neocamp.saldo.conta.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioTaskDTO implements Serializable {
    private String nickName;
    private String team;
}