/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author BME_PERSONAL
 */
@Embeddable
public class SegpantallasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODMOD",nullable = false)
    private BigInteger codmod;
    @Basic(optional = false)
    @Column(name = "CODPANTALLA",nullable = false)
    private BigInteger codpantalla;

    public SegpantallasPK() {
    }

    public SegpantallasPK(BigInteger codmod, BigInteger codpantalla) {
        this.codmod = codmod;
        this.codpantalla = codpantalla;
    }

    public BigInteger getCodmod() {
        return codmod;
    }

    public void setCodmod(BigInteger codmod) {
        this.codmod = codmod;
    }

    public BigInteger getCodpantalla() {
        return codpantalla;
    }

    public void setCodpantalla(BigInteger codpantalla) {
        this.codpantalla = codpantalla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codmod != null ? codmod.hashCode() : 0);
        hash += (codpantalla != null ? codpantalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegpantallasPK)) {
            return false;
        }
        SegpantallasPK other = (SegpantallasPK) object;
        if ((this.codmod == null && other.codmod != null) || (this.codmod != null && !this.codmod.equals(other.codmod))) {
            return false;
        }
        if ((this.codpantalla == null && other.codpantalla != null) || (this.codpantalla != null && !this.codpantalla.equals(other.codpantalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.SegpantallasPK[ codmod=" + codmod + ", codpantalla=" + codpantalla + " ]";
    }
    
}
