package com.cyrillo.bff.investpessbffwebativo.infra.config;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.ConexaoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;

import java.util.UUID;

public class Sessao implements DataProviderInterface {

    private UUID uniqueKey;
    private LogInterface log;

    public Sessao(){
        this.uniqueKey = UUID.randomUUID();
        this.log = Aplicacao.getInstance().gerarNovoObjetoLog();
    }

    public UUID getUniqueKey() {
        return uniqueKey;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return Aplicacao.getInstance().healthCheckOk(data);
    }

    @Override
    public int getTimeOutDefault() {
        return Aplicacao.getInstance().getTimeOutDefault();
    }

    @Override
    public DataProviderInterface geraSessao() {
        return this;
    }


    public LogInterface getLoggingInterface() {
        return this.log;
    }


    public AtivoRepositorioInterface getAtivoRepositorio() {
        return Aplicacao.getInstance().getAtivoRepositorio();
    }


    public ClienteGRPC getClienteGRPC() {
        return Aplicacao.getInstance().getClienteGRPC();
    }

}
