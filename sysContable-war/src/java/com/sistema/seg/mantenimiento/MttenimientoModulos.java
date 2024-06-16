/*
 // Este archivo es la definición de la clase MttenimientoModulos,
que se encarga de manejar el mantenimiento de módulos Y SUS PANTALLAS
// y está ubicado en el paquete com.contabilidad.seg.menu
 */
package com.sistema.seg.mantenimiento;

import com.sistema.general.negocio.GenBusquedadLocal;
import com.sistema.seguridad.entidades.Segmodulo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import com.sistema.general.negocio.GenProcesosLocal;
import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.general.validaciones.ValidacionesException;
import com.sistema.seguridad.entidades.Segpantallas;
import com.sistema.seguridad.entidades.SegpantallasPK;
import com.sistema.seguridad.negocio.SegBusquedaLocal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
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

  

//<editor-fold defaultstate="collapsed" desc="DECLARACIÓN DE VARIABLES">
    @EJB
    private GenProcesosLocal genProcesos;
   @EJB
    private SegBusquedaLocal segBusqueda;
    @EJB
    private GenBusquedadLocal genbusqueda;

//<editor-fold defaultstate="collapsed" desc="Variables de modulo">
    /**
     * Variables para los mensajes al usuario
     */
    private final ValidacionMensajes validar = new ValidacionMensajes();

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
     * Maneja el valor del tab que se mostrara
     */
    private int indexTab = 0;
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
            // Obtén la lista de Segmodulo
            lstModulos = genbusqueda.buscarTodos(Segmodulo.class);

            // Define un comparador para comparar los Segmodulo por el atributo codmod
            Comparator<Segmodulo> comparator = Comparator.comparing(Segmodulo::getCodmod);

            // Ordena la lista utilizando el comparador
            Collections.sort(lstModulos, comparator);
            // lstModulos = genbusqueda.buscarTodos(Segmodulo.class);
        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error al bsucar modulo");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="METODO PARA EL MODULO">
    public void buscarModulo() {
        try {
            Map parametros = new HashMap();
            if (this.nomModulo != null && !this.nomModulo.trim().isEmpty()) {
                parametros.put("nonmodulo", this.nomModulo);
            }
            if (this.codModulo != null) {
                parametros.put("codmod", this.codModulo);
            }
             lstModulos.clear();
            if(parametros.isEmpty()){
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "Ingrese parametros de busqueda");
                validar.mostrarMsj();
                return;
            }
            lstModulos = segBusqueda.buscarModulo(parametros);
            if (lstModulos.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "No se encontraron resultados");
                validar.mostrarMsj();
            }
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Comuniquese con el equipo de informatica");
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

    /**
     * Metodo que agrega el nuevo modulo
     */
    public void agregarModulo() {
        try {

            /**
             * VALIDACION DE VARIABLES
             */
            if (moduloAgregar.getNommodulo() == null || moduloAgregar.getNommodulo().isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "Ingrese el nombre del modulo");
                validar.mostrarMsj();
                return;
            }
            //PROCESOS
            BigInteger correlati;
            correlati = genbusqueda.obtenerCorrelativo("GENCORSMODULO");

            moduloAgregar.setCodmod(correlati);
            moduloAgregar.setNommodulo(moduloAgregar.getNommodulo().toUpperCase());
            moduloAgregar.setUrldirecc("LOCAL");
            genProcesos.create(moduloAgregar);
            lstModulos.add(moduloAgregar);
            validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Modulo agregado correctamente");
            validar.mostrarMsj();
            this.setIndexTab(0);
            PrimeFaces.current().executeScript("PF('addModulo').hide();");
            //ERRORES
        } catch (ValidacionesException ve) {
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, ve.getMensaje());
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, ve.getMessage());
            for (String msj : ve.getMensajes()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, msj);
            }
            validar.mostrarMsj();
            validar.mostrarMsj();

        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error al buscar módulos comuniquise con el equipo de"
                    + "informatica");
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
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un modulo");
            validar.mostrarMsj();
        }

    }

    /**
     * Metodo que eliminar el modulo asignado
     */
    public void eliminarModulo() {
        try {
// Verificar si se ha seleccionado un módulo para eliminar
            if (eliminarModulo != null) {
                // Verificar si el módulo contiene pantallas
                if (eliminarModulo.getSegpantallasList() != null
                        && !eliminarModulo.getSegpantallasList().isEmpty()) {
                    // Mostrar un mensaje de error si el módulo contiene pantallas y salir del método
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "No se puede eliminar el módulo porque contiene pantallas");
                    validar.mostrarMsj();
                    return;
                }

                // Eliminar el módulo de la base de datos
                genProcesos.remove(eliminarModulo);

                // Eliminar el módulo de la lista de módulos
                lstModulos.remove(eliminarModulo);

                // Mostrar un mensaje de éxito
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Módulo eliminado con éxito");
            } else {
                // Mostrar un mensaje de error si no se ha seleccionado un módulo para eliminar
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un módulo");
            }

            // Mostrar los mensajes al usuario
            validar.mostrarMsj();
        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error en eliminacion del modulo");
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
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un modulo");
            validar.mostrarMsj();
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
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Modulo editado con exito");
            } else {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione u nmodulo");
            }
            validar.mostrarMsj();
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
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "El modulo no contiene pantallas");
            validar.mostrarMsj();
        } else {
// Define un comparador para comparar los Segpantallas por el atributo codpantalla
            Comparator<Segpantallas> comparator
                    = Comparator.comparing(segpantallas -> segpantallas.getPantallasPK().getCodpantalla());

// Ordena la lista utilizando el comparador
            Collections.sort(this.selectecModulo.getSegpantallasList(), comparator);
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
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione una pantalla");
            validar.mostrarMsj();
        }

    }

    /**
     * Metodo que elimina la pantalla asiganada
     */
    public void eliminarPantalla() {
        try {
// Verificar si se ha seleccionado una pantalla para eliminar
            if (eliminarPantalla != null) {
                // Verificar si la pantalla está asignada a algún menú
                if (eliminarPantalla.getSegmenuList() != null && !eliminarPantalla.getSegmenuList().isEmpty()) {
                    // Mostrar un mensaje de error si la pantalla está asignada a algún menú y salir del método
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                            "No se puede eliminar la pantalla porque está asignada a un menú");
                    validar.mostrarMsj();
                    return;
                }

                // Eliminar la pantalla de la base de datos
                genProcesos.remove(eliminarPantalla);

                // Eliminar la pantalla de la lista de pantallas del módulo
                if (selectecModulo != null && selectecModulo.getSegpantallasList() != null) {
                    selectecModulo.getSegpantallasList().remove(eliminarPantalla);
                }

                // Mostrar un mensaje de éxito
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Pantalla eliminada con éxito");
            } else {
                // Mostrar un mensaje de error si no se ha seleccionado una pantalla para eliminar
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione una pantalla");
            }

            // Mostrar los mensajes al usuario
            validar.mostrarMsj();
        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error al eliminar pantalla");
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
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione una pantalla");
            validar.mostrarMsj();
        }

    }

    /**
     * Metodo que guarda la edición de la pantalla
     */
    public void editarPantalla() {
        try {
            // Verificar si se ha seleccionado una pantalla para editar
            if (editPantalla != null) {
                // Verificar si el nombre de la pantalla termina en .xhtml
                if (!editPantalla.getUrlpantalla().endsWith(".xhtml")) {
                    // Mostrar un mensaje de error si el nombre de la pantalla no termina en .xhtml
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                            "La url de la pantalla debe terminar en .xhtml");
                    validar.mostrarMsj();
                    return; // Terminar la ejecución del método si hay un error
                }

                // Editar la pantalla en la base de datos
                genProcesos.edit(editPantalla);

                // Actualizar la pantalla editada en la lista de pantallas del módulo
                if (selectecModulo != null && selectecModulo.getSegpantallasList() != null) {
                    int index = selectecModulo.getSegpantallasList().indexOf(editPantalla);
                    if (index != -1) {
                        selectecModulo.getSegpantallasList().set(index, editPantalla);
                    }
                }

                // Ocultar el diálogo de edición de pantalla
                PrimeFaces.current().executeScript("PF('editPantalla').hide();");

                // Mostrar un mensaje de éxito
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Pantalla editada con éxito");
            } else {
                // Mostrar un mensaje de error si no se ha seleccionado una pantalla para editar
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione una pantalla");
            }

            // Mostrar los mensajes al usuario
            validar.mostrarMsj();
        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error al editar pantalla");
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
                        = segBusqueda.maxCodPantalla(selectecModulo.getCodmod());
                SegpantallasPK pkPantalla = new SegpantallasPK(selectecModulo.getCodmod(),
                        corPantalla.add(new BigInteger("5")));
                addPantalla.setPantallasPK(pkPantalla);
                selectecModulo.getSegpantallasList().add(addPantalla);
                lstModulos.set(lstModulos.indexOf(selectecModulo), selectecModulo);
                genProcesos.create(addPantalla);
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Pantalla agregada correctamente");
                validar.mostrarMsj();
            } else {
                lstMsj.forEach((msj) -> {
                    validar.agregarMsj(ValidacionMensajes.Severidad.INFO, msj);
                });
                validar.mostrarMsj();
            }
        } catch (ValidacionesException ex) {
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, ex.getMensaje());
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, ex.getMessage());
            for (String msj : ex.getMensajes()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, msj);
            }
            validar.mostrarMsj();
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error al guardar pantalla");
        }

    }
    //</editor-fold>

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="GET AND SET">
//<editor-fold defaultstate="collapsed" desc="GENERALES">
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

    public ValidacionMensajes getValidar() {
        return validar;
    }
//</editor-fold>
//</editor-fold>

}
