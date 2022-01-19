package com.cyrillo.bff.investpessbffwebativo.infra.config;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.infra.dataprovider.AtivoRepositorioImplGRPC;
import com.cyrillo.bff.investpessbffwebativo.infra.dataprovider.ClienteGRPC;
import com.cyrillo.bff.investpessbffwebativo.infra.dataprovider.LogInterfaceImplConsole;
import com.cyrillo.bff.investpessbffwebativo.infra.facade.FacadeAtivo;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.entidade"})
@EnableJpaRepositories(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.repositorio"})
@ComponentScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.usecase","com.cyrillo.bff.investpessbffwebativo.infra.entrypoint"})
@SpringBootApplication
public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;
    private AtivoRepositorioInterface ativoRepositorio;
    private LogInterface logAplicacao;

    private ClienteGRPC clienteGRPC;

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
            FacadeAtivo facadeAtivo = FacadeAtivo.getInstance();
            facadeAtivo.setDataProviderInterface(this);
            // cria um canal cliente para chamada GRPC
            clienteGRPC = new ClienteGRPC();
            clienteGRPC.inicializaClienteGRPC();



            //this.ativoRepositorio = new AtivoRepositorioImplMemoria();
            this.ativoRepositorio = new AtivoRepositorioImplGRPC();
            this.logAplicacao = new LogInterfaceImplConsole();
        }
        catch (Exception e){
          System.out.println("Não foi possível inicializar a aplicação.");
          e.printStackTrace();
        }
        // Precisa colocar um tratamento aqui para não iniciar a aplicação se der erro na exceção.
        SpringApplication.run(Aplicacao.class,args);
    }

    public void finalizaAplicacao(){
        this.clienteGRPC.finalizaClienteGRPC();
    }


    public ClienteGRPC getClienteGRPC() {
        return clienteGRPC;
    }




    public UUID getUniqueKey(){
        return null;
    }

    @Override
    public int getTimeOutDefault() {
        return 5;
    }

    @Override
    public DataProviderInterface geraSessao(){
        return new Sessao();
    }


    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return true;
    }

    public LogInterface getLoggingInterface() {
        return this.logAplicacao;
    }

    @Override
    public AtivoRepositorioInterface getAtivoRepositorio() { return this.ativoRepositorio;}

    public LogInterface gerarNovoObjetoLog() {
        // deve ler parametros de configurar e instanciar a implementação correta de log
        // Por enquanto so tem uma implementacao
        // pode ser usado para o log de cada sessao / requisição
        return new LogInterfaceImplConsole();
    }



}
