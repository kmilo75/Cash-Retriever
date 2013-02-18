/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Camilo Cañas G
 */
@Entity
@Table(name = "cobros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cobros.findAll", query = "SELECT c FROM Cobros c"),
    @NamedQuery(name = "Cobros.findByIdcobros", query = "SELECT c FROM Cobros c WHERE c.idcobros = :idcobros"),
    @NamedQuery(name = "Cobros.findByAbono", query = "SELECT c FROM Cobros c WHERE c.abono = :abono")})
public class Cobros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcobros")
    private Integer idcobros;
    @Basic(optional = false)
    @Column(name = "abono")
    private float abono;
    @JoinColumn(name = "salida", referencedColumnName = "idsalidas")
    @ManyToOne(optional = false)
    private Salidas salida;
    @JoinColumn(name = "deuda", referencedColumnName = "iddeudas")
    @ManyToOne(optional = false)
    private Deudas deuda;

    public Cobros() {
    }

    public Cobros(Integer idcobros) {
        this.idcobros = idcobros;
    }

    public Cobros(Integer idcobros, float abono) {
        this.idcobros = idcobros;
        this.abono = abono;
    }

    public Integer getIdcobros() {
        return idcobros;
    }

    public void setIdcobros(Integer idcobros) {
        this.idcobros = idcobros;
    }

    public float getAbono() {
        return abono;
    }

    public void setAbono(float abono) {
        this.abono = abono;
    }

    public Salidas getSalida() {
        return salida;
    }

    public void setSalida(Salidas salida) {
        this.salida = salida;
    }

    public Deudas getDeuda() {
        return deuda;
    }

    public void setDeuda(Deudas deuda) {
        this.deuda = deuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobros != null ? idcobros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobros)) {
            return false;
        }
        Cobros other = (Cobros) object;
        if ((this.idcobros == null && other.idcobros != null) || (this.idcobros != null && !this.idcobros.equals(other.idcobros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Cobros[ idcobros=" + idcobros + " ]";
    }
    
}
