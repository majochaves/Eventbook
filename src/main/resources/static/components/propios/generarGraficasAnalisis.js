/*
 * Uso:
 * 1. Importar este archivo cuando cargue el DOM (ya sea al final, con defer o usando un evento cuando carga el DOM)
 * 2. OBLIGATORIO: Importar CDN de https://www.chartjs.org/
 * 3. Colocar un canvas con id="myChart + ELID". Se debe concatenar el id del Tipoanalisis
 * 4. Crear una variable listaItems que añada los datos necesarios para la creacion de la grafica
 *
 */


Chart.defaults.font.size = 19;


for(let i=0; i < listaItems.length; i++){


    let data = {
        labels: listaItems[i].items,
        datasets: [{
            label: listaItems[i].nombreTipoanalisis,
            backgroundColor: listaItems[i].listaColores,
            hoverBorderWidth: 3,
            hoverBorderColor: '#000',
            data: listaItems[i].valores,
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
                    text: listaItems[i].nombreTipoanalisis
                }
            }
        },
    };

    let myChart = new Chart(
        document.getElementById('myChart' + listaItems[i].idTipoanalisis),
        config
    );


}


// let config = {
//     type: 'line',
//     data,
//     options: {}
// };




