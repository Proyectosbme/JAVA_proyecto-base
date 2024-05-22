/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.seg.menu;

import com.sistema.contable.general.procesos.GenProcesosLocal;
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
public class RefresEntity implements Serializable{

    @EJB
    private GenProcesosLocal genProcesos;
      /**
     * Mensjae que se mostraran al usuario
     */
    private List<FacesMessage> messages = new ArrayList<>();
    

    /**
     * Creates a new instance of RefresEntity
     */
    public RefresEntity() {
    }
    
    public void refresEntity(){
        try{
            genProcesos.refreshAllEntities();
            agregarMsj(1, "Se refrecaron las entidades");
        }catch(Exception ex ){
            agregarMsj(1, "Ocurrio un error" + ex.toString());
        }finally{
            mostrarMsj();
        }
        
    }
    
     //<editor-fold defaultstate="collapsed" desc="METODOS PARA MENSAJES">
    /**
     * Metodo que muestra el mensaje al usuario y abre un popup
     */
    public void mostrarMsj() {
        PrimeFaces.current().executeScript("PF('dlg1').show();");
        FacesContext context = FacesContext.getCurrentInstance();
        for (FacesMessage message : messages) {
            context.addMessage(null, message);
        }
        messages.clear();
    }

    /**
     * Metodo que agrega el mensaje a una lista
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
//</editor-fold>
}
