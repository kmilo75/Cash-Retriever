/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.personal;

import co.com.beanslab.cashretriver.modelo.Barrios;
import co.com.beanslab.cashretriver.modelo.Personas;
import co.com.beanslab.cashretriver.modelo.Roles;
import co.com.beanslab.cashretriver.modelo.controllers.PersonasJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Clase validadora de información
 *
 * @author Juan Camilo Cañas G
 */
public class PersonalAdmin_Controller {

    private PersonalAdminTopComponent topComponent;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModeloPU");

    ;

    public PersonalAdmin_Controller(PersonalAdminTopComponent topComponent) {
        this.topComponent = topComponent;

    }

    public PersonalAdmin_Controller() {
    }

    /**
     * Metodo que comprueba y valida las reglas de negocio de
     * PersonalAdmin_Controller.
     */
    public void guardar() {
        if (compruebaCampos(topComponent)) {

            //Recojemos la info del formulario y creamos el objeto personas.
            Personas p = new Personas();
            p.setIdpersonas(null);//Se hace la prueba para ver si es necesario insertarle un numero
            p.setNombre(topComponent.getNombre_jTextField().getText());
            p.setApellido(topComponent.getApellido_jTextField().getText());
            p.setTelefono1(topComponent.getTelefono_jTextField().getText());
            p.setTelelfono2(topComponent.getCelular_jTextField().getText());
            p.setDireccion(topComponent.getDireccion_jTextField().getText());
            p.setBarrio((Barrios) topComponent.getBarrio_jComboBox().getSelectedItem());
            p.setRol((Roles) topComponent.getRol_jComboBox().getSelectedItem());
            p.setNit(topComponent.getCedula_jTextField().getText());


            PersonasJpaController controller = new PersonasJpaController(emf);
            try {
                controller.create(p);

                NotifyDescriptor.Message mensaje = new NotifyDescriptor.Message("Persona grabada exitósamente.");
//                mensaje.setTitle("Error guardando la persona");
                mensaje.setMessageType(NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(mensaje);

                //Actualizamos la lista de personas en el lookup

                //Limpiamos el formulario
                limpiarCampos();

            } catch (Exception e) {
                NotifyDescriptor.Message mensaje = new NotifyDescriptor.Message("Hubo problemas con la grabación de la persona");
                mensaje.setTitle("Error guardando la persona");
                mensaje.setMessageType(NotifyDescriptor.ERROR_MESSAGE);
                DialogDisplayer.getDefault().notify(mensaje);
            }
        }


    }

    /**
     * Comprueba los campos para ver si están bien formateados.
     *
     * @param topComponent
     * @return
     */
    private boolean compruebaCampos(PersonalAdminTopComponent topComponent) {

        //Comprobaciones mediante javabeans
        if (topComponent.getNombre_jTextField().getText() == "") {
        }

        return true;
    }

    public PersonalAdminTopComponent getTopComponent() {
        return topComponent;
    }

    public void setTopComponent(PersonalAdminTopComponent topComponent) {
        this.topComponent = topComponent;
    }

    public void limpiarCampos() {
        topComponent.getNombre_jTextField().setText("");
        topComponent.getApellido_jTextField().setText("");
        topComponent.getTelefono_jTextField().setText("");
        topComponent.getCelular_jTextField().setText("");
        topComponent.getDireccion_jTextField().setText("");
        topComponent.getBarrio_jComboBox().setSelectedIndex(0);
        topComponent.getRol_jComboBox().setSelectedIndex(2);
        topComponent.getCedula_jTextField().setText("");
    }
}
