
let listaBotonesEliminar =  document.querySelectorAll('[class~="eliminarFila"]');


for(let i=0; i<listaBotonesEliminar.length; i++){
    listaBotonesEliminar[i].addEventListener('click', function (){
        let fila = listaBotonesEliminar[i].parentNode.parentNode;
        fila.parentNode.removeChild(fila);
    });
}