package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.PrecoPrazo;

public class PrecoPrazoService {

    private Configuracao configuracao;

    public PrecoPrazo buscar(String parameters) throws Exception {
        String url = String.format("%s/%s", configuracao.getConsultaPrecoPrazoUrl(), parameters);
        return new RemoteService().getAndParseXml(url, PrecoPrazo.class);
    }

}
