/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author BME_PERSONAL
 */
@Embeddable
public class GencatdetaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODMOD",nullable = false)
    private BigInteger codmod;
    @Basic(optional = false)
    @Column(name = "CODCAT",nullable = false)
    private BigInteger codcat;
    @Basic(optional = false)
    @Column(name = "CODCOR", nullable = false)
    private BigInteger codcor;

    public GencatdetaPK() {
    }

    public GencatdetaPK(BigInteger codmod, BigInteger codcat, BigInteger codcor) {
        this.codmod = codmod;
        this.codcat = codcat;
        this.codcor = codcor;
    }

    public BigInteger getCodmod() {
        return codmod;
    }

    public void setCodmod(BigInteger codmod) {
        this.codmod = codmod;
    }

    public BigInteger getCodcat() {
        return codcat;
    }

    public void setCodcat(BigInteger codcat) {
        this.codcat = codcat;
    }

    public BigInteger getCodcor() {
        return codcor;
    }

    public void setCodcor(BigInteger codcor) {
        this.codcor = codcor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.codmod);
        hash = 29 * hash + Objects.hashCode(this.codcat);
        hash = 29 * hash + Objects.hashCode(this.codcor);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GencatdetaPK)) {
            return false;
        }
        GencatdetaPK other = (GencatdetaPK) object;
        if (this.codmod != other.codmod) {
            return false;
        }
        if (this.codcat != other.codcat) {
            return false;
        }
        if (this.codcor != other.codcor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.general.entidades.GencatdetaPK[ codmod=" + codmod + ", codcat=" + codcat + ", codcor=" + codcor + " ]";
    }
    
}
