// Pop-up Animation when user clicks on "Participer"
document.querySelector('.btn-dark').addEventListener('click', function() {
    alert('Vous êtes prêt à participer ! Bonne chance.');
});

// Logo Hover Effect
const logo = document.querySelector('.navbar-brand img');
logo.addEventListener('mouseover', () => {
    logo.style.transform = 'rotate(360deg)';
    logo.style.transition = 'transform 0.5s';
});
logo.addEventListener('mouseout', () => {
    logo.style.transform = 'rotate(0deg)';
});

// Cookie Banner Function
function acceptCookies() {
    document.getElementById('cookie-banner').style.display = 'none';
    // Ici, vous pouvez ajouter un cookie pour mémoriser le choix de l'utilisateur
}
// Animation lors du clic sur le bouton "Jouer Maintenant"
document.querySelector('.cta-button').addEventListener('click', function() {
    alert('Bonne chance dans le jeu concours de Thé Tip Top !');
});
