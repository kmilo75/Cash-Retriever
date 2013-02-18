/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Camilo Cañas G
 */
@Entity
@Table(name = "departamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamentos.findAll", query = "SELECT d FROM Departamentos d"),
    @NamedQuery(name = "Departamentos.findByIddepartamentos", query = "SELECT d FROM Departamentos d WHERE d.iddepartamentos = :iddepartamentos"),
    @NamedQuery(name = "Departamentos.findByNombre", query = "SELECT d FROM Departamentos d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Departamentos.findByDepartamentoscol", query = "SELECT d FROM Departamentos d WHERE d.departamentoscol = :departamentoscol")})
public class Departamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iddepartamentos")
    private String iddepartamentos;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "departamentoscol")
    private String departamentoscol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departamentos")
    private Collection<Municipios> municipiosCollection;

    public Departamentos() {
    }

    public Departamentos(String iddepartamentos) {
        this.iddepartamentos = iddepartamentos;
    }

    public Departamentos(String iddepartamentos, String nombre) {
        this.iddepartamentos = iddepartamentos;
        this.nombre = nombre;
    }

    public String getIddepartamentos() {
        return iddepartamentos;
    }

    public void setIddepartamentos(String iddepartamentos) {
        this.iddepartamentos = iddepartamentos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamentoscol() {
        return departamentoscol;
    }

    public void setDepartamentoscol(String departamentoscol) {
        this.departamentoscol = departamentoscol;
    }

    @XmlTransient
    public Collection<Municipios> getMunicipiosCollection() {
        return municipiosCollection;
    }

    public void setMunicipiosCollection(Collection<Municipios> municipiosCollection) {
        this.municipiosCollection = municipiosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepartamentos != null ? iddepartamentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamentos)) {
            return false;
        }
        Departamentos other = (Departamentos) object;
        if ((this.iddepartamentos == null && other.iddepartamentos != null) || (this.iddepartamentos != null && !this.iddepartamentos.equals(other.iddepartamentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Departamentos[ iddepartamentos=" + iddepartamentos + " ]";
    }
    
}
