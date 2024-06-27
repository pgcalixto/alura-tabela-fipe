package com.pgcalixto.tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public record Veiculo(
    TipoVeiculo tipoVeiculo,
    String valor,
    String marca,
    String modelo,
    int anoModelo,
    String combustivel,
    String codigoFipe,
    String mesReferencia,
    String siglaCombustivel
) {

}
