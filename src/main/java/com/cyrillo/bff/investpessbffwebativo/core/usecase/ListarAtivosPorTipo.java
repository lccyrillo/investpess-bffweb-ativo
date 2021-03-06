package com.cyrillo.bff.investpessbffwebativo.core.usecase;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.*;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

import java.util.ArrayList;
import java.util.List;

public class ListarAtivosPorTipo {
    public ListarAtivosPorTipo() {
    }

    public List<AtivoDtoInterface> executar(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey = String.valueOf(data.getUniqueKey());
        try {
            List<AtivoDtoInterface> listaAtivos = new ArrayList<AtivoDtoInterface>();
            listaAtivos = repo.listarAtivosPorTipo(data, tipoAtivo);
            if (listaAtivos == null) {
                listaAtivos = new ArrayList<AtivoDtoInterface>();
            }
            return listaAtivos;
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


