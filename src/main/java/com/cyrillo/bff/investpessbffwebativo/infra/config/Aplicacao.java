package com.cyrillo.bff.investpessbffwebativo.infra.config;

import com.cyrillo.bff.investpessbffwebativo.InvestpessBffwebAtivoApplication;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@EntityScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.entidade"})
@EnableJpaRepositories(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.repositorio"})
@ComponentScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.usecase","com.cyrillo.bff.investpessbffwebativo.infra.entrypoint"})
@SpringBootApplication
public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;

    public static Aplicacao getInstance(){
        if(instance == null){
            synchronized (Aplicacao.class) {
                if(instance == null){
                    instance = new Aplicacao();
                }
            }
        }
        return instance;
    }

    public void inicializaAplicacao(String[] args){
        try {
            // do some stuff
        }
        catch (Exception e){
          System.out.println("Não foi possível inicializar a aplicação.");
          e.printStackTrace();
        }
        SpringApplication.run(Aplicacao.class,args);
    }

    public UUID getUniqueKey(){
        return null;
    }

    @Override
    public int getTimeOutDefault() {
        return 5;
    }

    @Override
    public DataProviderInterface geraSessao() {
        return null;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return true;
    }

}
