/*
 * Uso:
 * 1. Importar este archivo cuando cargue el DOM (ya sea al final, con defer o usando un evento cuando carga el DOM)
 * 2. OBLIGATORIO: Importar CDN de https://www.chartjs.org/
 * 3. Colocar un canvas con id="myChart + ELID". Se debe concatenar el id del Tipoanalisis
 * 4. Crear una variable listaItems que añada los datos necesarios para la creacion de la grafica
 *
 *
 */

//Configuracion de Chart.js
Chart.defaults.font.size = 19;

//Constantes de los valores de los radio
const ROSQUILLA = 'ROSQUILLA';
const LINEA = 'LINEA';

let conjuntoGraficas = [];


//---------------------------------DEFINICION DE GRAFICAS---------------------------------
function generarGraficaRosquilla(elItem){
    let data = {
        labels: elItem.items,
        datasets: [{
            label: elItem.nombreTipoanalisis,
            backgroundColor: elItem.listaColores,
            hoverBorderWidth: 3,
            hoverBorderColor: '#000',
            data: elItem.valores,
        }]
    };

    let config = {
        type: 'doughnut',
        data: data,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: elItem.nombreTipoanalisis
                }
            }
        },
    };


    return (new Chart(
        document.getElementById('myChart' + elItem.idTipoanalisis),
        config
    ));

}

function generarGraficaLinea(elItem){
    let data = {
        labels: elItem.items,
        datasets: [{
            label: elItem.nombreTipoanalisis,
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: elItem.valores,
        }]
    };

    let config = {
        type: 'line',
        data,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: elItem.nombreTipoanalisis
                }
            },
            layout: {
                padding: {
                    left: 50,
                    right: 50
                }
            }
        }
    };

    return (new Chart(
        document.getElementById('myChart' + elItem.idTipoanalisis),
        config
    ));
}


function generarGraficaBarras(elItem){

    let data = {
        labels: elItem.items,
        datasets: [{
                label: elItem.nombreTipoanalisis,
                data: Utils.numbers(NUMBER_CFG),
                borderColor: Utils.CHART_COLORS.red,
                backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
            }]
    };
    let config = {
        type: 'bar',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Chart.js Bar Chart'
                }
            }
        },
    };
}





//---------------------------------Primera ejecucion inicial---------------------------------
for(let i=0; i < listaItems.length; i++){

    let myChart = generarGraficaRosquilla(listaItems[i]);

    conjuntoGraficas.push({
        grafica: myChart,
        id: listaItems[i].idTipoanalisis
    });

}



//-------------------------------------EVENTOS BOTONES-------------------------------------
const listaConjuntoBotones = document.querySelectorAll('[class~="conjuntoBotones"]');

for(let i=0; i<listaConjuntoBotones.length; i++){
    listaConjuntoBotones[i].addEventListener('click', function (){
        setTimeout(function(){
            let idTipoanalisis = listaConjuntoBotones[i].getAttribute("id");
            let conjuntoOpciones = document.getElementsByName("option" + idTipoanalisis);
            let valor;
            for(let q=0; q<conjuntoOpciones.length;q++){
                if(conjuntoOpciones[q].checked)
                    valor = conjuntoOpciones[q].getAttribute("value");
            }

            console.log(valor);
            mostrarGrafica(valor, idTipoanalisis);
        }, 500);    //Esperamos a que termine el JQuery de Bootstrap
        console.log('Termina');
    });
}


//Destruccion de la grafica anterior y creacion de la nueva
function mostrarGrafica(tipoGrafica, idTipoanalisis){

    let posListaItems, posGrafica;
    // console.log('idTipoanalisis: ' + idTipoanalisis);
    for(let n=0; n<listaItems.length;n++){
        // console.log('n:' + n + ' -> ' + listaItems[n].idTipoanalisis + ' VS ' + idTipoanalisis);
        if(listaItems[n].idTipoanalisis == idTipoanalisis){
            posListaItems = n;
        }
        if(conjuntoGraficas[n].id == idTipoanalisis){
            posGrafica = n;
        }
    }
    // console.log('Valor de posListaItems: ' + posListaItems);
    let myChart;
    switch (tipoGrafica){
        case ROSQUILLA:
            //Destruimos el anterior
            conjuntoGraficas[posGrafica].grafica.destroy();

            myChart = generarGraficaRosquilla(listaItems[posListaItems]);

            conjuntoGraficas[posGrafica].grafica = myChart;

            break;
        case LINEA:

            conjuntoGraficas[posGrafica].grafica.destroy();

            myChart = generarGraficaLinea(listaItems[posListaItems]);

            conjuntoGraficas[posGrafica].grafica = myChart;

            break;
    }
}








