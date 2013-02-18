/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Juan Camilo Cañas G
 */
@Embeddable
public class BarriosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idbarrios")
    private int idbarrios;
    @Basic(optional = false)
    @Column(name = "municipio")
    private String municipio;

    public BarriosPK() {
    }

    public BarriosPK(int idbarrios, String municipio) {
        this.idbarrios = idbarrios;
        this.municipio = municipio;
    }

    public int getIdbarrios() {
        return idbarrios;
    }

    public void setIdbarrios(int idbarrios) {
        this.idbarrios = idbarrios;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idbarrios;
        hash += (municipio != null ? municipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BarriosPK)) {
            return false;
        }
        BarriosPK other = (BarriosPK) object;
        if (this.idbarrios != other.idbarrios) {
            return false;
        }
        if ((this.municipio == null && other.municipio != null) || (this.municipio != null && !this.municipio.equals(other.municipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.BarriosPK[ idbarrios=" + idbarrios + ", municipio=" + municipio + " ]";
    }
    
}
