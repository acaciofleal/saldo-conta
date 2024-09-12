package br.com.neocamp.saldo.conta.mapper;

import br.com.neocamp.saldo.conta.domain.ContaBancaria;
import br.com.neocamp.saldo.conta.dto.ContaBancariaRequestDTO;

public class ContaBancariaMapper {

    public static ContaBancaria mapToDomain(ContaBancariaRequestDTO contaBancariaRequestDTO) {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setSaldo(contaBancariaRequestDTO.getSaldo());
        contaBancaria.setNumeroConta(contaBancariaRequestDTO.getNumeroConta());
        contaBancaria.setTitular(contaBancariaRequestDTO.getTitular());
        contaBancaria.setTipo(contaBancariaRequestDTO.getTipo());
        return contaBancaria;
    }
}
