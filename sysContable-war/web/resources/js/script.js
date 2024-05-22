$(document).ready(function () {
    // Verificar si hay un estado guardado en el almacenamiento local
    var isMenuActive = localStorage.getItem('menuActive') === 'true';

    // Si el menú estaba activo antes de la recarga de la página, aplicar la clase 'active'
    if (isMenuActive) {
        $('#sidebar, #content').addClass('active');
    }

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar, #content').toggleClass('active');
        $('.collapse.in').toggleClass('in');
        $('a[aria-expanded=true]').attr('aria-expanded', 'false');
        document.getElementById("bodyContent").style.width = "100%";

        // Guardar el estado del menú en el almacenamiento local
        localStorage.setItem('menuActive', $('#sidebar').hasClass('active'));
    });
});

function validarSoloLetras(event) {
    var charCode = event.which || event.keyCode;
    var charStr = String.fromCharCode(charCode);
    // Verificar si el carácter presionado es una letra (mayúscula o minúscula)
    if (/^[A-Za-zñÑ]+$/.test(charStr)) {
        return true; // Permitir la entrada del carácter
    } else {
        event.preventDefault(); // Evitar la entrada del carácter
        return false;
    }
}

function soloNumeros(event) {
    // Obtener el código de la tecla presionada
    var charCode = (event.which) ? event.which : event.keyCode;

    // Permitir solo los números (0-9) y las teclas de control como Backspace, Enter y flechas
    if ((charCode < 48 || charCode > 57) && (charCode != 8 && charCode != 13 && charCode != 37 && charCode != 39)) {
        event.preventDefault();
    }
}
