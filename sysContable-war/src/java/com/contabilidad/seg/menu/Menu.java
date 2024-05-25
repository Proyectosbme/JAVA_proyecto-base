package com.contabilidad.seg.menu;

import java.io.Serializable;
import java.util.Objects;
import org.primefaces.model.TreeNode;

/**
 * La clase Menu representa un elemento de menú en la aplicación.
 * Implementa Serializable para permitir la serialización y deserialización 
 * de sus instancias, facilitando su almacenamiento y transmisión.
 * 
 * @autor BME_PERSONAL
 */
public class Menu implements Serializable {

    // Nombre del menú
    private String nombre;
    
    // Nodo del árbol que representa la estructura del menú
    private TreeNode tree;
    
    // URL asociada al menú
    private String url;

    /**
     * Obtiene el nombre del menú.
     * 
     * @return El nombre del menú.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del menú.
     * 
     * @param nombre El nombre del menú.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nodo del árbol del menú.
     * 
     * @return El nodo del árbol del menú.
     */
    public TreeNode getTree() {
        return tree;
    }

    /**
     * Establece el nodo del árbol del menú.
     * 
     * @param tree El nodo del árbol del menú.
     */
    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    /**
     * Obtiene la URL asociada al menú.
     * 
     * @return La URL asociada al menú.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL asociada al menú.
     * 
     * @param url La URL asociada al menú.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Calcula el código hash para la instancia del menú.
     * 
     * @return El código hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.tree);
        hash = 23 * hash + Objects.hashCode(this.url);
        return hash;
    }

    /**
     * Compara este menú con otro objeto para determinar si son iguales.
     * 
     * @param obj El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Menu other = (Menu) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.tree, other.tree)) {
            return false;
        }
        return true;
    }
}
