package com.contabilidad.seg.login;

import com.sistema.contable.seguridad.busquedas.SegusuariosBusquedaLocal;
import com.sistema.contable.seguridad.entidades.Segusuarios;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private SegusuariosBusquedaLocal segusuariosBusqueda;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    //declaracion de variables
    private String username = "";
    private String password = "";
    private Date fecha = new Date();
    private List<FacesMessage> messages;
    private Segusuarios usuario ;
//</editor-fold>

    /*
    Metodo que se carga al inicio con la pagina que se selecciona y se llama al beans
     */
    @PostConstruct
    public void init() {
        password = "";
        username = "";
        this.logout();
    }

    public String login() {
        try {
            messages = new ArrayList<>();
            usuario = new Segusuarios();
            this.validarDatos();
            if (messages != null && messages.isEmpty()) {
                Map elementos = new HashMap();
                elementos.put("usuario", this.username);
                elementos.put("password2", this.password);

                // Lógica para verificar las credenciales del usuario
                this.validaDatosUsuario(elementos);

                if (messages != null && messages.isEmpty()) {
                    this.guardarSession();
                    return "index"; // Redirigir al usuario a la página de inicio
                } else {
                    this.mostrarMsj();
                }

            } else {
                mostrarMsj();
            }

        } catch (Exception e) {
            agregarMsj(1, "Ocurrio un error " + e.toString());
            mostrarMsj();
            return "login";
        }
        return "login";
    }

    /*
    Metodo que vaida los datos de inicio de session
     */
    private void validarDatos() {
        if (username.trim().equals("") || password.trim().equals("")) {
            agregarMsj(1, "Ingrese usuario y contraseña");

        }
    }

    private void validaDatosUsuario(Map elementos) {
        try {
            usuario = this.segusuariosBusqueda.buscarSubMenu(elementos);

            if (usuario == null) {
                agregarMsj(1, "El usuario no existe");
            } else if (usuario.getEstado().compareTo(BigInteger.ONE) != 0) {
                agregarMsj(1, "Usuario inactivo");

            } else if (!usuario.getClave().equals(password)) {
                agregarMsj(1, "La contraseña es incorrecta");
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

                }

    /**
     * Metodo que invalida la session del usuario
     *
     * @return
     */
    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        // Invalidar la sesión actual
        externalContext.invalidateSession();
        // Redirigir al usuario a la página de inicio de sesión
        return "login";
    }

    /**
     * Metodo que Muestra los msj al usuario
     */
    public void mostrarMsj() {
        PrimeFaces.current().executeScript("PF('dlgMensajes').show();");
        FacesContext context = FacesContext.getCurrentInstance();
        for (FacesMessage message : messages) {
            context.addMessage(null, message);
        }
        messages.clear();
    }

    /**
     * Metodo que recibe y agrega los mensajes para porsterior mostrar al
     * usuario
     *
     * @param numero
     * @param msj
     */
    public void agregarMsj(int numero, String msj) {
        switch (numero) {
            case 1:
                messages.add(new FacesMessage(FacesMessage.SEVERITY_INFO, null, msj));
                break;
            case 2:
                messages.add(new FacesMessage(FacesMessage.SEVERITY_WARN, null, msj));
                break;
            case 3:
                messages.add(new FacesMessage(FacesMessage.SEVERITY_FATAL, null, msj));
                break;
            case 4:
                messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msj));
                break;

        }
    }

//<editor-fold defaultstate="collapsed" desc="GET AND SET">
    // Métodos para manejar los mensajes y la lógica de negocio
    // Getters y setters
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

    public List<FacesMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FacesMessage> messages) {
        this.messages = messages;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
//</editor-fold>
}
