package br.unicamp.bookstore.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.WireMockServer;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.Endereco;
import br.unicamp.bookstore.model.StatusEntrega;
import br.unicamp.bookstore.service.BuscaEnderecoService;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class StatusEntregaSteps {
	
	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private BuscaEnderecoService buscaEnderecoService;

	private StatusEntrega statusEntrega;

	private String codigoRastreio;

	private Throwable throwable;
	
	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getBuscarEnderecoUrl()).thenReturn("http://localhost:9876/ws");
		statusEntrega = null;
		codigoRastreio = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}
	
	@Dado("^um Código de rastreio válido:$")
	public void um_Código_de_rastreio_válido(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Quando("^eu informo o codigo de rastreio na consulta de status$")
	public void eu_informo_o_codigo_de_rastreio_na_consulta_de_status() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Então("^o resultado deve ser as informações do rastreio:$")
	public void o_resultado_deve_ser_as_informações_do_rastreio(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Dado("^um Código de rastreio  não existente:$")
	public void um_Código_de_rastreio_não_existente(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Quando("^eu informo o código de rastreio na consulta de status$")
	public void eu_informo_o_código_de_rastreio_na_consulta_de_status() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Então("^o resultado deve ser o retorno da consulta:$")
	public void o_resultado_deve_ser_o_retorno_da_consulta(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Dado("^um código de rastreio invalido inválido:$")
	public void um_código_de_rastreio_invalido_inválido(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Quando("^eu informo o código de rastreio inválido:$")
	public void eu_informo_o_código_de_rastreio_inválido() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Dado("^um código de rastreio válido:$")
	public void um_código_de_rastreio_válido(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Dado("^o serviço sro não esta respondendo$")
	public void o_serviço_sro_não_esta_respondendo() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	
}
