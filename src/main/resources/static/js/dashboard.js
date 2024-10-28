// Graphique pour les tickets fournis et utilisés
var ticketsCtx = document.getElementById('ticketsChart').getContext('2d');
var ticketsChart = new Chart(ticketsCtx, {
    type: 'bar',
    data: {
        labels: ['Tickets Gagnés', 'Tickets Utilisés'],
        datasets: [{
            label: 'Tickets Gagnés',
            data: [lotsGagner, lotsRecuperer], // Données fictives
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

// Graphique pour le nombre de lots gagnés
var lotsCtx = document.getElementById('lotsGagnesChart').getContext('2d');
var lotsGagnesChart = new Chart(lotsCtx, {
    type: 'doughnut',
    data: {
        labels: ['Lots Gagnés', 'Lots Restants'],
        datasets: [{
            label: 'Lots',
            data: [lotsGagner, ticketsData - lotsGagner], // Données fictives
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
            }
        },
    }
});

// Graphique pour les gagnants par sexe
var sexeCtx = document.getElementById('sexeChart').getContext('2d');
var sexeChart = new Chart(sexeCtx, {
    type: 'pie',
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
                position: 'top',
            },
        },
    }
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
