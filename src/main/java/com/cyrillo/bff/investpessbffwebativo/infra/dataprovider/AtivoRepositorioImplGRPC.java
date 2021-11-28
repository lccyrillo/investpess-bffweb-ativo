package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.config.ClienteGRPC;
import proto.ativo.ativoobjetoproto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplGRPC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplGRPC(){




    }

    @Override
    public void incluirAtivo(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao, DadosInvalidosDataProviderExcecao, AtivoJaExistenteDataProviderExcecao {

        ClienteGRPC clienteGRPC = data.getClienteGRPC();
        AtivoServerServiceGrpc.AtivoServerServiceBlockingStub client = clienteGRPC.getInstanciaClienteGRPC();


        AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(tipoAtivo).
                setDescricaoCnpjAtivo(descricaoCNPJAtivo).
                setNomeAtivo(nomeAtivo).
                setSiglaAtivo(sigla).
                build();

        CadastraAtivoObjetoRequest request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        CadastraAtivoObjetoResponse response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());




        if (descricaoCNPJAtivo == null || sigla == null || nomeAtivo == null  ) {
            throw new DadosInvalidosDataProviderExcecao("Sigla, nome do ativo ou CPNPJ não podem ser nulos!");
        }
        //else if (this.consultarPorSigla(data, sigla) == true) {
        //    throw new AtivoJaExistenteDataProviderExcecao("Ativo já existente!");
       // }
    }


    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepositorioDataProviderExcecao {
            return false;

    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return null;

    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) {

        ClienteGRPC clienteGRPC = data.getClienteGRPC();
        AtivoServerServiceGrpc.AtivoServerServiceBlockingStub client = clienteGRPC.getInstanciaClienteGRPC();


        ConsultaListaAtivoRequest request2 = ConsultaListaAtivoRequest.newBuilder()
                .setTipoAtivo(1)
                .build();
        ConsultaListaAtivoResponse response2 = client.consultaListaAtivo(request2);
        String resultadoProcessamento = "Resultado Processamento: " + response2.getResponseCode() + " - " + response2.getResponseMessage();
        System.out.println(resultadoProcessamento);
        // faz loop pela lista de ativos
        int totalItens = response2.getAtivosCount();

        AtivoDtoInterface ativoObjetoDto;
        List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();
        for (int i =0; i <totalItens; i++) {

            ativoObjetoDto = new AtivoDto(response2.getAtivos(i).getSiglaAtivo(), response2.getAtivos(i).getNomeAtivo(), response2.getAtivos(i).getDescricaoCnpjAtivo(), response2.getAtivos(i).getTipoAtivo());
            listaAtivoObjeto.add(ativoObjetoDto);
            System.out.println(response2.getAtivos(i).getSiglaAtivo() + " - " + response2.getAtivos(i).getNomeAtivo());
        }


        return listaAtivoObjeto;


    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepositorioDataProviderExcecao {

    }
}
