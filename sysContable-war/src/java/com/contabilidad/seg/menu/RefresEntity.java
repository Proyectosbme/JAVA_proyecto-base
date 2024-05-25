/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.seg.menu;

import com.sistema.contable.general.procesos.GenProcesosLocal;
import com.sistema.contable.general.validaciones.ValidacionMensajes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "refresEntity")
@SessionScoped
public class RefresEntity implements Serializable {

    @EJB
    private GenProcesosLocal genProcesos;
    /**
     * Mensjae que se mostraran al usuario
     */
    private List<FacesMessage> messages = new ArrayList<>();
    /**
     *
     */
    ValidacionMensajes validar = new ValidacionMensajes();

    /**
     * Creates a new instance of RefresEntity
     */
    public RefresEntity() {
    }

    public void refresEntity() {
        try {
            genProcesos.refreshAllEntities();
            validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Se refrecaron las entidades");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error inesperado , comuniquese con informatica");
        }

    }

//</editor-fold>
}
