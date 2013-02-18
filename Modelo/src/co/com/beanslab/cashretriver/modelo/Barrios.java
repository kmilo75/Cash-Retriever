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
@Table(name = "barrios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Barrios.findAll", query = "SELECT b FROM Barrios b"),
    @NamedQuery(name = "Barrios.findByIdbarrios", query = "SELECT b FROM Barrios b WHERE b.barriosPK.idbarrios = :idbarrios"),
    @NamedQuery(name = "Barrios.findByMunicipio", query = "SELECT b FROM Barrios b WHERE b.barriosPK.municipio = :municipio"),
    @NamedQuery(name = "Barrios.findByNombre", query = "SELECT b FROM Barrios b WHERE b.nombre = :nombre")})
public class Barrios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BarriosPK barriosPK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "municipio", referencedColumnName = "idmunicipios", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Municipios municipios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "barrio")
    private Collection<Personas> personasCollection;

    public Barrios() {
    }

    public Barrios(BarriosPK barriosPK) {
        this.barriosPK = barriosPK;
    }

    public Barrios(BarriosPK barriosPK, String nombre) {
        this.barriosPK = barriosPK;
        this.nombre = nombre;
    }

    public Barrios(int idbarrios, String municipio) {
        this.barriosPK = new BarriosPK(idbarrios, municipio);
    }

    public BarriosPK getBarriosPK() {
        return barriosPK;
    }

    public void setBarriosPK(BarriosPK barriosPK) {
        this.barriosPK = barriosPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipios getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Municipios municipios) {
        this.municipios = municipios;
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
        hash += (barriosPK != null ? barriosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barrios)) {
            return false;
        }
        Barrios other = (Barrios) object;
        if ((this.barriosPK == null && other.barriosPK != null) || (this.barriosPK != null && !this.barriosPK.equals(other.barriosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.Barrios[ barriosPK=" + barriosPK + " ]";
    }
    
}
