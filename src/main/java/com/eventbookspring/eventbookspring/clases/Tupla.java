/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.clases;

/**
 *
 * @author Merli
 */
public class Tupla<T, K> {
    private T primerElem;
    private K segundoElem;

    public Tupla(){
        primerElem = null;
        segundoElem = null;
    }

    public Tupla(T p, K q){
        primerElem = p;
        segundoElem = q;
    }

    public Tupla(T p, T p2, K q){
        if(p instanceof Integer){
            Integer pInt = (Integer) p;
            Integer pInt2 = (Integer) p2;
            primerElem = (T) (pInt.toString() + "/" + pInt2.toString());
            segundoElem = q;
        }

    }


    public T getPrimerElem() {
        return primerElem;
    }

    public void setPrimerElem(T primerElem) {
        this.primerElem = primerElem;
    }

    public K getSegundoElem() {
        return segundoElem;
    }

    public void setSegundoElem(K segundoElem) {
        this.segundoElem = segundoElem;
    }
    
    
    @Override
    public boolean equals(Object o){
        boolean ok = false;
        if (o instanceof Tupla){
            Tupla<?,?> ot = (Tupla)o;
            //En el caso de que sea una String queremos que ignore las mayusculas
            if(this.primerElem instanceof String && ot.primerElem instanceof String){
                String cPrimerElem = (String) this.primerElem;
                ok = cPrimerElem.equalsIgnoreCase((String)ot.primerElem);
            } else {
                ok = ot.getPrimerElem().equals(this.primerElem);
            }
           
        }
        return ok;
    }
    
    @Override
    public int hashCode(){
        int hash;
        //Daremos el mismo hash independientemente del letras mayus o minus
        if(this.primerElem instanceof String)
            hash = ((String) primerElem).toUpperCase().hashCode();
        else
            hash = primerElem.hashCode();
        return hash;
    }
}
