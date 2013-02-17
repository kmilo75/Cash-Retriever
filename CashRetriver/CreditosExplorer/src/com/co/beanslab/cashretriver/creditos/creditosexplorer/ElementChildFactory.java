/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.beanslab.cashretriver.creditos.creditosexplorer;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;

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

    public ElementChildFactory() {
    }
    

    @Override
    protected boolean createKeys(List<T> toPopulate) {
        try {
            for (T element : toPopulate) {
                result.add(element);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Node createNodeForKey(T key) {
//        try {
//            return new BeanNode(key);
//        } catch (IntrospectionException ex) {
//            Exceptions.printStackTrace(ex);
//            return null;
//        }
        
        Node result=new AbstractNode(Children.create(new ElementChildFactory<T>(null), true), Lookups.singleton(key));
        return result;
        
    }
}
