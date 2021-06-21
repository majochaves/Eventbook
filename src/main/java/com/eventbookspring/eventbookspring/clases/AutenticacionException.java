package com.eventbookspring.eventbookspring.clases;

public class AutenticacionException extends Exception{
    public AutenticacionException(){
        super("Error de Autenticación.");
    }
    public AutenticacionException(String msg){
        super(msg);
    }
}
