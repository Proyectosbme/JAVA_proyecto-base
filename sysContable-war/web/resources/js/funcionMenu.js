/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function toggleMenu() {
    var menu = document.getElementById("menuOcultar");
    var content = document.getElementById("contentOcultar");
    if (menu.style.display === "none") {
        menu.style.display = "block";
        content.classList.remove("ui-g-12");
        content.classList.add("ui-g-9");
    } else {
        menu.style.display = "none";
        content.classList.remove("ui-g-9");
        content.classList.add("ui-g-12");
    }
}
