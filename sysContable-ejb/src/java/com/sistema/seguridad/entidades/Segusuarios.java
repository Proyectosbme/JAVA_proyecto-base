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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "segusuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segusuarios.findAll", query = "SELECT s FROM Segusuarios s")
    , @NamedQuery(name = "Segusuarios.findByCoduser", query = "SELECT s FROM Segusuarios s WHERE s.coduser = :coduser")
    , @NamedQuery(name = "Segusuarios.findByClave", query = "SELECT s FROM Segusuarios s WHERE s.clave = :clave")
    , @NamedQuery(name = "Segusuarios.findByDuraclave", query = "SELECT s FROM Segusuarios s WHERE s.duraclave = :duraclave")
    , @NamedQuery(name = "Segusuarios.findByEstado", query = "SELECT s FROM Segusuarios s WHERE s.estado = :estado")
    , @NamedQuery(name = "Segusuarios.findByCodper", query = "SELECT s FROM Segusuarios s WHERE s.codper = :codper")
    , @NamedQuery(name = "Segusuarios.findByTipo", query = "SELECT s FROM Segusuarios s WHERE s.tipo = :tipo")})
public class Segusuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODUSER",nullable = false,length = 100)
    private String coduser;
    @Column(name = "CLAVE",length = 100)
    private String clave;
    @Column(name = "DURACLAVE")
    private BigInteger duraclave;
    @Column(name = "ESTADO")
    private BigInteger estado;
    @Column(name = "CODPER")
    private BigInteger codper;
    @Column(name = "TIPO")
    private Short tipo;
    @JoinColumn(name = "CODPERFIL", referencedColumnName = "CODPERFIL")
    @ManyToOne
    private Segperfiles SegPerfiles;

    public Segusuarios() {
    }

    public Segusuarios(String coduser) {
        this.coduser = coduser;
    }

    public String getCoduser() {
        return coduser;
    }

    public void setCoduser(String coduser) {
        this.coduser = coduser;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public BigInteger getDuraclave() {
        return duraclave;
    }

    public void setDuraclave(BigInteger duraclave) {
        this.duraclave = duraclave;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public BigInteger getCodper() {
        return codper;
    }

    public void setCodper(BigInteger codper) {
        this.codper = codper;
    }

    public Short getTipo() {
        return tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    public Segperfiles getSegPerfiles() {
        return SegPerfiles;
    }

    public void setSegPerfiles(Segperfiles SegPerfiles) {
        this.SegPerfiles = SegPerfiles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coduser != null ? coduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segusuarios)) {
            return false;
        }
        Segusuarios other = (Segusuarios) object;
        if ((this.coduser == null && other.coduser != null) || (this.coduser != null && !this.coduser.equals(other.coduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.comtable.seguridad.entidades.Segusuarios[ coduser=" + coduser + " ]";
    }
    
}
