package com.cyrillo.bff.investpessbffwebativo.infra.entrypoint;

import com.cyrillo.bff.investpessbffwebativo.core.entidade.LogProcessamento;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.LogProcessamentoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ativo")
public class AtivoControlador {

    @Autowired
    private LogProcessamentoServico logProcessamentoServico;

    @GetMapping
    public List<LogProcessamento> listarTodas(){
        return logProcessamentoServico.listarTodas();
    }

    @GetMapping("/{idLog}")
    public ResponseEntity<Optional<LogProcessamento>> buscarPorId(@PathVariable(name = "idLog") Long id){
        Optional<LogProcessamento> logProcessamento = logProcessamentoServico.buscaPorId(id);
        return logProcessamento.isPresent() ? ResponseEntity.ok(logProcessamento) : ResponseEntity.notFound().build();
    }

}
