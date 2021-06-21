package com.eventbookspring.eventbookspring.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.eventbookspring.eventbookspring.chat.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;




public class TestChatMessages extends Config {

	
	private void buscarChat(List<WebElement> chats) {
    	for (WebElement chat : chats) {
    		WebElement chatTeleTemp = chat.findElement(By.xpath("td[2]"));
    		WebElement chatUserTemp = chat.findElement(By.xpath("td[3]"));
    		if(chatTeleTemp.getText().equalsIgnoreCase(nombreTeleoperador) && 
    				chatUserTemp.getText().equalsIgnoreCase(nombreUsuario)) {
    			WebElement chatBtn = chat.findElement(By.xpath("td[4]/a"));
    			chatBtn.click();
    			break;
    		}
    	}
	}
	
	private void testEnviarMensaje(WebDriver driverEnvia, WebDriver driverRecibe, String nombreEnvia) {
		WebElement mensajesContainer = driverEnvia.findElement(By.className("chat-history"));
		List<WebElement> mensajes = mensajesContainer.findElements(By.tagName("li"));
		
		int numMsgInicio = mensajes.size();
		
		// Enviar mensaje
		String dat = crearStringRandom();
		String msg = "Mensaje de ejemplo"+ dat + " \n";
		enviarMensaje(driverEnvia, msg);
		
		// Se crea un mensaje
		mensajes = mensajesContainer.findElements(By.tagName("li"));
		
		
		// Si el enter no lo ha cogido usar el botón
		WebElement MsgToSendContainer = driverEnvia.findElement(By.id("message-to-send"));
		while (!MsgToSendContainer.getText().isEmpty()) {
			WebElement btnEnviar = driverEnvia.findElement(By.xpath("/html/body/section[2]/div/div[2]/div[3]/button"));
			btnEnviar.click();
		}
		
	
		// El mensaje se ha añadido
		assertEquals(numMsgInicio + 1, mensajes.size());
		
		// Comprobar último mensaje en usuario
		WebElement ultMensaje = mensajes.get(mensajes.size()-1);
		comprobarMensaje(ultMensaje, msg.trim(), nombreEnvia);
		
		// El mensaje es visible (scroll to bottom funciona)
		assertTrue(ultMensaje.isDisplayed());
	
		// COMPROBAR EN DRIVERAUX (Teleoperador)
		WebElement mensajesContainerTeleop = driverRecibe.findElement(By.className("chat-history"));
		List<WebElement> mensajesTeleop = mensajesContainerTeleop.findElements(By.tagName("li"));
		WebElement ultMensajeTeleop = mensajesTeleop.get(mensajesTeleop.size()-1);
		comprobarMensaje(ultMensajeTeleop, msg.trim(), nombreEnvia);
		
		// El mensaje se ha añadido
		assertEquals(numMsgInicio + 1, mensajesTeleop.size());
		
		// El mensaje es visible (scroll to bottom funciona)
		assertTrue(ultMensajeTeleop.isDisplayed());
	}

	private String crearStringRandom() {
		Random ran = new Random();
		int top = 3;
		char data = ' ';
		String dat = "";
	
		for (int i=0; i<=top; i++) {
		  data = (char)(ran.nextInt(25)+97);
		  dat = data + dat;
		}
		
		return dat;
	}

	private void accederChats() {
		login(strUsuario, passUser, driverMain);
		login(strUserTele,strPWTele, driverAux);
		
		
		// Abrir lista chats
		driverMain.get("http://localhost:8080/Eventbook/ServletChatListar");
		driverAux.get("http://localhost:8080/Eventbook/ServletChatListar");
		
		// Conseguir lista chats
		By xpathChats = By.xpath("/html/body/div/div/div/table/tbody/tr");
    	List<WebElement> chatsA = driverMain.findElements(xpathChats);
    	List<WebElement> chatsB = driverAux.findElements(xpathChats);
    	wait.until(ExpectedConditions.elementToBeClickable(driverMain.findElement(xpathChats)));
    	
    	// Buscar chat y acceder
    	buscarChat(chatsA);
    	buscarChat(chatsB);

	}

	private void crearChat(WebDriver driver) {
		driver.get("http://localhost:8080/Eventbook/ServletChatCrear");
		Select input =new Select(driver.findElement(By.xpath("/html/body/div/div/div/form/table/tbody/tr[1]/td[2]/select")));
		WebElement enviar = driver.findElement(By.xpath("/html/body/div/div/div/form/table/tbody/tr[2]/td/input"));
		
		wait.until(ExpectedConditions.elementToBeClickable(enviar));
		input.selectByVisibleText(nombreTeleoperador);
	
		enviar.click();
	}

	private void login(String user, String pw, WebDriver driver) {
		driver.get("http://localhost:8080/Eventbook/usuario-iniciar-sesion.jsp");
		WebElement usuario = driver.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[1]/td[2]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(usuario));
		usuario.sendKeys(user);
		
		WebElement pass = driver.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[2]/td[2]/input"));
		pass.sendKeys(pw);
		
		WebElement login = driver.findElement(By.xpath("/html/body/div/div/div/form/table[1]/tbody/tr[3]/td[2]/input"));
		login.click();
	}

	private void enviarMensaje(WebDriver driver, String msg) {
		WebElement MsgToSendContainer = driver.findElement(By.id("message-to-send"));
		for (char character : msg.toCharArray()) {
			MsgToSendContainer.sendKeys(String.valueOf(character));
		}
		
		
		
	}

	private void comprobarMensaje(WebElement ultMensaje, String strMsg, String strNombre) {
			WebElement nombre = ultMensaje.findElement(By.className("message-data-name"));
			WebElement texto = ultMensaje.findElement(By.className("message"));
			
			assertEquals(strMsg, texto.getText());
			assertEquals(strNombre, nombre.getText());
	//		assertEquals(//TODO FECHA)
		}

	@BeforeEach
	public void setUp() {
		// Create driver for main user
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driverMain = new ChromeDriver();
		driverMain.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driverMain, 20);	
		
		// Create driver in private window for second user
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-private");
		driverAux = new ChromeDriver();
		driverAux.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driverAux,20);
		
		
		// Gets into chat for Teleoperador and usuario
		accederChats();
		
		
	}

	@AfterEach
	public void tearDown() {
//		driverMain.quit();
//		driverAux.quit();
	}
	
	public boolean DetectarError(String msgError, WebDriver driver) {
        // Página de error
		if (!"Error".equals(driver.getTitle()))
			return false;
        
        // Se muestra mensaje de error
        WebElement errorMsg = driver.findElement(By.id("errorMsg"));
        wait.until(ExpectedConditions.elementToBeClickable(errorMsg));
        return msgError.equals(errorMsg.getText());
	}
	
	@Test
	public void listaTeleoperadoresConBusqueda() {
		WebElement lista= driverMain.findElement(By.className("list"));
		List<WebElement> teleops = lista.findElements(By.tagName("li"));
		
		// Comprobar que la lista es correcta
		List<String> strteleops = new ArrayList();
		strteleops.add(strUserTele);
		int i = 0;
		for (WebElement teleop : teleops) {
			assertEquals(strteleops.get(i), teleop.findElement(By.xpath("div/a/div[1]")).getText());
			i++;
		}
		
		// BUSQUEDA EN MAIN (Usuario)
		// Búsqueda de teleoperador
		WebElement buscador = driverMain.findElement(By.xpath("/html/body/section[2]/div/div[1]/div/input"));
		buscador.sendKeys(strUserTele);
		i = 0;
		for (WebElement teleop : teleops) {
			assertEquals(strteleops.get(i), teleop.findElement(By.xpath("div/a/div[1]")).getText());
			i++;
		}
		
		// Buscar teleoperador que no existe
		buscador.sendKeys("jfdksljfklsdjfklsdjfklsdjflksdjflksdjfkljsdklfjsdlkfjlksd");
		WebElement noElement = driverMain.findElement(By.id("no-items-found"));
		assertTrue(noElement.isDisplayed());
		
		// BUSQUEDA EN AUX (Teleoperador)
		// Búsqueda de usuario
		WebElement listaUsuariosContainer = driverAux.findElement(By.className("list"));
		List<WebElement> listaUsuarios = listaUsuariosContainer.findElements(By.tagName("li"));
		WebElement buscadorUser = driverAux.findElement(By.xpath("/html/body/section[2]/div/div[1]/div/input"));
		buscadorUser.sendKeys(strUsuario);
		assertEquals(strUsuario, listaUsuarios.get(0).findElement(By.xpath("div/a/div[1]")).getText());

		
		// Buscar usuario que no existe
		buscadorUser.sendKeys("jfdksljfklsdjfklsdjfklsdjflksdjflksdjfkljsdklfjsdlkfjlksd");
		WebElement noElementUser = driverAux.findElement(By.id("no-items-found"));
		assertTrue(noElementUser.isDisplayed());
		
	}
	
	@Test
	public void testEnviarMensajeYRecibe() {
		for (int i = 0; i < 10; i++) {
			testEnviarMensaje(driverMain, driverAux, nombreUsuario);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			testEnviarMensaje(driverAux, driverMain, nombreTeleoperador);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}
	
	
	
	@Test
	public void deleteMessages() {
		// Enviar mensajes
		enviarMensaje(driverAux, crearStringRandom()+"\n");

		// Recargar página para cargar mensajes desde base de datos
		driverAux.navigate().refresh();
		try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Enviar mensaje así está cargado mediante async request
		enviarMensaje(driverAux, crearStringRandom()+"\n");
		
		// Coger elementos
		By borrarXpath = By.xpath("/html/body/section[2]/div/div[2]/div[2]/ul/li/div/a[2]");
		List<WebElement> borrarElements = driverMain.findElements(borrarXpath);
		List<WebElement> borrarElementsTeleop = driverAux.findElements(borrarXpath);
		
		// Usuario no puede borrar mensajes
		assertTrue(borrarElements.size() == 0);
		
		// Borrar todos los mensajes desde teleoperador
		while (borrarElementsTeleop.size() > 0) {
			Actions action = new Actions(driverAux);
			action.moveToElement(borrarElementsTeleop.get(borrarElementsTeleop.size()-1)).click().build().perform();
		    borrarElementsTeleop = driverAux.findElements(borrarXpath);
		    
		}
		
		// Los mensajes no se muestran en ningún driver al recargar
		// Usuario
		driverMain.navigate().refresh();
		WebElement mensajesContainer = driverMain.findElement(By.className("chat-history"));
		List<WebElement> mensajes = mensajesContainer.findElements(By.tagName("li"));
		assertTrue(mensajes.size() == 0);
		
		// Teleop
		driverAux.navigate().refresh();
		mensajesContainer = driverAux.findElement(By.className("chat-history"));
		mensajes = mensajesContainer.findElements(By.tagName("li"));
		assertTrue(mensajes.size() == 0);
	}
	
	
	@Test
	public void usuariosNoPuedenBorrarMensajes() {
		// Enviar mensaje
		enviarMensaje(driverMain, crearStringRandom()+"\n");
		
		// Coger url del último mensaje
		WebElement mensajesContainer = driverAux.findElement(By.className("chat-history"));
		List<WebElement> mensajes = mensajesContainer.findElements(By.tagName("li"));
		WebElement ultMensaje = mensajes.get(mensajes.size()-1);
		
		// Provocar error
		String id = ultMensaje.getAttribute("id");
		String userid = ultMensaje.getAttribute("userid");
		driverMain.get("http://localhost:8080/Eventbook/ServletMessageBorrar?msgId="+ id +"&userID="+userid);
		
		// El error se cumple
		DetectarError("No estás logeado, no tienes suficientes permisos o el mensaje no ha sido encontrado.", driverMain);
	}
	
	
	@Test
	public void teleoperadorUpdateMessage() {
		// Enviar mensajes
		enviarMensaje(driverAux, crearStringRandom()+"\n");

		// Recargar página para cargar mensajes desde base de datos
		driverAux.navigate().refresh();
		try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Enviar mensaje así está cargado mediante async request
		enviarMensaje(driverAux, crearStringRandom()+"\n");
		
		// Coger elementos
		WebElement mensajesContainer = driverMain.findElement(By.className("chat-history"));
		List<WebElement> mensajes = mensajesContainer.findElements(By.tagName("li"));
		WebElement ultMensaje = mensajes.get(mensajes.size()-1);
		
		WebElement mensajesContainerTeleOp = driverAux.findElement(By.className("chat-history"));
		List<WebElement> mensajesTeleOP = mensajesContainerTeleOp.findElements(By.tagName("li"));
		WebElement ultMensajeTeleOp = mensajesTeleOP.get(mensajesTeleOP.size()-1);
		
		// El usuario no puede editar nada
		By editarClassName = By.className("message-data-edit");		
		Throwable exception = assertThrows(NoSuchElementException.class, () -> ultMensaje.findElement(editarClassName));
		

		// Editar el mensaje
		WebElement editarElementsTeleop = ultMensajeTeleOp.findElement(editarClassName);
		Actions action = new Actions(driverAux);
		action.moveToElement(editarElementsTeleop).click().build().perform();
		

		// Introducir form mensaje cambiado
		WebElement input = driverAux.findElement(By.xpath("/html/body/div/div/div/form/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(input));
		input.clear();
		String msg = "Mensaje editado "+ crearStringRandom();
		input.sendKeys(msg);
		
		System.out.println(input.getAttribute("innerHTML"));
		System.out.println(input.getTagName());
		WebElement btnModificar = driverAux.findElement(By.xpath("/html/body/div/div/div/form/div[2]/input"));
		System.out.println(btnModificar.getText());
		btnModificar.click();
		
		
		// Comprobar que el mensaje se ha editado correctamente
		mensajesContainerTeleOp = driverAux.findElement(By.className("chat-history"));
		mensajesTeleOP = mensajesContainerTeleOp.findElements(By.tagName("li"));
		comprobarMensaje(mensajesTeleOP.get(mensajesTeleOP.size()-1), msg, nombreTeleoperador); 
		
		// Comprobar desde el lado del usuario también
		driverMain.navigate().refresh();
		WebElement mensajesContainerUser = driverMain.findElement(By.className("chat-history"));
		List<WebElement> mensajesUser = mensajesContainerUser.findElements(By.tagName("li"));
		comprobarMensaje(mensajesUser.get(mensajesUser.size()-1), msg, nombreTeleoperador); 
		
	}
	
	@Test
	public void usuarioNoPuedeEditarMensaje() {
		// Enviar mensaje
		enviarMensaje(driverMain, crearStringRandom()+"\n");
		
		// Coger url del último mensaje
		WebElement mensajesContainer = driverAux.findElement(By.className("chat-history"));
		List<WebElement> mensajes = mensajesContainer.findElements(By.tagName("li"));
		WebElement ultMensaje = mensajes.get(mensajes.size()-1);
		
		// Provocar error
		String id = ultMensaje.getAttribute("id");
		String userid = ultMensaje.getAttribute("userid");
		driverMain.get("http://localhost:8080/Eventbook/ServletMessageEditar?msgId="+ id +"&userID="+userid);
		
		// El error se cumple
		DetectarError("Parece que no dispones de los permisos necesarios para acceder a esta página.", driverMain);
	}
	
	
	@Test
	public void abrirChatsParaDebugConAdmin() {
		WebDriver driveradmin = new ChromeDriver();
		driveradmin.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driveradmin, 20);
		
		login(strUserAdmin,strPWAdmin, driveradmin);
		
		
		// Abrir lista chats
		driveradmin.get("http://localhost:8080/Eventbook/ServletChatListarTeleoperador");
	}

}
