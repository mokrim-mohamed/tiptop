// Graphique pour les tickets fournis et utilisés
var ticketsCtx = document.getElementById('ticketsChart').getContext('2d');
var ticketsChart = new Chart(ticketsCtx, {
    type: 'bar',
    data: {
        labels: ['Tickets Gagnés', 'Tickets non Utilisés'],
        datasets: [{
            label: 'Tickets Gagnés',
            data: [ lotsRecuperer,lotsGagner], // Données fictives
            backgroundColor: ['#4CAF50', '#FF9800']
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Type de Tickets',
                },
                ticks: {
                    autoSkip: false,
                    maxRotation: 0, // Pas de rotation
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Nombre de Tickets',
                },
            },
        },
    }
});
var lotsCtx = document.getElementById('lotsGagnesChart').getContext('2d');
var lotsGagnesChart = new Chart(lotsCtx, {
    type: 'doughnut',
    data: {
        labels: ['Lots Gagnés', 'Lots Restants'],
        datasets: [{
            label: 'Lots',
            data: [lotsGagner, ticketsData - lotsGagner], // Utiliser les données dynamiques
            backgroundColor: ['#FF6384', '#36A2EB'],
            borderWidth: 1 // Épaisseur de bordure
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                position: 'top', // Positionne la légende en haut
            },
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        return tooltipItem.label + ': ' + tooltipItem.raw; // Affiche la valeur dans l'infobulle
                    }
                }
            },
            datalabels: {
                color: '#ffffff', // Couleur du texte
                anchor: 'center', // Centre le texte
                align: 'center', // Aligne le texte au centre
                formatter: function(value, context) {
                    // Calcule le total des données
                    const total = context.chart.data.datasets[context.datasetIndex].data.reduce((acc, val) => acc + val, 0);
                    const percentage = ((value / total) * 100).toFixed(1) + '%'; // Calcul du pourcentage
                    return percentage; // Retourne le pourcentage
                }
            }
        },
    },
    plugins: [ChartDataLabels] // Ajoutez le plugin ici
});

var sexeCtx = document.getElementById('sexeChart').getContext('2d');
var sexeChart = new Chart(sexeCtx, {
    type: 'doughnut',
    data: {
        labels: ['Homme', 'Femme'],
        datasets: [{
            label: 'Sexe des Gagnants',
            data: [genderDataM, genderDataF], // Données fictives
            backgroundColor: ['#42A5F5', '#FF6384'],
            borderWidth: 1 // Épaisseur de bordure
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                position: 'top', // Positionne la légende en haut
            },
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        return tooltipItem.label + ': ' + tooltipItem.raw; // Affiche la valeur dans l'infobulle
                    }
                }
            },
            datalabels: {
                color: '#ffffff', // Couleur du texte
                anchor: 'center', // Centre le texte
                align: 'center', // Aligne le texte au centre
                formatter: function(value, context) {
                    // Calcule le total des données
                    const total = context.chart.data.datasets[context.datasetIndex].data.reduce((acc, val) => acc + val, 0);
                    const percentage = ((value / total) * 100).toFixed(1) + '%'; // Calcul du pourcentage
                    return percentage; // Retourne le pourcentage
                }
            }
        },
    },
    plugins: [ChartDataLabels] // Ajoutez le plugin ici
});


// Graphique pour les gagnants par type de gain
var ageCtx = document.getElementById('typeChart').getContext('2d');
var ageChart = new Chart(ageCtx, {
    type: 'bar',
    data: {
        labels: gainLabels,
        datasets: [{
            label: 'Nombre de Gagnants par Type de Gain',
            data: countGainsByType, // Données fictives
            backgroundColor: ['#FFCE56', '#36A2EB', '#FF6384', '#4BC0C0'],
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Type de Gain',
                },
                ticks: {
                    autoSkip: false,
                    maxRotation: 90, // Rotation des étiquettes si nécessaire
                    minRotation: 90,
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Nombre de Gagnants',
                },
            },
        },
    }
});

    // Initialisation du graphique
    const ctx = document.getElementById('ageChart').getContext('2d');
    const ageRangeChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ageLabels, // Labels provenant des tranches d'âge
            datasets: [{
                label: 'Gains par tranche d\'âge',
                data: ageCounts, // Données des gains par tranche d'âge
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(255, 99, 132, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(255, 99, 132, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `${context.dataset.label}: ${context.raw}`;
                        }
                    }
                }
            }
        }
    });
