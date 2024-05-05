package com.dataflow.apidomrock.entities.enums;

public enum Estagio {
    LZ("Landing Zone"),
    B("Bronze Zone"),
    S("Silver Zone");

    private final String descricao;

    Estagio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao.toUpperCase();
    }
}
