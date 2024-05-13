/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.seg.menu;

import com.sistema.contable.seguridad.entidades.Segmodulo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import com.sistema.contable.seguridad.busquedas.SegmoduloBusquedaLocal;
import java.math.BigInteger;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttenimientoModulos")
@SessionScoped
public class MttenimientoModulos implements Serializable {

    @EJB
    private SegmoduloBusquedaLocal segmoduloFacade;

    private List<Segmodulo> lstModulos = new ArrayList();
    private Segmodulo selectecModulo = new Segmodulo();
    private List<FacesMessage> messages = new ArrayList<>();
    private int indexTab = 0;

    /**
     * Creates a new instance of MttenimientoModulos
     */
    /*
    Declaracion de variables
     */
    public MttenimientoModulos() {
    }

    /**
     * Metodo que se manda a llamar la primera ves que se carga la pagina
     * propiedad de jsf
     */
    @PostConstruct
    public void init() {
        try {
            lstModulos = segmoduloFacade.buscarModulos();
        } catch (Exception e) {

        }

    }

    public void cargarPantallas() {
        if (this.selectecModulo.getSegpantallasList().isEmpty()) {
            agregarMsj(1, "El modulo no contiene pantallas");
            mostrarMsj();
            return;
        }else{
            this.setIndexTab(1);
             FacesContext context = FacesContext.getCurrentInstance();
        }
    }

    public void mostrarMsj() {
        PrimeFaces.current().executeScript("PF('dlg1').show();");
        FacesContext context = FacesContext.getCurrentInstance();
        for (FacesMessage message : messages) {
            context.addMessage(null, message);
        }
        messages.clear();
    }

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
    public List<FacesMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FacesMessage> messages) {
        this.messages = messages;
    }

    public int getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(int indexTab) {
        this.indexTab = indexTab;
    }

    public List<Segmodulo> getLstModulos() {
        return lstModulos;
    }

    public void setLstModulos(List<Segmodulo> lstModulos) {
        this.lstModulos = lstModulos;
    }

    public Segmodulo getSelectecModulo() {
        return selectecModulo;
    }

    public void setSelectecModulo(Segmodulo selectecModulo) {
        this.selectecModulo = selectecModulo;
    }

//</editor-fold>
}
