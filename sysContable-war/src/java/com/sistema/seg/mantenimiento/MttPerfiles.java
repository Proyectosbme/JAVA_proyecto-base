/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.mantenimiento;

import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.general.busquedas.GenBusquedadLocal;
import com.sistema.general.procesos.GenProcesosLocal;
import com.sistema.seguridad.busquedas.SegmenuBusquedaLocal;
import com.sistema.seguridad.busquedas.SegperfilesBusquedaLocal;
import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segperfiles;
import com.sistema.seguridad.procesos.SegProcesosLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.DualListModel;

/**
 * Clase que se ocupara para el mantenimiento de perfiles, en la cual se
 * agregara, buscara, editara y eliminra
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttPerfiles")
@SessionScoped
public class MttPerfiles implements Serializable {

    @EJB
    private GenProcesosLocal genProcesos;

    @EJB
    private GenBusquedadLocal genbusqueda;

    @EJB
    private SegProcesosLocal segProcesos;

    @EJB
    private SegmenuBusquedaLocal segmenuBusqueda;

    @EJB
    private SegperfilesBusquedaLocal busPerfilLocal;

    //EJB
//<editor-fold defaultstate="collapsed" desc="DECLARACION DE VARIABLES">
//<editor-fold defaultstate="collapsed" desc="Generales">
    /**
     * Variable que contendra si el perfil es nuevo
     */
    private boolean esNuevo = false;
    /**
     * Maneja el valor del tab que se mostrara
     */
    private int indexTab = 0;
    /**
     * Variable para la validacion de mensajes al usuario
     */
    private static final ValidacionMensajes validar = new ValidacionMensajes();
    /**
     * Varianle utilizada para alamcenar los menus que tieney no tiene el perfil
     */
    private DualListModel<Segmenu> menus = new DualListModel<>();

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Busqueda">
    /**
     * Variable que alamacena el nombre del perfil a buscar
     */
    private String nombrePerfilBusq;
    /**
     * Variable que almacena el valor de codigo de perfil a buscar
     */
    private BigInteger codPerfilBusq;
    /**
     * Variables que obtendre la lista de perfiles que existen
     */
    List<Segperfiles> lstPerfilesBus = new ArrayList();
    /**
     * Variable que tendra los menus del perfil
     */
    List<Segmenu> lstMenuPerfil = new ArrayList();
    /**
     * variable que tendra los menus que no pertenecen al perfil
     */
    List<Segmenu> lstMenuNoPerfil = new ArrayList();
    /**
     * Variable que tendra el busPerfil seleccionado
     */
    private Segperfiles perfilSelect = new Segperfiles();
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Editar">
    /**
     * Variable que alamacena el nombre del perfil a edita y alamacenar
     */
    private String nombrePerfilEditGuar;
    /**
     * Variable que almacena el valor de codigo de perfil a ediatr y almacenar
     */
    private BigInteger codPerfilEditGuar;

//</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="METODOS IMPLEMENTADOS">
    /**
     * Creates a new instance of MttPerfiles
     */
    public MttPerfiles() {
    }

    /**
     * Meotodo que se encarga de buscar los perfiles segun los parametros que
     * obtengand e la vista
     */
    public void buscarPerfiles() {
        try {
            Map parametros = new HashMap();
            lstPerfilesBus.clear();
            if (this.nombrePerfilBusq != null
                    && !this.nombrePerfilBusq.trim().isEmpty()) {
                parametros.put("nombreperfil", this.nombrePerfilBusq);
            }
            if (this.codPerfilBusq != null
                    && !this.codPerfilBusq.toString().trim().isEmpty()) {
                parametros.put("codperfil", this.codPerfilBusq);

            }
            if (parametros.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "No se encontraron resultados");
                validar.mostrarMsj();
                return;

            }
            lstPerfilesBus = busPerfilLocal.buscarPerfiles(parametros);
            if (lstPerfilesBus != null && lstPerfilesBus.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "No se encontraron resultados");
                validar.mostrarMsj();
            }

        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error al buscar perfiles");
        }
    }

    /**
     * Meotodo que se encarga de cargar el detalle del perfil en la cual se
     * podra editar eliminar, y agregar
     */
    public void cargarDetaPerfil() {
        try {
            if (this.perfilSelect == null) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un perfil");
                validar.mostrarMsj();
            } else {
                this.setIndexTab(1);
                this.codPerfilEditGuar = perfilSelect.getCodperfil();
                this.nombrePerfilEditGuar = perfilSelect.getNombreperfil();
                this.cargarMenus(perfilSelect);
                this.esNuevo = false;
            }
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error al cargar el pick de los menu");
        }
    }

    /**
     * Crea e inicializa las variables para crear un nuevo perfil
     */
    public void nuevoPerfil() {
        try {
            if (this.getIndexTab() == 0) {
                this.setIndexTab(1);
            }

            perfilSelect = new Segperfiles();
            this.codPerfilEditGuar = null;
            this.nombrePerfilEditGuar = "";
            cargarMenus(perfilSelect);
            esNuevo = true;
            validar.agregarMsj(ValidacionMensajes.Severidad.INFO,
                    "nuevo: Ingreso los parametros ncesarios");
            validar.mostrarMsj();
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error al crear nuevo perfil");
        }

    }

    /**
     * Guarda el perfil creado o una actulizacion
     */
    public void guardarPerfil() {
        try {
            String msj = "Perfil actualizado correctamente";
            if (this.esNuevo) {
                this.perfilSelect.setCodperfil(genbusqueda.obtenerCorrelativo("PERFILES"));
                if (this.nombrePerfilEditGuar != null && !this.nombrePerfilEditGuar.trim().isEmpty()) {
                    this.perfilSelect.setNombreperfil(this.nombrePerfilEditGuar);
                    genProcesos.create(perfilSelect);
                    msj = "Perfil creado correctamente";
                } else {
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Ingrese el nombre del perfil");
                    validar.mostrarMsj();
                    return;
                }

            }
            if (perfilSelect == null || perfilSelect.getCodperfil() == null) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un perfil,"
                        + " o cree un perfil nuevo");
                validar.mostrarMsj();
            } else {
                this.perfilSelect.setNombreperfil(this.nombrePerfilEditGuar);
                if (perfilSelect.getNombreperfil() != null && perfilSelect.getNombreperfil().trim().isEmpty()) {
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Ingrese el nombre del perfil2");
                    validar.mostrarMsj();
                    return;
                }

                List<Segmenu> lstMenuTodos = genbusqueda.buscarTodos(Segmenu.class);
                Map parametros = new HashMap();
                parametros.put("perfil", perfilSelect);
                parametros.put("menusAsignados", menus.getTarget());
                parametros.put("todos", lstMenuTodos);
                segProcesos.guardarMenuSeleccionado(parametros);

                //buscar perfil con las modificaciones
                Map param = new HashMap();
                param.put("codperfil", perfilSelect.getCodperfil());
                List<Segperfiles> lstPerfilBuscaG = busPerfilLocal.buscarPerfiles(param);
                lstPerfilBuscaG.forEach((perf) -> {
                    perfilSelect = perf;
                });

                this.cargarMenus(perfilSelect);

                this.esNuevo = false;
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, msj);
                validar.mostrarMsj();
            }
        } catch (NullPointerException ex) {
            validar.manejarExcepcion(ex, "Error por datos nulos al guardar perfil");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error inesperado al guardar perfil");
        }
    }

    /**
     * Carga el menu segun el perfil que se le envia
     *
     * @param perfilSelect
     * @throws NullPointerException
     * @throws Exception
     */
    public void cargarMenus(Segperfiles perfilSelect) throws NullPointerException, Exception {
        try {
            //menus que pertenecen al perfil
            if (perfilSelect != null && perfilSelect.getCodperfil() != null) {
                lstMenuPerfil = segmenuBusqueda.findMenusByPerfil(perfilSelect.getCodperfil());
                //menus que no pertenecen al perfil
                lstMenuNoPerfil = segmenuBusqueda.findMenusNotByPerfil(perfilSelect.getCodperfil());
                //cargar a la seleccion
            } else {
                lstMenuPerfil = new ArrayList<>();
                lstMenuNoPerfil = genbusqueda.buscarTodos(Segmenu.class);
            }
            menus = new DualListModel<>(lstMenuNoPerfil, lstMenuPerfil);
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarPerfil() {
        try {
            List<String> lstMsj = new ArrayList<>();

            if (esNuevo) {
                lstMsj.add("El perfil no a sido guardado, No es necesario eliminarlos");
            } else {
                if (perfilSelect != null) {
                    if (!perfilSelect.getSegmenuList().isEmpty()) {
                        lstMsj.add("El perfil tiene menus asignados");
                    }
                    if (!perfilSelect.getSegusuariosList().isEmpty()) {
                        lstMsj.add("Usuarios tienen el perfil asignado");
                    }
                } else {
                    lstMsj.add("Seleccione un perfil a eliminar");
                }
            }

            if (!lstMsj.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Error el perfil no se puede eliminar por:");
                lstMsj.forEach((msj) -> {
                    validar.agregarMsj(ValidacionMensajes.Severidad.WARN, msj);
                });
                validar.mostrarMsj();
                return;
            }
            genProcesos.remove(perfilSelect);
            lstPerfilesBus = busPerfilLocal.buscarPerfiles(new HashMap());
            this.setIndexTab(0);
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "El perfil a sido eliminado con excito");
            validar.mostrarMsj();
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error al eliminar el perfil");
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="GET AND SET">
//<editor-fold defaultstate="collapsed" desc="Generales">

    public int getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(int indexTab) {
        this.indexTab = indexTab;
    }

    /**
     * Obtiene el valor de la instancia de la clase
     *
     * @return
     */
    public static ValidacionMensajes getValidar() {
        return validar;
    }

    public String getNombrePerfilBusq() {
        return nombrePerfilBusq;
    }

    public void setNombrePerfilBusq(String nombrePerfilBusq) {
        this.nombrePerfilBusq = nombrePerfilBusq;
    }

    public BigInteger getCodPerfilBusq() {
        return codPerfilBusq;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Busqueda">
    public void setCodPerfilBusq(BigInteger codPerfilBusq) {
        this.codPerfilBusq = codPerfilBusq;
    }

    public List<Segperfiles> getLstPerfilesBus() {
        return lstPerfilesBus;
    }

    public void setLstPerfilesBus(List<Segperfiles> lstPerfilesBus) {
        this.lstPerfilesBus = lstPerfilesBus;
    }

    public Segperfiles getPerfilSelect() {
        return perfilSelect;
    }

    public void setPerfilSelect(Segperfiles perfilSelect) {
        this.perfilSelect = perfilSelect;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Editar">

    public String getNombrePerfilEditGuar() {
        return nombrePerfilEditGuar;
    }

    public void setNombrePerfilEditGuar(String nombrePerfilEditGuar) {
        this.nombrePerfilEditGuar = nombrePerfilEditGuar;
    }

    public BigInteger getCodPerfilEditGuar() {
        return codPerfilEditGuar;
    }

    public void setCodPerfilEditGuar(BigInteger codPerfilEditGuar) {
        this.codPerfilEditGuar = codPerfilEditGuar;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Menus para perfil">

    public List<Segmenu> getLstMenuPerfil() {
        return lstMenuPerfil;
    }

    public void setLstMenuPerfil(List<Segmenu> lstMenuPerfil) {
        this.lstMenuPerfil = lstMenuPerfil;
    }

    public List<Segmenu> getLstMenuNoPerfil() {
        return lstMenuNoPerfil;
    }

    public void setLstMenuNoPerfil(List<Segmenu> lstMenuNoPerfil) {
        this.lstMenuNoPerfil = lstMenuNoPerfil;
    }

    public DualListModel<Segmenu> getMenus() {
        return menus;
    }

    public void setMenus(DualListModel<Segmenu> menus) {
        this.menus = menus;
    }
//</editor-fold>

//</editor-fold>
}
