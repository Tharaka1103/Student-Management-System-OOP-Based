// Form Validation Script
document.addEventListener('DOMContentLoaded', function() {
    // Remove default HTML5 validation
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.setAttribute('novalidate', 'novalidate');
    });
});

// ========================================
// LOGIN VALIDATION
// ========================================
function validateLogin() {
    let isValid = true;
    clearErrors();
    
    // Username validation
    const username = document.getElementById('username');
    if (username) {
        const usernameValue = username.value.trim();
        if (usernameValue === '') {
            showError('username', 'Username is required');
            isValid = false;
        }
    }
    
    // Password validation
    const password = document.getElementById('password');
    if (password) {
        const passwordValue = password.value;
        if (passwordValue === '') {
            showError('password', 'Password is required');
            isValid = false;
        }
    }
    
    return isValid;
}

// ========================================
// REGISTRATION VALIDATION
// ========================================
function validateRegistration() {
    let isValid = true;
    clearErrors();
    
    // First Name validation
    const firstName = document.getElementById('firstName');
    if (firstName) {
        const firstNameValue = firstName.value.trim();
        if (firstNameValue === '') {
            showError('firstName', 'First name is required');
            isValid = false;
        } else if (firstNameValue.length < 2) {
            showError('firstName', 'First name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(firstNameValue)) {
            showError('firstName', 'First name can only contain letters');
            isValid = false;
        }
    }
    
    // Last Name validation
    const lastName = document.getElementById('lastName');
    if (lastName) {
        const lastNameValue = lastName.value.trim();
        if (lastNameValue === '') {
            showError('lastName', 'Last name is required');
            isValid = false;
        } else if (lastNameValue.length < 2) {
            showError('lastName', 'Last name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(lastNameValue)) {
            showError('lastName', 'Last name can only contain letters');
            isValid = false;
        }
    }
    
    // Username validation
    const username = document.getElementById('username');
    if (username) {
        const usernameValue = username.value.trim();
        const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
        if (usernameValue === '') {
            showError('username', 'Username is required');
            isValid = false;
        } else if (!usernameRegex.test(usernameValue)) {
            showError('username', 'Username must be 3-20 characters (letters, numbers, underscores)');
            isValid = false;
        }
    }
    
    // Email validation
    const email = document.getElementById('email');
    if (email) {
        const emailValue = email.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailValue === '') {
            showError('email', 'Email is required');
            isValid = false;
        } else if (!emailRegex.test(emailValue)) {
            showError('email', 'Please enter a valid email address');
            isValid = false;
        }
    }
    
    // Phone validation (optional but must be valid if provided)
    const phone = document.getElementById('phone');
    if (phone) {
        const phoneValue = phone.value.trim();
        if (phoneValue !== '') {
            const phoneRegex = /^\d{10}$/;
            if (!phoneRegex.test(phoneValue)) {
                showError('phone', 'Phone number must be exactly 10 digits');
                isValid = false;
            }
        }
    }
    
    // Password validation
    const password = document.getElementById('password');
    if (password) {
        const passwordValue = password.value;
        if (passwordValue === '') {
            showError('password', 'Password is required');
            isValid = false;
        } else if (passwordValue.length < 6) {
            showError('password', 'Password must be at least 6 characters');
            isValid = false;
        }
    }
    
    // Confirm Password validation
    const confirmPassword = document.getElementById('confirmPassword');
    if (confirmPassword && password) {
        const confirmPasswordValue = confirmPassword.value;
        const passwordValue = password.value;
        if (confirmPasswordValue === '') {
            showError('confirmPassword', 'Please confirm your password');
            isValid = false;
        } else if (confirmPasswordValue !== passwordValue) {
            showError('confirmPassword', 'Passwords do not match');
            isValid = false;
        }
    }
    
    return isValid;
}

// ========================================
// PROFILE VALIDATION
// ========================================
function validateProfileForm() {
    let isValid = true;
    clearErrors();
    
    // First Name validation
    const firstName = document.getElementById('firstName');
    if (firstName) {
        const firstNameValue = firstName.value.trim();
        if (firstNameValue === '') {
            showError('firstName', 'First name is required');
            isValid = false;
        } else if (firstNameValue.length < 2) {
            showError('firstName', 'First name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(firstNameValue)) {
            showError('firstName', 'First name can only contain letters');
            isValid = false;
        }
    }
    
    // Last Name validation
    const lastName = document.getElementById('lastName');
    if (lastName) {
        const lastNameValue = lastName.value.trim();
        if (lastNameValue === '') {
            showError('lastName', 'Last name is required');
            isValid = false;
        } else if (lastNameValue.length < 2) {
            showError('lastName', 'Last name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(lastNameValue)) {
            showError('lastName', 'Last name can only contain letters');
            isValid = false;
        }
    }
    
    // Email validation
    const email = document.getElementById('email');
    if (email) {
        const emailValue = email.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailValue === '') {
            showError('email', 'Email is required');
            isValid = false;
        } else if (!emailRegex.test(emailValue)) {
            showError('email', 'Please enter a valid email address');
            isValid = false;
        }
    }
    
    // Phone validation (optional but must be valid if provided)
    const phone = document.getElementById('phone');
    if (phone) {
        const phoneValue = phone.value.trim();
        if (phoneValue !== '') {
            const phoneRegex = /^\d{10}$/;
            if (!phoneRegex.test(phoneValue)) {
                showError('phone', 'Phone number must be exactly 10 digits');
                isValid = false;
            }
        }
    }
    
    return isValid;
}

// ========================================
// HELPER FUNCTIONS
// ========================================
function showError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorSpan = document.getElementById(fieldId + 'Error');
    
    if (field) {
        field.classList.add('error-input');
    }
    
    if (errorSpan) {
        errorSpan.textContent = message;
        errorSpan.style.display = 'block';
    }
}

function clearErrors() {
    const errorSpans = document.querySelectorAll('.error');
    errorSpans.forEach(span => {
        span.textContent = '';
        span.style.display = 'none';
    });
    
    const errorInputs = document.querySelectorAll('.error-input');
    errorInputs.forEach(input => {
        input.classList.remove('error-input');
    });
}

// ========================================
// PASSWORD TOGGLE
// ========================================
function togglePassword(fieldId) {
    const field = document.getElementById(fieldId);
    const button = event.currentTarget;
    
    if (field.type === 'password') {
        field.type = 'text';
        button.textContent = '🙈';
    } else {
        field.type = 'password';
        button.textContent = '👁️';
    }
}

// ========================================
// REAL-TIME VALIDATION
// ========================================
document.addEventListener('DOMContentLoaded', function() {
    // Login page
    const loginUsername = document.getElementById('username');
    const loginPassword = document.getElementById('password');
    
    if (loginUsername && loginPassword) {
        loginUsername.addEventListener('blur', function() {
            const errorSpan = document.getElementById('usernameError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            if (this.value.trim() === '') {
                showError('username', 'Username is required');
            }
        });
        
        loginPassword.addEventListener('blur', function() {
            const errorSpan = document.getElementById('passwordError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            if (this.value === '') {
                showError('password', 'Password is required');
            }
        });
    }
    
    // Registration and Profile pages
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');
    const username = document.getElementById('username');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    
    if (firstName) {
        firstName.addEventListener('blur', function() {
            const errorSpan = document.getElementById('firstNameError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            if (value === '') {
                showError('firstName', 'First name is required');
            } else if (value.length < 2) {
                showError('firstName', 'First name must be at least 2 characters');
            } else if (!/^[a-zA-Z\s]+$/.test(value)) {
                showError('firstName', 'First name can only contain letters');
            }
        });
    }
    
    if (lastName) {
        lastName.addEventListener('blur', function() {
            const errorSpan = document.getElementById('lastNameError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            if (value === '') {
                showError('lastName', 'Last name is required');
            } else if (value.length < 2) {
                showError('lastName', 'Last name must be at least 2 characters');
            } else if (!/^[a-zA-Z\s]+$/.test(value)) {
                showError('lastName', 'Last name can only contain letters');
            }
        });
    }
    
    if (username && !loginUsername) {
        username.addEventListener('blur', function() {
            const errorSpan = document.getElementById('usernameError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
            if (value === '') {
                showError('username', 'Username is required');
            } else if (!usernameRegex.test(value)) {
                showError('username', 'Username must be 3-20 characters (letters, numbers, underscores)');
            }
        });
    }
    
    if (email) {
        email.addEventListener('blur', function() {
            const errorSpan = document.getElementById('emailError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (value === '') {
                showError('email', 'Email is required');
            } else if (!emailRegex.test(value)) {
                showError('email', 'Please enter a valid email address');
            }
        });
    }
    
    if (phone) {
        phone.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
        
        phone.addEventListener('blur', function() {
            const errorSpan = document.getElementById('phoneError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            if (value !== '') {
                const phoneRegex = /^\d{10}$/;
                if (!phoneRegex.test(value)) {
                    showError('phone', 'Phone number must be exactly 10 digits');
                }
            }
        });
    }
    
    if (password && !loginPassword) {
        password.addEventListener('blur', function() {
            const errorSpan = document.getElementById('passwordError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value;
            if (value === '') {
                showError('password', 'Password is required');
            } else if (value.length < 6) {
                showError('password', 'Password must be at least 6 characters');
            }
        });
    }
    
    if (confirmPassword) {
        confirmPassword.addEventListener('blur', function() {
            const errorSpan = document.getElementById('confirmPasswordError');
            if (errorSpan) errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value;
            const passwordValue = password ? password.value : '';
            if (value === '') {
                showError('confirmPassword', 'Please confirm your password');
            } else if (value !== passwordValue) {
                showError('confirmPassword', 'Passwords do not match');
            }
        });
    }
});