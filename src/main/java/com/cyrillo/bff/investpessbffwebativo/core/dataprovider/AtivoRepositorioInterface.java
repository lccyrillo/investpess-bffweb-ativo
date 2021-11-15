package com.cyrillo.bff.investpessbffwebativo.core.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;

import java.util.List;

// Acerto exceção
public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoDataProvExcecao, AtivoParametrosInvalidosUseCaseExcecao;
    void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao;
}
