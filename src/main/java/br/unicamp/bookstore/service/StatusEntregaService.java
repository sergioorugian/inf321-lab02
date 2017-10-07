package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.StatusEntrega;

public class StatusEntregaService {
	private Configuracao configuracao;

	  public StatusEntrega buscar(String codigoRastreio) throws Exception {
	    String url = String.format("%s/%s/xml", configuracao.getStatusEntregaUrl(), codigoRastreio);
	    return new RemoteService().getAndParseXml(url, StatusEntrega.class);
	  }
}
