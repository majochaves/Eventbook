/*
 * Uso:
 * 1. Colocar el boton de añadir filas el id="anyadirFila"
 * 2. Colocar en la tabla la cual se desea añadir dicha fila id="tablaAnyadirFila"
 * 3. OBLIGATORIO: Añadir en el html el import de este script
 * 4. OBLIGATORIO: Añadir en el html el import de valoresNoRepetibles.js
 * 5. Recomendado: Añadir en el html el import de eliminarFilaEnUnaTabla.js
 *
 */


const botonAnyadirFila = document.getElementById('anyadirFila');
const tabla = document.getElementById('tablaAnyadirFila');

botonAnyadirFila.onclick = function() {
    let fila = tabla.insertRow(tabla.rows.length);
    var celdaNombre = fila.insertCell(0);
    var celdaValor = fila.insertCell(1);
    var celdaEliminar = fila.insertCell(2);
    celdaNombre.innerHTML = '<input class="form-control form-control-sm textoColumna1" type="text" name="nombres">';
    celdaValor.innerHTML = '<input class="form-control form-control-sm" type="number" name="valores" value="0">';
    celdaEliminar.innerHTML = '<div class="text-center"><button type="button" class="shadow-sm badge badge-danger eliminarFila">Eliminar fila</button></div>';
    inputs = document.querySelectorAll('[class~="textoColumna1"]');
    let listaBotonesEliminar2 =  document.querySelectorAll('[class~="eliminarFila"]');

    //Anyadimos el evento al ultimo elemento introducido
    inputs[inputs.length - 1].addEventListener('input', function() {
        restablecerListaPalabras();
        restablecerListaInputs();
        realizarConteo();
        colocarInvalidos();

    });

    //Anyadimos el evento de eliminar el elemento
    listaBotonesEliminar2[listaBotonesEliminar2.length - 1].addEventListener('click', function (){
        let fila = listaBotonesEliminar2[listaBotonesEliminar2.length - 1].parentNode.parentNode.parentNode;
        fila.parentNode.removeChild(fila);

        restablecerListaPalabras();
        restablecerListaInputs();
        realizarConteo();
        colocarInvalidos();

    });

    //Lo colocamos invalido
    inputs[inputs.length - 1].className = nombreClassInicial + " is-invalid";

    //Colocamos el boton deshabilitado
    button.setAttribute('disabled', '');
};