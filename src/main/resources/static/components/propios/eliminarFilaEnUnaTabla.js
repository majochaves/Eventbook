/*
 *
 * Uso:
 * 1. Colocar el boton de eliminar una fila, el atributo class ="eliminarFila". La class puede contener mas cosas sin problemas (como propiedades CSS)
 * 2. OBLIGATORIO: Añadir en el html el import de este script
 * 3. OBLIGATORIO: Añadir en el html el import de valoresNoRepetibles.js
 * 4. Recomendado: Añadir en el html el import de anyadirFilaEnUnaTabla.js
 */

let listaBotonesEliminar =  document.querySelectorAll('[class~="eliminarFila"]');


for(let i=0; i<listaBotonesEliminar.length; i++){
    listaBotonesEliminar[i].addEventListener('click', function (){
        let fila = listaBotonesEliminar[i].parentNode.parentNode;
        fila.parentNode.removeChild(fila);

        restablecerListaPalabras();
        restablecerListaInputs();
        realizarConteo();
        colocarInvalidos();
    });
}