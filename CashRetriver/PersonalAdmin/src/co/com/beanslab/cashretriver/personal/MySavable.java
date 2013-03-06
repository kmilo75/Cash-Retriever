/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personal;

import java.awt.Component;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.Icon;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class MySavable extends AbstractSavable implements Icon {

    private PersonalAdminTopComponent tc;
    private static final Icon ICON = ImageUtilities.loadImageIcon("co/com/beanslab/cashretriver/personal/personal_16x16.png", true);

    public MySavable() {

        
    }

    @Override
    protected String findDisplayName() {
        String rol = tc.getRol_jComboBox().getSelectedItem().toString();
        String nombre = tc.getNombre_jTextField().getText();
        String apell = tc.getApellido_jTextField().getText();
        return rol + " " + nombre + " " + apell;
    }

    @Override
    protected void handleSave() throws IOException {
        NotifyDescriptor.Confirmation message = new NotifyDescriptor.Confirmation("Quiere grabar al "
                +tc.getRol_jComboBox().getSelectedItem().toString()+" "
                + tc.getNombre_jTextField().getText() + " " + tc.getApellido_jTextField().getText() + "?",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.QUESTION_MESSAGE);
        Object result = DialogDisplayer.getDefault().notify(message);
        //When user clicks "Yes", indicating they really want to save,
        //we need to disable the Save action,
        //so that it will only be usable when the next change is made
        //to the JTextArea:
        if (NotifyDescriptor.YES_OPTION.equals(result)) {
            PersonalAdmin_Controller controller = new PersonalAdmin_Controller();
            controller.setTopComponent(tc);
            controller.guardar();
            tc.getInstanceContent().remove(this);
            unregister();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MySavable) {
            MySavable m = (MySavable) o;
            return tc == m.tc;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tc.hashCode();
    }

    public PersonalAdminTopComponent getTc() {
        return tc;
    }

    public void setTc(PersonalAdminTopComponent tc) {
        this.tc = tc;
        register();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
       ICON.paintIcon(c, g, x, y);
    }

    @Override
    public int getIconWidth() {
       return ICON.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return ICON.getIconHeight();
    }
}