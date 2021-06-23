package com.eventbookspring.eventbookspring.chat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Config {

	protected String nombreUsuario = "el";
	protected String strUsuario = "pepe";
	protected String passUser = "123";
	protected String nombreTeleoperador = "Tele";
	protected String strUserTele = "elTeleop";
	protected String strPWTele = "test";
	protected String nombreAdmin = "NombreAdmin";
	protected String strUserAdmin = "admin";
	protected String strPWAdmin = "123";
	
	protected static WebDriver driverMain;
	static WebDriver driverAux;
	protected static WebDriverWait wait;

	public Config() {
		super();
	}

}