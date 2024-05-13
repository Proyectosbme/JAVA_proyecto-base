package com.contabilidad.seg.menu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "contextMenuView")
@SessionScoped
public class ContextMenuView implements Serializable {

    private List<Menu> lstMenu = new ArrayList();
    private TreeNode selectedNode;
    private String currentPage = "bienvenido.xhtml";

    @Inject
    MenuService menuService;

    @PostConstruct
    public void init() {
        try {
            lstMenu = menuService.crearMenuPadre();
            //   this.crearMenuPadre();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();

        if (selectedNode.getData() instanceof MenuStructura) {
            MenuStructura document = (MenuStructura) selectedNode.getData();
            String fileName = document.getType();
            if (fileName.equals(new String("Pantalla"))) {
                this.currentPage = document.getUrl();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", "File Name: " + fileName);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        }
    }

    public List<Menu> getLstMenu() {
        return lstMenu;
    }

    public void setLstMenu(List<Menu> lstMenu) {
        this.lstMenu = lstMenu;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
