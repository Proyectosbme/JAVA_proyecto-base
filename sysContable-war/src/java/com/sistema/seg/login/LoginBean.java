package com.sistema.seg.login;

import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.general.entidades.Gencatdeta;
import com.sistema.general.entidades.GencatdetaPK;
import com.sistema.general.negocio.GenProcesosLocal;
import static com.sistema.seg.login.cambioClave.encryptPassword;
import com.sistema.seguridad.entidades.Segusuarios;
import com.sistema.seguridad.negocio.SegBusquedaLocal;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private GenProcesosLocal genProcesos;
    @EJB
    private SegBusquedaLocal segBusqueda;

    @Inject
    private SessionManager sessionManager;

    private String username = "";
    private String password = "";
    private Date fecha = new Date();
    private Segusuarios usuario;
    private boolean nuevo = false;
    private final ValidacionMensajes validarMsj = new ValidacionMensajes();
    private int intentos = 0;

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
                    try {
                        this.guardarSession();
                    } catch (IllegalStateException e) {
                        return "login";
                    }
                    if (!nuevo) {
                        return "index"; // Redirigir al usuario a la página de inicio
                    } else {
                        return "clave";
                    }

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
            List<Segusuarios> lstUsuarios = new ArrayList();
            lstUsuarios = this.segBusqueda.buscarUsuarios(elementos);
            if (!lstUsuarios.isEmpty()) {
                usuario = lstUsuarios.get(0);
            }
            if (usuario == null) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "El usuario no existe");
            } else if (usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor()
                    .compareTo(new BigInteger("20")) == 0) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Usuario bloqueado");
            } else if (usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor()
                    .compareTo(new BigInteger("30")) == 0) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Usuario inactivo");
            } else if (usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor()
                    .compareTo(new BigInteger("40")) == 0 || usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor()
                    .compareTo(new BigInteger("50")) == 0) {
                nuevo = true;
            } else if (!verifyPassword(password, usuario.getClave())) {
                validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Contraseña incorrecta");
                intentos++;

                if (intentos == 3) {
                    Gencatdeta estadodt = new Gencatdeta(new GencatdetaPK(new BigInteger("10"), new BigInteger("10"), new BigInteger("20")));
                    usuario.setGenCatdetaEstado(estadodt);
                    genProcesos.edit(usuario);
                     validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Usuario bloqueado");
                } else {
                    int restan = 3 - intentos;
                    validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Quedan :" + restan + " Intentos");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarSession() throws IllegalStateException {
        try {
            // Obtener la sesión actual
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            // Establecer un atributo en la sesión para indicar que el usuario está autenticado
            session.setAttribute("loggedIn", true);
            session.setAttribute("usuario", usuario);
            // Verifica si la sesión aún no ha expirado
            session.getLastAccessedTime();
            // Añadir la sesión al SessionManager, invalidando cualquier sesión anterior del mismo usuario
            sessionManager.addSession(usuario.getCoduser(), session);
        } catch (IllegalStateException e) {
            throw e;
        }

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

    // Método para verificar si la contraseña ingresada coincide con la contraseña almacenada
    public static boolean verifyPassword(String enteredPassword, String storedEncryptedPassword) {
        // Encriptar la contraseña ingresada para compararla con la almacenada
        String enteredEncryptedPassword = encryptPassword(enteredPassword);
        
        // Comparar las versiones encriptadas
        return enteredEncryptedPassword.equals(storedEncryptedPassword);
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
