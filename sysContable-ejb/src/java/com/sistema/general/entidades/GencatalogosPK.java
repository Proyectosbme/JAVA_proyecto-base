/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

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
public class GencatalogosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CODCATALOGO")
    private BigInteger codcatalogo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODMODULO")
    private BigInteger codmodulo;

    public GencatalogosPK() {
    }

    public GencatalogosPK(BigInteger codcatalogo, BigInteger codmodulo) {
        this.codcatalogo = codcatalogo;
        this.codmodulo = codmodulo;
    }

    public BigInteger getCodcatalogo() {
        return codcatalogo;
    }

    public void setCodcatalogo(BigInteger codcatalogo) {
        this.codcatalogo = codcatalogo;
    }

    public BigInteger getCodmodulo() {
        return codmodulo;
    }

    public void setCodmodulo(BigInteger codmodulo) {
        this.codmodulo = codmodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcatalogo != null ? codcatalogo.hashCode() : 0);
        hash += (codmodulo != null ? codmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GencatalogosPK)) {
            return false;
        }
        GencatalogosPK other = (GencatalogosPK) object;
        if ((this.codcatalogo == null && other.codcatalogo != null) || (this.codcatalogo != null && !this.codcatalogo.equals(other.codcatalogo))) {
            return false;
        }
        if ((this.codmodulo == null && other.codmodulo != null) || (this.codmodulo != null && !this.codmodulo.equals(other.codmodulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.contable.general.entidades.GencatalogosPK[ codcatalogo=" + codcatalogo + ", codmodulo=" + codmodulo + " ]";
    }
    
}
