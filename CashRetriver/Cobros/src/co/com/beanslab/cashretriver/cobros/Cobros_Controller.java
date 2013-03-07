/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.cobros;

import co.com.beanslab.cashretriver.modelo.Personas;
import co.com.beanslab.cashretriver.modelo.Roles;
import co.com.beanslab.cashretriver.modelo.controllers.PersonasJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Timer;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class Cobros_Controller {

    CobrosTopComponent topComponent;
    InstanceContent content = new InstanceContent();

    public CobrosTopComponent getTopComponent() {
        return topComponent;
    }

    public void setTopComponent(CobrosTopComponent topComponent) {
        this.topComponent = topComponent;
    }

    public Cobros_Controller(CobrosTopComponent topComponent) {
        this.topComponent = topComponent;
    }

    public Cobros_Controller() {
    }

    /**
     * Método que controla la recarga cada cierto tiempo
     *
     * @param delay tiempo entre recarga y recarga en milisegundos
     */
    public void refresh(int delay) {


        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tarea();
            }
        };

        Timer t = new Timer(delay, task);
        t.start();


    }

    private void tarea() {
        cargarDatos();
    }

    public void cargarDatos() {

        cargarPersonas(new Roles(2), topComponent.getCobrador_jComboBox());
        cargarPersonas(new Roles(3), topComponent.getCliente_jComboBox());
    }

    /**
     * Método que llena los comboBoxes de personas identificandolas con el rol
     * correspondiente. si rol =1 administrador, 2=cobrador, 3 cliente
     *
     * @param rol Rol que se desea buscar.
     * @param combo Combo que se quiere llenar
     */
    private void cargarPersonas(Roles rol, JComboBox<Personas> combo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModeloPU");
        PersonasJpaController clienteController = new PersonasJpaController(emf);
        List<Personas> personas = clienteController.findPersonasEntitiesByRol(rol);
        int index = combo.getSelectedIndex();
        //Metemos las personas en el lookup


        //llenamos los comboboxes seleccionando el rol

        //llenamos los comboboxes 
        DefaultComboBoxModel modelo = new DefaultComboBoxModel((Vector<Personas>) personas);
        combo.setModel(modelo);
        combo.setSelectedIndex(index);
    }

   
}
