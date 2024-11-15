document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');
    const passwordInput = document.getElementById('motDePasse');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const emailInput = document.getElementById('email');
    const confirmEmailInput = document.getElementById('confirmEmail');
    const telephoneInput = document.getElementById('telephone');
    const strengthBar = document.querySelector('.password-strength');
    const strengthText = document.querySelector('.strength-text');

    // Prevent copy/paste for sensitive fields
    const preventCopyPaste = (e) => {
        e.preventDefault();
        return false;
    };

    // Only prevent paste on confirmation fields
    const preventOnlyPaste = (e) => {
        if (e.type === 'paste') {
            e.preventDefault();
            return false;
        }
    };

    // Empêcher le copier-coller uniquement sur les champs de confirmation
    [confirmPasswordInput, confirmEmailInput].forEach(field => {
        field.addEventListener('paste', preventCopyPaste);
    });

    // Permettre la copie dans l'email mais pas le collage dans la confirmation
    emailInput.addEventListener('copy', () => true);
    emailInput.addEventListener('paste', () => true);
    emailInput.addEventListener('cut', () => true);

    // Validation du numéro de téléphone (10 chiffres max)
    telephoneInput.addEventListener('input', function(e) {
        // Supprimer tout ce qui n'est pas un chiffre
        this.value = this.value.replace(/\D/g, '');
        
        // Limiter à 10 chiffres
        if (this.value.length > 10) {
            this.value = this.value.slice(0, 10);
        }

        // Formater le numéro au format français (XX XX XX XX XX)
        if (this.value.length > 0) {
            let formattedNumber = this.value.match(/(\d{2})/g).join(' ');
            this.value = formattedNumber;
        }
    });

    // Password strength checker
    const checkPasswordStrength = (password) => {
        let strength = 0;
        const requirements = {
            length: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            lowercase: /[a-z]/.test(password),
            number: /[0-9]/.test(password),
        };

        // Update requirement indicators
        Object.keys(requirements).forEach(req => {
            const element = document.getElementById(req);
            if (requirements[req]) {
                element.classList.add('met');
                strength++;
            } else {
                element.classList.remove('met');
            }
        });

        // Update strength bar
        strengthBar.className = 'password-strength';
        let strengthClass = '';
        let strengthMessage = '';

        if (strength === 0) {
            strengthClass = 'very-weak';
            strengthMessage = 'Très faible';
        } else if (strength === 1) {
            strengthClass = 'weak';
            strengthMessage = 'Faible';
        } else if (strength === 2) {
            strengthClass = 'medium';
            strengthMessage = 'Moyen';
        } else if (strength === 3 || strength === 4) {
            strengthClass = 'strong';
            strengthMessage = 'Fort';
        } else {
            strengthClass = 'very-strong';
            strengthMessage = 'Très fort';
        }

        strengthBar.classList.add(strengthClass);
        strengthText.textContent = strengthMessage;
        return strength;
    };

    // Real-time password strength checking
    passwordInput.addEventListener('input', function() {
        checkPasswordStrength(this.value);
    });

    // Email validation
    const validateEmails = () => {
        const emailsMatch = emailInput.value === confirmEmailInput.value;
        const isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value);
        
        if (!isValidEmail) {
            emailInput.classList.add('is-invalid');
            emailInput.nextElementSibling.style.display = 'block';
        } else {
            emailInput.classList.remove('is-invalid');
            emailInput.nextElementSibling.style.display = 'none';
        }

        if (!emailsMatch && confirmEmailInput.value) {
            confirmEmailInput.classList.add('is-invalid');
            confirmEmailInput.nextElementSibling.style.display = 'block';
        } else {
            confirmEmailInput.classList.remove('is-invalid');
            confirmEmailInput.nextElementSibling.style.display = 'none';
        }

        return isValidEmail && emailsMatch;
    };

    // Password validation
    const validatePasswords = () => {
        const passwordsMatch = passwordInput.value === confirmPasswordInput.value;
        const strongEnough = checkPasswordStrength(passwordInput.value) >= 3;

        if (!passwordsMatch && confirmPasswordInput.value) {
            confirmPasswordInput.classList.add('is-invalid');
            confirmPasswordInput.nextElementSibling.style.display = 'block';
        } else {
            confirmPasswordInput.classList.remove('is-invalid');
            confirmPasswordInput.nextElementSibling.style.display = 'none';
        }

        return passwordsMatch && strongEnough;
    };

    // Phone validation
    const validatePhone = () => {
        const phoneValue = telephoneInput.value.replace(/\s/g, '');
        return phoneValue.length === 0 || phoneValue.length === 10;
    };

    // Real-time validation
    emailInput.addEventListener('input', validateEmails);
    confirmEmailInput.addEventListener('input', validateEmails);
    passwordInput.addEventListener('input', validatePasswords);
    confirmPasswordInput.addEventListener('input', validatePasswords);

    // Form submission
    form.addEventListener('submit', function(e) {
        const isEmailValid = validateEmails();
        const isPasswordValid = validatePasswords();
        const isPhoneValid = validatePhone();

        if (!isEmailValid || !isPasswordValid || !isPhoneValid) {
            e.preventDefault();
            return false;
        }
    });
});