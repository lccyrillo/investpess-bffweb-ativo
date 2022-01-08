package com.cyrillo.bff.investpessbffwebativo.infra.entrypoint.inout;

public class AtivoResponse {
    private AtivoRequest ativoRequest;
    private int codigoResultado;
    private String mensagemResultado;

    public AtivoResponse(AtivoRequest ativoRequest, int codigoResultado, String mensagemResultado) {
        this.ativoRequest = ativoRequest;
        this.codigoResultado = codigoResultado;
        this.mensagemResultado = mensagemResultado;
    }

    public String getSigla() {
        return ativoRequest.getSigla();
    }

    public String getNomeAtivo() {
        return ativoRequest.getNomeAtivo();
    }

    public String getDescricaoCNPJAtivo() {
        return ativoRequest.getDescricaoCNPJAtivo();
    }

    public int getTipoAtivoInt() {
        return ativoRequest.getTipoAtivoInt();
    }


    public int getCodigoResultado() {
        return codigoResultado;
    }

    public String getMensagemResultado() {
        return mensagemResultado;
    }
}
