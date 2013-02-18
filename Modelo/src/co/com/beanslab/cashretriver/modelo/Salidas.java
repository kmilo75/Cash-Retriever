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
@Table(name = "salidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salidas.findAll", query = "SELECT s FROM Salidas s"),
    @NamedQuery(name = "Salidas.findByIdsalidas", query = "SELECT s FROM Salidas s WHERE s.idsalidas = :idsalidas"),
    @NamedQuery(name = "Salidas.findByFechaSalida", query = "SELECT s FROM Salidas s WHERE s.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "Salidas.findByBase", query = "SELECT s FROM Salidas s WHERE s.base = :base"),
    @NamedQuery(name = "Salidas.findByLiquidacion", query = "SELECT s FROM Salidas s WHERE s.liquidacion = :liquidacion"),
    @NamedQuery(name = "Salidas.findByRecaudo", query = "SELECT s FROM Salidas s WHERE s.recaudo = :recaudo")})
public class Salidas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalidas")
    private Integer idsalidas;
    @Basic(optional = false)
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Basic(optional = false)
    @Column(name = "base")
    private float base;
    @Basic(optional = false)
    @Column(name = "liquidacion")
    private float liquidacion;
    @Basic(optional = false)
    @Column(name = "recaudo")
    private float recaudo;
    @JoinColumn(name = "cobrador", referencedColumnName = "idpersonas")
    @ManyToOne(optional = false)
    private Personas cobrador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salida")
    private Collection<Cobros> cobrosCollection;

    public Salidas() {
    }

    public Salidas(Integer idsalidas) {
        this.idsalidas = idsalidas;
    }

    public Salidas(Integer idsalidas, Date fechaSalida, float base, float liquidacion, float recaudo) {
        this.idsalidas = idsalidas;
        this.fechaSalida = fechaSalida;
        this.base = base;
        this.liquidacion = liquidacion;
        this.recaudo = recaudo;
    }

    public Integer getIdsalidas() {
        return idsalidas;
    }

    public void setIdsalidas(Integer idsalidas) {
        this.idsalidas = idsalidas;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public float getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(float liquidacion) {
        this.liquidacion = liquidacion;
    }

    public float getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(float recaudo) {
        this.recaudo = recaudo;
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
        hash += (idsalidas != null ? idsalidas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salidas)) {
            return false;
        }
        Salidas other = (Salidas) object;
        if ((this.idsalidas == null && other.idsalidas != null) || (this.idsalidas != null && !this.idsalidas.equals(other.idsalidas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Salidas[ idsalidas=" + idsalidas + " ]";
    }
    
}
