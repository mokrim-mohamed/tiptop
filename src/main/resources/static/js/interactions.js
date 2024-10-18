// Pop-up Animation when user clicks on "Participer"
document.querySelector('.btn-dark').addEventListener('click', function() {
    alert('Vous êtes prêt à participer ! Bonne chance.');
});

// Logo Hover Effect
const logo = document.querySelector('.navbar-brand img');
logo.addEventListener('mouseover', () => {
    logo.style.transform = 'rotate(360deg)';
});
logo.addEventListener('mouseout', () => {
    logo.style.transform = 'rotate(0deg)';
});
