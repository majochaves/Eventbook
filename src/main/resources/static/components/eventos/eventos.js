/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var numEtiquetas = $("#numEtiquetas").val();
    if(numEtiquetas === 0) {
        $('#selectEventos').attr('disabled', 'disabled')
    }else{
        $('#selectEventos').attr('enabled', 'enabled')
    }

   $('input[type="radio"]').click(function() {
       if($(this).attr('id') === "asientosFijosSi") {
            $('#configuracionAsientos').show();
       }
       else {
            $('#configuracionAsientos').hide();
            $('#numFilas').val("");
            $('#numAsientosFila').val("");
       }
   });

});