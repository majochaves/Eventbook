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
const BARRA = 'BARRA';
const RADAR = 'RADAR';

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
                    left: 100,
                    right: 100
                }
            },
            scales: {
                y: {
                    suggestedMin: 0,
                    suggestedMax: 5,
                    ticks: {
                        stepSize: 1
                    }
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
                data: elItem.valores,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.4)',
                hoverBorderWidth: 3,
                hoverBorderColor: 'rgba(255,0,213,0.8)'
            }]
    };

    let config = {
        type: 'bar',
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
            },
            layout: {
                padding: {
                    left: 100,
                    right: 100
                }
            }
        },
    };

    return (new Chart(
        document.getElementById('myChart' + elItem.idTipoanalisis),
        config
    ));

}


function generarGraficaRadar(elItem){


    let data = {
        labels: elItem.items,
        datasets: [{
            label: elItem.nombreTipoanalisis,
            data: elItem.valores,
            borderColor: 'rgb(54, 162, 235)',
            backgroundColor: 'rgb(54, 162, 235, 0.4)',
            hoverBorderWidth: 3,
            hoverBorderColor: 'rgb(7,155,255)',
        }]
    };

    let config = {
        type: 'radar',
        data: data,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
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
            },
            scales: {
                r: {
                    suggestedMin: 0,
                    ticks: {
                        stepSize: 1
                    }
                }

            }
        },
    };

    return myChart = new Chart(
        document.getElementById('myChart' + elItem.idTipoanalisis),
        config
    );

}


//---------------------------------Primera ejecucion inicial---------------------------------
for(let i=0; i < listaItems.length; i++){

    let myChart = generarGraficaRosquilla(listaItems[i]);
    // myChart.canvas.parentNode.style.height = '500px';
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

            mostrarGrafica(valor, idTipoanalisis);
        }, 500);    //Esperamos a que termine el JQuery de Bootstrap

    });
}


//Destruccion de la grafica anterior y creacion de la nueva
function mostrarGrafica(tipoGrafica, idTipoanalisis){

    let posListaItems, posGrafica;
    for(let n=0; n<listaItems.length;n++){
        // console.log('n:' + n + ' -> ' + listaItems[n].idTipoanalisis + ' VS ' + idTipoanalisis);
        if(listaItems[n].idTipoanalisis == idTipoanalisis){
            posListaItems = n;
        }
        if(conjuntoGraficas[n].id == idTipoanalisis){
            posGrafica = n;
        }
    }
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
        case BARRA:

            conjuntoGraficas[posGrafica].grafica.destroy();
            myChart = generarGraficaBarras(listaItems[posListaItems]);
            conjuntoGraficas[posGrafica].grafica = myChart;

            break;
        case RADAR:

            conjuntoGraficas[posGrafica].grafica.destroy();
            myChart = generarGraficaRadar(listaItems[posListaItems]);
            conjuntoGraficas[posGrafica].grafica = myChart;

            break;
    }
}








