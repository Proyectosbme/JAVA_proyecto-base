/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BME_PERSONAL
 */
@Entity
@Table(name = "genpersonas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genpersonas.findAll", query = "SELECT g FROM Genpersonas g")
    , @NamedQuery(name = "Genpersonas.findByCorrper", query = "SELECT g FROM Genpersonas g WHERE g.corrper = :corrper")
    , @NamedQuery(name = "Genpersonas.findByPrinombre", query = "SELECT g FROM Genpersonas g WHERE g.prinombre = :prinombre")
    , @NamedQuery(name = "Genpersonas.findBySegnombre", query = "SELECT g FROM Genpersonas g WHERE g.segnombre = :segnombre")
    , @NamedQuery(name = "Genpersonas.findByPriapellido", query = "SELECT g FROM Genpersonas g WHERE g.priapellido = :priapellido")
    , @NamedQuery(name = "Genpersonas.findBySegapellido", query = "SELECT g FROM Genpersonas g WHERE g.segapellido = :segapellido")
    , @NamedQuery(name = "Genpersonas.findByApecasada", query = "SELECT g FROM Genpersonas g WHERE g.apecasada = :apecasada")
    , @NamedQuery(name = "Genpersonas.findByDui", query = "SELECT g FROM Genpersonas g WHERE g.dui = :dui")
    , @NamedQuery(name = "Genpersonas.findByFechac", query = "SELECT g FROM Genpersonas g WHERE g.fechac = :fechac")
    , @NamedQuery(name = "Genpersonas.findByFecham", query = "SELECT g FROM Genpersonas g WHERE g.fecham = :fecham")
    , @NamedQuery(name = "Genpersonas.findByUserc", query = "SELECT g FROM Genpersonas g WHERE g.userc = :userc")
    , @NamedQuery(name = "Genpersonas.findByUserm", query = "SELECT g FROM Genpersonas g WHERE g.userm = :userm")
    , @NamedQuery(name = "Genpersonas.findByNomcom", query = "SELECT g FROM Genpersonas g WHERE g.nomcom = :nomcom")
    , @NamedQuery(name = "Genpersonas.findByCodtipoper", query = "SELECT g FROM Genpersonas g WHERE g.codtipoper = :codtipoper")
    , @NamedQuery(name = "Genpersonas.findByCattipoper", query = "SELECT g FROM Genpersonas g WHERE g.cattipoper = :cattipoper")
    , @NamedQuery(name = "Genpersonas.findByCortipoper", query = "SELECT g FROM Genpersonas g WHERE g.cortipoper = :cortipoper")
    , @NamedQuery(name = "Genpersonas.findByRezsocial", query = "SELECT g FROM Genpersonas g WHERE g.rezsocial = :rezsocial")
    , @NamedQuery(name = "Genpersonas.findByNomcomercial", query = "SELECT g FROM Genpersonas g WHERE g.nomcomercial = :nomcomercial")})
public class Genpersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORRPER",nullable = false)
    private BigInteger corrper;
    @Column(name = "PRINOMBRE",length = 100)
    private String prinombre;
    @Column(name = "SEGNOMBRE",length = 100)
    private String segnombre;
    @Column(name = "PRIAPELLIDO",length = 100)
    private String priapellido;
    @Column(name = "SEGAPELLIDO",length = 100)
    private String segapellido;
    @Column(name = "APECASADA",length = 100)
    private String apecasada;
    @Column(name = "DUI",length = 25)
    private String dui;
    @Column(name = "FECHAC")
    @Temporal(TemporalType.DATE)
    private Date fechac;
    @Column(name = "FECHAM")
    @Temporal(TemporalType.DATE)
    private Date fecham;
    @Column(name = "USERC",length = 100)
    private String userc;
    @Column(name = "USERM",length = 100)
    private String userm;
    @Column(name = "NOMCOM",length = 500)
    private String nomcom;
    @Column(name = "CODTIPOPER")
    private BigInteger codtipoper;
    @Column(name = "CATTIPOPER")
    private BigInteger cattipoper;
    @Column(name = "CORTIPOPER")
    private BigInteger cortipoper;
    @Column(name = "REZSOCIAL",length = 500)
    private String rezsocial;
    @Column(name = "NOMCOMERCIAL",length = 500)
    private String nomcomercial;
    @JoinColumn(name = "PUNTOVENTA", referencedColumnName = "CORRPVENTA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Genpuntoventas puntoventa;

    public Genpersonas() {
    }

    public Genpersonas(BigInteger corrper) {
        this.corrper = corrper;
    }

    public BigInteger getCorrper() {
        return corrper;
    }

    public void setCorrper(BigInteger corrper) {
        this.corrper = corrper;
    }

    public String getPrinombre() {
        return prinombre;
    }

    public void setPrinombre(String prinombre) {
        this.prinombre = prinombre;
    }

    public String getSegnombre() {
        return segnombre;
    }

    public void setSegnombre(String segnombre) {
        this.segnombre = segnombre;
    }

    public String getPriapellido() {
        return priapellido;
    }

    public void setPriapellido(String priapellido) {
        this.priapellido = priapellido;
    }

    public String getSegapellido() {
        return segapellido;
    }

    public void setSegapellido(String segapellido) {
        this.segapellido = segapellido;
    }

    public String getApecasada() {
        return apecasada;
    }

    public void setApecasada(String apecasada) {
        this.apecasada = apecasada;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Date getFechac() {
        return fechac;
    }

    public void setFechac(Date fechac) {
        this.fechac = fechac;
    }

    public Date getFecham() {
        return fecham;
    }

    public void setFecham(Date fecham) {
        this.fecham = fecham;
    }

    public String getUserc() {
        return userc;
    }

    public void setUserc(String userc) {
        this.userc = userc;
    }

    public String getUserm() {
        return userm;
    }

    public void setUserm(String userm) {
        this.userm = userm;
    }

    public String getNomcom() {
        return nomcom;
    }

    public void setNomcom(String nomcom) {
        this.nomcom = nomcom;
    }

    public BigInteger getCodtipoper() {
        return codtipoper;
    }

    public void setCodtipoper(BigInteger codtipoper) {
        this.codtipoper = codtipoper;
    }

    public BigInteger getCattipoper() {
        return cattipoper;
    }

    public void setCattipoper(BigInteger cattipoper) {
        this.cattipoper = cattipoper;
    }

    public BigInteger getCortipoper() {
        return cortipoper;
    }

    public void setCortipoper(BigInteger cortipoper) {
        this.cortipoper = cortipoper;
    }

    public String getRezsocial() {
        return rezsocial;
    }

    public void setRezsocial(String rezsocial) {
        this.rezsocial = rezsocial;
    }

    public String getNomcomercial() {
        return nomcomercial;
    }

    public void setNomcomercial(String nomcomercial) {
        this.nomcomercial = nomcomercial;
    }

    public Genpuntoventas getPuntoventa() {
        return puntoventa;
    }

    public void setPuntoventa(Genpuntoventas puntoventa) {
        this.puntoventa = puntoventa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrper != null ? corrper.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genpersonas)) {
            return false;
        }
        Genpersonas other = (Genpersonas) object;
        if ((this.corrper == null && other.corrper != null) || (this.corrper != null && !this.corrper.equals(other.corrper))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistema.general.entidades.Genpersonas[ corrper=" + corrper + " ]";
    }
    
}
