/*
 * Este filtro se encarga de restringir el acceso a ciertas páginas y recursos estáticos
 * según las reglas definidas en los conjuntos ALLOWED_PAGES y STATIC_RESOURCES.
 */
package com.sistema.seg.filtros;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filtro para restringir el acceso a páginas y recursos estáticos.
 * Este filtro redirige a los usuarios a la página de inicio de sesión si intentan acceder
 * a páginas no permitidas sin estar autenticados.
 */
public class PageRestrictionFilter implements Filter {

    // Conjunto de páginas permitidas que los usuarios pueden acceder sin necesidad de autenticación
    private static final Set<String> ALLOWED_PAGES = new HashSet<>(Arrays.asList(
            "/index.xhtml",
            "/login.xhtml",
            "/ImpresionReporteServlet"
    ));

    // Conjunto de recursos estáticos permitidos
    private static final Set<String> STATIC_RESOURCES = new HashSet<>(Arrays.asList(
            "/javax.faces.resource/"
    ));

    /**
     * Método de inicialización del filtro.
     * @param filterConfig La configuración del filtro.
     * @throws ServletException Si ocurre un error durante la inicialización.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Código de inicialización, si es necesario
    }

    /**
     * Método principal del filtro que se ejecuta en cada solicitud.
     * @param request La solicitud del cliente.
     * @param response La respuesta del servidor.
     * @param chain El objeto FilterChain para invocar el siguiente filtro en la cadena.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Verificar si la página solicitada está permitida
        if (!ALLOWED_PAGES.contains(path) && !isStaticResource(path)) {
            // Redirigir a la página de inicio de sesión si la página no está permitida
            res.sendRedirect(req.getContextPath() + "/index.xhtml");
            return;
        }

        // Continuar con la cadena de filtros si la página está permitida
        chain.doFilter(request, response);
    }

    /**
     * Método para determinar si un recurso es estático.
     * @param path El camino del recurso.
     * @return true si el recurso es estático, false en caso contrario.
     */
    private boolean isStaticResource(String path) {
        for (String staticResource : STATIC_RESOURCES) {
            if (path.startsWith(staticResource)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método de destrucción del filtro.
     */
    @Override
    public void destroy() {
        // Código de limpieza, si es necesario
    }
}
