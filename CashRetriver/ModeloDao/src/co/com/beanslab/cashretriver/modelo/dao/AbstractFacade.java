/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.dao;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Juan Camilo Cañas Gómez
 */
public abstract class AbstractFacade<T> {

    EntityManager entityManager= Persistence.createEntityManagerFactory("ModeloVergaraPU").createEntityManager();
    
    /**
     * Constructor por defecto.
     */
    public AbstractFacade() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    
    
    
    
    
    
    
    
}
