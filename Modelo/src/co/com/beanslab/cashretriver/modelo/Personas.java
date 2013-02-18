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
@Table(name = "personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByIdpersonas", query = "SELECT p FROM Personas p WHERE p.idpersonas = :idpersonas"),
    @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personas.findByApellido", query = "SELECT p FROM Personas p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "Personas.findByTelefono1", query = "SELECT p FROM Personas p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Personas.findByDireccion", query = "SELECT p FROM Personas p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Personas.findByTelelfono2", query = "SELECT p FROM Personas p WHERE p.telelfono2 = :telelfono2"),
    @NamedQuery(name = "Personas.findByNit", query = "SELECT p FROM Personas p WHERE p.nit = :nit")})
public class Personas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonas")
    private Integer idpersonas;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono1")
    private String telefono1;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telelfono2")
    private String telelfono2;
    @Column(name = "nit")
    private String nit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moroso")
    private Collection<Deudas> deudasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobrador")
    private Collection<Deudas> deudasCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobrador")
    private Collection<Salidas> salidasCollection;
    @JoinColumn(name = "rol", referencedColumnName = "idroles")
    @ManyToOne(optional = false)
    private Roles rol;
    @JoinColumn(name = "barrio", referencedColumnName = "idbarrios")
    @ManyToOne(optional = false)
    private Barrios barrio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personas")
    private Collection<Usuarios> usuariosCollection;

    public Personas() {
    }

    public Personas(Integer idpersonas) {
        this.idpersonas = idpersonas;
    }

    public Integer getIdpersonas() {
        return idpersonas;
    }

    public void setIdpersonas(Integer idpersonas) {
        this.idpersonas = idpersonas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelelfono2() {
        return telelfono2;
    }

    public void setTelelfono2(String telelfono2) {
        this.telelfono2 = telelfono2;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @XmlTransient
    public Collection<Deudas> getDeudasCollection() {
        return deudasCollection;
    }

    public void setDeudasCollection(Collection<Deudas> deudasCollection) {
        this.deudasCollection = deudasCollection;
    }

    @XmlTransient
    public Collection<Deudas> getDeudasCollection1() {
        return deudasCollection1;
    }

    public void setDeudasCollection1(Collection<Deudas> deudasCollection1) {
        this.deudasCollection1 = deudasCollection1;
    }

    @XmlTransient
    public Collection<Salidas> getSalidasCollection() {
        return salidasCollection;
    }

    public void setSalidasCollection(Collection<Salidas> salidasCollection) {
        this.salidasCollection = salidasCollection;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Barrios getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrios barrio) {
        this.barrio = barrio;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersonas != null ? idpersonas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.idpersonas == null && other.idpersonas != null) || (this.idpersonas != null && !this.idpersonas.equals(other.idpersonas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre()+" "+getApellido();
    }
    
}
