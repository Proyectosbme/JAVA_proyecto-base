package com.sistema.seg.login;

import com.sistema.seguridad.entidades.Segusuarios;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet es un servlet que maneja la funcionalidad de cierre de sesión.
 * Invalida la sesión actual del usuario y lo redirige a la página de inicio de sesión.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
     @Inject
    private SessionManager sessionManager;
    /**
     * Maneja las solicitudes HTTP POST para cerrar la sesión del usuario.
     *
     * @param request El objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response El objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtiene la sesión actual, sin crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verifica si la sesión existe y no es nueva
       if (session != null) {
            Segusuarios currentUser = (Segusuarios) session.getAttribute("usuario");
            if (currentUser != null) {
                sessionManager.removeSession(currentUser.getCoduser());
            }
            session.invalidate();
        }

        // Redirige al usuario a la página de inicio de sesión
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "faces/login.xhtml");
    }
    
}


