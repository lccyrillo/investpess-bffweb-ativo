package com.cyrillo.bff.investpessbffwebativo.core.usecase;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public void executar(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteUseCaseExcecao, ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao  {
        // Mapa de resultados do use case
        AtivoRepositorioInterface ativoRepositorio = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey =String.valueOf(data.getUniqueKey());

        log.logInfo(uniqueKey,"Iniciando Use Case Incluir Novo Ativo");

        if (sigla != null) {
            sigla = sigla.toUpperCase();
        }

        try {
            ativoRepositorio.incluirAtivo(data,sigla,nomeAtivo,descricaoCNPJAtivo,tipoAtivo);
        }
        catch (AtivoJaExistenteDataProviderExcecao e) {
            AtivoJaExistenteUseCaseExcecao falha = new AtivoJaExistenteUseCaseExcecao("Ativo já existente!");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Ativo já existente.");
            e.printStackTrace();
            throw falha;
        }
        catch (DadosInvalidosDataProviderExcecao e) {
            AtivoParametrosInvalidosUseCaseExcecao falha = new AtivoParametrosInvalidosUseCaseExcecao("Dados inválidos da solicitação.");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Dados inválidos da solicitação.");
            e.printStackTrace();
            throw falha;
        }
        catch (ComunicacaoRepositorioDataProviderExcecao e) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Erro na comunicação com repositório.");
            e.printStackTrace();
            throw falha;
        }
    }
}
