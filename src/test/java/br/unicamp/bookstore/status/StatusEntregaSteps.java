//package br.unicamp.bookstore.status;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.client.WireMock.get;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.ThrowableAssert.catchThrowable;
//
//import java.util.List;
//import java.util.Map;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import com.github.tomakehurst.wiremock.WireMockServer;
//
//import br.unicamp.bookstore.Configuracao;
//import br.unicamp.bookstore.model.StatusEntrega;
//import br.unicamp.bookstore.service.StatusEntregaService;
//import cucumber.api.java.After;
//import cucumber.api.java.Before;
//import cucumber.api.java.pt.Dado;
//import cucumber.api.java.pt.Então;
//import cucumber.api.java.pt.Quando;
//
//public class StatusEntregaSteps {
//	
//	public WireMockServer wireMockServer;
//
//	@Mock
//	private Configuracao configuration;
//
//	@InjectMocks
//	private StatusEntregaService statusEntregaService;
//
//	private StatusEntrega statusEntrega;
//
//	private String codigoRastreio;
//
//	private Throwable throwable;
//	
//	@Before
//	public void setUp() {
//		wireMockServer = new WireMockServer(9876);
//		wireMockServer.start();
//		MockitoAnnotations.initMocks(this);
//		Mockito.when(configuration.getBuscarEnderecoUrl()).thenReturn("http://localhost:9876/ws");
//		statusEntrega = null;
//		codigoRastreio = null;
//		throwable = null;
//	}
//
//	@After
//	public void teardown() {
//		wireMockServer.stop();
//	}
//	
//	@Dado("^um Código de rastreio válido:$")
//	public void um_Código_de_rastreio_válido(Map<String, String> map) throws Throwable {
//		codigoRastreio = map.get("codigoRastreio");
//		wireMockServer.stubFor(get(urlMatching("/ws/"+ codigoRastreio + ".*")).willReturn(aResponse().withStatus(200)
//				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-BuscaStatus.xml")));
//	}
//
//	@Então("^o resultado deve ser as informações do rastreio:$")
//	public void o_resultado_deve_ser_as_informações_do_rastreio(List<Map<String,String>> resultado) throws Throwable {
//		assertThat(this.statusEntrega.getStatus()).isEqualTo(resultado.get(0).get("Status"));
//		assertThat(this.statusEntrega.getDescricao()).isEqualTo(resultado.get(0).get("Descricao"));
//		assertThat(throwable).isNull();
//	}
//
//	@Dado("^um Código de rastreio  não existente:$")
//	public void um_Código_de_rastreio_não_existente(Map<String, String> map) throws Throwable {
//		codigoRastreio = map.get("codigoRastreio");
//		wireMockServer.stubFor(get(urlMatching("/ws/" + codigoRastreio + ".*")).willReturn(aResponse().withStatus(200)
//				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-BuscaStatus_ERR.xml")));
//	}
//
//	@Quando("^eu informo o código de rastreio na consulta de status$")
//	public void eu_informo_o_código_de_rastreio_na_consulta_de_status() throws Throwable {
//		throwable = catchThrowable(() -> this.statusEntrega = statusEntregaService.buscar(codigoRastreio));
//	}
//
//	@Então("^o resultado deve ser o retorno da consulta:$")
//	public void o_resultado_deve_ser_o_retorno_da_consulta(List<Map<String,String>> resultado) throws Throwable {
//		assertThat(this.statusEntrega.getErro()).isEqualTo(resultado.get(0).get("erro"));
//		assertThat(throwable).isNull();
//	}
//
//	@Dado("^um código de rastreio invalido inválido:$")
//	public void um_código_de_rastreio_invalido_inválido(Map<String, String> map) throws Throwable {
//		codigoRastreio = map.get("codigoRastreio");
//		wireMockServer.stubFor(get(urlMatching("/ws/" + codigoRastreio + ".*"))
//				.willReturn(aResponse().withStatus(400).withHeader("Content-Type", "text/xml")
//						.withBodyFile("resultado-pesquisa-BuscaStatus_BAD.xml")));
//	}
//
//	@Dado("^o serviço sro não esta respondendo$")
//	public void o_serviço_sro_não_esta_respondendo() throws Throwable {
//		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
//				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-BuscaStatus_out.xml")));
//	}
//	
//	@Então("^uma exceção deve ser lançada com a mensagem de erro:$")
//	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
//		assertThat(throwable).hasMessage(message);
//	}
//	
//}
