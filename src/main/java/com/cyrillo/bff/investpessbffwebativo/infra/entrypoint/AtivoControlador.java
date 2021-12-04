package com.cyrillo.bff.investpessbffwebativo.infra.entrypoint;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.entidade.LogProcessamento;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.IncluirNovoAtivo;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.LogProcessamentoServico;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoJaExistenteUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.config.Aplicacao;
import com.cyrillo.bff.investpessbffwebativo.infra.entrypoint.inout.AtivoRequest;
import com.cyrillo.bff.investpessbffwebativo.infra.entrypoint.inout.AtivoResponse;
import com.cyrillo.bff.investpessbffwebativo.infra.facade.FacadeAtivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ativo")
public class AtivoControlador {

    @Autowired
    private LogProcessamentoServico logProcessamentoServico;

    @PostMapping
    public ResponseEntity<AtivoResponse> incluirAtivo(@RequestBody AtivoRequest ativoRequest)  {
        // Respostas de informação (100-199),
        //    Respostas de sucesso (200-299),
        //    Redirecionamentos (300-399)
        //    Erros do cliente (400-499)
        //    Erros do servidor (500-599).

        String msgResultado;
        HttpStatus codResultado;
        DataProviderInterface dataProvider = FacadeAtivo.getInstance().getDataProviderInterface();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();
        log.logInfo(null,"Entrou no método controller incluir ativo");
        try {
            // use case
            DataProviderInterface sessao = dataProvider.geraSessao();
            FacadeAtivo.getInstance().executarIncluirNovoAtivo(sessao,ativoRequest.getSigla(), ativoRequest.getNomeAtivo(), ativoRequest.getDescricaoCNPJAtivo(), ativoRequest.getTipoAtivoInt());
            codResultado = HttpStatus.CREATED;
            msgResultado = "Ativo criado com sucesso";
            // 201
        }
        catch (AtivoJaExistenteUseCaseExcecao e) {
            codResultado = HttpStatus.ALREADY_REPORTED;
            msgResultado = "Ativo já existente no repositório";
            // 208
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = HttpStatus.SERVICE_UNAVAILABLE;
            msgResultado = "Erro na comunicação com o Repositório!";
            //503 Service Unavailable
        }
        catch (AtivoParametrosInvalidosUseCaseExcecao e) {
            codResultado = HttpStatus.UNPROCESSABLE_ENTITY;
            msgResultado = "Parâmetros inválido enviado na consulta";
            // 422
        }
        catch(Exception e){
            codResultado = HttpStatus.INTERNAL_SERVER_ERROR;
            msgResultado = "Erro não identificado" + e.getMessage();
            // 500 Internal Server Error
        }
        log.logInfo(null,"Controller ativo. Formatando response.");
        //ResponseEntity<AtivoDto> responseEntity = new ResponseEntity<>();
        AtivoResponse ativoResponse = new AtivoResponse(ativoRequest,codResultado.value(),msgResultado);
        log.logInfo(null,"Controller ativo. Resposta formatada.");
        return ResponseEntity.status(codResultado).body(ativoResponse);
    }




    @GetMapping
    public ResponseEntity<List<AtivoDtoInterface>> listarTodas()  {
    //public List<AtivoDtoInterface> listarTodas(){


        // 200 - Lista gerada com sucesso
        // 201 - Lista vazia
        // 401 - Erro na comunicação com repositório
        // 402 - Tipo Ativo inválido enviado na consulta
        // 500 - Erro técnico não identificado.

        String msgResultado;
        int codResultado;
        FacadeAtivo facadeAtivo = FacadeAtivo.getInstance();

        DataProviderInterface dataProvider = facadeAtivo.getDataProviderInterface();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();
        List<AtivoDtoInterface> lista = null;

        log.logInfo(uniqueKey,"Iniciando API GRPC de Consulta de ativos.");
        int tipoAtivo = 1;
        log.logInfo(uniqueKey,"Dados da Request de Lista de Ativos identificados");

        try {
                // use case
                DataProviderInterface sessao = dataProvider.geraSessao();
                lista = FacadeAtivo.getInstance().executarListarAtivosPorTipo(sessao,tipoAtivo);
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
            codResultado = 503;
            msgResultado = "Erro na comunicação com o Repositório!";

            //codResultado = HttpStatus.SERVICE_UNAVAILABLE;
            //msgResultado = "Erro na comunicação com o Repositório!";
            //503 Service Unavailable


        }
        catch (AtivoParametrosInvalidosUseCaseExcecao e) {
            codResultado = 402;
            msgResultado = "Tipo Ativo inválido enviado na consulta";
        }
        catch(Exception e){
            codResultado = 500;
            msgResultado = "Erro não identificado" + e.getMessage();
        }

        log.logInfo(null,"Controller ativo. Resposta formatada.");
        return ResponseEntity.status(codResultado).body(lista);

        //return lista;
    }

    @GetMapping("/{idLog}")
    public ResponseEntity<Optional<LogProcessamento>> buscarPorId(@PathVariable(name = "idLog") Long id){
        Optional<LogProcessamento> logProcessamento = logProcessamentoServico.buscaPorId(id);
        return logProcessamento.isPresent() ? ResponseEntity.ok(logProcessamento) : ResponseEntity.notFound().build();
    }

}
