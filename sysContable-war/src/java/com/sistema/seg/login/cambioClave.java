/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.login;

import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.general.entidades.Gencatdeta;
import com.sistema.general.entidades.GencatdetaPK;
import com.sistema.general.negocio.GenProcesosLocal;
import com.sistema.seguridad.entidades.Segusuarios;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "cambioClave")
@SessionScoped
public class cambioClave implements Serializable {

    @EJB
    private GenProcesosLocal genProcesos;
    private String confirnewPassword = "";
    private String newPassword = "";
    private String password = "";
    private final ValidacionMensajes validarMsj = new ValidacionMensajes();
    private boolean render = false;

    /**
     * Creates a new instance of cambioClave
     */
    public cambioClave() {
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        if (session != null) {
            Segusuarios usuario = (Segusuarios) session.getAttribute("usuario");
            if (usuario != null && usuario.getSegPerfiles() != null && usuario.getSegPerfiles().getCodperfil() != null) {
                if (usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor().compareTo(new BigInteger("40")) != 0) {
                    render = true;
                }
                // Aquí puedes realizar alguna acción adicional si es necesario
            } else {
                // Redirigir a la página de login
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
                } catch (IOException e) {
                    // Manejar la excepción si ocurre algún error al redirigir
                    e.printStackTrace();
                }
            }
        } else {
            // Redirigir a la página de login si no hay sesión
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al redirigir
                e.printStackTrace();
            }
        }
    }

    public String cambiarCable() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Segusuarios usuario = new Segusuarios();

            if (session != null) {
                usuario = (Segusuarios) session.getAttribute("usuario");
            }

            if (usuario != null && usuario.getSegPerfiles().getCodperfil() != null) {
                if (!LoginBean.verifyPassword(password, usuario.getClave())
                        && usuario.getGenCatdetaEstado().getGencatdetaPK().getCodcor().compareTo(new BigInteger("40")) != 0) {
                    validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Contraseña incorrecta");
                } else if (!newPassword.equals(confirnewPassword)) {
                    validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Las contraseñas nuevas no coinciden");
                } else if (newPassword.trim().isEmpty() || confirnewPassword.trim().isEmpty()) {
                    validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Las contraseñas no pueden ser nulas");
                } else if (!validatePassword(newPassword)) {
                    validarMsj.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                            "La contraseña debe de terner minimo 6 caracteres, "
                            + "Mayusculas, Minusculas y un caracter especial");
                } else {
                    Gencatdeta estadodt = new Gencatdeta(new GencatdetaPK(new BigInteger("10"),
                            new BigInteger("10"), new BigInteger("10")));
                    usuario.setClave(encryptPassword(newPassword));
                    usuario.setGenCatdetaEstado(estadodt);
                    genProcesos.edit(usuario);
                }

            }

        } catch (Exception ex) {
            return null;
        }

        if (validarMsj.getMessages()
                .isEmpty()) {
            return "index";
        } else {
            validarMsj.mostrarMsj();
            return null;
        }

    }

    public static boolean validatePassword(String contra) {
        // Longitud mínima de 6 caracteres
        if (contra.length() < 6) {
            return false;
        }

        // Al menos una mayúscula y una minúscula
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Matcher upperCaseMatcher = upperCasePattern.matcher(contra);
        Matcher lowerCaseMatcher = lowerCasePattern.matcher(contra);
        if (!upperCaseMatcher.find() || !lowerCaseMatcher.find()) {
            return false;
        }

        // Al menos un carácter especial (dólar, asterisco, etc.)
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
        Matcher specialCharMatcher = specialCharPattern.matcher(contra);
        if (!specialCharMatcher.find()) {
            return false;
        }

        // Cumple todos los criterios
        return true;
    }

    // Método para encriptar la contraseña
    public static String encryptPassword(String password) {
        try {
            // Crear instancia de MessageDigest para SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Convertir la contraseña a bytes y aplicar hash
            byte[] hashedPassword = md.digest(password.getBytes());

            // Convertir bytes a formato Base64 para almacenamiento seguro
            String encodedPassword = Base64.getEncoder().encodeToString(hashedPassword);

            return encodedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirnewPassword() {
        return confirnewPassword;
    }

    public void setConfirnewPassword(String confirnewPassword) {
        this.confirnewPassword = confirnewPassword;
    }

    public boolean isRender() {
        return render;
    }

}
