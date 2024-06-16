/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "gencatdeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gencatdeta.findAll", query = "SELECT g FROM Gencatdeta g")
    , @NamedQuery(name = "Gencatdeta.findByCodmod", query = "SELECT g FROM Gencatdeta g WHERE g.gencatdetaPK.codmod = :codmod")
    , @NamedQuery(name = "Gencatdeta.findByCodcat", query = "SELECT g FROM Gencatdeta g WHERE g.gencatdetaPK.codcat = :codcat")
    , @NamedQuery(name = "Gencatdeta.findByCodcor", query = "SELECT g FROM Gencatdeta g WHERE g.gencatdetaPK.codcor = :codcor")
    , @NamedQuery(name = "Gencatdeta.findByDscripc", query = "SELECT g FROM Gencatdeta g WHERE g.dscripc = :dscripc")
    , @NamedQuery(name = "Gencatdeta.findByValor", query = "SELECT g FROM Gencatdeta g WHERE g.valor = :valor")})
public class Gencatdeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GencatdetaPK gencatdetaPK;
    @Column(name = "DSCRIPC" ,length = 300)
    private String dscripc;
    @Column(name = "VALOR" , length = 100)
    private String valor;
    @JoinColumns({
        @JoinColumn(name = "CODCAT", referencedColumnName = "CODCATALOGO", insertable = false, updatable = false)
        , @JoinColumn(name = "CODMOD", referencedColumnName = "CODMODULO", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gencatalogos catalogo;

    public Gencatdeta() {
    }

    public Gencatdeta(GencatdetaPK gencatdetaPK) {
        this.gencatdetaPK = gencatdetaPK;
    }

    public Gencatdeta(BigInteger codmod, BigInteger codcat, BigInteger codcor) {
        this.gencatdetaPK = new GencatdetaPK(codmod, codcat, codcor);
    }

    public GencatdetaPK getGencatdetaPK() {
        return gencatdetaPK;
    }

    public void setGencatdetaPK(GencatdetaPK gencatdetaPK) {
        this.gencatdetaPK = gencatdetaPK;
    }

    public String getDscripc() {
        return dscripc;
    }

    public void setDscripc(String dscripc) {
        this.dscripc = dscripc;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Gencatalogos getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Gencatalogos catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gencatdetaPK != null ? gencatdetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gencatdeta)) {
            return false;
        }
        Gencatdeta other = (Gencatdeta) object;
        if ((this.gencatdetaPK == null && other.gencatdetaPK != null) || (this.gencatdetaPK != null && !this.gencatdetaPK.equals(other.gencatdetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.general.entidades.Gencatdeta[ gencatdetaPK=" + gencatdetaPK + " ]";
    }
    
}
