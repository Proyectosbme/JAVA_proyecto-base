/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

import com.sistema.seguridad.entidades.Segmodulo;
import java.io.Serializable;
import java.math.BigInteger;
///555
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "GENCATALOGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gencatalogos.findAll", query = "SELECT g FROM Gencatalogos g")
    , @NamedQuery(name = "Gencatalogos.findByCodcatalogo", query = "SELECT g FROM Gencatalogos g WHERE g.gencatalogosPK.codcatalogo = :codcatalogo")
    , @NamedQuery(name = "Gencatalogos.findByCodmodulo", query = "SELECT g FROM Gencatalogos g WHERE g.gencatalogosPK.codmodulo = :codmodulo")
    , @NamedQuery(name = "Gencatalogos.findByDescripcion", query = "SELECT g FROM Gencatalogos g WHERE g.descripcion = :descripcion")
    , @NamedQuery(name = "Gencatalogos.findByNombre", query = "SELECT g FROM Gencatalogos g WHERE g.nombre = :nombre")})
public class Gencatalogos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GencatalogosPK gencatalogosPK;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "CODMODULO", referencedColumnName = "CODMOD", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Segmodulo segmodulo;

    public Gencatalogos() {
    }

    public Gencatalogos(GencatalogosPK gencatalogosPK) {
        this.gencatalogosPK = gencatalogosPK;
    }

    public Gencatalogos(BigInteger codcatalogo, BigInteger codmodulo) {
        this.gencatalogosPK = new GencatalogosPK(codcatalogo, codmodulo);
    }

    public GencatalogosPK getGencatalogosPK() {
        return gencatalogosPK;
    }

    public void setGencatalogosPK(GencatalogosPK gencatalogosPK) {
        this.gencatalogosPK = gencatalogosPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (gencatalogosPK != null ? gencatalogosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gencatalogos)) {
            return false;
        }
        Gencatalogos other = (Gencatalogos) object;
        if ((this.gencatalogosPK == null && other.gencatalogosPK != null) || (this.gencatalogosPK != null && !this.gencatalogosPK.equals(other.gencatalogosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.contable.general.entidades.Gencatalogos[ gencatalogosPK=" + gencatalogosPK + " ]";
    }
    
}
