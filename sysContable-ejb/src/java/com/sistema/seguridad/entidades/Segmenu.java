/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "SEGMENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmenu.findAll", query = "SELECT s FROM Segmenu s")
    ,@NamedQuery(name = "Segmenu.findByPerfil", query = "SELECT m FROM Segmenu m JOIN m.lstPerfiles p WHERE p.codperfil = :codperfil")
    ,@NamedQuery(name = "Segmenu.findNotByPerfil", query = "SELECT m FROM Segmenu m WHERE :perfil NOT MEMBER OF m.lstPerfiles")
    , @NamedQuery(name = "Segmenu.findByCodmenu", query = "SELECT s FROM Segmenu s WHERE s.codmenu = :codmenu")
    , @NamedQuery(name = "Segmenu.findByNommenu", query = "SELECT s FROM Segmenu s WHERE s.nommenu = :nommenu")
    , @NamedQuery(name = "Segmenu.findByDscmenu", query = "SELECT s FROM Segmenu s WHERE s.dscmenu = :dscmenu")
    , @NamedQuery(name = "Segmenu.findByJerarquia", query = "SELECT s FROM Segmenu s WHERE s.jerarquia = :jerarquia")
    , @NamedQuery(name = "Segmenu.findByOrdenes", query = "SELECT s FROM Segmenu s WHERE s.ordenes = :ordenes")
    , @NamedQuery(name = "Segmenu.findByVersion", query = "SELECT s FROM Segmenu s WHERE s.version = :version")
    , @NamedQuery(name = "Segmenu.findByTipo", query = "SELECT s FROM Segmenu s WHERE s.tipo = :tipo")})
public class Segmenu implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODMENU",nullable = false)
    private BigInteger codmenu;
    @Column(name = "NOMMENU",length = 100)
    private String nommenu;
    @Column(name = "DSCMENU",length = 100)
    private String dscmenu;
    @Column(name = "JERARQUIA")
    private BigInteger jerarquia;
    @Column(name = "ORDENES")
    private BigInteger ordenes;
    @Column(name = "VERSION",length = 100)
    private String version;
    @Column(name = "TIPO",length = 100)
    private String tipo;
    @JoinTable(name = "SEGMENUXPERFIL", joinColumns = {
        @JoinColumn(name = "CODMENU", referencedColumnName = "CODMENU")}, inverseJoinColumns = {
        @JoinColumn(name = "CODPERFIL", referencedColumnName = "CODPERFIL")})
    @ManyToMany
    private List<Segperfiles> lstPerfiles;
    @OneToMany(mappedBy = "menuPadre")
    private List<Segmenu> lstMenuPadre;
    @JoinColumn(name = "CODMENUPADRE", referencedColumnName = "CODMENU")
    @ManyToOne
    private Segmenu menuPadre;
    @JoinColumns({
        @JoinColumn(name = "CODMOD", referencedColumnName = "CODMOD")
        , @JoinColumn(name = "CODPANT", referencedColumnName = "CODPANTALLA")})
    @ManyToOne
    private Segpantallas pantalla;

    public Segmenu() {
    }

    public Segmenu(BigInteger codmenu) {
        this.codmenu = codmenu;
    }

    public BigInteger getCodmenu() {
        return codmenu;
    }

    public void setCodmenu(BigInteger codmenu) {
        this.codmenu = codmenu;
    }

    public String getNommenu() {
        return nommenu;
    }

    public void setNommenu(String nommenu) {
        this.nommenu = nommenu;
    }

    public String getDscmenu() {
        return dscmenu;
    }

    public void setDscmenu(String dscmenu) {
        this.dscmenu = dscmenu;
    }

    public BigInteger getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(BigInteger jerarquia) {
        this.jerarquia = jerarquia;
    }

    public BigInteger getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(BigInteger ordenes) {
        this.ordenes = ordenes;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Segperfiles> getLstPerfiles() {
        return lstPerfiles;
    }

    public void setLstPerfiles(List<Segperfiles> segperfilesList) {
        this.lstPerfiles = segperfilesList;
    }

    @XmlTransient
    public List<Segmenu> getLstMenuPadre() {
        return lstMenuPadre;
    }

    public void setLstMenuPadre(List<Segmenu> segmenuList) {
        this.lstMenuPadre = segmenuList;
    }

    public Segmenu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Segmenu codmenupadre) {
        this.menuPadre = codmenupadre;
    }

    public Segpantallas getPantalla() {
        return pantalla;
    }

    public void setPantalla(Segpantallas pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codmenu != null ? codmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmenu)) {
            return false;
        }
        Segmenu other = (Segmenu) object;
        if ((this.codmenu == null && other.codmenu != null) || (this.codmenu != null && !this.codmenu.equals(other.codmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.Segmenu[ codmenu=" + codmenu + " ]";
    }

}
