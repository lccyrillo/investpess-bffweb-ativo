package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;


public class LogInterfaceImplConsole implements LogInterface {
    private static LogInterfaceImplConsole instance;

    public LogInterfaceImplConsole() {
    }

    public static LogInterfaceImplConsole getInstance(){
        if(instance == null){
            synchronized (LogInterfaceImplConsole.class) {
                if(instance == null){
                    instance = new LogInterfaceImplConsole();
                }
            }

        }
        return instance;
    }

    @Override
    public void logError(String flowid, String mensagem) {
        System.out.println("Level: Error | Flowid: " + flowid + " Mensagem: " +mensagem);
    }

    @Override
    public void logInfo(String flowid, String mensagem) {
        System.out.println("Level: Info | Flowid: " + flowid + " Mensagem: " +mensagem);
    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao {

    }
}
