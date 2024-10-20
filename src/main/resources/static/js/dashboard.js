
        // Graphique pour les tickets fournis et utilisés
        var ticketsCtx = document.getElementById('ticketsChart').getContext('2d');
        var ticketsChart = new Chart(ticketsCtx, {
            type: 'bar',
            data: {
                labels: ['Tickets Fournis', 'Tickets Utilisés'],
                datasets: [{
                    label: 'Nombre de tickets',
                    data: [3000, 1500], // Données fictives, à remplacer par des données dynamiques
                    backgroundColor: ['#4CAF50', '#FF9800']
                }]
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
                    data: [50, 150], // Données fictives
                    backgroundColor: ['#FF6384', '#36A2EB']
                }]
            }
        });

        // Graphique pour les gagnants par sexe
        var sexeCtx = document.getElementById('sexeChart').getContext('2d');
        var sexeChart = new Chart(sexeCtx, {
            type: 'pie',
            data: {
                labels: ['Homme', 'Femme'],
                datasets: [{
                    label: 'Sexe des gagnants',
                    data: [60, 40], // Données fictives
                    backgroundColor: ['#42A5F5', '#FF6384']
                }]
            }
        });

        // Graphique pour les gagnants par tranche d'âge
        var ageCtx = document.getElementById('ageChart').getContext('2d');
        var ageChart = new Chart(ageCtx, {
            type: 'bar',
            data: {
                labels: ['18-25', '26-35', '36-45', '46+'],
                datasets: [{
                    label: 'Nombre de gagnants par âge',
                    data: [15, 25, 10, 5], // Données fictives
                    backgroundColor: ['#FFCE56', '#36A2EB', '#FF6384', '#4BC0C0']
                }]
            }
        });
        
    