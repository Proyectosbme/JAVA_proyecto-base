package com.sistema.seg.menu;

import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segusuarios;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.sistema.seguridad.negocio.SegBusquedaLocal;

/**
 * MenuService es una clase que proporciona servicios relacionados con el menú
 * de la aplicación. Está anotada como @Named para ser referenciada en las
 * páginas JSF y @SessionScoped para mantener el estado durante la sesión del
 * usuario. Implementa Serializable para permitir la serialización y
 * deserialización.
 *
 * @autor BME_PERSONAL
 */
@Named(value = "menuService")
@SessionScoped
public class MenuService implements Serializable {

    // Inyección del EJB que maneja las búsquedas relacionadas con el menú.
    @EJB
    private SegBusquedaLocal segmenuFacade;

    // Lista de objetos Segmenu que representa los menús.
    private List<Segmenu> lstSegmenu = new ArrayList<>();
    private List<Segmenu> lstSegmenuSub = new ArrayList<>();

    // Nodo raíz del árbol de menús.
    TreeNode root;

    // Lista de objetos Menu que representan la estructura del menú.
    private List<Menu> lstMenu = new ArrayList<>();

    /**
     * Crea el menú principal basado en el perfil del usuario.
     *
     * @return Lista de menús creados.
     */
    public List<Menu> crearMenuPadre() throws NullPointerException, Exception {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Segusuarios usuario = new Segusuarios();

            if (session != null) {
                usuario = (Segusuarios) session.getAttribute("usuario");

                if (usuario != null && usuario.getSegPerfiles().getCodperfil() != null) {

                    lstSegmenu = segmenuFacade.buscarMenuXPerfil(usuario.getSegPerfiles().getCodperfil());
                    if (lstSegmenu != null && !lstSegmenu.isEmpty()) {
                        for (Segmenu m : lstSegmenu) {
                            root = new DefaultTreeNode(new MenuStructura("Menus", "-", "Modulos", "-", m), null);
                            Menu m1 = new Menu();
                            if (m.getMenuPadre() == null) {
                                m1.setNombre(m.getNommenu());
                                crearSubMenu(root, usuario.getSegPerfiles().getCodperfil(), m);
                            }
                            m1.setTree(root);
                            lstMenu.add(m1);
                        }
                    }

                }
            }
        } catch (NullPointerException ne) {
            throw ne;
        } catch (Exception ex) {
            throw ex;
        }
        return lstMenu;
    }

    /**
     * Crea submenús para un nodo dado y los agrega al árbol de menús.
     *
     * @param node Nodo padre en el árbol de menús.
     * @param codPerfil Código del perfil del usuario.
     * @param menu Objeto Segmenu que representa el menú actual.
     */
    public void crearSubMenu(TreeNode node, BigInteger codPerfil, Segmenu menu)
            throws NullPointerException, Exception {
        try {
            lstSegmenuSub = segmenuFacade.busMenuXPerfilXCodPadre(codPerfil, menu.getCodmenu());

            if (lstSegmenuSub != null && !lstSegmenuSub.isEmpty()) {
                for (Segmenu sm : lstSegmenuSub) {
                    if (sm.getPantalla() != null
                            && sm.getPantalla().getPantallasPK().getCodpantalla().compareTo(BigInteger.ZERO) == 0) {
                        TreeNode menusp = new DefaultTreeNode(new MenuStructura(sm.getNommenu(), "-", "Submenu", "-", sm), node);
                        this.crearSubMenu(menusp, codPerfil, sm);
                    } else {
                        TreeNode pantall = new DefaultTreeNode("pantalla", new MenuStructura(sm.getNommenu(), "-", "Pantalla", sm.getPantalla().getUrlpantalla(), sm), node);
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
     * Obtiene la lista de menús.
     *
     * @return La lista de menús.
     */
    public List<Menu> getLstMenu() {
        return lstMenu;
    }

    /**
     * Establece la lista de menús.
     *
     * @param lstMenu La lista de menús a establecer.
     */
    public void setLstMenu(List<Menu> lstMenu) {
        this.lstMenu = lstMenu;
    }
}
