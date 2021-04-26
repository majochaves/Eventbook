/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Merli
 */
public class Tupla<T, K> {
    private T primerElem;
    private K segundoElem;
    
    public Tupla(T p, K q){
        primerElem = p;
        segundoElem = q;
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
            ok = ot.getPrimerElem().equals(this.primerElem);// && ot.getSegundoElem().equals(this.segundoElem)
        }
        return ok;
    }
}
