/*
 // Este archivo es la definición de la clase MttenimientoModulos,
que se encarga de manejar el mantenimiento de módulos Y SUS PANTALLAS
// y está ubicado en el paquete com.contabilidad.seg.menu
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
import com.sistema.contable.seguridad.busquedas.SegpantallasBusquedaLocal;
import com.sistema.contable.seguridad.entidades.Segpantallas;
import com.sistema.contable.seguridad.entidades.SegpantallasPK;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BME_PERSONAL Clase que se encargara del mantenimiento del modulo y
 * sus pantallas en la cual sera un crud para cada uno , con sus respectivas
 * validaciones
 */
@Named(value = "mttenimientoModulos")
@SessionScoped
public class MttenimientoModulos implements Serializable {

    @EJB
    private SegpantallasBusquedaLocal segpantallasBusqueda;

//<editor-fold defaultstate="collapsed" desc="DECLARACIÓN DE VARIABLES">
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
     * Variable que alamacenara el codigo del modulo
     */
    private BigInteger codModulo;
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
    /**
     * Guardara la nueva pantalla que se almacenara
     */
    private Segpantallas addPantalla = new Segpantallas();
//</editor-fold>
//</editor-fold>

    public MttenimientoModulos() {
    }
//<editor-fold defaultstate="collapsed" desc="DECLARACIÓN DE METODOS">

//<editor-fold defaultstate="collapsed" desc="METODO DE INICIO">
    /**
     * Metodo que se cargara al inicio, cuando se manda a llamar la pantalla
     */
    @PostConstruct
    public void init() {
        try {
            lstModulos = busquedaModulo.buscarModulo(new HashMap());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", e);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="METODO PARA EL MODULO">
    public void buscarModulo() {
        try {
            Map parametros = new HashMap();
            if (this.nomModulo != null && !this.nomModulo.isEmpty()) {
                parametros.put("nonmodulo", this.nomModulo);
            }
            if (this.codModulo != null) {
                parametros.put("codmod", this.codModulo);
            }
            lstModulos = busquedaModulo.buscarModulo(parametros);
            if (lstModulos.isEmpty()) {
                agregarMsj(2, "No se encontraron resultados");
                mostrarMsj();
            }
        } catch (Exception ex) {
            agregarMsj(4, "Ocurrio un error");
            mostrarMsj();
        }
    }

    public void limpiarBusqueda() {
        this.nomModulo = "";
        this.codModulo = null;
        this.lstModulos.clear();
    }

    /**
     * Metodo que se utiliza para agregar modulo nuevo y persistirlos
     */
    public void iniciarModulo() {

        moduloAgregar = new Segmodulo();
        PrimeFaces.current().executeScript("PF('addModulo').show();");
    }

    public void agregarModulo() {
        try {

            /**
             * VALIDACION DE VARIABLES
             */
            if (moduloAgregar.getNommodulo() == null || moduloAgregar.getNommodulo().isEmpty()) {
                agregarMsj(1, "Ingrese el nombre del modulo");
                mostrarMsj();
                return;
            }
            //PROCESOS
            BigInteger correlati;
            correlati = busGenCors.obtenerCorrelativo("GENCORSMODULO");

            moduloAgregar.setCodmod(correlati);
            moduloAgregar.setNommodulo(moduloAgregar.getNommodulo().toUpperCase());
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

    /**
     * Metodo que asigna el valor del modulo a eliminar
     *
     * @param modulo modulo que se envia para eliminar
     */
    public void asigModEliminar(Segmodulo modulo) {
        if (modulo != null) {
            eliminarModulo = modulo;
            PrimeFaces.current().executeScript("PF('deleteMod').show();");
        } else {
            agregarMsj(4, "Seleccione un modulo");
            mostrarMsj();
        }

    }

    /**
     * Metodo que eliminar el modulo asignado
     */
    public void eliminarModulo() {
        try {

            if (eliminarModulo != null) {
                genProcesos.remove(eliminarModulo);
                lstModulos.remove(eliminarModulo);
                agregarMsj(1, "Modulo eliminado con exito");
            } else {
                agregarMsj(4, "Seleccione un modulo");
            }
            mostrarMsj();
        } catch (Exception e) {
        }
    }

    /**
     * Metod que asigna el modulo que se va editar
     *
     * @param modulo modulo que se envia para editar posteriormente
     */
    public void asigEditarModulo(Segmodulo modulo) {
        if (modulo != null) {
            selectecModulo = modulo;
            PrimeFaces.current().executeScript("PF('editModulo').show();");
        } else {
            agregarMsj(4, "Seleccione un modulo");
            mostrarMsj();
        }

    }

    /**
     * Metodo que guarda la edicion del modulo asignado
     */
    public void editarModulo() {
        try {
            if (selectecModulo != null) {

                genProcesos.edit(selectecModulo);
                lstModulos.set(lstModulos.indexOf(selectecModulo), selectecModulo);
                PrimeFaces.current().executeScript("PF('editModulo').hide();");
                agregarMsj(1, "Modulo editado con exito");
            } else {
                agregarMsj(4, "Seleccione u nmodulo");
            }
            mostrarMsj();
        } catch (Exception e) {
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="METODOS PARA PANTALLAS">
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
     * Metodo que asigna la pantalla a eliminar
     *
     * @param pantalla pantalla que se envia para asignarla y luego eliminarla
     */
    public void asigPanEliminar(Segpantallas pantalla) {
        if (pantalla != null) {
            eliminarPantalla = pantalla;
            PrimeFaces.current().executeScript("PF('deletePantaid').show();");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
            mostrarMsj();
        }

    }

    /**
     * Metodo que elimina la pantalla asiganada
     */
    public void eliminarPantalla() {
        try {

            if (eliminarPantalla != null) {
                genProcesos.remove(eliminarPantalla);
                selectecModulo.getSegpantallasList().remove(eliminarPantalla);
                agregarMsj(1, "Pantalla eliminada con exito");
            } else {
                agregarMsj(4, "Seleccione una pantalla");
            }
            mostrarMsj();
        } catch (Exception e) {
        }

    }

    /**
     * Metodo que asigna la pantalla que se va editar posteriormente
     *
     * @param pantalla pantalal que se envia para asignarla para su edicion
     */
    public void asigEditarPantalla(Segpantallas pantalla) {
        if (pantalla != null) {
            editPantalla = pantalla;
            PrimeFaces.current().executeScript("PF('editPantalla').show();");
        } else {
            agregarMsj(4, "Seleccione una pantalla");
            mostrarMsj();
        }

    }

    /**
     * Metodo que guarda la edición de la pantalla
     */
    public void editarPantalla() {
        try {
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
        } catch (Exception e) {
        }
    }

    public void guardarPantalla() {
        try {

            List<String> lstMsj = new ArrayList<>();
            if (addPantalla.getNompantalla() == null || addPantalla.getNompantalla().isEmpty()) {
                lstMsj.add("Ingrese el nombre de la pantalla");
            }
            if (addPantalla.getUrlpantalla() == null || addPantalla.getUrlpantalla().isEmpty()) {
                lstMsj.add("Ingrese la url de la pantalla ");
            }
            if (lstMsj.isEmpty()) {
                addPantalla.setSegmodulo(selectecModulo);
                BigInteger corPantalla
                        = segpantallasBusqueda.maxCodPantalla(selectecModulo.getCodmod());
                SegpantallasPK pkPantalla = new SegpantallasPK(selectecModulo.getCodmod(),
                        corPantalla.add(new BigInteger("5")));
                addPantalla.setPantallasPK(pkPantalla);
                selectecModulo.getSegpantallasList().add(addPantalla);
                lstModulos.set(lstModulos.indexOf(selectecModulo), selectecModulo);
                genProcesos.create(addPantalla);
                agregarMsj(1, "Pantalla agregada correctamente");
                mostrarMsj();
            } else {
                lstMsj.forEach((msj) -> {
                    agregarMsj(4, msj);
                });
                mostrarMsj();
            }
        } catch (ValidacionesException ex) {
            agregarMsj(4, ex.getMessage());
            agregarMsj(4, ex.getMensaje());
            mostrarMsj();
        } catch (Exception ex) {
            agregarMsj(4, "Error inesperado" + ex.toString());
            mostrarMsj();
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="METODOS PARA MENSAJES">
    /**
     * Metodo que muestra el mensaje al usuario y abre un popup
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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GET AND SET">
//<editor-fold defaultstate="collapsed" desc="GENERALES">
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
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="MODULOS">

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
//<editor-fold defaultstate="collapsed" desc="AGREGAR PANTALLA">

    public Segpantallas getEditPantalla() {
        return editPantalla;
    }

    public void setEditPantalla(Segpantallas editPantalla) {
        this.editPantalla = editPantalla;
    }

    public Segpantallas getAddPantalla() {
        return addPantalla;
    }

    public void setAddPantalla(Segpantallas addPantalla) {
        this.addPantalla = addPantalla;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="AGREGAR MODULO">
    public BigInteger getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(BigInteger codModulo) {
        this.codModulo = codModulo;
    }

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
}
