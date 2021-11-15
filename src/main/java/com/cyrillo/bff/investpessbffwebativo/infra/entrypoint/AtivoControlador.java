package com.cyrillo.bff.investpessbffwebativo.infra.entrypoint;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.LogProcessamento;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.LogProcessamentoServico;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.config.Aplicacao;
import com.cyrillo.bff.investpessbffwebativo.infra.facade.FacadeAtivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ativo")
public class AtivoControlador {

    @Autowired
    private LogProcessamentoServico logProcessamentoServico;

    @GetMapping
    public List<AtivoDtoInterface> listarTodas(){


        // 200 - Lista gerada com sucesso
        // 201 - Lista vazia
        // 401 - Erro na comunicação com repositório
        // 402 - Tipo Ativo inválido enviado na consulta
        // 500 - Erro técnico não identificado.

        String msgResultado;
        int codResultado;

        DataProviderInterface dataProvider = Aplicacao.getInstance();
        //data.geraSessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();
        List<AtivoDtoInterface> lista = null;

        log.logInfo(uniqueKey,"Iniciando API GRPC de Consulta de ativos.");
        int tipoAtivo = 1;
        log.logInfo(uniqueKey,"Dados da Request de Lista de Ativos identificados");

        try {
            // use case

            new IncluirNovoAtivo().executar(dataProvider,"'ITUB4","iTAU","60.872.504/0001-23",tipoAtivo);

            lista = new FacadeAtivo().executarListarAtivosPorTipo(dataProvider,tipoAtivo);
            if (lista.size() == 0) {
                codResultado = 201;
                msgResultado = "Lista Vazia.";
            }
            else {
                codResultado = 200;
                msgResultado = "Lista ok";
            }
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com o Repositório!";
        }
        catch (AtivoParametrosInvalidosUseCaseExcecao e) {
            codResultado = 402;
            msgResultado = "Tipo Ativo inválido enviado na consulta";
        }
        catch(Exception e){
            codResultado = 500;
            msgResultado = "Erro não identificado" + e.getMessage();
        }



        return lista;
    }

    @GetMapping("/{idLog}")
    public ResponseEntity<Optional<LogProcessamento>> buscarPorId(@PathVariable(name = "idLog") Long id){
        Optional<LogProcessamento> logProcessamento = logProcessamentoServico.buscaPorId(id);
        return logProcessamento.isPresent() ? ResponseEntity.ok(logProcessamento) : ResponseEntity.notFound().build();
    }

}
