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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "mttMenu")
@SessionScoped
public class MttMenu implements Serializable {

    @EJB
    private GenBusquedadLocal genbusqueda;

    @EJB
    private GencorrelativosBusquedaLocal gencorrelativosBusqueda;
    @EJB
    private GenProcesosLocal genProcesos;
    @EJB
    private SegmoduloBusquedaLocal segmoduloBusqueda;
    @EJB
    private SegmenuBusquedaLocal segmenuBusqueda;

    private ValidacionMensajes validacionMensajes = new ValidacionMensajes();
    private List<Segmenu> lstSegmenu = new ArrayList();
    private List<Segmenu> lstSegmenuSub = new ArrayList();
    private TreeNode root;
    private TreeNode selectedNode;
    private Segmenu menuSelect = new Segmenu();
    private List<SelectItem> itmPadre = new ArrayList<>();
    private List<SelectItem> itemPantalla = new ArrayList<>();
    private List<SelectItem> itemModulo = new ArrayList<>();
    private BigInteger itemCodMenu;
    private BigInteger itemCodModulo;
    private BigInteger itemCodPantalla;
    private boolean esNuevo = false;
    private static final Logger LOGGER = Logger.getLogger(MttMenu.class.getName());
    private Map<String, Boolean> expandedNodes;
    /**
     * Mensjae que se mostraran al usuario
     */
    private List<FacesMessage> messages = new ArrayList<>();

    /**
     * Creates a new instance of MttMenu
     */
    public MttMenu() {
    }

    @PostConstruct
    public void init() {
        this.crearMenuPadre();
    }

    public void crearMenuPadre() {
        try {
            expandedNodes = new HashMap<>();
            Map parametros = new HashMap();
            parametros.put("jerarquia", BigInteger.ZERO);
            lstSegmenu = segmenuBusqueda.buscarTodosMenu(parametros);
            // lstSegmenu = segmenuFacade.buscarSubMenu(new BigInteger("10"), new BigInteger("10"));
            root = new DefaultTreeNode(new MenuStructura("Menus", "-", "Modulos", "-", null), null);
            root.setExpanded(true);
            for (Segmenu m : lstSegmenu) {
                if (m.getCodmenupadre() == null) {
                    TreeNode menu = new DefaultTreeNode(new MenuStructura(m.getNommenu(), "-", "Submenu", "-", m), root);
                    menu.setExpanded(true);
                    crearSubMenu(menu, m);
                    expandedNodes.put(menu.getRowKey(), true);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearSubMenu(TreeNode node, Segmenu menu) {
        try {

            lstSegmenuSub = segmenuBusqueda.buscarSubMenu(menu.getCodmenu());
            if (!lstSegmenuSub.isEmpty()) {
                for (Segmenu sm : lstSegmenuSub) {
                    if (sm.getSegpantallas() == null
                            || sm.getSegpantallas().getSegpantallasPK().getCodpantalla().compareTo(BigInteger.ZERO) == 0) {
                        TreeNode menusp = new DefaultTreeNode(
                                new MenuStructura(sm.getNommenu(), "-", "Submenu", "-", sm), node);
                        menusp.setExpanded(true);
                        this.crearSubMenu(menusp, sm);
                    } else {
                        //     TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
                        TreeNode pantall = new DefaultTreeNode("pantalla", new MenuStructura(sm.getSegpantallas().getNompantalla(), "-", "Pantalla",
                                sm.getSegpantallas().getUrlpantalla(), sm), node);
                    }
                }
            }

        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
                            menuSelect.setCodmenupadre(segm);
                            menuSelect.setJerarquia(segm.getJerarquia().add(BigInteger.ONE));
                        }
                    } else {
                        menuSelect.setJerarquia(BigInteger.ZERO);
                    }

                }
                menuSelect.setSegpantallas(pantalla);
                menuSelect.setTipo("WEB");
                if (esNuevo) {
                    genProcesos.create(menuSelect);
                } else {
                    genProcesos.edit(menuSelect);
                }

                this.crearMenuPadre();
                esNuevo = false;
                validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.INFO, "Menu guardado con exito");
            } else {
                for (String msj : lstMsj) {
                    validacionMensajes.agregarMsj(ValidacionMensajes.Severidad.WARN, msj);
                }
                mostrarMsj();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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
                    itemPantalla.add(new SelectItem(pt.getSegpantallasPK().getCodpantalla(),
                            pt.getNompantalla()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
                    if (menuRecorrido.getCodmenupadre() != null) {
                        menuRecorrido = menuRecorrido.getCodmenupadre();
                    } else {
                        menuRecorrido = null;
                    }

                }

                itmPadre.add(new SelectItem(codPadre, mPadre));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void onNodeSelect(NodeSelectEvent event) {
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
        itemModulo.add(new SelectItem(menuSelect.getSegpantallas().getSegmodulo().getCodmod(),
                menuSelect.getSegpantallas().getSegmodulo().getNommodulo()));
        itemCodModulo = menuSelect.getSegpantallas().getSegmodulo().getCodmod();

        itemPantalla.add(new SelectItem(menuSelect.getSegpantallas().getSegpantallasPK().getCodpantalla(),
                menuSelect.getSegpantallas().getNompantalla()));
        itemCodPantalla = menuSelect.getSegpantallas().getSegpantallasPK().getCodpantalla();
    }

    public void crearMenuPadreSelect(Segmenu menuSelect) {
        int contador = 0;
        String mPadre = "Raiz";
        BigInteger codPadre = menuSelect.getCodmenupadre() != null ? menuSelect.getCodmenupadre().getCodmenu() : BigInteger.ZERO;
        Segmenu menuRecorrido = menuSelect;
        itemCodMenu = codPadre;
        while (menuRecorrido != null && menuRecorrido.getCodmenupadre() != null) {
            if (contador != 0) {
                mPadre = menuRecorrido.getCodmenupadre().getNommenu() + "->" + mPadre;
            } else {
                mPadre = menuRecorrido.getCodmenupadre().getNommenu();
            }
            contador++;
            menuRecorrido = menuRecorrido.getCodmenupadre();
        }

        itmPadre.add(new SelectItem(codPadre, mPadre));

    }

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

    public List<FacesMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FacesMessage> messages) {
        this.messages = messages;
    }

    public Map<String, Boolean> getExpandedNodes() {
        return expandedNodes;
    }

    public void setExpandedNodes(Map<String, Boolean> expandedNodes) {
        this.expandedNodes = expandedNodes;
    }

}
