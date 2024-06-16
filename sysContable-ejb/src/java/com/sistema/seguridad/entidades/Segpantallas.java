/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "segpantallas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segpantallas.findAll", query = "SELECT s FROM Segpantallas s")
    , @NamedQuery(name = "Segpantallas.findByCodmod", query = "SELECT s FROM Segpantallas s WHERE s.pantallasPK.codmod = :codmod")
    , @NamedQuery(name = "Segpantallas.findByCodpantalla", query = "SELECT s FROM Segpantallas s WHERE s.pantallasPK.codpantalla = :codpantalla")
    , @NamedQuery(name = "Segpantallas.findByNompantalla", query = "SELECT s FROM Segpantallas s WHERE s.nompantalla = :nompantalla")
    , @NamedQuery(name = "Segpantallas.findByUrlpantalla", query = "SELECT s FROM Segpantallas s WHERE s.urlpantalla = :urlpantalla")})
public class Segpantallas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SegpantallasPK pantallasPK;
    @Column(name = "NOMPANTALLA",length = 100)
    private String nompantalla;
    @Column(name = "URLPANTALLA",length = 100)
    private String urlpantalla;
    @OneToMany(mappedBy = "pantalla")
    private List<Segmenu> segmenuList;
    @JoinColumn(name = "CODMOD", referencedColumnName = "CODMOD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Segmodulo segmodulo;

    public Segpantallas() {
    }

    public Segpantallas(SegpantallasPK segpantallasPK) {
        this.pantallasPK = segpantallasPK;
    }

    public Segpantallas(BigInteger codmod, BigInteger codpantalla) {
        this.pantallasPK = new SegpantallasPK(codmod, codpantalla);
    }

    public SegpantallasPK getPantallasPK() {
        return pantallasPK;
    }

    public void setPantallasPK(SegpantallasPK pantallasPK) {
        this.pantallasPK = pantallasPK;
    }

    public String getNompantalla() {
        return nompantalla;
    }

    public void setNompantalla(String nompantalla) {
        this.nompantalla = nompantalla;
    }

    public String getUrlpantalla() {
        return urlpantalla;
    }

    public void setUrlpantalla(String urlpantalla) {
        this.urlpantalla = urlpantalla;
    }

    @XmlTransient
    public List<Segmenu> getSegmenuList() {
        return segmenuList;
    }

    public void setSegmenuList(List<Segmenu> segmenuList) {
        this.segmenuList = segmenuList;
    }

    public Segmodulo getSegmodulo() {
        return segmodulo;
    }

    public void setSegmodulo(Segmodulo segmodulo) {
        this.segmodulo = segmodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pantallasPK != null ? pantallasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segpantallas)) {
            return false;
        }
        Segpantallas other = (Segpantallas) object;
        if ((this.pantallasPK == null && other.pantallasPK != null) || (this.pantallasPK != null && !this.pantallasPK.equals(other.pantallasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.Segpantallas[ segpantallasPK=" + pantallasPK + " ]";
    }
    
}
