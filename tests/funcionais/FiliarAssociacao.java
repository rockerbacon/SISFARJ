package funcionais;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FiliarAssociacao {
	@Test
	public void lancarFiliacaoSelenium () throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "lib/chromedriver");
		  ChromeOptions options = new ChromeOptions();
		  options.addArguments("--disable-extensions"); // disabling extensions
		  options.addArguments("--disable-gpu"); // applicable to windows os only
		  options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		  options.addArguments("--no-sandbox"); // Bypass OS security model
		  WebDriver driver = new ChromeDriver(options);
		  driver.get("http://localhost:8080/SISFARJ/home.jsp");
		  Thread.sleep(2000);  // Let the user actually see something!
		  WebElement searchBox = driver.findElement(By.id("filiar"));
		  searchBox.submit();
		  WebElement usuario = driver.findElement(By.id("inputUser"));
		  usuario.sendKeys("admin");
		  WebElement senha = driver.findElement(By.id("inputPassword"));
		  senha.sendKeys("admin");
		  Thread.sleep(2000);
		  WebElement enviar = driver.findElement(By.id("submitButton"));
		  enviar.submit();
		  Thread.sleep(2000);  // Let the user actually see something!
		  WebElement noficio = driver.findElement(By.id("nOficio"));
		  noficio.sendKeys("1234");
		  WebElement dataOficio = driver.findElement(By.id("dataOficio"));
		  dataOficio.sendKeys("01/01/2012");
		  WebElement nomeAssoc = driver.findElement(By.id("nomeAssoc"));
		  nomeAssoc.sendKeys("Nadadores");
		  WebElement siglaAssoc = driver.findElement(By.id("siglaAssoc"));
		  siglaAssoc.sendKeys("ndr");
		  WebElement enderecoAssoc = driver.findElement(By.id("enderecoAssoc"));
		  enderecoAssoc.sendKeys("rua teste 123");
		  WebElement telefone = driver.findElement(By.id("telAssoc"));
		  telefone.sendKeys("12345678");
		  WebElement comprovante = driver.findElement(By.id("numComprovantePag"));
		  comprovante.sendKeys("123456");
		  Thread.sleep(2000);
		  WebElement botao = driver.findElement(By.id("botaoCadastro"));
		  botao.submit();
		  Thread.sleep(4000);
		  driver.quit();


	}
}