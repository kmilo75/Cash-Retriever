/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Juan Camilo Cañas G
 */
@Entity
@Table(name = "deudas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deudas.findAll", query = "SELECT d FROM Deudas d"),
    @NamedQuery(name = "Deudas.findByIddeudas", query = "SELECT d FROM Deudas d WHERE d.iddeudas = :iddeudas"),
    @NamedQuery(name = "Deudas.findByFechaInicio", query = "SELECT d FROM Deudas d WHERE d.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Deudas.findByPrestamoBruto", query = "SELECT d FROM Deudas d WHERE d.prestamoBruto = :prestamoBruto"),
    @NamedQuery(name = "Deudas.findByTasaInteres", query = "SELECT d FROM Deudas d WHERE d.tasaInteres = :tasaInteres"),
    @NamedQuery(name = "Deudas.findByTipoInteres", query = "SELECT d FROM Deudas d WHERE d.tipoInteres = :tipoInteres"),
    @NamedQuery(name = "Deudas.findByFrecuenciaInteres", query = "SELECT d FROM Deudas d WHERE d.frecuenciaInteres = :frecuenciaInteres"),
    @NamedQuery(name = "Deudas.findByFrecuenciaRecaudo", query = "SELECT d FROM Deudas d WHERE d.frecuenciaRecaudo = :frecuenciaRecaudo"),
    @NamedQuery(name = "Deudas.findBySaldo", query = "SELECT d FROM Deudas d WHERE d.saldo = :saldo")})
public class Deudas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeudas")
    private Integer iddeudas;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "prestamo_bruto")
    private float prestamoBruto;
    @Basic(optional = false)
    @Column(name = "tasa_interes")
    private float tasaInteres;
    @Basic(optional = false)
    @Column(name = "tipo_interes")
    private String tipoInteres;
    @Basic(optional = false)
    @Column(name = "frecuencia_interes")
    private String frecuenciaInteres;
    @Basic(optional = false)
    @Column(name = "frecuencia_recaudo")
    private String frecuenciaRecaudo;
    @Basic(optional = false)
    @Column(name = "saldo")
    private float saldo;
    @JoinColumn(name = "moroso", referencedColumnName = "idpersonas")
    @ManyToOne(optional = false)
    private Personas moroso;
    @JoinColumn(name = "cobrador", referencedColumnName = "idpersonas")
    @ManyToOne(optional = false)
    private Personas cobrador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deuda")
    private Collection<Cobros> cobrosCollection;

    public Deudas() {
    }

    public Deudas(Integer iddeudas) {
        this.iddeudas = iddeudas;
    }

    public Deudas(Integer iddeudas, Date fechaInicio, float prestamoBruto, float tasaInteres, String tipoInteres, String frecuenciaInteres, String frecuenciaRecaudo, float saldo) {
        this.iddeudas = iddeudas;
        this.fechaInicio = fechaInicio;
        this.prestamoBruto = prestamoBruto;
        this.tasaInteres = tasaInteres;
        this.tipoInteres = tipoInteres;
        this.frecuenciaInteres = frecuenciaInteres;
        this.frecuenciaRecaudo = frecuenciaRecaudo;
        this.saldo = saldo;
    }

    public Integer getIddeudas() {
        return iddeudas;
    }

    public void setIddeudas(Integer iddeudas) {
        this.iddeudas = iddeudas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public float getPrestamoBruto() {
        return prestamoBruto;
    }

    public void setPrestamoBruto(float prestamoBruto) {
        this.prestamoBruto = prestamoBruto;
    }

    public float getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(float tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public String getTipoInteres() {
        return tipoInteres;
    }

    public void setTipoInteres(String tipoInteres) {
        this.tipoInteres = tipoInteres;
    }

    public String getFrecuenciaInteres() {
        return frecuenciaInteres;
    }

    public void setFrecuenciaInteres(String frecuenciaInteres) {
        this.frecuenciaInteres = frecuenciaInteres;
    }

    public String getFrecuenciaRecaudo() {
        return frecuenciaRecaudo;
    }

    public void setFrecuenciaRecaudo(String frecuenciaRecaudo) {
        this.frecuenciaRecaudo = frecuenciaRecaudo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Personas getMoroso() {
        return moroso;
    }

    public void setMoroso(Personas moroso) {
        this.moroso = moroso;
    }

    public Personas getCobrador() {
        return cobrador;
    }

    public void setCobrador(Personas cobrador) {
        this.cobrador = cobrador;
    }

    @XmlTransient
    public Collection<Cobros> getCobrosCollection() {
        return cobrosCollection;
    }

    public void setCobrosCollection(Collection<Cobros> cobrosCollection) {
        this.cobrosCollection = cobrosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeudas != null ? iddeudas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deudas)) {
            return false;
        }
        Deudas other = (Deudas) object;
        if ((this.iddeudas == null && other.iddeudas != null) || (this.iddeudas != null && !this.iddeudas.equals(other.iddeudas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Deudas[ iddeudas=" + iddeudas + " ]";
    }
    
}
