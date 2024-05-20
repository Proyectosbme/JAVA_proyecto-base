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
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sistema.contable.general.busquedas.GencorrelativosBusquedaLocal;
import com.sistema.contable.general.procesos.GenProcesosLocal;
import com.sistema.contable.general.validaciones.ValidacionesException;
import com.sistema.contable.seguridad.entidades.Segpantallas;
import java.math.BigInteger;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttenimientoModulos")
@SessionScoped
public class MttenimientoModulos implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaración de variables">
    @EJB
    private GenProcesosLocal genProcesos;
    @EJB
    private GencorrelativosBusquedaLocal busGenCors;
    @EJB
    private SegmoduloBusquedaLocal busquedaModulo;

//<editor-fold defaultstate="collapsed" desc="Variables de modulo">
    /**
     * Guarda la lista de modulos que se encuentran en la base de datos
     */
    private List<Segmodulo> lstModulos = new ArrayList();
    /**
     * Variable que se usa para almacenar el modulo seleccionado
     */
    private Segmodulo selectecModulo = new Segmodulo();
    /**
     * Variable que se ocupara para alamacenar el nuevo modulo
     */
    private Segmodulo moduloAgregar;
    /**
     * Almacenara el nombre del modulo que se desea guardar
     */
    private String nomModulo = "";
    /**
     * Mensjae que se mostraran al usuario
     */
    private List<FacesMessage> messages = new ArrayList<>();
    /**
     * Maneja el valor del tab que se mostrara
     */
    private int indexTab = 0;
    /**
     * Variable que mostrara los mensjaes a mayor detalle en el log
     */
    private static final Logger LOGGER = Logger.getLogger(MttenimientoModulos.class.getName());
    /**
     * Variables que contiene el modulo a eliminar
     */
    private Segmodulo eliminarModulo = new Segmodulo();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Variables para pantallas">
    /**
     * Almacenara el valor a eliminar
     */
    private Segpantallas eliminarPantalla = new Segpantallas();
    /**
     * Almacenara el valor de la pantalla a editart
     */
    private Segpantallas editPantalla = new Segpantallas();

//</editor-fold>
//</editor-fold>
    public MttenimientoModulos() {
    }

    /**
     * Metodo que se cargara al inicio, cuando se manda a llamar la pantalla
     */
    @PostConstruct
    public void init() {
        try {
            lstModulos = busquedaModulo.buscarModulos();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", e);
        }

    }

    /**
     * Metodo que carga las pantallas que contiene el modulo y envia un msj en
     * dado caso el modulo no tenga pantallas
     */
    public void cargarPantallas() {
        if (this.selectecModulo.getSegpantallasList().isEmpty()) {
            agregarMsj(1, "El modulo no contiene pantallas");
            mostrarMsj();
        } else {
            this.setIndexTab(1);
        }
    }

    /**
     * Metodo que se utiliza para agregar modulo nuevo y persistirlos
     */
    public void agregarModulo() {
        try {

            /**
             * VALIDACION DE VARIABLES
             */
            if (nomModulo == null || nomModulo.isEmpty()) {
                agregarMsj(1, "Ingrese el nombre del modulo");
                mostrarMsj();
                return;
            }
            //PROCESOS
            BigInteger correlati;
            correlati = busGenCors.obtenerCorrelativo("GENCORSMODULO2");
            moduloAgregar = new Segmodulo();
            moduloAgregar.setCodmod(correlati);
            moduloAgregar.setNommodulo(nomModulo.toUpperCase());
            moduloAgregar.setUrldirecc("LOCAL");
            genProcesos.create(moduloAgregar);
            lstModulos.add(moduloAgregar);
            agregarMsj(1, "Modulo agregado correctamente");
            mostrarMsj();
            this.setIndexTab(0);

            //ERRORES
        } catch (ValidacionesException ve) {
            agregarMsj(1, ve.getMessage());
            agregarMsj(1, ve.getMensaje());
            mostrarMsj();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", ex);
            agregarMsj(4, "Error inesperado");
        }
    }

    public void asigModEliminar(Segmodulo modulo) {
        if (modulo != null) {
            eliminarModulo = modulo;
            PrimeFaces.current().executeScript("PF('deleteMod').show();");
        } else {
            agregarMsj(4, "Seleccione un modulo");
            mostrarMsj();
        }

    }

    public void eliminarModulo() {
        if (eliminarModulo != null) {
            genProcesos.remove(eliminarModulo);
            lstModulos.remove(eliminarModulo);
            agregarMsj(1, "Modulo eliminado con exito");
        } else {
            agregarMsj(4, "Seleccione un modulo");
        }
        mostrarMsj();

    }

    public void asigEditarModulo(Segmodulo modulo) {
        if (modulo != null) {
            selectecModulo = modulo;
            PrimeFaces.current().executeScript("PF('editModulo').show();");
        } else {
            agregarMsj(4, "Seleccione un modulo");
            mostrarMsj();
        }

    }

    public void editarModulo() {
        if (selectecModulo != null) {
            genProcesos.edit(selectecModulo);
            lstModulos.set(lstModulos.indexOf(selectecModulo), selectecModulo);
            PrimeFaces.current().executeScript("PF('editModulo').hide();");
            agregarMsj(1, "Modulo editado con exito");
        } else {
            agregarMsj(4, "Seleccione u nmodulo");
        }
        mostrarMsj();
    }

    public void asigPanEliminar(Segpantallas pantalla) {
        if (pantalla != null) {
            eliminarPantalla = pantalla;
            PrimeFaces.current().executeScript("PF('deletePantaid').show();");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
            mostrarMsj();
        }

    }

    public void eliminarPantalla() {
        if (eliminarPantalla != null) {
            genProcesos.remove(eliminarPantalla);
            selectecModulo.getSegpantallasList().remove(eliminarPantalla);
            agregarMsj(1, "Pantalla eliminada con exito");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
        }
        mostrarMsj();

    }

    public void asigEditarPantalla(Segpantallas pantalla) {
        if (pantalla != null) {
            editPantalla = pantalla;
            PrimeFaces.current().executeScript("PF('editPantalla').show();");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
            mostrarMsj();
        }

    }

    public void editarPantalla() {
        if (editPantalla != null) {
            genProcesos.edit(editPantalla);
            selectecModulo.getSegpantallasList()
                    .set(selectecModulo.getSegpantallasList().indexOf(editPantalla),
                            editPantalla);
            PrimeFaces.current().executeScript("PF('editPantalla').hide();");
            agregarMsj(1, "Pantalla editado con exito");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
        }
        mostrarMsj();
    }

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

//<editor-fold defaultstate="collapsed" desc="AGREGAR MODULO">
    public Segmodulo getModuloAgregar() {
        return moduloAgregar;
    }

    public void setModuloAgregar(Segmodulo moduloAgregar) {
        this.moduloAgregar = moduloAgregar;
    }

    public String getNomModulo() {
        return nomModulo;
    }

    public void setNomModulo(String nomModulo) {
        this.nomModulo = nomModulo;
    }

//</editor-fold>
//</editor-fold>

    public Segpantallas getEditPantalla() {
        return editPantalla;
    }

    public void setEditPantalla(Segpantallas editPantalla) {
        this.editPantalla = editPantalla;
    }
    
    
}
