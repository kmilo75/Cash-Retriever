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
        setDisplayName ("Creditos y cobros");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/cobros/cobro16x16.png");
    }

    CreditoAbstractRoot(Children create) {
        super(create);
        setDisplayName("Creditos y Cobros");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/cobros/cobro16x16.png");
    }

       
    
    
    
    
}
