/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.mantenimiento;

import com.sistema.seg.menu.MenuStructura;
import com.sistema.general.negocio.GenBusquedadLocal;
import com.sistema.general.negocio.GenProcesosLocal;
import com.sistema.gen.utilidades.ValidacionMensajes;
import com.sistema.gen.utilidades.ImpresionReporte;
import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segmodulo;
import com.sistema.seguridad.entidades.Segpantallas;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import com.sistema.seguridad.negocio.SegBusquedaLocal;

/**
 * Controlador para la gestión de menús. Permite crear, modificar y eliminar
 * menús en el sistema.
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttMenu")
@SessionScoped
public class MttMenu implements Serializable {
// Inyección de dependencias para los EJBs necesarios

    @EJB
    private GenBusquedadLocal genbusqueda; // Bean para realizar búsquedas generales
    @EJB
    private GenProcesosLocal genProcesos; // Bean para realizar procesos generales
    @EJB
    private SegBusquedaLocal segBusqueda; // Bean para buscar menús de seguridad

    /**
     * Manejador de mensajes de validación y errores.
     */
    private final ValidacionMensajes validacionMensajes = new ValidacionMensajes();

    /**
     * Lista de menús principales.
     */
    private List<Segmenu> lstSegmenu = new ArrayList<>();

    /**
     * Lista de submenús.
     */
    private List<Segmenu> lstSegmenuSub = new ArrayList<>();

    /**
     * Nodo raíz del árbol de menús.
     */
    private TreeNode root;

    /**
     * Nodo seleccionado en el árbol de menús.
     */
    private TreeNode selectedNode;

    /**
     * Menú seleccionado.
     */
    private Segmenu menuSelect = new Segmenu();

    /**
     * Lista de elementos para seleccionar el menú padre.
     */
    private List<SelectItem> itmPadre = new ArrayList<>();

    /**
     * Lista de elementos para seleccionar la pantalla del menú.
     */
    private List<SelectItem> itemPantalla = new ArrayList<>();

    /**
     * Lista de elementos para seleccionar el módulo del menú.
     */
    private List<SelectItem> itemModulo = new ArrayList<>();

    /**
     * Código del menúPadre seleccionado.
     */
    private BigInteger itemCodMenu;

    /**
     * Código del módulo seleccionado.
     */
    private BigInteger itemCodModulo;

    /**
     * Código de la pantalla seleccionada.
     */
    private BigInteger itemCodPantalla;

    /**
     * Indicador de si se está creando un nuevo menú.
     */
    private boolean esNuevo = false;

    /**
     * Logger para registrar información y errores.
     */
    private static final Logger LOGGER = Logger.getLogger(MttMenu.class.getName());

    /**
     * Creates a new instance of MttMenu
     */
    public MttMenu() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        try {
            this.crearMenuPadre();
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");
        }

    }

    /**
     * Crea los nodos raíz y los nodos de menús principales del árbol de menús.
     * Los menús principales son aquellos que no tienen un menú padre asociado.
     *
     * @throws java.lang.Exception
     */
    public void crearMenuPadre() throws Exception {
        try {
            // Parámetros para buscar menús con jerarquía cero (menús principales)
            Map parametros = new HashMap();
            parametros.put("jerarquia", BigInteger.ZERO);

            // Busca todos los menús principales
            lstSegmenu = segBusqueda.buscarTodosMenu(parametros);

            // Crea el nodo raíz del árbol de menús
            root = new DefaultTreeNode(new MenuStructura("Menus", "-", "Modulos", "-", null), null);
            root.setExpanded(true);

            // Itera sobre los menús principales y crea los nodos correspondientes en el árbol
            for (Segmenu m : lstSegmenu) {
                if (m.getMenuPadre() == null) {
                    // Crea un nuevo nodo para el menú principal
                    TreeNode menu = new DefaultTreeNode(new MenuStructura(m.getNommenu(), "-", "Submenu", "-", m), root);
                    menu.setExpanded(true);

                    // Crea los submenús recursivamente
                    crearSubMenu(menu, m);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Metodo que crea los submenu
     *
     * @param node
     * @param menu
     * @throws java.lang.Exception
     */
    public void crearSubMenu(TreeNode node, Segmenu menu) throws NullPointerException, Exception {
        try {

            lstSegmenuSub = segBusqueda.buscarSubMenu(menu.getCodmenu());
            if (!lstSegmenuSub.isEmpty()) {
                for (Segmenu sm : lstSegmenuSub) {
                    if (sm.getPantalla() == null
                            || sm.getPantalla().getPantallasPK().getCodpantalla().compareTo(BigInteger.ZERO) == 0) {
                        TreeNode menusp = new DefaultTreeNode(
                                new MenuStructura(sm.getNommenu(), "-", "Submenu", "-", sm), node);
                        menusp.setExpanded(true);
                        this.crearSubMenu(menusp, sm);
                    } else {
                        //     TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
                        TreeNode pantall = new DefaultTreeNode("pantalla", new MenuStructura(sm.getNommenu(), "-", "Pantalla",
                                sm.getPantalla().getUrlpantalla(), sm), node);
                    }
                }
            }

        } catch (NullPointerException ne) {
            throw ne;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Metodo que limpia las variables
     */
    public void limpiar() {
        itemModulo = new ArrayList<>();
        itemPantalla = new ArrayList<>();
        itmPadre = new ArrayList<>();;
        itemCodMenu = null;
        itemCodModulo = null;
        itemCodPantalla = null;
        menuSelect = new Segmenu();
    }

    /**
     * Metodo que inicializa para agregar un nuevo menu
     */
    public void nuevo() {
        try {
            limpiar();

            esNuevo = true;
            //      List<Segmodulo> lstmodulos = segmoduloBusqueda.buscarModulo(new HashMap());
            List<Segmodulo> lstmodulos = genbusqueda.buscarTodos(Segmodulo.class);
            Comparator<Segmodulo> comparator = Comparator.comparing(Segmodulo::getCodmod);

            // Ordena la lista utilizando el comparador
            Collections.sort(lstmodulos, comparator);
            itemCodModulo = BigInteger.ZERO;
            for (Segmodulo md : lstmodulos) {
                itemModulo.add(new SelectItem(md.getCodmod(), md.getNommodulo()));
            }
            validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.INFO,
                    "Nuevo menu, ingreso la informacion necesaria");
            validacionMensajes.mostrarMsj();
            // Mostrar mensajes
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error en metodo de nuevo menu", ex);
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");

        }
    }

    /**
     * Valida los datatos antes de agregar el nuevo menu o su edicion
     *
     * @return
     */
    public List<String> validarGuardar() throws Exception {
        try {

            List<String> lstMsj = new ArrayList<>();
            if (itemCodModulo == null) {
                lstMsj.add("Seleccione un modulo");
            }
            if (itemCodMenu == null) {
                lstMsj.add("Seleccione un menu");
            }
            if (itemCodPantalla == null) {
                lstMsj.add("Seleccione una pantalla");
            }
            if (menuSelect != null && menuSelect.getDscmenu().isEmpty()) {
                lstMsj.add("Ingrese la descripcion del menu");
            }
            if (menuSelect != null && menuSelect.getNommenu().isEmpty()) {
                lstMsj.add("Ingrese el nombre del menu");
            }
            if (menuSelect != null && menuSelect.getOrdenes() == null) {
                lstMsj.add("Ingrese la jeraquia del menu");
            }
            if (menuSelect != null && menuSelect.getVersion().isEmpty()) {
                lstMsj.add("Ingrese la version del menu");
            }
            return lstMsj;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Metodo que guarda o edita el nuevo menu
     */
    public void guardarMenu() {
        try {
            String msjUser = "Menu editado con exito";
            List<String> lstMsj = validarGuardar();
            if (lstMsj.isEmpty()) {
                Segpantallas pantalla = new Segpantallas(itemCodModulo, itemCodPantalla);
                if (esNuevo) {
                    msjUser = "Menu agregado con exito";
                    BigInteger codMenu = genbusqueda.obtenerCorrelativo("GENCORSMENU");
                    menuSelect.setCodmenu(codMenu);
                }
                if (itemCodMenu != null) {
                    Map parametros = new HashMap();
                    parametros.put("codmenu", itemCodMenu);
                    List<Segmenu> lstMenu = segBusqueda.buscarTodosMenu(parametros);
                    if (lstMenu != null && !lstMenu.isEmpty()) {
                        for (Segmenu segm : lstMenu) {
                            menuSelect.setMenuPadre(segm);
                            menuSelect.setJerarquia(segm.getJerarquia().add(BigInteger.ONE));
                        }
                    } else {
                        menuSelect.setJerarquia(BigInteger.ZERO);
                    }

                }
                menuSelect.setPantalla(pantalla);
                menuSelect.setTipo("WEB");
                if (esNuevo) {
                    genProcesos.create(menuSelect);
                } else {
                    genProcesos.edit(menuSelect);
                }
                //   this.menuSelect = new Segmenu();

                this.crearMenuPadre();
                esNuevo = false;
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.INFO, msjUser);
                validacionMensajes.mostrarMsj();
            } else {
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                        "Faltan los siguentes datos");
                for (String msj : lstMsj) {
                    validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.WARN, msj);
                }
                validacionMensajes.mostrarMsj();
            }

        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Error inesperado en guardar menu");
        }
    }

    /**
     * Obtiene las pantallas asignadas al modulo
     */
    public void obtenerPantallas() {
        try {
            cargarMenuPadre();
            itemPantalla = new ArrayList<>();
            Map parametros = new HashMap();
            parametros.put("codmod", itemCodModulo);
            List<Segmodulo> lstmodulos = segBusqueda.buscarModulo(parametros);
            for (Segmodulo md : lstmodulos) {
                // Define un comparador para comparar los Segpantallas por el atributo codpantalla
                Comparator<Segpantallas> comparator
                        = Comparator.comparing(segpantallas -> segpantallas.getPantallasPK().getCodpantalla());
                // Ordena la lista utilizando el comparador
                Collections.sort(md.getSegpantallasList(), comparator);
                for (Segpantallas pt : md.getSegpantallasList()) {
                    itemPantalla.add(new SelectItem(pt.getPantallasPK().getCodpantalla(),
                            pt.getNompantalla()));
                }
            }
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");
        }
    }

    /**
     * Carga el menu padre de los menu raiz del modulo seleccionado
     */
    public void cargarMenuPadre() {
        try {
            itmPadre = new ArrayList<>();
            itmPadre.add(new SelectItem(BigInteger.ZERO, "RAIZ"));
            List<Segmenu> lstMenusCargar = new ArrayList<>();
            lstMenusCargar = segBusqueda.busMenuRaiz(itemCodModulo);
            for (Segmenu sm : lstMenusCargar) {
                int contador = 0;
                String mPadre = "RAIZ";
                BigInteger codPadre = sm.getCodmenu();
                Segmenu menuRecorrido = sm;
                while (menuRecorrido != null) {
                    if (contador != 0) {
                        mPadre = menuRecorrido.getNommenu() + "->" + mPadre;
                    } else {
                        mPadre = menuRecorrido.getNommenu();
                    }
                    contador++;
                    if (menuRecorrido.getMenuPadre() != null) {
                        menuRecorrido = menuRecorrido.getMenuPadre();
                    } else {
                        menuRecorrido = null;
                    }

                }

                itmPadre.add(new SelectItem(codPadre, mPadre));
            }

        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");
        }

    }

    /**
     * Nodo seleccionado y asignar su variables
     *
     * @param event evento que se maneja al seleccionar un nodo
     */
    public void onNodeSelect(NodeSelectEvent event) {
        try {
            esNuevo = false;
            itemModulo = new ArrayList<>();
            itemPantalla = new ArrayList<>();
            itmPadre = new ArrayList<>();
            esNuevo = false;
            TreeNode nodoSelect = event.getTreeNode();
            this.selectedNode = nodoSelect;
            if (nodoSelect.getData() instanceof MenuStructura) {
                MenuStructura obtenerMenu = (MenuStructura) nodoSelect.getData();
                this.setMenuSelect(obtenerMenu.getSegMenu());

            }
            crearMenuPadreSelect(menuSelect);
            itemModulo.add(new SelectItem(menuSelect.getPantalla().getSegmodulo().getCodmod(),
                    menuSelect.getPantalla().getSegmodulo().getNommodulo()));
            itemCodModulo = menuSelect.getPantalla().getSegmodulo().getCodmod();

            itemPantalla.add(new SelectItem(menuSelect.getPantalla().getPantallasPK().getCodpantalla(),
                    menuSelect.getPantalla().getNompantalla()));
            itemCodPantalla = menuSelect.getPantalla().getPantallasPK().getCodpantalla();
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");
        }
    }

    /**
     * Crea el menu padre del modulo seleccionado
     *
     * @param menuSelect parametros del menu seleccionado
     * @throws Exception error que puedo arrojar el metodo
     */
    public void crearMenuPadreSelect(Segmenu menuSelect) throws Exception {
        try {

            int contador = 0;
            String mPadre = "Raiz";
            BigInteger codPadre = menuSelect.getMenuPadre() != null ? menuSelect.getMenuPadre().getCodmenu() : BigInteger.ZERO;
            Segmenu menuRecorrido = menuSelect;
            itemCodMenu = codPadre;
            while (menuRecorrido != null && menuRecorrido.getMenuPadre() != null) {
                if (contador != 0) {
                    mPadre = menuRecorrido.getMenuPadre().getNommenu() + "->" + mPadre;
                } else {
                    mPadre = menuRecorrido.getMenuPadre().getNommenu();
                }
                contador++;
                menuRecorrido = menuRecorrido.getMenuPadre();
            }

            itmPadre.add(new SelectItem(codPadre, mPadre));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Abre un popup para confirmar la eliminacion del menu
     */
    public void confirmarEliminarMenu() {
        try {
            List<String> msjError = new ArrayList<>();
            //MENU NO ES NULLO O VACIO
            if (menuSelect == null || menuSelect.getCodmenu() == null) {
                msjError.add("ERROR: Seleccione un menú a eliminar");
            } else {

                if (menuSelect.getLstPerfiles() != null && !menuSelect.getLstPerfiles().isEmpty()) {
                    msjError.add("ERROR: Hay perfiles que tienen asignado el menú");
                }

                List<Segmenu> lstMenuHijos = segBusqueda.buscarSubMenu(menuSelect.getCodmenu());
                if (lstMenuHijos != null && !lstMenuHijos.isEmpty()) {
                    msjError.add("ERROR: No se puede eliminar, elimine los submenús primero");
                }
            }
            if (!msjError.isEmpty()) {
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.ERROR,
                        "Complete lo siguientes enunciados");
                for (String msj : msjError) {
                    validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.WARN, msj);
                }
                validacionMensajes.mostrarMsj();
                return;
            }
            PrimeFaces.current().executeScript("PF('deleteMenu').show();");
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Error al eliminar menu");
        }
    }

    /**
     * Metodo que elimina el menu seleccionado
     */
    public void eliminarMenu() {
        try {
            if (menuSelect != null && menuSelect.getCodmenu() != null) {
                genProcesos.remove(menuSelect);
                if (selectedNode != null) {
                    selectedNode.getParent().getChildren().remove(selectedNode);
                    selectedNode = null;
                }
                this.limpiar();
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.INFO,
                        "Menu eliminado exitosamente");
                validacionMensajes.mostrarMsj();
                //this.crearMenuPadre();
            } else {
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.ERROR, "No hay menu que eliminar");
                validacionMensajes.mostrarMsj();
            }
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Error al eliminar el menu");
        }
    }

    public void imprimir() {
        // imprimirReportes("modulos", "DOCX");
        Map parametros = new HashMap();
        parametros.put("user", "user");
        ImpresionReporte.imprimirReporte("modulos", "/reportes/", parametros, "PDF");
    }

    private void imprimirReportes(String reporte, String form) {
        // Crear y llenar el mapa de parámetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", "user");

        try {
            // Obtener el contexto de Faces y la solicitud HTTP
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

            // Configurar los atributos de sesión necesarios para el servlet
            String servletUrl = request.getContextPath() + "/ImpresionReporteServlet";
            request.getSession().setAttribute("ds", "jdbc/_contabilidad");
            request.getSession().setAttribute("url", "/reportes/" + reporte + ".jasper");
            request.getSession().setAttribute("parameters", parameters);
            request.getSession().setAttribute("format", form);

            // Generar el código JavaScript para abrir una nueva ventana del navegador con el reporte
            String javascriptCode = String.format(
                    "window.open('%s','Rpt','location=0,menubar=0,resizable=1,status=0,toolbar=0');",
                    servletUrl
            );

            // Ejecutar el código JavaScript usando PrimeFaces
            PrimeFaces.current().executeScript(javascriptCode);
        } catch (Exception ex) {
            // Imprimir la traza de la excepción para el diagnóstico de errores
            ex.printStackTrace();
        }
    }

//</editor-fold>
    public List<Segmenu> getLstSegmenu() {
        return lstSegmenu;
    }

    public void setLstSegmenu(List<Segmenu> lstSegmenu) {
        this.lstSegmenu = lstSegmenu;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Segmenu getMenuSelect() {
        return menuSelect;
    }

    public void setMenuSelect(Segmenu menuSelect) {
        this.menuSelect = menuSelect;
    }

    public List<SelectItem> getItmPadre() {
        return itmPadre;
    }

    public void setItmPadre(List<SelectItem> itmPadre) {
        this.itmPadre = itmPadre;
    }

    public BigInteger getItemCodMenu() {
        return itemCodMenu;
    }

    public void setItemCodMenu(BigInteger itemCodMenu) {
        this.itemCodMenu = itemCodMenu;
    }

    public List<SelectItem> getItemPantalla() {
        return itemPantalla;
    }

    public void setItemPantalla(List<SelectItem> itemPantalla) {
        this.itemPantalla = itemPantalla;
    }

    public List<SelectItem> getItemModulo() {
        return itemModulo;
    }

    public void setItemModulo(List<SelectItem> itemModulo) {
        this.itemModulo = itemModulo;
    }

    public BigInteger getItemCodModulo() {
        return itemCodModulo;
    }

    public void setItemCodModulo(BigInteger itemCodModulo) {
        this.itemCodModulo = itemCodModulo;
    }

    public BigInteger getItemCodPantalla() {
        return itemCodPantalla;
    }

    public void setItemCodPantalla(BigInteger itemCodPantalla) {
        this.itemCodPantalla = itemCodPantalla;
    }

}
