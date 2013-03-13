/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personalexplorer;

import co.com.beanslab.cashretriver.modelo.Personas;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Juan Camilo Cañas G
 */
class PersonalBeanNode extends BeanNode<Personas> {

    public PersonalBeanNode(Personas bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
    }
}
