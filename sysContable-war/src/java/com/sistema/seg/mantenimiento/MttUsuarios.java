/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.mantenimiento;

import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.general.entidades.Genpersonas;
import com.sistema.general.entidades.Genpuntoventas;
import com.sistema.general.negocio.GenBusquedadLocal;
import com.sistema.seguridad.entidades.Segperfiles;
import com.sistema.seguridad.entidades.Segusuarios;
import com.sistema.seguridad.negocio.SegBusquedaLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttUsuarios")
@SessionScoped
public class MttUsuarios implements Serializable {

//<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private GenBusquedadLocal genBusqueda;

    @EJB
    private SegBusquedaLocal segBusqueda;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="VARIABLES DE BUSQUEDA">
    /**
     * Contiene lista de usuario que obntendra la busqueda
     */
    List<Segusuarios> lstUsuarios = new ArrayList();
    /**
     * Contendra el nombre del usuario a buscar
     */
    private String nombre;
    /**
     * Contendra el codigo de usuario a buscar
     */
    private String codUser;
    /**
     * Variable que contendra el correlativo de la sucursal
     */
    private BigInteger corSucursal;
    /**
     * Contendra la lista de select item a selecionar para buscar
     */
    private List<SelectItem> itmSucursales = new ArrayList<>();
    /**
     * Variable que almacenara la lista de nombre para el auto complete
     */
    List<String> lstNombre = new ArrayList();
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="VARIABLES GENERALES">
    /**
     * Variable general para validar los datos
     */
    private static final ValidacionMensajes validar = new ValidacionMensajes();
    /**
     * Contendra el valor del tab a mostra
     */
    private int indexTab = 0;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="VARIABLES PARA GUARDAR USUARIO">
    /**
     * Contiene los literales de los perfiles a seleccionar
     */
    private List<SelectItem> itmPerfiles = new ArrayList<>();
    /**
     * Contendra el nombre de la persona
     */
    String nombreUser = "";
    /**
     * Contendra el codigo de usuario
     */
    String codiUser = "";
    /**
     * Contendra el correlativo del punto de venta
     */
    BigInteger corrVenta = BigInteger.ZERO;
    /**
     * Contendra la duracion de la clave de usuario
     */
    BigInteger duraClave = BigInteger.ZERO;
    /**
     * Contendra el estado de usuario
     */
    BigInteger estado = BigInteger.ZERO;
    /**
     * Contendra el codigo de perfil que tiene o se le asignara
     */
    BigInteger codPerfil = BigInteger.ZERO;
    /**
     * Variable que se usa para almacenar el usuario seleccionado
     */
    private Segusuarios selectUsuario = new Segusuarios();
//</editor-fold>

    /**
     * Creates a new instance of MttUsuarios
     */
    public MttUsuarios() {
    }
//<editor-fold defaultstate="collapsed" desc="METODOS DE BUSQUEDA">

    /**
     * Metodo que se carga al inicio, la primera ves que se selecciona la
     * pantalla
     */
    @PostConstruct
    public void init() {
        try {
            List<Segperfiles> lstPerfiles = genBusqueda.buscarTodos(Segperfiles.class);
            for (Segperfiles perfil : lstPerfiles) {
                itmPerfiles.add(new SelectItem(perfil.getCodperfil(), perfil.getNombreperfil()));
            }
            List<Segusuarios> lstUsuarios = genBusqueda.buscarTodos(Segusuarios.class);
            for (Segusuarios user : lstUsuarios) {
                lstNombre.add(user.getPersona().getNomcom());
            }
            List<Genpuntoventas> lstPuntoVentas = genBusqueda.buscarTodos(Genpuntoventas.class);
            for (Genpuntoventas punto : lstPuntoVentas) {
                itmSucursales.add(new SelectItem(punto.getCorrpventa(), punto.getNombre()));
            }
        } catch (NullPointerException ne) {
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                    "Error por datos nulos, comuniquese con el equipo informatico");
        } catch (Exception ex) {
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                    "Error general, comuniquese con el equipo informatico");
        }
    }

    /**
     * Metodo que busca el nombre al ingresar 3 letras o mas en el inputtex
     *
     * @param query recibe la letras digitadas
     * @return regres una lista de coincidencias
     */
    public List<String> obtenerNombre(String query) {
        String queryLowerCase = query.toLowerCase();
        return lstNombre.stream().filter(t
                -> t.toLowerCase().contains(queryLowerCase))
                .collect(Collectors.toList());
    }

    /**
     * Realiza la busqueda de usuarios segun los parametros que se le envian
     */
    public void buscarUsuario() {
        try {
            Map parametros = new HashMap();
            if (codUser != null && !codUser.trim().isEmpty()) {
                parametros.put("usuario", nombre);
            }
            if (nombre != null && !nombre.trim().isEmpty()) {
                parametros.put("nombre", nombre);
            }
            if (corSucursal != null && !corSucursal.toString().trim().isEmpty()) {
                parametros.put("corsucursal", corSucursal);
            }
            if (parametros.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                        "Agregue un parametro de busqueda");
                validar.mostrarMsj();
                return;
            }
            lstUsuarios = segBusqueda.buscarUsuarios(parametros);
        } catch (NullPointerException ne) {
            validar.manejarExcepcion(ne, "Error por datos nulos en buscar usuario");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error general en buscar usuario");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="METODO PARA CARGAR USUARIOS">

    /**
     * Metodo que carga el usuario al seleccionarlo
     */
    public void cargarUsuario() {
        try {
            if (selectUsuario == null) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un usuario a mostrar");
                validar.mostrarMsj();
                return;
            }
            this.codiUser = selectUsuario.getCoduser();
            this.duraClave = selectUsuario.getDuraclave();
            this.estado = selectUsuario.getEstado();
            if (selectUsuario.getPersona() != null) {
                nombreUser = selectUsuario.getPersona().getNomcom();

            }
            if (selectUsuario.getPersona().getPuntoventa() != null) {
                this.corrVenta = selectUsuario.getPersona().getPuntoventa().getCorrpventa();
            }
            if (selectUsuario.getSegPerfiles() != null) {
                this.codPerfil = selectUsuario.getSegPerfiles().getCodperfil();
            }
            indexTab = 1;
        } catch (NullPointerException ex) {

        } catch (Exception ex) {

        }
    }

    public void limpiarUserSelccionado() {
        selectUsuario = new Segusuarios();
        nombreUser = "";
        codiUser = "";
        corrVenta = BigInteger.ZERO;
        duraClave = BigInteger.ZERO;
        estado = BigInteger.ZERO;
        codPerfil = BigInteger.ZERO;
    }

    public void limpiarBusqueda() {
        lstUsuarios = new ArrayList();
        nombre = "";
        codUser = "";
        corSucursal = BigInteger.ZERO;
        limpiarUserSelccionado();
    }
//</editor-fold>

    public void guardarUsuario() {
        /**
         * Contendra el valor de la persona que se le creara el usuario
         */
        Genpersonas usuarioPersona = new Genpersonas();
        /**
         * punto de venta de la persona seleccionada
         */
        Genpuntoventas usuarioPventa = new Genpuntoventas();
        /**
         * contendra el perfil asignado al usuario
         */
        Segperfiles userPerfil = new Segperfiles();
    }
//<editor-fold defaultstate="collapsed" desc="GET AND SET GENERALES">

    public int getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(int indexTab) {
        this.indexTab = indexTab;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="GET AND SET DE BUSQUEDA">

    public List<String> getLstNombre() {
        return lstNombre;
    }

    public void setLstNombre(List<String> lstNombre) {
        this.lstNombre = lstNombre;
    }

    public List<Segusuarios> getLstUsuarios() {
        return lstUsuarios;
    }

    public void setLstUsuarios(List<Segusuarios> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodUser() {
        return codUser;
    }

    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }

    public BigInteger getCorSucursal() {
        return corSucursal;
    }

    public void setCorSucursal(BigInteger corSucursal) {
        this.corSucursal = corSucursal;
    }

    public List<SelectItem> getItmSucursales() {
        return itmSucursales;
    }

    public void setItmSucursales(List<SelectItem> itmSucursales) {
        this.itmSucursales = itmSucursales;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="GET AND SET DE CARGAR USUARIIO">
    public Segusuarios getSelectUsuario() {
        return selectUsuario;
    }

    public void setSelectUsuario(Segusuarios selectUsuario) {
        this.selectUsuario = selectUsuario;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="GET AND SET GUARDAR USUARIO">

    public List<SelectItem> getItmPerfiles() {
        return itmPerfiles;
    }

    public void setItmPerfiles(List<SelectItem> itmPerfiles) {
        this.itmPerfiles = itmPerfiles;
    }

    public BigInteger getDuraClave() {
        return duraClave;
    }

    public void setDuraClave(BigInteger duraClave) {
        this.duraClave = duraClave;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getCodiUser() {
        return codiUser;
    }

    public void setCodiUser(String codiUser) {
        this.codiUser = codiUser;
    }

    public BigInteger getCorrVenta() {
        return corrVenta;
    }

    public void setCorrVenta(BigInteger corrVenta) {
        this.corrVenta = corrVenta;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public BigInteger getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(BigInteger codPerfil) {
        this.codPerfil = codPerfil;
    }
//</editor-fold>

}
