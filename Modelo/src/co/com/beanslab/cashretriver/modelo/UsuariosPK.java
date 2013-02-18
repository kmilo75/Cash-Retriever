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
public class UsuariosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idusuarios")
    private int idusuarios;
    @Basic(optional = false)
    @Column(name = "persona")
    private int persona;

    public UsuariosPK() {
    }

    public UsuariosPK(int idusuarios, int persona) {
        this.idusuarios = idusuarios;
        this.persona = persona;
    }

    public int getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(int idusuarios) {
        this.idusuarios = idusuarios;
    }

    public int getPersona() {
        return persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idusuarios;
        hash += (int) persona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariosPK)) {
            return false;
        }
        UsuariosPK other = (UsuariosPK) object;
        if (this.idusuarios != other.idusuarios) {
            return false;
        }
        if (this.persona != other.persona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.beanslab.cashretriver.modelo.UsuariosPK[ idusuarios=" + idusuarios + ", persona=" + persona + " ]";
    }
    
}
