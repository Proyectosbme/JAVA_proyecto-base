/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.entidades;

import com.sistema.general.entidades.Gencatalogos;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "SEGMODULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmodulo.findAll", query = "SELECT s FROM Segmodulo s")
    , @NamedQuery(name = "Segmodulo.findByCodmod", query = "SELECT s FROM Segmodulo s WHERE s.codmod = :codmod")
    , @NamedQuery(name = "Segmodulo.findByNommodulo", query = "SELECT s FROM Segmodulo s WHERE s.nommodulo = :nommodulo")
    , @NamedQuery(name = "Segmodulo.findByUrldirecc", query = "SELECT s FROM Segmodulo s WHERE s.urldirecc = :urldirecc")})
public class Segmodulo implements Serializable {

    @Size(max = 100)
    @Column(name = "NOMMODULO")
    private String nommodulo;
    @Size(max = 100)
    @Column(name = "URLDIRECC")
    private String urldirecc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segmodulo", fetch = FetchType.LAZY)
    private List<Gencatalogos> gencatalogosList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODMOD")
    private BigInteger codmod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segmodulo")
    private List<Segpantallas> segpantallasList;

    public Segmodulo() {
    }

    public Segmodulo(BigInteger codmod) {
        this.codmod = codmod;
    }

    public BigInteger getCodmod() {
        return codmod;
    }

    public void setCodmod(BigInteger codmod) {
        this.codmod = codmod;
    }


    @XmlTransient
    public List<Segpantallas> getSegpantallasList() {
        return segpantallasList;
    }

    public void setSegpantallasList(List<Segpantallas> segpantallasList) {
        this.segpantallasList = segpantallasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codmod != null ? codmod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmodulo)) {
            return false;
        }
        Segmodulo other = (Segmodulo) object;
        if ((this.codmod == null && other.codmod != null) || (this.codmod != null && !this.codmod.equals(other.codmod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.Segmodulo[ codmod=" + codmod + " ]";
    }

    public String getNommodulo() {
        return nommodulo;
    }

    public void setNommodulo(String nommodulo) {
        this.nommodulo = nommodulo;
    }

    public String getUrldirecc() {
        return urldirecc;
    }

    public void setUrldirecc(String urldirecc) {
        this.urldirecc = urldirecc;
    }

    @XmlTransient
    public List<Gencatalogos> getGencatalogosList() {
        return gencatalogosList;
    }

    public void setGencatalogosList(List<Gencatalogos> gencatalogosList) {
        this.gencatalogosList = gencatalogosList;
    }
    
}
