/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.creditos.creditosexplorer;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class CreditoChildFactory<T> extends ChildFactory<T> {

    List<T> resultList;

    public CreditoChildFactory(List<T> resultList) {
        this.resultList = resultList;
    }

    CreditoChildFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean createKeys(List<T> list) {
        for (T credito : resultList) {
            list.add(credito);
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(T c) {
        try {
            BeanNode<T> nodo = new BeanNode<T>(c);
            nodo.setName(c.toString());
            nodo.setIconBaseWithExtension("co/com/beanslab/cashretriver/creditos/creditosexplorer/creditoexplorer-16.png");
            
            return nodo;
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
}
