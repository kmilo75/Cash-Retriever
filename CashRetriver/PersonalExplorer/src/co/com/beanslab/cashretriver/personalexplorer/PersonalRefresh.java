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
        id = "co.com.beanslab.cashretriver.personalexplorer.PersonalRefresh")
@ActionRegistration(
        displayName = "#CTL_PersonalRefresh")
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 100, separatorAfter = 150),
    @ActionReference(path = "Shortcuts", name = "D-R")
})
@Messages("CTL_PersonalRefresh=Refrescar lista")
public final class PersonalRefresh implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent tc = WindowManager.getDefault().findTopComponent("PersonalAdminTopComponent");
        
        
    }
}
