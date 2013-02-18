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
public class MunicipiosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmunicipios")
    private String idmunicipios;
    @Basic(optional = false)
    @Column(name = "departamento")
    private String departamento;

    public MunicipiosPK() {
    }

    public MunicipiosPK(String idmunicipios, String departamento) {
        this.idmunicipios = idmunicipios;
        this.departamento = departamento;
    }

    public String getIdmunicipios() {
        return idmunicipios;
    }

    public void setIdmunicipios(String idmunicipios) {
        this.idmunicipios = idmunicipios;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmunicipios != null ? idmunicipios.hashCode() : 0);
        hash += (departamento != null ? departamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipiosPK)) {
            return false;
        }
        MunicipiosPK other = (MunicipiosPK) object;
        if ((this.idmunicipios == null && other.idmunicipios != null) || (this.idmunicipios != null && !this.idmunicipios.equals(other.idmunicipios))) {
            return false;
        }
        if ((this.departamento == null && other.departamento != null) || (this.departamento != null && !this.departamento.equals(other.departamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.MunicipiosPK[ idmunicipios=" + idmunicipios + ", departamento=" + departamento + " ]";
    }
    
}
