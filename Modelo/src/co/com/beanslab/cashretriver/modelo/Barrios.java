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
@Table(name = "barrios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Barrios.findAll", query = "SELECT b FROM Barrios b"),
    @NamedQuery(name = "Barrios.findByIdbarrios", query = "SELECT b FROM Barrios b WHERE b.idbarrios = :idbarrios"),
    @NamedQuery(name = "Barrios.findByNombre", query = "SELECT b FROM Barrios b WHERE b.nombre = :nombre")})
public class Barrios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbarrios")
    private Integer idbarrios;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "municipio", referencedColumnName = "idmunicipios")
    @ManyToOne(optional = false)
    private Municipios municipio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "barrio")
    private Collection<Personas> personasCollection;

    public Barrios() {
    }

    public Barrios(Integer idbarrios) {
        this.idbarrios = idbarrios;
    }

    public Barrios(Integer idbarrios, String nombre) {
        this.idbarrios = idbarrios;
        this.nombre = nombre;
    }

    public Integer getIdbarrios() {
        return idbarrios;
    }

    public void setIdbarrios(Integer idbarrios) {
        this.idbarrios = idbarrios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipios getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipios municipio) {
        this.municipio = municipio;
    }

    @XmlTransient
    public Collection<Personas> getPersonasCollection() {
        return personasCollection;
    }

    public void setPersonasCollection(Collection<Personas> personasCollection) {
        this.personasCollection = personasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbarrios != null ? idbarrios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barrios)) {
            return false;
        }
        Barrios other = (Barrios) object;
        if ((this.idbarrios == null && other.idbarrios != null) || (this.idbarrios != null && !this.idbarrios.equals(other.idbarrios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
