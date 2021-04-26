/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
window.onload=function(){
    
    function noenter() {
        return !(window.event && window.event.keyCode === 13); 
    }
    
    var inputs = document.getElementsByTagName("input");
    
    for(var i = 0; i < inputs.length; i++){
        inputs[i].onkeypress = noenter;
    }
    
    nEtiquetas = 0;
    etiquetasDiv = document.getElementById("etiquetasDiv");
    etiquetaField = document.getElementById("etiqueta");
    etiquetaField.addEventListener("keyup", function(event) {
        if (event.code === "Enter")
        {
            event.preventDefault();
            addNewTag(etiquetaField.value);
        }
    });
    
    addNewEtiqueta = function(tagValue){
        let etiqueta = document.createElement("SPAN");
        etiqueta.classList.add("badge");
        etiqueta.classList.add("badge-primary");
        etiqueta.innerHTML = tagValue;
        etiquetasDiv.appendChild(etiqueta);
        
    }
    
    var asientosFijos = document.getElementById("asientosFijos");
    var configuracionAsientos = document.getElementById("configuracionAsientos");

    asientosFijos.addEventListener("change", function(){
       if(asientosFijos.checked) {
         configuracionAsientos.style['display'] = "block";
       } else {
         configuracionAsientos.style['display'] = "none";
       } 
    });
    
    
};
