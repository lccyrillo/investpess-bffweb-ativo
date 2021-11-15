package com.cyrillo.bff.investpessbffwebativo.core.entidade.builder;

import com.cyrillo.bff.investpessbffwebativo.core.entidade.AtivoObjeto;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.TipoAtivo;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroCNPJInvalidoEntExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;

public class AtivoObjetoBuilder {
    private AtivoObjeto ativoObjeto;
    public AtivoObjetoBuilder() {}
    public AtivoObjetoBuilder infoCompleta(String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws ParametroCNPJInvalidoEntExcecao, ParametroTipoInvalidoEntExcecao {
        this.ativoObjeto = new AtivoObjeto(sigla, nomeAtivo,descricaoCNPJAtivo, new TipoAtivo(tipoAtivo));
        return this;
    }
    public AtivoObjeto build(){
        return this.ativoObjeto;
    }
}

