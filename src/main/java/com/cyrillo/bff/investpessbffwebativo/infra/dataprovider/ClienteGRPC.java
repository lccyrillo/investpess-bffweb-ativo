package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.usecase.excecao.AtivoParametrosInvalidosUseCaseExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.config.excecao.FalhaCriacaoCanalGRPCConfigExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.dataprovider.AtivoRepositorioImplGRPC;
import com.cyrillo.bff.investpessbffwebativo.infra.dataprovider.LogInterfaceImplConsole;
import com.cyrillo.bff.investpessbffwebativo.infra.facade.FacadeAtivo;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.ativo.ativoobjetoproto.AtivoServerServiceGrpc;


public class ClienteGRPC {


    private AtivoServerServiceGrpc.AtivoServerServiceBlockingStub instanciaClienteGRPC;
    private ManagedChannel channel;

    public ClienteGRPC() {
    }

    public void inicializaClienteGRPC() throws FalhaCriacaoCanalGRPCConfigExcecao {
        try {
            // do some stuff
            channel = ManagedChannelBuilder.forAddress("192.168.0.26", 50051).
                    usePlaintext().
                    build();
            instanciaClienteGRPC = AtivoServerServiceGrpc.newBlockingStub(channel);

        } catch (Exception e) {
            FalhaCriacaoCanalGRPCConfigExcecao falha = new FalhaCriacaoCanalGRPCConfigExcecao("Falha na criação de canal GRPC.");
            falha.addSuppressed(e);
            e.printStackTrace();
            throw falha;
        }
    }

    public void finalizaClienteGRPC() {
        channel.shutdown();
    }

    public AtivoServerServiceGrpc.AtivoServerServiceBlockingStub getInstanciaClienteGRPC() {
        return instanciaClienteGRPC;
    }


}
