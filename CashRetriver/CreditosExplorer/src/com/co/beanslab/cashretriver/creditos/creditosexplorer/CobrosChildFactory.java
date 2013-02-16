/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.beanslab.cashretriver.creditos.creditosexplorer;

import java.beans.IntrospectionException;
import java.util.List;
import modelovergara.bo.Cobros;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Clase encargada de crear el nodo de tipo bean que representa el nodo en el mannager.
 * @author Juan Camilo Cañas Gómez
 */
public class CobrosChildFactory extends ChildFactory<Cobros> {

    List<Cobros> cs;

    public CobrosChildFactory(List<Cobros> cs) {
        this.cs = cs;
    }

    @Override
    protected boolean createKeys(List<Cobros> toPopulate) {
        try {
            for (Cobros cobros : toPopulate) {
                cs.add(cobros);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Node createNodeForKey(Cobros key) {
        try {
            return new BeanNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
    
    
}
