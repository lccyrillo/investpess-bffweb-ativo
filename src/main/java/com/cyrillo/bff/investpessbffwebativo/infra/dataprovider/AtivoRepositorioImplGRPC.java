package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.LogInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.infra.facade.FacadeAtivo;
import proto.ativo.ativoobjetoproto.*;



import java.util.ArrayList;
import java.util.List;

public class AtivoRepositorioImplGRPC implements AtivoRepositorioInterface {

    public AtivoRepositorioImplGRPC(){




    }

    @Override
    public void incluirAtivo(DataProviderInterface data,String sigla, String nomeAtivo, String descricaoCNPJAtivo, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao, DadosInvalidosDataProviderExcecao, AtivoJaExistenteDataProviderExcecao {
        int responseCode;

        String uniqueKey = String.valueOf(data.getUniqueKey());
        LogInterface log = data.getLoggingInterface();
        log.logInfo(uniqueKey,"Entrou em AtivoRepositorioImplGRPC.incluirAtivo");

        if (descricaoCNPJAtivo == null || sigla == null || nomeAtivo == null  ) {
            responseCode = 422; //
        }
        else {
            try {
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
                log.logInfo(uniqueKey,response.getResponseCode() + " - " + response.getResponseMessage());

                responseCode = response.getResponseCode();
            } catch (Exception e) {
                ComunicacaoRepositorioDataProviderExcecao falha = new ComunicacaoRepositorioDataProviderExcecao("Falha na comunicação com servidor GRPC!");
                falha.addSuppressed(e);
                e.printStackTrace();
                throw falha;
            }
        }

        if (responseCode == 200) {
            // Sucesso!
        }
        else if (responseCode == 101) {
            throw new AtivoJaExistenteDataProviderExcecao("Ativo já existente!");
        }
        else if (responseCode == 102) {
            throw new DadosInvalidosDataProviderExcecao("CNPJ inválido!");
        }
        else if (responseCode == 422) {
            throw new DadosInvalidosDataProviderExcecao("Sigla, nome do ativo ou CPNPJ não podem ser nulos!");
        }
        else {
            throw new ComunicacaoRepositorioDataProviderExcecao("Falha na comunicação com servidor GRPC!");
        }
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
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepositorioDataProviderExcecao {
        List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

        try {

            ClienteGRPC clienteGRPC = data.getClienteGRPC();
            AtivoServerServiceGrpc.AtivoServerServiceBlockingStub client = clienteGRPC.getInstanciaClienteGRPC();

            ConsultaListaAtivoRequest requestListaAtivosPorTipo = ConsultaListaAtivoRequest.newBuilder()
                    .setTipoAtivo(tipoAtivo)
                    .build();
            ConsultaListaAtivoResponse responseListaAtivosPorTipo = client.consultaListaAtivo(requestListaAtivosPorTipo);
            //String resultadoProcessamento = "Resultado Processamento: " + response2.getResponseCode() + " - " + response2.getResponseMessage();
            //System.out.println(resultadoProcessamento);
            // faz loop pela lista de ativos
            int totalItens = responseListaAtivosPorTipo.getAtivosCount();

            AtivoDtoInterface ativoObjetoDto;
            for (int i = 0; i < totalItens; i++) {

                ativoObjetoDto = new AtivoDto(responseListaAtivosPorTipo.getAtivos(i).getSiglaAtivo(), responseListaAtivosPorTipo.getAtivos(i).getNomeAtivo(), responseListaAtivosPorTipo.getAtivos(i).getDescricaoCnpjAtivo(), responseListaAtivosPorTipo.getAtivos(i).getTipoAtivo());
                listaAtivoObjeto.add(ativoObjetoDto);
                //System.out.println(responseListaAtivosPorTipo.getAtivos(i).getSiglaAtivo() + " - " + responseListaAtivosPorTipo.getAtivos(i).getNomeAtivo());
            }
        }
        catch (Exception e) {
            ComunicacaoRepositorioDataProviderExcecao falha = new ComunicacaoRepositorioDataProviderExcecao("Falha na comunicação com servidor GRPC!");
            falha.addSuppressed(e);
            //log.logError(uniqueKey, "Dados inválidos da solicitação.");
            e.printStackTrace();
            throw falha;
        }
        return listaAtivoObjeto;
    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepositorioDataProviderExcecao {

    }
}
