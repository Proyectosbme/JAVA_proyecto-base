package com.contabilidad.seg.menu;

import com.sistema.contable.seguridad.entidades.Segmenu;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import com.sistema.contable.seguridad.busquedas.SegmenuBusquedaLocal;
import com.sistema.contable.seguridad.entidades.Segusuarios;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BME_PERSONAL
 */
@Named(value = "menuService")
@SessionScoped
public class MenuService implements Serializable {

    @EJB
    private SegmenuBusquedaLocal segmenuFacade;
    private List<Segmenu> lstSegmenu = new ArrayList();
    private List<Segmenu> lstSegmenuSub = new ArrayList();
    TreeNode root;
    private List<Menu> lstMenu = new ArrayList();

    public List<Menu> crearMenuPadre() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Segusuarios usuario = new Segusuarios();
        if (session != null) {
            usuario = (Segusuarios) session.getAttribute("usuario");

            lstSegmenu = segmenuFacade.buscarMenu(usuario.getSegPerfiles().getCodperfil());
            // lstSegmenu = segmenuFacade.buscarSubMenu(new BigInteger("10"), new BigInteger("10"));
            for (Segmenu m : lstSegmenu) {
                root = new DefaultTreeNode(new MenuStructura("Menus", "-", "Modulos", "-"), null);
                Menu m1 = new Menu();
                if (m.getCodmenupadre() == null) {
                    m1.setNombre(m.getNommenu());
                    crearSubMenu(root, usuario.getSegPerfiles().getCodperfil(), m);
                }
                m1.setTree(root);
                lstMenu.add(m1);

            }
        }
        return lstMenu;
    }

    public void crearSubMenu(TreeNode node, BigInteger codPerfil, Segmenu menu) {
        lstSegmenuSub = segmenuFacade.buscarSubMenu(codPerfil, menu.getCodmenu());
        if (!lstSegmenuSub.isEmpty()) {
            for (Segmenu sm : lstSegmenuSub) {
                if (sm.getSegpantallas() == null) {
                    TreeNode menusp = new DefaultTreeNode(new MenuStructura(sm.getNommenu(), "-", "Submenu", "-"), node);
                    this.crearSubMenu(menusp, codPerfil, sm);
                } else {
                    //     TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
                    TreeNode pantall = new DefaultTreeNode("pantalla", new MenuStructura(sm.getSegpantallas().getNompantalla(), "-", "Pantalla",
                            sm.getSegpantallas().getUrlpantalla()), node);
                }
            }
        }
    }

    public List<Menu> getLstMenu() {
        return lstMenu;
    }

    public void setLstMenu(List<Menu> lstMenu) {
        this.lstMenu = lstMenu;
    }

}
