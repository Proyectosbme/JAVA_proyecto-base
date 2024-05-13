package com.contabilidad.seg.login;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && !session.isNew()) {
            session.invalidate(); // Invalida la sesión si existe y no es nueva
        }

        // Redirige al usuario a la página de inicio de sesión
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/login.xhtml");
    }
}

