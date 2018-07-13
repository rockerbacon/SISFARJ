package receivers;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SecretarioSelenium {
	@Test
	public void lancarFiliacaoSelenium () throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "/Users/gustavoebbo/Downloads/chromedriver");
		  WebDriver driver = new ChromeDriver();
		  driver.get("http://localhost:8080/SISFARJ/home.jsp");
		  Thread.sleep(5000);  // Let the user actually see something!
		  WebElement searchBox = driver.findElement(By.id("filiar"));
		  searchBox.submit();
		  WebElement usuario = driver.findElement(By.id("inputUser"));
		  usuario.sendKeys("admin");
		  WebElement senha = driver.findElement(By.id("inputPassword"));
		  senha.sendKeys("admin");
		  Thread.sleep(3000);
		  WebElement enviar = driver.findElement(By.id("submitButton"));
		  enviar.submit();
		  Thread.sleep(3000);  // Let the user actually see something!
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
		  Thread.sleep(5000);
		  WebElement botao = driver.findElement(By.id("botaoCadastro"));
		  botao.submit();
		  Thread.sleep(10000);
		  driver.quit();
		  
		
	}
}
