package com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroCNPJInvalidoEntExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;

public class AtivoDto implements AtivoDtoInterface {
    private String sigla;
    private String nomeAtivo;
    private String descricaoCNPJAtivo;
    private int tipoAtivo;

    public AtivoDto(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) {
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

    public int getTipoAtivoInt() {
        return tipoAtivo;
    }

}
