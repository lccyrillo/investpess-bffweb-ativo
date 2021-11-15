package com.cyrillo.bff.investpessbffwebativo;

import com.cyrillo.bff.investpessbffwebativo.infra.config.Aplicacao;

public class InvestpessBffwebAtivoApplication {

	public static void main(String[] args) {
		Aplicacao.getInstance().inicializaAplicacao(args);
	}

}
