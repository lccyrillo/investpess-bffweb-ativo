package com.cyrillo.bff.investpessbffwebativo.infra.dataprovider;

import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoDtoInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.AtivoRepositorioInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.DataProviderInterface;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.dto.AtivoDto;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.AtivoJaExistenteDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.ComunicacaoRepositorioDataProviderExcecao;
import com.cyrillo.bff.investpessbffwebativo.core.dataprovider.excecao.DadosInvalidosDataProviderExcecao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplMemoria implements AtivoRepositorioInterface {
    private List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplMemoria(){

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
