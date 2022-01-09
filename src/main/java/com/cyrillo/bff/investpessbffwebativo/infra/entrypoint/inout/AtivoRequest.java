package com.cyrillo.bff.investpessbffwebativo.infra.entrypoint.inout;

public class AtivoRequest {
    private String sigla;
    private String nomeAtivo;
    private String descricaoCNPJAtivo;
    private int tipoAtivo;

    public AtivoRequest(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) {
        this.sigla = sigla;
        this.nomeAtivo = nomeAtivo;
        this.descricaoCNPJAtivo = descricaoCNPJAtivo;
        this.tipoAtivo = tipoAtivo;
    }
    public String getSigla() {
        return sigla;
    }

    public String getNomeAtivo() {
        return nomeAtivo;
    }

    public String getDescricaoCNPJAtivo() {
        return descricaoCNPJAtivo;
    }

    public int getTipoAtivo() {
        return tipoAtivo;
    }
}