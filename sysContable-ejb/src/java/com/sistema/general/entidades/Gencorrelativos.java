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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "gencorrelativos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gencorrelativos.findAll", query = "SELECT g FROM Gencorrelativos g")
    , @NamedQuery(name = "Gencorrelativos.findByNombre", query = "SELECT g FROM Gencorrelativos g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "Gencorrelativos.findByNuminic", query = "SELECT g FROM Gencorrelativos g WHERE g.numinic = :numinic")
    , @NamedQuery(name = "Gencorrelativos.findByNumactual", query = "SELECT g FROM Gencorrelativos g WHERE g.numactual = :numactual")
    , @NamedQuery(name = "Gencorrelativos.findByMultiplo", query = "SELECT g FROM Gencorrelativos g WHERE g.multiplo = :multiplo")
    , @NamedQuery(name = "Gencorrelativos.findByNumfinal", query = "SELECT g FROM Gencorrelativos g WHERE g.numfinal = :numfinal")})
public class Gencorrelativos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE",nullable = false)
    private String nombre;
    @Column(name = "NUMINIC")
    private BigInteger numinic;
    @Column(name = "NUMACTUAL")
    private BigInteger numactual;
    @Column(name = "MULTIPLO")
    private BigInteger multiplo;
    @Column(name = "NUMFINAL")
    private BigInteger numfinal;

    public Gencorrelativos() {
    }

    public Gencorrelativos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getNuminic() {
        return numinic;
    }

    public void setNuminic(BigInteger numinic) {
        this.numinic = numinic;
    }

    public BigInteger getNumactual() {
        return numactual;
    }

    public void setNumactual(BigInteger numactual) {
        this.numactual = numactual;
    }

    public BigInteger getMultiplo() {
        return multiplo;
    }

    public void setMultiplo(BigInteger multiplo) {
        this.multiplo = multiplo;
    }

    public BigInteger getNumfinal() {
        return numfinal;
    }

    public void setNumfinal(BigInteger numfinal) {
        this.numfinal = numfinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gencorrelativos)) {
            return false;
        }
        Gencorrelativos other = (Gencorrelativos) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.contable.general.entidades.Gencorrelativos[ nombre=" + nombre + " ]";
    }
    
}
