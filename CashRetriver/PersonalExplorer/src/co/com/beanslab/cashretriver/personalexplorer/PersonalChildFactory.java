/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personalexplorer;

import co.com.beanslab.cashretriver.modelo.Personas;
import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class PersonalChildFactory extends ChildFactory<Personas> {

    private List<Personas> resulList;

    public PersonalChildFactory(List<Personas> resulList) {
        this.resulList = resulList;
    }

    @Override
    protected boolean createKeys(List<Personas> list) {
        for (Personas personas : resulList) {
            list.add(personas);

        }
        return true;

    }

    @Override
    protected Node createNodeForKey(Personas p) {
        try {
            PersonalBeanNode bean = new PersonalBeanNode(p);
            bean.setDisplayName(p.getIdpersonas() + " " + p.getNombre() + " " + p.getApellido());
            bean.setShortDescription("Vive en el barrio " + p.getBarrio().getNombre());
            Integer idroles = p.getRol().getIdroles();
            switch (idroles) {
                case 1://admin  
                    bean.setIconBaseWithExtension("co/com/beanslab/cashretriver/personalexplorer/admin16x16.png");
                    break;
                case 2://admin  
                    bean.setIconBaseWithExtension("co/com/beanslab/cashretriver/personalexplorer/cobrador16x16.png");
                    break;
                case 3://admin  
                    bean.setIconBaseWithExtension("co/com/beanslab/cashretriver/personalexplorer/cliente16x16.png");
                    break;
                default:
                    throw new AssertionError();
            }

            return bean;
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }



    }
    
    
}
