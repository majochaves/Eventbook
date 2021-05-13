/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    var limit = $('#numAsientos').val();
    console.log(limit);
    $('input[type=checkbox]').on('change', function (e) {
        if ($('input[type=checkbox]:checked').length > limit) {
            $(this).prop('checked', false);
        }
        if ($('input[type=checkbox]:checked').length == limit) {
            $('#btn-enviar').show();
        }
        if ($('input[type=checkbox]:checked').length < limit) {
            $('#btn-enviar').hide();
        }
    });
});
