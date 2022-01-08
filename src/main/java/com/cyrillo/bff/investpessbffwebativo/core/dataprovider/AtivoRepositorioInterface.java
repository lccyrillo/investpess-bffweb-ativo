package com.cyrillo.bff.investpessbffwebativo.core.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;

import java.util.List;

// Acerto exceção
public interface AtivoRepositorioInterface {

    void incluirAtivo(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao, DadosInvalidosDataProviderExcecao, AtivoJaExistenteDataProviderExcecao;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioDataProviderExcecao;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao, AtivoParametrosInvalidosUseCaseExcecao;
    void healthCheck(DataProviderInterface data) throws ComunicacaoRepositorioDataProviderExcecao;
}
