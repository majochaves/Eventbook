/* 
 * Uso:
 * 1. Colocar el boton de enviar con el id="btnEnviar"
 * 2. Colocar en todos los <input type="text"/> el atributo class="... textoColumna1"
 */

const inputs = document.querySelectorAll('[class~="textoColumna1"]');
const button = document.getElementById('btnEnviar');
const nombreClassInicial = inputs[0].className;     //Al menos debe de haber algun elemento o excepcion
const spanTooltip = document.getElementById('spanTooltip');

let listaPalabras = [];



function buscarYAnyadir(cadena, index){
    let existe = false;

    for(let i=0; i<listaPalabras.length; i++){
        if(listaPalabras[i].texto === cadena){
            //listaPalabras[i].numVeces = listaPalabras[i].numVeces + 1;
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

function realizarConteo(inputs){
    for(let i=0;i<inputs.length; i++){
        buscarYAnyadir(inputs[i].value, i);
    }
}

function colocarInvalidos(inputs){
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
        //spanTooltip.setAttribute('data-toggle', 'tooltip');
        //spanTooltip.setAttribute('data-original-title', 'Hay elementos repetidos');
        spanTooltip.setAttribute('title', 'Hay elementos repetidos');
    }
        
    else{
        button.removeAttribute('disabled', '');
        //spanTooltip.removeAttribute('data-toggle', 'tooltip');
        //spanTooltip.removeAttribute('data-original-title', 'Hay elementos repetidos');
        //spanTooltip.removeAttribute('data-placement', 'top');
        spanTooltip.removeAttribute('title', 'Hay elementos repetidos');
    }
        
}

for(let i = 0; i<inputs.length; i++){

    inputs[i].addEventListener('input', function() {
        listaPalabras = [];
        realizarConteo(inputs);
        colocarInvalidos(inputs);
            
    });
}

/*document.addEventListener("DOMContentLoaded", function(event) {
    spanTooltip.removeAttribute('data-original-title', 'Hay elementos repetidos');
    spanTooltip.removeAttribute('data-toggle', 'tooltip');
    spanTooltip.removeAttribute('title', 'Hay elementos repetidos');
    console.log('CARGADO');
 });*/
