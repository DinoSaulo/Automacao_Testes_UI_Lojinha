package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.LoginPage;

import java.time.Duration;

@DisplayName("Testes WEB do módulo de produtos")
public class ProdutosTest {

    public WebDriver navegador;

    @BeforeEach
    public void beforeEach(){
        // Abrir o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver101\\chromedriver.exe");
        this.navegador = new ChromeDriver();

        // vou maximizar a tela
        navegador.manage().window().maximize();

        // Vou definir um tempo de espera padrão de 1 segundo
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        // Navegar para a página da Lojinha Web
        navegador.get("http://165.227.93.41/lojinha-web/v2/");

    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEhPermitidoRegistrarProdutoComValorIgualAZero(){

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUser("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook PRO")
                .informarValorDoProduto("000")
                .informarCoresDoProduto("preto,branco")
                .submbeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        // Vou validar que a mensagem de erro foi apresentada
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor maior que R$ 7.000,00")
    public void testNaoEhPermitidoRegistrarProdutoComValorAcimaDeSeteMil(){

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUser("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook PRO")
                .informarValorDoProduto("700001")
                .informarCoresDoProduto("preto,branco")
                .submbeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        // Vou validar que a mensagem de erro foi apresentada
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos de valor de R$ 0,01")
    public void testPossoAdicionarProdutosComValorDeUmCentavo(){

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUser("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook PRO")
                .informarValorDoProduto("001")
                .informarCoresDoProduto("Rosa")
                .submbeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();

        // Vou validar que a mensagem de erro foi apresentada
        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos que estejam na faixa de R$ 0,01 a R$ 7.000,00")
    public void testPossoAdicionarProdutosComValorDeUmCentavoASeteMilReais(){

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUser("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook PRO")
                .informarValorDoProduto("600000")
                .informarCoresDoProduto("preto")
                .submbeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();

        // Vou validar que a mensagem de erro foi apresentada
        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @AfterEach
    public void afterEach(){
        navegador.quit();
    }
}
