package br.com.neocamp.saldo.conta.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioJSONPlaceHolderDTO implements Serializable {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
}
