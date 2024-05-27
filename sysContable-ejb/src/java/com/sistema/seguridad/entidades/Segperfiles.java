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
import javax.persistence.ManyToMany;
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
@Table(name = "SEGPERFILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segperfiles.findAll", query = "SELECT s FROM Segperfiles s")
    , @NamedQuery(name = "Segperfiles.findByCodperfil", query = "SELECT s FROM Segperfiles s WHERE s.codperfil = :codperfil")
    , @NamedQuery(name = "Segperfiles.findByNombreperfil", query = "SELECT s FROM Segperfiles s WHERE s.nombreperfil = :nombreperfil")})
public class Segperfiles implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODPERFIL")
    private BigInteger codperfil;
    @Size(max = 100)
    @Column(name = "NOMBREPERFIL")
    private String nombreperfil;
    @ManyToMany(mappedBy = "lstPerfiles")
    private List<Segmenu> segmenuList;
    @OneToMany(mappedBy = "SegPerfiles")
    private List<Segusuarios> segusuariosList;

    public Segperfiles() {
    }

    public Segperfiles(BigInteger codperfil) {
        this.codperfil = codperfil;
    }

    public BigInteger getCodperfil() {
        return codperfil;
    }

    public void setCodperfil(BigInteger codperfil) {
        this.codperfil = codperfil;
    }

    public String getNombreperfil() {
        return nombreperfil;
    }

    public void setNombreperfil(String nombreperfil) {
        this.nombreperfil = nombreperfil;
    }

    @XmlTransient
    public List<Segmenu> getSegmenuList() {
        return segmenuList;
    }

    public void setSegmenuList(List<Segmenu> segmenuList) {
        this.segmenuList = segmenuList;
    }

    @XmlTransient
    public List<Segusuarios> getSegusuariosList() {
        return segusuariosList;
    }

    public void setSegusuariosList(List<Segusuarios> segusuariosList) {
        this.segusuariosList = segusuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codperfil != null ? codperfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segperfiles)) {
            return false;
        }
        Segperfiles other = (Segperfiles) object;
        if ((this.codperfil == null && other.codperfil != null) || (this.codperfil != null && !this.codperfil.equals(other.codperfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.Segperfiles[ codperfil=" + codperfil + " ]";
    }
    
}
