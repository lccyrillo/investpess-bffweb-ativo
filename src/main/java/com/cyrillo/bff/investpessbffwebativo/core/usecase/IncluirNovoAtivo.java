package com.cyrillo.bff.investpessbffwebativo.core.usecase;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.AtivoObjeto;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroCNPJInvalidoEntExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.excecao.ParametroTipoInvalidoEntExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

public class IncluirNovoAtivo {

    public IncluirNovoAtivo(){}

    public void executar(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws AtivoJaExistenteUseCaseExcecao, ComunicacaoRepoUseCaseExcecao, AtivoParametrosInvalidosUseCaseExcecao {
        // Mapa de resultados do use case
        AtivoRepositorioInterface repo = data.getAtivoRepositorio();
        LogInterface log = data.getLoggingInterface();
        String uniqueKey =String.valueOf(data.getUniqueKey());

        log.logInfo(uniqueKey,"Iniciando Use Case Incluir Novo Ativo");

        sigla = sigla.toUpperCase();
        try {
            if (repo.consultarPorSigla(data, sigla) == false) {
                // --> Se a consulta falhar na comunicação com banco de dados, vai gerar uma exceção que precisará ser tratada.
                // Posso cadastrar ativo
                AtivoDto ativoDto = new AtivoDto(sigla, nomeAtivo, descricaoCNPJAtivo, tipoAtivo);
                // Faço esse passo para garantir o Dto está criando um objeto válido.
                AtivoObjeto ativoObjeto = ativoDto.builder();
                repo.incluir(data, ativoDto);
                log.logInfo(uniqueKey, "Ativo incluído com sucesso");
            }
            else {
                // Erro: Sigla já existente
                // Lançar exceção AtivoJaExistenteException
                log.logInfo(uniqueKey, "Ativo já existe no repositório");
                throw new AtivoJaExistenteUseCaseExcecao(sigla);
            }
        }
        catch (ParametroCNPJInvalidoEntExcecao e){
            log.logError(uniqueKey,"CNPJ Inválido");
            e.printStackTrace();
            throw new AtivoParametrosInvalidosUseCaseExcecao("CNPJ Inválido");
        }
        catch (ParametroTipoInvalidoEntExcecao e){
            log.logError(uniqueKey,"Tipo Inválido");
            e.printStackTrace();
            throw new AtivoParametrosInvalidosUseCaseExcecao("Tipo Inválido");
        }
        catch (ComunicacaoRepoDataProvExcecao e) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Erro na comunicação com repositório.");
            e.printStackTrace();
            throw falha;
        }
    }
}
