document.addEventListener("DOMContentLoaded", function() {
    const banner = document.getElementById("cookie-banner");

    // Vérification du choix de l'utilisateur dans localStorage
    const cookiesAccepted = localStorage.getItem("cookiesAccepted");

    // Afficher la bannière seulement si l'utilisateur n'a pas encore fait de choix
    if (cookiesAccepted === null) {
        banner.style.display = "flex";
    }

    // Lorsqu'on accepte les cookies
    document.getElementById("accept-cookies").addEventListener("click", function() {
        localStorage.setItem("cookiesAccepted", "true");
        banner.style.display = "none";
    });

    // Lorsqu'on refuse les cookies
    document.getElementById("decline-cookies").addEventListener("click", function() {
        localStorage.setItem("cookiesAccepted", "false");
        banner.style.display = "none";
    });

    // Affiche les paramètres (dans ce cas, juste un message de placeholder)
    document.getElementById("settings-cookies").addEventListener("click", function() {
        alert("Paramètres des cookies à personnaliser ici.");
    });
});
