package com.eventbookspring.eventbookspring.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.eventbookspring.eventbookspring.chat.Config;
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

public class TestChatNoLogin extends Config {

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
	
	public void NotLoggedIn(String url, String msgError) {
		driverMain.get(url);
        wait.until(ExpectedConditions.elementToBeClickable(driverMain.findElement(By.id("errorMsg"))));
       
        
        // Página de error
        assertEquals("Error", driverMain.getTitle());
        
        // Se muestra mensaje de no login
        assertEquals(msgError,driverMain.findElement(By.id("errorMsg")).getText());
	}

    @Test
    public void NotLoggedIn_ServletChatBorrar() {
        NotLoggedIn("http://localhost:8080/chat/borrar/0/0", "¿Has iniciado sesión?");
    }
    
    @Test
    public void NotLoggedIn_ServletChatCrear() {
        NotLoggedIn("http://localhost:8080/chat/crear", "¿Has iniciado sesión?");
    }

//    @Test
//    public void NotLoggedIn_ServletChatGuardar() {
//        NotLoggedIn("http://localhost:8080/Eventbook/chat/guardar", "¿Has iniciado sesión?");
//    }
    
    @Test
    public void NotLoggedIn_ServletChatListar() {
        NotLoggedIn("http://localhost:8080/chat/", "¿Has iniciado sesión?");
    }
    
    @Test
    public void NotLoggedIn_ServletChatUI() {
        NotLoggedIn("http://localhost:8080/chat/0/0", "¿Has iniciado sesión?");
    }
    
    @Test
    public void NotLoggedIn_ServletChatTeleoperador() {
        NotLoggedIn("http://localhost:8080/chat/teleoperador", "¿Has iniciado sesión?");
    }
    
    @Test
    public void NotLoggedIn_ServletMessageBorrar() {
        NotLoggedIn("http://localhost:8080/mensaje/borrar/0/0/0", "¿Has iniciado sesión?");
    }
    
    @Test
    public void NotLoggedIn_ServletMessageEditar() {
        NotLoggedIn("http://localhost:8080/mensaje/editarMsg/0/0/0", "¿Has iniciado sesión?");
    }


}
