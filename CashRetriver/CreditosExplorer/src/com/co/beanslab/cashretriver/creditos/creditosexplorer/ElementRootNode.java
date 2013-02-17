/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.beanslab.cashretriver.creditos.creditosexplorer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author bruce
 */
public class ElementRootNode extends AbstractNode{

    public ElementRootNode(Children children) {
        super(children);
        setDisplayName("Cobros");
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] result = new Action[]{
            new RefreshAction(),
            new CreateAction()
                
        };
        
        return result;
    }

    
 private final class RefreshAction extends AbstractAction {

        public RefreshAction() {
            putValue(Action.NAME, "Refrescar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CreditosExplorerTopComponent.refreshNode();
        }
    }
   
  private final class CreateAction extends AbstractAction{

        public CreateAction() {
            putValue(NAME, "Crear");

        }

      
        @Override
        public void actionPerformed(ActionEvent e) {
            CreditosExplorerTopComponent.crearCobro();
        }
      
  }
    
    
}
