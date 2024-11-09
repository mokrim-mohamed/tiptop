document.addEventListener('DOMContentLoaded', () => {
    const loginBox = document.querySelector('.login-box');
    const button = document.querySelector('button');

    // Animation d'apparition de la boîte de connexion
    loginBox.style.opacity = 0;
    loginBox.style.transform = 'translateY(30px)';
    setTimeout(() => {
        loginBox.style.transition = 'opacity 0.8s ease, transform 0.8s ease';
        loginBox.style.opacity = 1;
        loginBox.style.transform = 'translateY(0)';
    }, 100);

    // Changement de couleur du bouton au clic
    button.addEventListener('mousedown', () => {
        button.style.backgroundColor = '#1e4602';
    });

    button.addEventListener('mouseup', () => {
        button.style.backgroundColor = '#367507';
    });
});
