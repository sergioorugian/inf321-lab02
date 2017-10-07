package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.Endereco;
import br.unicamp.bookstore.model.PrecoPrazo;

public class PrecoPrazoService {

  private Configuracao configuracao;

  public PrecoPrazo buscar(String cep) throws Exception {
    String url = String.format("%s/%s/xml", configuracao.getConsultaPrecoPrazoUrl(), cep);
    return new RemoteService().getAndParseXml(url, PrecoPrazo.class);
  }

}
