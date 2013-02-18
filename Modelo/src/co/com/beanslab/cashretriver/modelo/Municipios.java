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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "municipios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m"),
    @NamedQuery(name = "Municipios.findByIdmunicipios", query = "SELECT m FROM Municipios m WHERE m.municipiosPK.idmunicipios = :idmunicipios"),
    @NamedQuery(name = "Municipios.findByDepartamento", query = "SELECT m FROM Municipios m WHERE m.municipiosPK.departamento = :departamento"),
    @NamedQuery(name = "Municipios.findByNombre", query = "SELECT m FROM Municipios m WHERE m.nombre = :nombre")})
public class Municipios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MunicipiosPK municipiosPK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipios")
    private Collection<Barrios> barriosCollection;
    @JoinColumn(name = "departamento", referencedColumnName = "iddepartamentos", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamentos departamentos;

    public Municipios() {
    }

    public Municipios(MunicipiosPK municipiosPK) {
        this.municipiosPK = municipiosPK;
    }

    public Municipios(MunicipiosPK municipiosPK, String nombre) {
        this.municipiosPK = municipiosPK;
        this.nombre = nombre;
    }

    public Municipios(String idmunicipios, String departamento) {
        this.municipiosPK = new MunicipiosPK(idmunicipios, departamento);
    }

    public MunicipiosPK getMunicipiosPK() {
        return municipiosPK;
    }

    public void setMunicipiosPK(MunicipiosPK municipiosPK) {
        this.municipiosPK = municipiosPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Barrios> getBarriosCollection() {
        return barriosCollection;
    }

    public void setBarriosCollection(Collection<Barrios> barriosCollection) {
        this.barriosCollection = barriosCollection;
    }

    public Departamentos getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (municipiosPK != null ? municipiosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.municipiosPK == null && other.municipiosPK != null) || (this.municipiosPK != null && !this.municipiosPK.equals(other.municipiosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Municipios[ municipiosPK=" + municipiosPK + " ]";
    }
    
}
