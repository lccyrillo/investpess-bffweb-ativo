package com.cyrillo.bff.investpessbffwebativo.core.dataprovider;

import java.util.UUID;

public interface DataProviderInterface {
    public UUID getUniqueKey();
    public boolean healthCheckOk(DataProviderInterface data);
    public int getTimeOutDefault();
    public DataProviderInterface geraSessao();
    public LogInterface getLoggingInterface();
    public AtivoRepositorioInterface getAtivoRepositorio();
}
