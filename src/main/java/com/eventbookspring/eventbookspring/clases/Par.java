package com.eventbookspring.eventbookspring.clases;

public class Par<T, K>{
    private T primerElem;
    private K segundoElem;

    private T extraTercerElem;


    public Par(T primerElem, K segundoElem){
        this.primerElem = primerElem;
        this.segundoElem = segundoElem;
        this.extraTercerElem = null;
    }

    public Par(){
        this(null,null);
    }

    public Par(T primerElem, T extraTercerElem, K segundoElem){
        this.primerElem = primerElem;
        this.segundoElem = segundoElem;
        this.extraTercerElem = extraTercerElem;
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

    public Double getSegundoElemLikeDouble(){
        if(this.segundoElem instanceof Number)
            return ((Number) this.segundoElem).doubleValue();
        else
            throw new ClassCastException("El segundo elemento no se puede convertir a Double");
    }

    public String getPrimerElemYExtraElemLikeString(){
        if(this.extraTercerElem != null){
            if(this.primerElem instanceof String && this.extraTercerElem instanceof String)
                return (String) this.primerElem + "/" + (String) this.extraTercerElem;
            else if(this.primerElem instanceof Number && this.primerElem instanceof Number)
                return (Number) this.primerElem + "/" + (Number)this.extraTercerElem;
            else
                throw new ClassCastException("El primer elemento o el elemento extra no se puede convertir a String");
        } else {
            if(this.primerElem instanceof String)
                return (String) this.primerElem;
            else if(this.primerElem instanceof Number)
                return ((Number) this.primerElem).toString();
            else
                throw new ClassCastException("El primer elemento no se puede convertir a String");
        }

    }
}
