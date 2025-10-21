// Form Validation Script
document.addEventListener('DOMContentLoaded', function() {
    // Remove default HTML5 validation
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.setAttribute('novalidate', 'novalidate');
    });
});

// Profile Form Validation
function validateProfileForm() {
    let isValid = true;
    
    // Clear previous errors
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

// Show error message
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

// Clear all errors
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

// Real-time validation on blur
document.addEventListener('DOMContentLoaded', function() {
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');
    
    if (firstName) {
        firstName.addEventListener('blur', function() {
            const errorSpan = document.getElementById('firstNameError');
            errorSpan.textContent = '';
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
            errorSpan.textContent = '';
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
    
    if (email) {
        email.addEventListener('blur', function() {
            const errorSpan = document.getElementById('emailError');
            errorSpan.textContent = '';
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
        phone.addEventListener('blur', function() {
            const errorSpan = document.getElementById('phoneError');
            errorSpan.textContent = '';
            this.classList.remove('error-input');
            
            const value = this.value.trim();
            if (value !== '') {
                const phoneRegex = /^\d{10}$/;
                if (!phoneRegex.test(value)) {
                    showError('phone', 'Phone number must be exactly 10 digits');
                }
            }
        });
        
        // Only allow numbers in phone field
        phone.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }
});

// Mobile menu toggle
function toggleMobileMenu() {
    const nav = document.querySelector('.main-nav');
    if (nav) {
        nav.classList.toggle('active');
    }
}