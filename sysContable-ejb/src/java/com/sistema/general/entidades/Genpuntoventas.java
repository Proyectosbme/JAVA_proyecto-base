/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "genpuntoventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genpuntoventas.findAll", query = "SELECT g FROM Genpuntoventas g")
    , @NamedQuery(name = "Genpuntoventas.findByCorrpventa", query = "SELECT g FROM Genpuntoventas g WHERE g.corrpventa = :corrpventa")
    , @NamedQuery(name = "Genpuntoventas.findByNombre", query = "SELECT g FROM Genpuntoventas g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "Genpuntoventas.findByDirecprincipal", query = "SELECT g FROM Genpuntoventas g WHERE g.direcprincipal = :direcprincipal")
    , @NamedQuery(name = "Genpuntoventas.findByFechaapert", query = "SELECT g FROM Genpuntoventas g WHERE g.fechaapert = :fechaapert")
    , @NamedQuery(name = "Genpuntoventas.findByFechac", query = "SELECT g FROM Genpuntoventas g WHERE g.fechac = :fechac")})
public class Genpuntoventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORRPVENTA",nullable = false)
    private BigInteger corrpventa;
    @Column(name = "NOMBRE",length = 500)
    private String nombre;
    @Column(name = "DIRECPRINCIPAL",length = 500)
    private String direcprincipal;
    @Column(name = "FECHAAPERT")
    @Temporal(TemporalType.DATE)
    private Date fechaapert;
    @Column(name = "FECHAC")
    @Temporal(TemporalType.DATE)
    private Date fechac;
    @OneToMany(mappedBy = "puntoventa", fetch = FetchType.LAZY)
    private List<Genpersonas> lstPersonas;

    public Genpuntoventas() {
    }

    public Genpuntoventas(BigInteger corrpventa) {
        this.corrpventa = corrpventa;
    }

    public BigInteger getCorrpventa() {
        return corrpventa;
    }

    public void setCorrpventa(BigInteger corrpventa) {
        this.corrpventa = corrpventa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirecprincipal() {
        return direcprincipal;
    }

    public void setDirecprincipal(String direcprincipal) {
        this.direcprincipal = direcprincipal;
    }

    public Date getFechaapert() {
        return fechaapert;
    }

    public void setFechaapert(Date fechaapert) {
        this.fechaapert = fechaapert;
    }

    public Date getFechac() {
        return fechac;
    }

    public void setFechac(Date fechac) {
        this.fechac = fechac;
    }

    @XmlTransient
    public List<Genpersonas> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<Genpersonas> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrpventa != null ? corrpventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genpuntoventas)) {
            return false;
        }
        Genpuntoventas other = (Genpuntoventas) object;
        if ((this.corrpventa == null && other.corrpventa != null) || (this.corrpventa != null && !this.corrpventa.equals(other.corrpventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.general.entidades.Genpuntoventas[ corrpventa=" + corrpventa + " ]";
    }
    
}
