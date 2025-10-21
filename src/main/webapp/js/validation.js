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
    
    const username = document.getElementById('username');
    if (username) {
        const usernameValue = username.value.trim();
        if (usernameValue === '') {
            showError('username', 'Username is required');
            isValid = false;
        }
    }
    
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
// COURSE FORM VALIDATION
// ========================================
function validateCourseForm() {
    let isValid = true;
    clearErrors();
    
    const courseCode = document.getElementById('courseCode');
    if (courseCode) {
        const value = courseCode.value.trim();
        if (value === '') {
            showError('courseCode', 'Course code is required');
            isValid = false;
        } else if (value.length < 2) {
            showError('courseCode', 'Course code must be at least 2 characters');
            isValid = false;
        }
    }
    
    const courseName = document.getElementById('courseName');
    if (courseName) {
        const value = courseName.value.trim();
        if (value === '') {
            showError('courseName', 'Course name is required');
            isValid = false;
        } else if (value.length < 3) {
            showError('courseName', 'Course name must be at least 3 characters');
            isValid = false;
        }
    }
    
    const duration = document.getElementById('duration');
    if (duration) {
        const value = duration.value;
        if (value === '' || value <= 0) {
            showError('duration', 'Duration must be greater than 0');
            isValid = false;
        }
    }
    
    const description = document.getElementById('description');
    if (description) {
        const value = description.value.trim();
        if (value === '') {
            showError('description', 'Description is required');
            isValid = false;
        } else if (value.length < 10) {
            showError('description', 'Description must be at least 10 characters');
            isValid = false;
        }
    }
    
    return isValid;
}

// ========================================
// INSTRUCTOR FORM VALIDATION
// ========================================
function validateInstructorForm() {
    let isValid = true;
    clearErrors();
    
    const firstName = document.getElementById('firstName');
    if (firstName) {
        const value = firstName.value.trim();
        if (value === '') {
            showError('firstName', 'First name is required');
            isValid = false;
        } else if (value.length < 2) {
            showError('firstName', 'First name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(value)) {
            showError('firstName', 'First name can only contain letters');
            isValid = false;
        }
    }
    
    const lastName = document.getElementById('lastName');
    if (lastName) {
        const value = lastName.value.trim();
        if (value === '') {
            showError('lastName', 'Last name is required');
            isValid = false;
        } else if (value.length < 2) {
            showError('lastName', 'Last name must be at least 2 characters');
            isValid = false;
        } else if (!/^[a-zA-Z\s]+$/.test(value)) {
            showError('lastName', 'Last name can only contain letters');
            isValid = false;
        }
    }
    
    const email = document.getElementById('email');
    if (email) {
        const value = email.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (value === '') {
            showError('email', 'Email is required');
            isValid = false;
        } else if (!emailRegex.test(value)) {
            showError('email', 'Please enter a valid email address');
            isValid = false;
        }
    }
    
    const phone = document.getElementById('phone');
    if (phone) {
        const value = phone.value.trim();
        if (value !== '') {
            const phoneRegex = /^\d{10}$/;
            if (!phoneRegex.test(value)) {
                showError('phone', 'Phone number must be exactly 10 digits');
                isValid = false;
            }
        }
    }
    
    const expertise = document.getElementById('expertise');
    if (expertise) {
        const value = expertise.value.trim();
        if (value === '') {
            showError('expertise', 'Expertise is required');
            isValid = false;
        } else if (value.length < 3) {
            showError('expertise', 'Expertise must be at least 3 characters');
            isValid = false;
        }
    }
    
    return isValid;
}

// ========================================
// CLASS FORM VALIDATION
// ========================================
function validateClassForm() {
    let isValid = true;
    clearErrors();
    
    const className = document.getElementById('className');
    if (className) {
        const value = className.value.trim();
        if (value === '') {
            showError('className', 'Class name is required');
            isValid = false;
        } else if (value.length < 3) {
            showError('className', 'Class name must be at least 3 characters');
            isValid = false;
        }
    }
    
    const courseId = document.getElementById('courseId');
    if (courseId) {
        const value = courseId.value;
        if (value === '') {
            showError('courseId', 'Please select a course');
            isValid = false;
        }
    }
    
    const instructorId = document.getElementById('instructorId');
    if (instructorId) {
        const value = instructorId.value;
        if (value === '') {
            showError('instructorId', 'Please select an instructor');
            isValid = false;
        }
    }
    
    const schedule = document.getElementById('schedule');
    if (schedule) {
        const value = schedule.value.trim();
        if (value === '') {
            showError('schedule', 'Schedule is required');
            isValid = false;
        }
    }
    
    const room = document.getElementById('room');
    if (room) {
        const value = room.value.trim();
        if (value === '') {
            showError('room', 'Room is required');
            isValid = false;
        }
    }
    
    const capacity = document.getElementById('capacity');
    if (capacity) {
        const value = capacity.value;
        if (value === '' || value <= 0) {
            showError('capacity', 'Capacity must be greater than 0');
            isValid = false;
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
// MOBILE MENU TOGGLE
// ========================================
function toggleMobileMenu() {
    const nav = document.querySelector('.main-nav');
    if (nav) {
        nav.classList.toggle('active');
    }
}

// ========================================
// REAL-TIME VALIDATION
// ========================================
document.addEventListener('DOMContentLoaded', function() {
    // Phone number - only allow digits
    const phoneInputs = document.querySelectorAll('input[type="tel"]');
    phoneInputs.forEach(phone => {
        phone.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    });
    
    // Number inputs - prevent negative values
    const numberInputs = document.querySelectorAll('input[type="number"]');
    numberInputs.forEach(input => {
        input.addEventListener('input', function() {
            if (this.value < 0) {
                this.value = 0;
            }
        });
    });
});