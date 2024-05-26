// Variable que almacenará el identificador del temporizador de inactividad
var inactivityTimeout = null;

// Tiempo máximo de inactividad permitido (en milisegundos), configurado a 1 minuto
var maxInactivityTime = 60000; // 1 minuto (en milisegundos)

/**
 * Reinicia el temporizador de inactividad. Se utiliza para evitar que el usuario sea desconectado
 * si interactúa con la página.
 */
function resetInactivityTimer() {
    // Limpia el temporizador de inactividad existente
    clearTimeout(inactivityTimeout);
    // Configura un nuevo temporizador de inactividad que llama a logoutAndRedirect después de maxInactivityTime
    inactivityTimeout = setTimeout(logoutAndRedirect, maxInactivityTime);
}

/**
 * Cierra la sesión del usuario y redirige a la página de inicio de sesión.
 */
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
    .catch(error => console.error('Error:', error)); // Muestra un mensaje de error en la consola si la solicitud falla
}

/**
 * Deshabilita los botones de navegación del navegador (atrás y adelante) para evitar
 * que el usuario navegue a páginas previas de la sesión después de cerrar la sesión.
 */
function disableBrowserNavigation() {
    // Agrega una nueva entrada al historial del navegador
    window.history.pushState(null, '', window.location.href);
    // Escucha el evento popstate y agrega una nueva entrada al historial para deshabilitar la navegación
    window.onpopstate = function () {
        window.history.pushState(null, '', window.location.href);
    };
}

// Inicializa las funciones cuando la página se carga
window.onload = function () {
    disableBrowserNavigation(); // Deshabilita la navegación del navegador
    resetInactivityTimer();     // Inicia el temporizador de inactividad
};

// Reinicia el temporizador de inactividad en caso de interacción del usuario
document.addEventListener('click', resetInactivityTimer);    // Detecta clics del ratón
document.addEventListener('mousemove', resetInactivityTimer); // Detecta movimientos del ratón
document.addEventListener('keypress', resetInactivityTimer);  // Detecta pulsaciones de teclas
