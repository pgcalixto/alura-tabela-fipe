package com.pgcalixto.tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelo(int codigo, String nome) {

    @Override
    public String toString() {
        return "Código: " + codigo + ", nome: " + nome;
    }
}
