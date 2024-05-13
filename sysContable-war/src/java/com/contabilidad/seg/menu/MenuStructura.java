package com.contabilidad.seg.menu;


import java.io.Serializable;
import java.util.Objects;

public class MenuStructura implements Serializable, Comparable<MenuStructura> {
 
    private String name;     
    private String size;     
    private String type;    
    private String url;
     
    public MenuStructura(String name, String size, String type, String url) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.url = url;
    }
 
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
 
    //Eclipse Generated hashCode and equals

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
        return "Document{" + "name=" + name + ", size=" + size + ", type=" + type + ", url=" + url + '}';
    }
 
 
 
    public int compareTo(MenuStructura document) {
        return this.getName().compareTo(document.getName());
    }
}