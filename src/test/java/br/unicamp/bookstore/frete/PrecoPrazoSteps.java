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
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import java.util.Iterator;

public class PrecoPrazoSteps {

    public WireMockServer wireMockServer;

    @Mock
    private Configuracao configuration;

    @InjectMocks
    private PrecoPrazoService precoPrazoService;

    private PrecoPrazo precoPrazo;

    private String cep;
    private String peso;
    private String largura;
    private String altura;
    private String comprimento;
    private String tipoEntrega;

    private Throwable throwable;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(9876);
        wireMockServer.start();
        MockitoAnnotations.initMocks(this);
        Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9876/calculator");
        //Mockito.when(configuration.getBuscarEnderecoUrl()).thenReturn("http://localhost:9876/ws");
        precoPrazo = null;
        throwable = null;
    }

    @After
    public void teardown() {
        wireMockServer.stop();
    }

    @Dado("^os dados validos:$")
    public void os_dados_validos(List<Map<String, String>> list) throws Throwable {
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> mapItem = list.get(i);

            cep = mapItem.get("cep");
            peso = mapItem.get("peso");
            largura = mapItem.get("largura");
            altura = mapItem.get("altura");
            comprimento = mapItem.get("comprimento");
            tipoEntrega = mapItem.get("tipoEntrega");

            wireMockServer.stubFor(get(urlMatching("/calculator/" + cep + ".*")).willReturn(aResponse().withStatus(200)
                    .withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-PrecoPrazo.xml")));
        }
    }

    @Quando("^eu informo o CEP no carrinho de compras$")
    public void eu_informo_o_CEP_no_carrinho_de_compras() throws Throwable {
        // throwable = catchThrowable(() -> this.precoPrazo = precoPrazoService.buscar(getUrl()));
        throwable = catchThrowable(() -> this.precoPrazo = precoPrazoService.buscar(cep));
    }

    @Entao("^o resultado deve ser o prazo e valor:$")
    public void o_resultado_deve_ser_o_prazo_e_valor(List<Map<String, String>> list) throws Throwable {

        for (int i = 0; i < list.size(); i++) {
            Map<String, String> mapItem = list.get(i);
            assertThat(this.precoPrazo.getCodigo()).isEqualTo(Integer.parseInt(mapItem.get("Codigo")));
            assertThat(this.precoPrazo.getValor()).isEqualTo(mapItem.get("Valor"));
            assertThat(this.precoPrazo.getPrazoEntrega()).isEqualTo(Integer.parseInt(mapItem.get("PrazoEntrega")));
        }

        assertThat(throwable).isNull();
    }

    @Dado("^um CEP nao existente:$")
    public void um_CEP_nao_existente(Map<String, String> map) throws Throwable {
        cep = map.get("cep");

        wireMockServer.stubFor(get(urlMatching("/calculator/" + cep + ".*")).willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-PrecoPrazo_ERR.xml")));
    }

    @Entao("^o retorno deve conter um valor de erro igual a \"([^\"]*)\"$")
    public void o_retorno_deve_conter_um_valor_de_erro_igual_a(String erro) throws Throwable {
        assertThat(precoPrazo.getErro()).isEqualTo(erro);
        assertThat(throwable).isNull();
    }

    @Dado("^um CEP valido:$")
    public void um_CEP_valido(List<Map<String, String>> list) throws Throwable {
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> mapItem = list.get(i);

            cep = mapItem.get("cep");
            peso = mapItem.get("peso");
            largura = mapItem.get("largura");
            altura = mapItem.get("altura");
            comprimento = mapItem.get("comprimento");
            tipoEntrega = mapItem.get("tipoEntrega");

            wireMockServer.stubFor(get(urlMatching("/calculator/" + cep + ".*")).willReturn(aResponse().withStatus(200)
                    .withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-PrecoPrazo_BAD.xml")));
        }
    }

    @Dado("^o servico CalcPrazo nao esta respondendo$")
    public void o_servico_CalcPrazo_nao_esta_respondendo() throws Throwable {
        wireMockServer.stubFor(get(urlMatching("/calculator/" + cep + ".*")).willReturn(aResponse().withStatus(200)
                .withFixedDelay(6000).withBodyFile("resultado-pesquisa-PrecoPrazo_out.xml")));
    }

    @Entao("^uma excecao deve ser lancada com a mensagem de erro:$")
    public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
        assertThat(throwable).hasMessage(message);
    }

//    private String getUrl() {
//
//        StringBuilder url = new StringBuilder();
//
//        url.append("?nCdEmpresa");
//        url.append("&sDsSenha");
//        url.append("&nCdServico=").append(tipoEntrega);
//        url.append("&sCepOrigem=" + "12970000");
//        url.append("&sCepDestino=").append(cep);
//        url.append("&nVlPeso=").append(peso);
//        url.append("&nCdFormato=" + "1"); //Caixa
//        url.append("&nVlComprimento=").append(comprimento);
//        url.append("&nVlAltura=").append(altura);
//        url.append("&nVlLargura=").append(largura);
//        url.append("&nVlDiametro=").append("1");
//        url.append("&sCdMaoPropria=").append("S");
//        url.append("&nVlValorDeclarado=").append("100");
//        url.append("&sCdAvisoRecebimento=").append("S");
//
//        return url.toString();
//    }
//    @Entao("^o resultado deve ser o prazo e valor:$")
//    public void o_resultado_deve_ser_o_endereco(List<Map<String, String>> resultado)
//            throws Throwable {
//        assertThat(this.precoPrazo.getPrazoEntrega()).isEqualTo(resultado.get(0).get("PrazoEntrega"));
//        assertThat(this.precoPrazo.getValor()).isEqualTo(resultado.get(0).get("Valor"));
//        assertThat(this.precoPrazo.getCodigo()).isEqualTo(resultado.get(0).get("Codigo"));
//        assertThat(this.precoPrazo.getEntregaDomiciliar()).isEqualTo(resultado.get(0).get("EntregaDomiciliar"));
//        assertThat(this.precoPrazo.getEntregaSabado()).isEqualTo(resultado.get(0).get("EntregaSabado"));
//        assertThat(this.precoPrazo.getValorAvisoRecebimento()).isEqualTo(resultado.get(0).get("ValorAvisoRecebimento"));
//        assertThat(this.precoPrazo.getValorMaoPropria()).isEqualTo(resultado.get(0).get("ValorMaoPropria"));
//        assertThat(this.precoPrazo.getValorFrete()).isEqualTo(resultado.get(0).get("ValorSemAdicionais"));
//        assertThat(this.precoPrazo.getValorValorDeclarado()).isEqualTo(resultado.get(0).get("ValorDeclarado"));
//
//        assertThat(throwable).isNull();
//    }
}
