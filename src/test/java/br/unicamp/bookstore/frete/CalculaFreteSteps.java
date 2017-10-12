package br.unicamp.bookstore.frete;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.WireMockServer;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.PrecoPrazo;
import br.unicamp.bookstore.service.PrecoPrazoService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class CalculaFreteSteps {
	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private PrecoPrazoService precoPrazoService;

	private PrecoPrazo precoPrazo;
	
	private String cep;

	private Throwable throwable;
	

	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9876/ws");
		precoPrazo = null;
		cep = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}
	
	@Quando("^eu informo o CEP no carrinho de compras$")
	public void eu_informo_o_CEP_na_busca_de_endereco() throws Throwable {
		throwable = catchThrowable(() -> this.precoPrazo = precoPrazoService.buscar(cep));
	}
	
	@Entao("^o resultado deve ser o prazo e valor:$")
	public void o_resultado_deve_ser_o_endereco(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(this.precoPrazo.getPrazoEntrega()).isEqualTo(resultado.get(0).get("PrazoEntrega"));
		assertThat(this.precoPrazo.getValor()).isEqualTo(resultado.get(0).get("Valor"));
		assertThat(this.precoPrazo.getCodigo()).isEqualTo(resultado.get(0).get("Codigo"));
		assertThat(this.precoPrazo.getEntregaDomiciliar()).isEqualTo(resultado.get(0).get("EntregaDomiciliar"));
		assertThat(this.precoPrazo.getEntregaSabado()).isEqualTo(resultado.get(0).get("EntregaSabado"));
		assertThat(this.precoPrazo.getValorAvisoRecebimento()).isEqualTo(resultado.get(0).get("ValorAvisoRecebimento"));
		assertThat(this.precoPrazo.getValorMaoPropria()).isEqualTo(resultado.get(0).get("ValorMaoPropria"));
		assertThat(this.precoPrazo.getValorFrete()).isEqualTo(resultado.get(0).get("ValorSemAdicionais"));
		assertThat(this.precoPrazo.getValorValorDeclarado()).isEqualTo(resultado.get(0).get("ValorDeclarado"));
		
		assertThat(throwable).isNull();
	}
	
	@Entao("^o retorno deve conter um valor de erro igual a \"-3\"")
	public void o_resultado_deve_ser_erro(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(("-3") == resultado.get(0).get("Erro"));
	}
	
	@Entao("^o retorno deve conter um valor de erro igual a \"7\"")
	public void o_resultado_deve_ser_excecao(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(("7") == resultado.get(0).get("Erro"));
	}
	
	@Entao("^uma excecao deve ser lancada com a mensagem de erro:$")
	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
		assertThat(throwable).hasMessage(message);
	}
	
	@E("^o servico ViaCep nao esta respondendo$")
	public void o_servico_via_cep_nao_esta_respondendo() throws Throwable {
		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-PrecoPrazo_out.xml")));
	}
}
