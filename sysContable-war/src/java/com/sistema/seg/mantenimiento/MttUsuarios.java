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
import com.sistema.general.negocio.GenProcesosLocal;
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
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;

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

    @EJB
    private GenProcesosLocal genProcesos;

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="VARIABLES DE BUSQUEDA">
    private static Random random = new Random();
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
     * Contendra el nombre de la persona seleccionada
     */
    String nombreUser = "";
    /**
     * Contendra el codigo de usuario seleccionado
     */
    String codiUser = "";
    /**
     * Contendra el correlativo del punto de venta seleccionado
     */
    BigInteger corrVenta = BigInteger.ZERO;
    /**
     * Contendra la duracion de la clave de usuario del usuario seleccionado
     */
    BigInteger duraClave = BigInteger.ZERO;
    /**
     * Contendra el estado de usuario seleccionado
     */
    BigInteger estado = BigInteger.ZERO;
    /**
     * Contendra el codigo de perfil que tiene o se le asignara al usuario nuevo
     * o select
     */
    BigInteger codPerfil = BigInteger.ZERO;
    /**
     * Variable que se usa para almacenar el usuario seleccionado
     */
    private Segusuarios selectUsuario = new Segusuarios();
    /**
     * Validara si el usuario es nuevo o una modificacionm
     */
    private boolean esNuevo = false;
    /**
     * buscarpersona
     */
    private String busPerNomcom = "";
    private String busPerDui = "";
    private BigInteger busPerCorVenta = BigInteger.ZERO;
    private List<Genpersonas> lstBusPerUsuario = new ArrayList();
    private Genpersonas personaSelect = new Genpersonas();
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
                parametros.put("usuario", codUser);
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
     * Metodo que que obtiene el usuario seleccionado luego agrega el contenido
     * a cada avriable para cargar el detalle
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
                personaSelect = selectUsuario.getPersona();
            }
            if (selectUsuario.getPersona().getPuntoventa() != null) {
                this.corrVenta = selectUsuario.getPersona().getPuntoventa().getCorrpventa();
            }
            if (selectUsuario.getSegPerfiles() != null) {
                this.codPerfil = selectUsuario.getSegPerfiles().getCodperfil();
            }

            esNuevo = false;
            indexTab = 1;
        } catch (NullPointerException ex) {
            validar.manejarExcepcion(ex, "Error por datos nulos, comunicarse con informatica");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error general , comuniquese con informatica");

        }
    }

    /**
     * Limpia el usuario seleccionado, para dejar las variables lista para usar
     * tambien limpia la busqueda de persona, mandado a llamar al metodo
     * correspondiente
     */
    public void limpiarUserSelccionado() {
        selectUsuario = new Segusuarios();
        nombreUser = "";
        codiUser = "";
        corrVenta = BigInteger.ZERO;
        duraClave = BigInteger.ZERO;
        estado = BigInteger.ZERO;
        codPerfil = BigInteger.ZERO;
        esNuevo = false;
        limpiarBusPersona();
        personaSelect = new Genpersonas();
    }

    /**
     * Limpia la bsuqueda y llama al metodo de limiar usaurioa seleccionado
     */
    public void limpiarBusqueda() {
        lstUsuarios = new ArrayList();
        nombre = "";
        codUser = "";
        corSucursal = BigInteger.ZERO;
        limpiarUserSelccionado();
    }

    /**
     * Agrega el nuevo usuario limpia todo y manda a tab de detalle
     * inicializando las variables correespondientes
     */
    public void agregarNuevo() {
        limpiarBusqueda();
        indexTab = 1;
        validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Agregue los parametros necesarios");
        validar.mostrarMsj();
        esNuevo = true;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="METODO PARA GUARDAR O EDITAR USUARIO">
    // Método para generar una letra aleatoria entre 'a' y 'z'

    private static char generarLetraAleatoria() {
        return (char) (random.nextInt(26) + 'a');
    }

    /**
     * Agrega o edita el usuario seguna la necesidad que se tenga
     */
    public void guardarUsuario() {
        if (selectUsuario == null && !esNuevo) {
            validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Seleccione un usuario o cree uno nuevo");
            validar.mostrarMsj();
            return;
        }

        if (esNuevo) {
            System.err.println("Es nuevo");
        } else {
            try {
                selectUsuario.setEstado(estado);
                selectUsuario.setDuraclave(duraClave);
                Map paramPerfil = new HashMap();
                //buscar perfil
                paramPerfil.put("codperfil", codPerfil);
                selectUsuario.setSegPerfiles(segBusqueda.buscarPerfiles(paramPerfil).get(0));
                //buscar punto de venta
                Map paramPVenta = new HashMap();
                paramPVenta.put("corrpventa", corrVenta);
                personaSelect.setPuntoventa((Genpuntoventas) genBusqueda.buscarPuntoVenta(paramPVenta).get(0));
                selectUsuario.setPersona(personaSelect);
                genProcesos.edit(selectUsuario);
                validar.agregarMsj(ValidacionMensajes.Severidad.INFO, "Usuario modificado correctamente");
                validar.mostrarMsj();
            } catch (Exception ex) {
                validar.manejarExcepcion(ex, "Error al editar usuario");
            }
        }

    }

    /**
     * Metodo que realiza una busqueda de personas, or diferentes parametros y
     * cargar una lista de personas
     */
    public void buscarPersona() {
        try {
            Map parametros = new HashMap();
            if (busPerCorVenta != null && busPerCorVenta.compareTo(BigInteger.ZERO) != 0) {
                parametros.put("puntoventa", busPerCorVenta);
            }
            if (busPerDui != null && !busPerDui.trim().isEmpty()) {
                parametros.put("dui", busPerDui);
            }
            if (busPerNomcom != null && !busPerNomcom.trim().isEmpty()) {
                parametros.put("nomcom", busPerNomcom);
            }
            if (parametros.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "Ingrese un parametro de busqueda");
                validar.mostrarMsj();
                return;
            }
            lstBusPerUsuario = genBusqueda.buscarPersona(parametros);
            if (lstBusPerUsuario.isEmpty()) {
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "No se encontraron registros");
                validar.mostrarMsj();
            }
        } catch (NullPointerException ne) {
            validar.manejarExcepcion(ne, "Error por datos nulos");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error general");

        }
    }

    /**
     * Limpia las variables y la lista que esta en busqueda de persona
     */
    public void limpiarBusPersona() {
        busPerNomcom = "";
        busPerDui = "";
        busPerCorVenta = BigInteger.ZERO;
        lstBusPerUsuario.clear();

    }

    /**
     * Metodo que carga la persona seleccionada para llenar el detalle y guardar
     * o editar persona
     */
    public void cargarPersonaSelect() {
        try {
            if (personaSelect != null && personaSelect.getNomcom() != null) {
                Map parametros = new HashMap();
                parametros.put("persona", personaSelect);
                System.err.println("iniico consulta");
                List<Segusuarios> lstUsuarios2 = segBusqueda.buscarUsuarios(parametros);
                System.err.println("fin consulta");
                if (lstUsuarios2 != null && !lstUsuarios2.isEmpty()) {
                    System.err.println("Ya existe persona con usuario");
                    validar.agregarMsj(ValidacionMensajes.Severidad.ERROR, "La persona ya cuenta con un usuario");
                    validar.mostrarMsj();
                    return;
                }
                generarUsuario(personaSelect);
                nombreUser = personaSelect.getNomcom();
                PrimeFaces.current().executeScript("PF('busPersona').hide();");
            } else {
                validar.agregarMsj(ValidacionMensajes.Severidad.WARN, "Seleccione una persona");
                validar.mostrarMsj();
            }
        } catch (NullPointerException ex) {
            validar.manejarExcepcion(ex, "Error por datos nulos, comuniquese con informatica");
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error general, comuniquese con informatica");
        }
    }

    /**
     * Metodo que crea el usuario de la persona seguna las variables de nombre o
     * apelliod, ademas por algun tipo de dato con error le genera un valor
     * aleatorio
     *
     * @param per parametro que recibe es una persona
     */
    public void generarUsuario(Genpersonas per) throws NullPointerException, Exception {
        if (per.getPrinombre() != null && !per.getPrinombre().trim().isEmpty()) {
            codiUser = per.getPrinombre().substring(0, 1);
        } else if (per.getSegnombre() != null && !per.getSegnombre().trim().isEmpty()) {
            codiUser = per.getSegnombre().substring(0, 1);
        } else {
            codiUser = String.valueOf(generarLetraAleatoria());
        }

        if (per.getPriapellido() != null && !per.getPriapellido().trim().isEmpty()) {
            codiUser = codiUser + per.getPriapellido();
        } else if (per.getSegapellido() != null && !per.getSegapellido().trim().isEmpty()) {
            codiUser = codiUser + per.getSegapellido();
        } else if (per.getApecasada() != null && !per.getApecasada().trim().isEmpty()) {
            codiUser = codiUser + per.getApecasada();
        } else {
            for (int i = 0; i < 5; i++) {
                codiUser = codiUser + String.valueOf(generarLetraAleatoria());
            }
        }
        int contador = 1;
        while (usuarioExiste(codiUser)) {
            codiUser = codiUser + contador;
            contador++;
        }
    }

    /**
     * Metodo que valida si un usario existe
     *
     * @param codUser paraque recibe el codigo de usuario
     * @return regresa falso o verdadero
     */
    public boolean usuarioExiste(String codUser) {
        boolean resp = false;
        try {
            Map parametros = new HashMap();
            parametros.put("usuario", codiUser);
            List<Segusuarios> lstUsuarios = segBusqueda.buscarUsuarios(parametros);
            if (lstUsuarios != null && !lstUsuarios.isEmpty()) {
                resp = true;
            }
        } catch (Exception ex) {
            validar.manejarExcepcion(ex, "Error general la cargar codigo de persona");
        }
        return resp;
    }
//</editor-fold>

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
    //<editor-fold defaultstate="collapsed" desc="GET AND SET BUSCAR PERSONA">

    public List<Genpersonas> getLstBusPerUsuario() {
        return lstBusPerUsuario;
    }

    public void setLstBusPerUsuario(List<Genpersonas> lstBusPerUsuario) {
        this.lstBusPerUsuario = lstBusPerUsuario;
    }

    public Genpersonas getPersonaSelect() {
        return personaSelect;
    }

    public void setPersonaSelect(Genpersonas personaSelect) {
        this.personaSelect = personaSelect;
    }

    public String getBusPerNomcom() {
        return busPerNomcom;
    }

    public void setBusPerNomcom(String busPerNomcom) {
        this.busPerNomcom = busPerNomcom;
    }

    public String getBusPerDui() {
        return busPerDui;
    }

    public void setBusPerDui(String busPerDui) {
        this.busPerDui = busPerDui;
    }

    public BigInteger getBusPerCorVenta() {
        return busPerCorVenta;
    }

    public void setBusPerCorVenta(BigInteger busPerCorVenta) {
        this.busPerCorVenta = busPerCorVenta;
    }
//</editor-fold>

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

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
