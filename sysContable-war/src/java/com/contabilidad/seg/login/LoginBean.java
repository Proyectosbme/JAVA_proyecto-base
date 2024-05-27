package com.contabilidad.seg.login;

import com.sistema.gen.ValidacionMensajes;
import com.sistema.contable.seguridad.busquedas.SegusuariosBusquedaLocal;
import com.sistema.contable.seguridad.entidades.Segusuarios;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private SegusuariosBusquedaLocal segusuariosBusqueda;
    
    @Inject
    private SessionManager sessionManager;

    private String username = "";
    private String password = "";
    private Date fecha = new Date();
    private Segusuarios usuario;
    private final ValidacionMensajes validarMsj = new ValidacionMensajes();

    @PostConstruct
    public void init() {
        password = "";
        username = "";
        this.logout();
    }

    public String login() {
        try {
            usuario = new Segusuarios();
            this.validarDatos();
            if (validarMsj.getMessages().isEmpty()) {
                Map<String, String> elementos = new HashMap<>();
                elementos.put("usuario", this.username);
                elementos.put("password2", this.password);

                // Lógica para verificar las credenciales del usuario
                this.validaDatosUsuario(elementos);

                if (validarMsj.getMessages().isEmpty()) {
                    this.guardarSession();
                    return "index"; // Redirigir al usuario a la página de inicio
                } else {
                    validarMsj.mostrarMsj();
                }
            } else {
                validarMsj.mostrarMsj();
            }
        } catch (Exception e) {
            e.printStackTrace();
            validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Ocurrio un error inesperado");
            validarMsj.mostrarMsj();
        }
        return null;
    }

    private void validarDatos() {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Ingrese usuario y contraseña");
        }
    }

    private void validaDatosUsuario(Map<String, String> elementos) {
        try {
            usuario = this.segusuariosBusqueda.buscarUsuarios(elementos);

            if (usuario == null) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "El usuario no existe");
            } else if (usuario.getEstado().compareTo(BigInteger.ONE) != 0) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Usuario inactivo");
            } else if (!usuario.getClave().equals(password)) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Contraseña incorrecta");
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarSession() {
        // Obtener la sesión actual
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        // Establecer un atributo en la sesión para indicar que el usuario está autenticado
        session.setAttribute("loggedIn", true);
        session.setAttribute("usuario", usuario);
        
        // Añadir la sesión al SessionManager, invalidando cualquier sesión anterior del mismo usuario
        sessionManager.addSession(usuario.getCoduser(), session);
    }

    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        // Obtener la sesión actual
        HttpSession session = (HttpSession) externalContext.getSession(false);
        if (session != null) {
            Segusuarios currentUser = (Segusuarios) session.getAttribute("usuario");
            if (currentUser != null) {
                sessionManager.removeSession(currentUser.getCoduser());
            }
            session.invalidate();
        }
        // Redirigir al usuario a la página de inicio de sesión
        return "login";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ValidacionMensajes getValidarMsj() {
        return validarMsj;
    }
}
