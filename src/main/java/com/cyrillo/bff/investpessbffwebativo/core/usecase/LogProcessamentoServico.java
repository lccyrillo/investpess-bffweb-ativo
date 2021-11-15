package com.cyrillo.bff.investpessbffwebativo.core.usecase;

import com.cyrillo.bff.investpessbffwebativo.core.entidade.LogProcessamento;
import com.cyrillo.bff.investpessbffwebativo.core.repositorio.LogProcessamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogProcessamentoServico {
    @Autowired
    private LogProcessamentoRepositorio logProcessamentoRepositorio;

    public List<LogProcessamento> listarTodas() {
        return logProcessamentoRepositorio.findAll();
    }

    public Optional<LogProcessamento> buscaPorId(Long id){
        return logProcessamentoRepositorio.findById(id);
    }

}



