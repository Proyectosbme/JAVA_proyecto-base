/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.seg.menu;

import com.sistema.contable.general.busquedas.GenBusquedadLocal;
import com.sistema.contable.general.busquedas.GencorrelativosBusquedaLocal;
import com.sistema.contable.general.procesos.GenProcesosLocal;
import com.sistema.contable.general.validaciones.ValidacionMensajes;
import com.sistema.contable.seguridad.busquedas.SegmenuBusquedaLocal;
import com.sistema.contable.seguridad.busquedas.SegmoduloBusquedaLocal;
import com.sistema.contable.seguridad.entidades.Segmenu;
import com.sistema.contable.seguridad.entidades.Segmodulo;
import com.sistema.contable.seguridad.entidades.Segpantallas;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

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
    private GencorrelativosBusquedaLocal gencorrelativosBusqueda; // Bean para buscar correlativos
    @EJB
    private GenProcesosLocal genProcesos; // Bean para realizar procesos generales
    @EJB
    private SegmoduloBusquedaLocal segmoduloBusqueda; // Bean para buscar módulos de seguridad
    @EJB
    private SegmenuBusquedaLocal segmenuBusqueda; // Bean para buscar menús de seguridad

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
            lstSegmenu = segmenuBusqueda.buscarTodosMenu(parametros);

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

            lstSegmenuSub = segmenuBusqueda.buscarSubMenu(menu.getCodmenu());
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

    public void nuevo() {
        try {
            itemModulo = new ArrayList<>();
            itemPantalla = new ArrayList<>();
            itmPadre = new ArrayList<>();;
            itemCodMenu = null;
            itemCodModulo = null;
            itemCodPantalla = null;
            menuSelect = new Segmenu();

            esNuevo = true;
            //      List<Segmodulo> lstmodulos = segmoduloBusqueda.buscarModulo(new HashMap());
            List<Segmodulo> lstmodulos = genbusqueda.buscarTodos(Segmodulo.class);
            itemCodModulo = BigInteger.ZERO;
            for (Segmodulo md : lstmodulos) {
                itemModulo.add(new SelectItem(md.getCodmod(), md.getNommodulo()));
            }

            // Mostrar mensajes
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error en metodo de nuevo menu", ex);
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");

        }
    }

    public List<String> validarGuardar() {
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
            return lstMsj;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void guardarMenu() {
        try {

            List<String> lstMsj = validarGuardar();
            if (lstMsj.isEmpty()) {
                Segpantallas pantalla = new Segpantallas(itemCodModulo, itemCodPantalla);
                if (esNuevo) {
                    BigInteger codMenu = gencorrelativosBusqueda.obtenerCorrelativo("GENCORSMENU");
                    menuSelect.setCodmenu(codMenu);
                }
                if (itemCodMenu != null) {
                    Map parametros = new HashMap();
                    parametros.put("codmenu", itemCodMenu);
                    List<Segmenu> lstMenu = segmenuBusqueda.buscarTodosMenu(parametros);
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

                this.crearMenuPadre();
                esNuevo = false;
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.INFO, "Menu guardado con exito");
                validacionMensajes.mostrarMsj();
            } else {
                for (String msj : lstMsj) {
                    validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.WARN, msj);
                }
                validacionMensajes.mostrarMsj();
            }

        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Error inesperado en guardar menu");
        }
    }

    public void obtenerPantallas() {
        try {
            cargarMenuPadre();
            itemPantalla = new ArrayList<>();
            Map parametros = new HashMap();
            parametros.put("codmod", itemCodModulo);
            List<Segmodulo> lstmodulos = segmoduloBusqueda.buscarModulo(parametros);
            for (Segmodulo md : lstmodulos) {
                for (Segpantallas pt : md.getSegpantallasList()) {
                    itemPantalla.add(new SelectItem(pt.getPantallasPK().getCodpantalla(),
                            pt.getNompantalla()));
                }
            }
        } catch (Exception ex) {
            validacionMensajes.manejarExcepcion(ex, "Comuniquese con informatica");
        }
    }

    public void cargarMenuPadre() {
        try {
            itmPadre = new ArrayList<>();
            itmPadre.add(new SelectItem(BigInteger.ZERO, "RAIZ"));
            List<Segmenu> lstMenusCargar = new ArrayList<>();
            lstMenusCargar = segmenuBusqueda.busMenuRaiz(itemCodModulo);
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

    public void onNodeSelect(NodeSelectEvent event) {
        try {
            esNuevo = false;
            itemModulo = new ArrayList<>();
            itemPantalla = new ArrayList<>();
            itmPadre = new ArrayList<>();
            esNuevo = false;
            TreeNode selectedNode = event.getTreeNode();
            if (selectedNode.getData() instanceof MenuStructura) {
                MenuStructura obtenerMenu = (MenuStructura) selectedNode.getData();
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
