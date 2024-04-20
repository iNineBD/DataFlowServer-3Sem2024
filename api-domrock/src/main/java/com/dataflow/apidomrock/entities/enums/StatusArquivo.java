package com.dataflow.apidomrock.entities.enums;

public enum StatusArquivo {

    AGUARDANDO_APROVACAO_BRONZE("Aguardando aprovação da Bronze"),
    BRONZE_ZONE("Bronze Zone"),
    AGUARDANDO_APROVACAO_SILVER("Aguardando aprovação da Silver"),
    SILVER_ZONE("Silver Zone"),
    NAO_APROVADO_PELA_BRONZE("Não aprovado pela Bronze"),
    NAO_APROVADO_PELA_SILVER("Não aprovado pela Silver");

    private final String descricao;

    StatusArquivo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao.toUpperCase();
    }
}
