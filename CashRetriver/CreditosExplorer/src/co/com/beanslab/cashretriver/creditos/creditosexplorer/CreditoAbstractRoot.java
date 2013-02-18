/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.creditos.creditosexplorer;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class CreditoAbstractRoot<T> extends AbstractNode{

    
    
    public CreditoAbstractRoot() {
        super (Children.create(new CreditoChildFactory<T>(), true));
        setDisplayName ("Cobros");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/cobros/cobro16x16.png");
    }

    CreditoAbstractRoot(Children create) {
        super(create);
        setDisplayName("Cobros");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/cobros/cobro16x16.png");
    }

       
    
    
    
    
}
