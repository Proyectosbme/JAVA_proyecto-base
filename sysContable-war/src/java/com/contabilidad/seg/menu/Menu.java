package com.contabilidad.seg.menu;


import java.io.Serializable;
import java.util.Objects;
import org.primefaces.model.TreeNode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BME_PERSONAL
 */
public class Menu implements Serializable{

    private String nombre;
    private TreeNode tree;
    private String url;
  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TreeNode getTree() {
        return tree;
    }

    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.tree);
        hash = 23 * hash + Objects.hashCode(this.url);
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
