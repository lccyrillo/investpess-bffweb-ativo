package com.cyrillo.bff.investpessbffwebativo.core.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.infra.config.ClienteGRPC;

import java.util.UUID;

public interface DataProviderInterface {
    public UUID getUniqueKey();
    public boolean healthCheckOk(DataProviderInterface data);
    public int getTimeOutDefault();
    public DataProviderInterface geraSessao();
    public LogInterface getLoggingInterface();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public ClienteGRPC getClienteGRPC();
}
