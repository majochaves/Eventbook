/* 
 * Uso:
 * 1. Colocar el boton de enviar o aceptar el id="btnEnviar"
 * 2. Colocar en todos los <input type="text"/> donde no se desea que se repitan valores,  el atributo class="textoColumna1".
 *    La class puede contener mas cosas sin problemas (como propiedades CSS).
 * 3. OBLIGATORIO: Añadir en el html el import de este script
 */

let inputs = document.querySelectorAll('[class~="textoColumna1"]');
const button = document.getElementById('btnEnviar');
const nombreClassInicial = inputs[0].className;     //Al menos debe de haber algun elemento o excepcion
const spanTooltip = document.getElementById('spanTooltip');

let listaPalabras = [];

function restablecerListaPalabras(){
    listaPalabras = [];
}

function restablecerListaInputs(){
    inputs = document.querySelectorAll('[class~="textoColumna1"]');
}


function buscarYAnyadir(cadena, index){
    let existe = false;

    for(let i=0; i<listaPalabras.length; i++){
        if(listaPalabras[i].texto === cadena){
            listaPalabras[i].indexs.push(index);
            existe = true;
        }
    }

    if(!existe){
        listaPalabras.push({
            texto: cadena,
            indexs: [index]
        });
    }

}

function realizarConteo(){
    for(let i=0;i<inputs.length; i++){
        buscarYAnyadir(inputs[i].value, i);
    }
}

function colocarInvalidos(){
    let existeInvalidos = false;
    for(let i=0;i<listaPalabras.length; i++){
        if(listaPalabras[i].indexs.length >= 2 || listaPalabras[i].texto === ""){
            existeInvalidos = true;
            for(let q=0;q<listaPalabras[i].indexs.length;q++){
                inputs[listaPalabras[i].indexs[q]].className = nombreClassInicial + " is-invalid";
            }
        } else {
            for(let q=0;q<listaPalabras[i].indexs.length;q++){
                inputs[listaPalabras[i].indexs[q]].className = nombreClassInicial;
            }
        }
    }
    if(existeInvalidos){
        button.setAttribute('disabled', '');
        spanTooltip.setAttribute('title', 'Hay elementos repetidos');
    }
        
    else{
        button.removeAttribute('disabled', '');
        spanTooltip.removeAttribute('title', 'Hay elementos repetidos');
    }
        
}

for(let i = 0; i<inputs.length; i++){

    inputs[i].addEventListener('input', function() {
        restablecerListaPalabras();
        restablecerListaInputs();
        realizarConteo();
        colocarInvalidos();
            
    });
}

