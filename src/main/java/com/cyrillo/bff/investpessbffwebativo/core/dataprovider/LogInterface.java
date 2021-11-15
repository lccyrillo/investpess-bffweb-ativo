package com.cyrillo.bff.investpessbffwebativo.core.dataprovider;


import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;

public interface LogInterface {
    public void logError(String flowid, String mensagem);
    public void logInfo(String flowid, String mensagem);
    void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao;

}
