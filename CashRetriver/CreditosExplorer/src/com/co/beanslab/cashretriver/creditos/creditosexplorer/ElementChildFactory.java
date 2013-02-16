/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.beanslab.cashretriver.creditos.creditosexplorer;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Clase encargada de crear el nodo de tipo bean que representa el nodo en el
 * mannager.
 *
 * @author Juan Camilo Cañas Gómez
 */
public class ElementChildFactory<T> extends ChildFactory<T> {

    List<T> result;

    public ElementChildFactory(List<T> result) {
        this.result = result;
    }

    @Override
    protected boolean createKeys(List<T> toPopulate) {
        try {
            for (T cobros : toPopulate) {
                result.add(cobros);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Node createNodeForKey(T key) {
        try {
            return new BeanNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
}
