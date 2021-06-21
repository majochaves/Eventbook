package com.eventbookspring.eventbookspring.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class TestChatLogin extends Config {


	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driverMain = new ChromeDriver();
		driverMain.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driverMain, 20);	

	}

	@AfterEach
	public void tearDown() {
		driverMain.quit();
	}
	
	public boolean DetectarError(String msgError) {
        // Página de error
		if (!"Error".equals(driverMain.getTitle()))
			return false;
        
        // Se muestra mensaje de error
        WebElement errorMsg = driverMain.findElement(By.id("errorMsg"));
        wait.until(ExpectedConditions.elementToBeClickable(errorMsg));
        return msgError.equals(errorMsg.getText());
	}
	
	private void login(String user, String pw) {
		driverMain.get("http://localhost:17242/Eventbook/usuario-iniciar-sesion.jsp");
		WebElement usuario = driverMain.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[1]/td[2]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(usuario));
		usuario.sendKeys(user);
		
		WebElement pass = driverMain.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[2]/td[2]/input"));
		pass.sendKeys(pw);
		
		WebElement login = driverMain.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[3]/td[2]/input"));
		login.click();
	}
	
	private void crearChat() {
		driverMain.get("http://localhost:17242/Eventbook/ServletChatCrear");
    	Select input =new Select(driverMain.findElement(By.xpath("/html/body/div/div/div/form/table/tbody/tr[1]/td[2]/select")));
    	WebElement enviar = driverMain.findElement(By.xpath("/html/body/div/div/div/form/table/tbody/tr[2]/td/input"));
    	
    	wait.until(ExpectedConditions.elementToBeClickable(enviar));
    	// TODO CHECK IF YOUR OWN USERNAME IS NOT IN THE LIST
    	input.selectByVisibleText(nombreTeleoperador);

    	enviar.click();
	}
	
	private int checkLista(List<String> chats) {
		// Cargar la página y obtener la lista de chats
		driverMain.get("http://localhost:8080/Eventbook/ServletChatListar");
		List<WebElement> teleoperadores = driverMain.findElements(By.xpath("/html/body/div/div/div/table/tbody/tr/td[2]"));
    	wait.until(ExpectedConditions.elementToBeClickable(driverMain.findElement(By.xpath("html/body/div/div/div/div[2]/p"))));
    	
    	// Comprobar que los chats se muestran como es debido
    	int i = 0;
    	if (teleoperadores.size() > 0) {
    		for (WebElement teleoperador : teleoperadores) {
        		assertEquals(teleoperador.getText(), chats.get(i));
        		i++;
        	}
    	}
    
    	
    	return i;
	}
	
	// Se debe empezar sin chats o puede causar problemas)
    @Test
    public void ServletChatCrear() {
		// Login con un usuario este crea el chat
    	login(strUsuario, passUser);
    	crearChat();
    	
    	// El chat ha sido creado
    	List<String> chats = new ArrayList<String>();
    	chats.add(nombreTeleoperador);
    	checkLista(chats);
    	
    	// Logout user
    	driverMain.get("http://localhost:8080/Eventbook/ServletUsuarioCerrarSesion");
    	
    	// Login con el otro usuario (Teleoperador)
    	login(strUserTele, strPWTele);
    	// El chat ha sido creado en el otro usuario también
    	checkLista(chats);
    	
    }
    
    @Test
    public void ServletChatCrearDuplicado () {
		login(strUsuario, passUser);
        crearChat();
        crearChat();
        assertTrue(DetectarError("Hemos encontrado un error, ¿Existe ya el chat?"));
    }
    
    @Test
    public void ServletChatListar() {
		login(strUsuario, passUser);
    	
    	List<String> chats = new ArrayList<String>();
    	chats.add(nombreTeleoperador);
    	chats.add(nombreAdmin);
    	checkLista(chats);
    }
    
    @Test
    public void ServletChatListarTeleoperadorNoAutorizado() {
		login(strUsuario, passUser);
    	driverMain.get("http://localhost:8080/Eventbook/ServletChatListarTeleoperador");
    	assertTrue(DetectarError("Solo los teleoperadores pueden ver esta página."));
    	
    }
    
    @Test
    public void ServletChatListarTeleoperadorAutorizado() {
		login(strUserAdmin, strPWAdmin);
    	assertFalse(DetectarError("Solo los teleoperadores pueden ver esta página."));
    	
    	// Se muestran todos los chats globales
    	List<String> chats = new ArrayList<String>();
    	chats.add(nombreTeleoperador);
    	checkLista(chats);
    	
    }
    
    
    @Test
    public void ServletChatFiltrar() {
		login(strUsuario, passUser);
    	
    	List<String> chats = new ArrayList<String>();
    	chats.add(nombreTeleoperador);
    	chats.add(nombreAdmin);
    	checkLista(chats);
    }
    
    @Test
    public void ServletChatBorrarChats() {
    	login(strUsuario, passUser);
    	driverMain.get("http://localhost:8080/Eventbook/ServletChatListar");
    	List<WebElement> BotonesBorrar = driverMain.findElements(By.xpath("/html/body/div/div/div/table/tbody[1]/tr/td[5]/a"));
    	wait.until(ExpectedConditions.elementToBeClickable(driverMain.findElement(By.xpath("html/body/div/div/div/div[2]/p"))));
    	
    	// Borrar todos los chats
    	for (WebElement btn : BotonesBorrar) {
    		btn.click();
    	}
    	
    	WebElement strSinElementos = driverMain.findElement(By.xpath("/html/body/div/div/div/h4"));
    	wait.until(ExpectedConditions.elementToBeClickable(strSinElementos));
    	
    	assertEquals("No hay chats para mostrar.", strSinElementos.getText());
    	List<String> noChats = new ArrayList<String>();
    	assertEquals(0, checkLista(noChats));
    	
    	login(strUserTele, strPWTele);
    	// El chat ha sido creado en el otro usuario también
    	assertEquals(0, checkLista(noChats));
    }
    
    @Test
    public void ServletChatBorrarUnChat() {
    	login(strUsuario, passUser);
    	driverMain.get("http://localhost:8080/Eventbook/ServletChatListar");
    	List<WebElement> BotonesBorrar = driverMain.findElements(By.xpath("/html/body/div/div/div/table/tbody[1]/tr/td[5]/a"));
    	wait.until(ExpectedConditions.elementToBeClickable(driverMain.findElement(By.xpath("html/body/div/div/div/div[2]/p"))));
    	
    	int chatsIniciales = BotonesBorrar.size();
    	
    	// Borrar un chat
    	if (chatsIniciales > 0) {
    		WebElement primerChat = BotonesBorrar.get(0);
    		primerChat.click();
    	}
    	
    	assertTrue(BotonesBorrar.size() == chatsIniciales || chatsIniciales == 0);
    	
    	if (chatsIniciales == 0) {
    		WebElement strSinElementos = driverMain.findElement(By.xpath("/html/body/div/div/div/h4"));
        	wait.until(ExpectedConditions.elementToBeClickable(strSinElementos));
        	
        	assertEquals("No hay chats para mostrar.", strSinElementos.getText());
        	assertEquals(0, checkLista(new ArrayList<String>()));
    	}
    	
    	
    }

}
