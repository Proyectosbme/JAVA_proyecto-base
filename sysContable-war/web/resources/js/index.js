var inactivityTimeout = null;
var maxInactivityTime = 60000; // 1 minuto (en milisegundos)

function resetInactivityTimer() {
    clearTimeout(inactivityTimeout);
    inactivityTimeout = setTimeout(logoutAndRedirect, maxInactivityTime);
}

function logoutAndRedirect() {
    // Realiza una solicitud AJAX al servidor para cerrar la sesión
    fetch('/logout', {
        method: 'POST',
        credentials: 'same-origin'  // Incluye las credenciales de sesión en la solicitud
    })
            .then(response => {
                // Redirige al usuario a la página de inicio de sesión después de cerrar la sesión
                window.location.href = 'login.xhtml';
            })
            .catch(error => console.error('Error:', error));
}

// Función para deshabilitar los botones de navegación del navegador
function disableBrowserNavigation() {
    window.history.pushState(null, '', window.location.href);
    window.onpopstate = function () {
        window.history.pushState(null, '', window.location.href);
    };
}

// Inicia la función para deshabilitar la navegación del navegador al cargar la página
window.onload = function () {
    disableBrowserNavigation();
    resetInactivityTimer(); // Inicia el temporizador de inactividad
};

// Reiniciar el temporizador de inactividad en caso de interacción del usuario
document.addEventListener('click', resetInactivityTimer);
document.addEventListener('mousemove', resetInactivityTimer);
document.addEventListener('keypress', resetInactivityTimer);


