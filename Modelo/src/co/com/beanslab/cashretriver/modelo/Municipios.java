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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Municipios.findByIdmunicipios", query = "SELECT m FROM Municipios m WHERE m.idmunicipios = :idmunicipios"),
    @NamedQuery(name = "Municipios.findByNombre", query = "SELECT m FROM Municipios m WHERE m.nombre = :nombre")})
public class Municipios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmunicipios")
    private Integer idmunicipios;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipio")
    private Collection<Barrios> barriosCollection;
    @JoinColumn(name = "departamento", referencedColumnName = "iddepartamentos")
    @ManyToOne(optional = false)
    private Departamentos departamento;

    public Municipios() {
    }

    public Municipios(Integer idmunicipios) {
        this.idmunicipios = idmunicipios;
    }

    public Municipios(Integer idmunicipios, String nombre) {
        this.idmunicipios = idmunicipios;
        this.nombre = nombre;
    }

    public Integer getIdmunicipios() {
        return idmunicipios;
    }

    public void setIdmunicipios(Integer idmunicipios) {
        this.idmunicipios = idmunicipios;
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

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmunicipios != null ? idmunicipios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.idmunicipios == null && other.idmunicipios != null) || (this.idmunicipios != null && !this.idmunicipios.equals(other.idmunicipios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre()+" "+getDepartamento();
    }
    
}
