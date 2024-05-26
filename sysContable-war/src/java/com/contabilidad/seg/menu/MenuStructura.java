package com.contabilidad.seg.menu;

import com.sistema.contable.seguridad.entidades.Segmenu;
import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa la estructura de un menú.
 */
public class MenuStructura implements Serializable, Comparable<MenuStructura> {
 
    // Atributos de la estructura del menú
    private String name;     // Nombre del menú
    private String size;     // Tamaño del menú
    private String type;     // Tipo del menú
    private String url;      // URL del menú
    private Segmenu segMenu; // Menú de seguridad asociado (entidad JPA)
     
    /**
     * Constructor de la clase MenuStructura.
     * @param name Nombre del menú.
     * @param size Tamaño del menú.
     * @param type Tipo del menú.
     * @param url URL del menú.
     * @param segMenu Menú de seguridad asociado (entidad JPA).
     */
    public MenuStructura(String name, String size, String type, String url, Segmenu segMenu) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.url = url;
        this.segMenu = segMenu;
    }
 
    // Métodos getter y setter para los atributos
    
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSize() {
        return size;
    }
 
    public void setSize(String size) {
        this.size = size;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Segmenu getSegMenu() {
        return segMenu;
    }

    public void setSegMenu(Segmenu segMenu) {
        this.segMenu = segMenu;
    }
    
    // Eclipse Generated hashCode, equals, and toString methods

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.size);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.url);
        return hash;
    }

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
        final MenuStructura other = (MenuStructura) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.size, other.size)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MenuStructura{" + "name=" + name + ", size=" + size + ", type=" + type + ", url=" + url + '}';
    }
 
    /**
     * Método para comparar dos objetos MenuStructura basados en el nombre del menú.
     * @param document El objeto MenuStructura a comparar.
     * @return Un valor entero que indica el resultado de la comparación.
     */
    public int compareTo(MenuStructura document) {
        return this.getName().compareTo(document.getName());
    }
}
