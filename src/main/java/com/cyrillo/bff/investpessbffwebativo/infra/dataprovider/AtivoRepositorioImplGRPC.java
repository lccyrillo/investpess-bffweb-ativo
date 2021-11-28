package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import proto.ativo.ativoobjetoproto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplGRPC implements AtivoRepositorioInterface {
    private List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplGRPC(){

        System.out.println("Hello I am Client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50051).
                usePlaintext().
                build();
        //System.out.println("Creating a Stub");

        AtivoServerServiceGrpc.AtivoServerServiceBlockingStub client = AtivoServerServiceGrpc.newBlockingStub(channel);

        AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("60.872.5----001-23").
                setNomeAtivo("ITAU UNIBANCO HOLDING S.A.").
                setSiglaAtivo("ITUB4").
                build();

        CadastraAtivoObjetoRequest request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        CadastraAtivoObjetoResponse response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());


        ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("60.872.504/0001-23").
                setNomeAtivo("ITAU UNIBANCO HOLDING S.A.").
                setSiglaAtivo("ITUB4").
                build();

        request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());


        ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("00.000.000/0001-91").
                setNomeAtivo("BANCO DO BRASIL S.A").
                setSiglaAtivo("BBAS3").
                build();

        request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());




        ConsultaListaAtivoRequest request2 = ConsultaListaAtivoRequest.newBuilder()
                .setTipoAtivo(1)
                .build();
        ConsultaListaAtivoResponse response2 = client.consultaListaAtivo(request2);
        String resultadoProcessamento = "Resultado Processamento: " + response2.getResponseCode() + " - " + response2.getResponseMessage();
        System.out.println(resultadoProcessamento);
        // faz loop pela lista de ativos
        int totalItens = response2.getAtivosCount();

        for (int i =0; i <totalItens; i++) {
            System.out.println(response2.getAtivos(i).getSiglaAtivo() + " - " + response2.getAtivos(i).getNomeAtivo());
        }
        System.out.println("Shutting down de client");
        channel.shutdown();


    }

    @Override
    public void incluirAtivo(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao, DadosInvalidosDataProviderExcecao, AtivoJaExistenteDataProviderExcecao {

        if (descricaoCNPJAtivo == null || sigla == null || nomeAtivo == null  ) {
            throw new DadosInvalidosDataProviderExcecao("Sigla, nome do ativo ou CPNPJ não podem ser nulos!");
        }
        else if (this.consultarPorSigla(data, sigla) == true) {
            throw new AtivoJaExistenteDataProviderExcecao("Ativo já existente!");
        }
        else {
            AtivoDtoInterface ativoObjeto = new AtivoDto(sigla, nomeAtivo, descricaoCNPJAtivo, tipoAtivo);
            this.listaAtivoObjeto.add(ativoObjeto);
        }
    }

    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepositorioDataProviderExcecao {
        this.listaAtivoObjeto.add(ativoObjeto);
    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioDataProviderExcecao {
        if (this.listaAtivoObjeto.stream()
            .filter(a -> a.getSigla().equals(siglaAtivo))
            .findFirst().isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return this.listaAtivoObjeto;

    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) {

        return this.listaAtivoObjeto.stream()
                .filter(a -> a.getTipoAtivoInt() == tipoAtivo)
                .collect(Collectors.toList());
    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepositorioDataProviderExcecao {

    }
}
