package com.cyrillo.bff.investpessbffwebativo.infra.facade;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.ListarAtivosPorTipo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

import java.util.List;

public class FacadeAtivo {
    public FacadeAtivo(){
    }
    public void executarIncluirNovoAtivo(DataProviderInterface data, String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteUseCaseExcecao, ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        new IncluirNovoAtivo().executar(data,sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
    }
    public List<AtivoDtoInterface> executarListarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        return new ListarAtivosPorTipo().executar(data,tipoAtivo);
    }
}
