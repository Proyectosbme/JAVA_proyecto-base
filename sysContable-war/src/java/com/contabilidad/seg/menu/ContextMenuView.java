package com.contabilidad.seg.menu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sistema.gen.ValidacionMensajes;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 * ContextMenuView es una clase que maneja la lógica del menú contextual en la
 * aplicación. Es un bean de sesión que mantiene el estado entre las diferentes
 * solicitudes HTTP durante una sesión de usuario.
 *
 * Está anotada como @Named para ser referenciada en las páginas JSF y
 *
 * @SessionScoped para mantener la sesión activa.
 *
 * Implementa Serializable para permitir que el estado del bean se serialice y
 * deserialice si es necesario.
 *
 * @autor BME_PERSONAL
 */
@Named(value = "contextMenuView")
@SessionScoped
public class ContextMenuView implements Serializable {

//<editor-fold defaultstate="collapsed" desc="DECLARACION DE VARIABLES">
    /**
     * Variable para manejo de mensajes
     */
    private final ValidacionMensajes validar = new ValidacionMensajes();
    /**
     * Lista de objetos Menu que representa el menú en la interfaz de usuario.
     */
    private List<Menu> lstMenu = new ArrayList<>();

    /**
     * Nodo seleccionado en el árbol de menú.
     */
    private TreeNode selectedNode;

    /**
     * Página actual que se está mostrando en la interfaz de usuario.
     */
    private String currentPage = "bienvenido.xhtml";

    /**
     * Servicio para manejar la lógica relacionada con el menú. Se inyecta
     * automáticamente mediante CDI.
     */
    @Inject
    MenuService menuService;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="METODOS IMPLEMENTADOS">
    /**
     * Método de inicialización que se ejecuta después de la construcción del
     * bean. Se utiliza para cargar el menú principal.
     */
    @PostConstruct
    public void init() {
        try {
            lstMenu = menuService.crearMenuPadre();
        } catch (Exception e) {
            validar.manejarExcepcion(e, "Error al cargar el menu");
        }
    }

    /**
     * Maneja el evento de selección de nodo en el árbol de menú.
     *
     * @param event Evento de selección de nodo.
     */
    public void onNodeSelect(NodeSelectEvent event) {
        try {
            TreeNode selectedNode = event.getTreeNode();

            if (selectedNode.getData() instanceof MenuStructura) {
                MenuStructura document = (MenuStructura) selectedNode.getData();
                String fileName = document.getType();
                if (fileName.equals("Pantalla")) {
                    this.currentPage = document.getUrl();
                }
            }
        } catch (Exception ex) {
            // Manejar la excepción (actualmente vacío)
            currentPage = "bienvenido.xhtml";
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GET AND ">
    
    public ValidacionMensajes getValidar() {
        return validar;
    }

    /**
     * Obtiene la lista de objetos Menu.
     *
     * @return La lista de menús.
     */
    public List<Menu> getLstMenu() {
        return lstMenu;
    }

    /**
     * Establece la lista de objetos Menu.
     *
     * @param lstMenu La lista de menús.
     */
    public void setLstMenu(List<Menu> lstMenu) {
        this.lstMenu = lstMenu;
    }

    /**
     * Obtiene el nodo seleccionado en el árbol de menú.
     *
     * @return El nodo seleccionado.
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * Establece el nodo seleccionado en el árbol de menú.
     *
     * @param selectedNode El nodo seleccionado.
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    /**
     * Obtiene la página actual que se está mostrando.
     *
     * @return La página actual.
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * Establece la página actual que se mostrará.
     *
     * @param currentPage La página a mostrar.
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
//</editor-fold>

}
