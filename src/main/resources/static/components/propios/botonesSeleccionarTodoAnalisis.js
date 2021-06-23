document.addEventListener('DOMContentLoaded', function(event) {
    //Botones
    const btnTipoUsuarioTablaUsuario = document.getElementById("todoTipoUsuarioTablaUsuario");
    const btnTodoTablaUsuario = document.getElementById("todoTablaUsuario");
    const btnTipoUsuarioTablaEvento = document.getElementById("todoTablaEvento");

    //Lista checkbox
    const checkBoxTipoUsuarioTablaUsuario = document.getElementsByName("tipoUsuario");
    const checkBoxTodoTablaUsuario = document.getElementsByName("tipoFiltroUsuario");
    const checkBoxTodoTablaEvento = document.getElementsByName("tipoFiltroEvento");

    let btnTipoUsuarioTablaUsuarioQuitarTodo = false;
    let btnTodoTablaUsuarioQuitarTodo = false;
    let btnTodoTablaEventoQuitarTodo = false;

    //Eventos
    btnTipoUsuarioTablaUsuario.onclick = function (){
        btnTipoUsuarioTablaUsuarioQuitarTodo = ponerOQuitar(
            checkBoxTipoUsuarioTablaUsuario,
            btnTipoUsuarioTablaUsuario,
            btnTipoUsuarioTablaUsuarioQuitarTodo);
    };

    btnTodoTablaUsuario.onclick = function (){
        btnTodoTablaUsuarioQuitarTodo = ponerOQuitar(
            checkBoxTodoTablaUsuario,
            btnTodoTablaUsuario,
            btnTodoTablaUsuarioQuitarTodo);
    };

    btnTipoUsuarioTablaEvento.onclick = function (){
        btnTodoTablaEventoQuitarTodo = ponerOQuitar(
            checkBoxTodoTablaEvento,
            btnTipoUsuarioTablaEvento,
            btnTodoTablaEventoQuitarTodo);
    }


    function ponerOQuitar(checkBoxList, btn, seEncuentraQuitado){
        for(let i=0; i< checkBoxList.length; i++){
            checkBoxList[i].checked = !seEncuentraQuitado;
        }

        if(seEncuentraQuitado){
            seEncuentraQuitado= false;
            btn.textContent = "Seleccionar todo";
        } else {
            seEncuentraQuitado= true;
            btn.textContent = "Quitar todo";
        }

        return seEncuentraQuitado;
    }
})

