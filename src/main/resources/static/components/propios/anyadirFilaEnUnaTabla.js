/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Uso: Poner en un boton el id="anyadirFila", en la tabla la cual se desea anyadir el id="tablaAnyadirFila" e importar dicho script en el html.
//OBLIGATORIO: REQUIERE USO DE valoresNoRepetibles.js en el propio HTML ANTES DE ESTE SCRIPT

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
    let inputs = document.querySelectorAll('[class~="textoColumna1"]');
    let listaBotonesEliminar2 =  document.querySelectorAll('[class~="eliminarFila"]');

    //Anyadimos el evento al ultimo elemento introducido
    inputs[inputs.length - 1].addEventListener('input', function() {
        listaPalabras = [];
        realizarConteo(inputs);
        colocarInvalidos(inputs);
            
    });

    //Anyadimos el evento de eliminar el elemento
    listaBotonesEliminar2[listaBotonesEliminar2.length - 1].addEventListener('click', function (){
        let fila = listaBotonesEliminar2[listaBotonesEliminar2.length - 1].parentNode.parentNode.parentNode;
        fila.parentNode.removeChild(fila);
    });

    //Lo colocamos invalido
    inputs[inputs.length - 1].className = nombreClassInicial + " is-invalid";
    
    //Colocamos el boton deshabilitado
    button.setAttribute('disabled', '');
};