package com.cyrillo.bff.investpessbffwebativo.infra.facade;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.ListarAtivosPorTipo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.config.Aplicacao;

import java.util.List;

public class FacadeAtivo {
    private static FacadeAtivo instance;

    public DataProviderInterface getDataProviderInterface() {
        return dataProviderInterface;
    }
    private DataProviderInterface dataProviderInterface;

    public static FacadeAtivo getInstance(){
        if(instance == null){
            synchronized (FacadeAtivo.class) {
                if(instance == null){
                    instance = new FacadeAtivo();
                }
            }
        }
        return instance;
    }


    public void setDataProviderInterface(DataProviderInterface dataProviderInterface) {
        this.dataProviderInterface = dataProviderInterface;
    }


    public void executarIncluirNovoAtivo(DataProviderInterface sessao, String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteUseCaseExcecao, ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        new IncluirNovoAtivo().executar(sessao,sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
    }
    public List<AtivoDtoInterface> executarListarAtivosPorTipo(DataProviderInterface sessao, int tipoAtivo) throws ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        return new ListarAtivosPorTipo().executar(sessao,tipoAtivo);
    }
}
