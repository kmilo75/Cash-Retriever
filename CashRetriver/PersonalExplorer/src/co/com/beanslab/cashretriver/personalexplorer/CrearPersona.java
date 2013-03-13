/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personalexplorer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Personas",
        id = "co.com.beanslab.cashretriver.personalexplorer.CrearPersona")
@ActionRegistration(
        displayName = "#CTL_CrearPersona")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1100),
    @ActionReference(path = "Shortcuts", name = "D-P")
})
@Messages("CTL_CrearPersona=Crear Persona")
public final class CrearPersona implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent tc = WindowManager.getDefault().findTopComponent("PersonalAdminTopComponent");
        if (!tc.isOpened()) {
            tc.open();
            tc.requestActive();
            
        }else{
            tc.requestActive();
        }
        
    }
}
