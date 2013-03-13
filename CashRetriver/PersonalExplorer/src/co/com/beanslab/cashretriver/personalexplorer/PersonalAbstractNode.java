/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personalexplorer;

import java.util.List;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Utilities;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class PersonalAbstractNode extends AbstractNode {

   
    public PersonalAbstractNode() {
        super(Children.create(new PersonalChildFactory(null), true));
        setDisplayName("Personal");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/personalexplorer/perex16x16.png");

    }

    public PersonalAbstractNode(Children children) {
        super(children);
        setDisplayName("Personal");
        setIconBaseWithExtension("co/com/beanslab/cashretriver/personalexplorer/perex16x16.png");
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actionsForPersonas = Utilities.actionsForPath("Actions/Personas");
        return actionsForPersonas.toArray(new Action[actionsForPersonas.size()]);
    }

  
  
    
}
