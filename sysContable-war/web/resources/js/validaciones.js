/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validarSoloLetras(event) {
    var charCode = event.which || event.keyCode;
    var charStr = String.fromCharCode(charCode);
    // Verificar si el carácter presionado es una letra (mayúscula o minúscula), espacio o tilde (pleca)
    if (/^[A-Za-zñÑ\sáéíóúÁÉÍÓÚ]+$/.test(charStr)) {
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
