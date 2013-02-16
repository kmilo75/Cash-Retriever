/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.cobros;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//co.com.beanslab.cashretriver.cobros//Cobros//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "CobrosTopComponent",
iconBase = "co/com/beanslab/cashretriver/cobros/cobro32x32.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "co.com.beanslab.cashretriver.cobros.CobrosTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_CobrosAction",
preferredID = "CobrosTopComponent")
@Messages({
    "CTL_CobrosAction=Cobros",
    "CTL_CobrosTopComponent=Cobros Window",
    "HINT_CobrosTopComponent=This is a Cobros window"
})
public final class CobrosTopComponent extends TopComponent {

    public CobrosTopComponent() {
        initComponents();
        setName(Bundle.CTL_CobrosTopComponent());
        setToolTipText(Bundle.HINT_CobrosTopComponent());
        AutoCompleteDecorator.decorate(cliente_jComboBox);
        AutoCompleteDecorator.decorate(cobrador_jComboBox);
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        cobrador_jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cobrador_jComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        fecha_jDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        baseMonto_jTextField = new javax.swing.JTextField();
        prestamo_jPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cliente_jComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        prestamoMonto_jTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        frecuenciaInteres_jComboBox = new javax.swing.JComboBox();
        interesPorcentual_jTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        totalPrestamo_jTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        cobrador_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.cobrador_jPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 14), new java.awt.Color(1, 1, 1))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel1.text")); // NOI18N

        cobrador_jComboBox.setEditable(true);
        cobrador_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Juanito Alimaña", "Juaquin Perez", "Armando Manzanero", "Paquita La del Barrio", "Pedro Navaja" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel3.text")); // NOI18N

        baseMonto_jTextField.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.baseMonto_jTextField.text")); // NOI18N
        baseMonto_jTextField.setToolTipText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.baseMonto_jTextField.toolTipText")); // NOI18N

        javax.swing.GroupLayout cobrador_jPanelLayout = new javax.swing.GroupLayout(cobrador_jPanel);
        cobrador_jPanel.setLayout(cobrador_jPanelLayout);
        cobrador_jPanelLayout.setHorizontalGroup(
            cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cobrador_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fecha_jDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cobrador_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baseMonto_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cobrador_jPanelLayout.setVerticalGroup(
            cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cobrador_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(fecha_jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(cobrador_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cobrador_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(baseMonto_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(cobrador_jPanel);

        prestamo_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.prestamo_jPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 14), new java.awt.Color(1, 1, 1))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel4.text")); // NOI18N

        cliente_jComboBox.setEditable(true);
        cliente_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Perdo", "JAcinta", "Mariana", "Sharon" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel5.text")); // NOI18N

        prestamoMonto_jTextField.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.prestamoMonto_jTextField.text")); // NOI18N
        prestamoMonto_jTextField.setToolTipText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.prestamoMonto_jTextField.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel7.text")); // NOI18N

        frecuenciaInteres_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diario", "Semanal", "Quincenal", "Mensual", "Trimestral", "Semestral", "Anual" }));

        interesPorcentual_jTextField.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.interesPorcentual_jTextField.text")); // NOI18N
        interesPorcentual_jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interesPorcentual_jTextFieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel8.text")); // NOI18N

        totalPrestamo_jTextField.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.totalPrestamo_jTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel9.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jTextField1.text")); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jLabel10.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(CobrosTopComponent.class, "CobrosTopComponent.jTextField2.text")); // NOI18N

        javax.swing.GroupLayout prestamo_jPanelLayout = new javax.swing.GroupLayout(prestamo_jPanel);
        prestamo_jPanel.setLayout(prestamo_jPanelLayout);
        prestamo_jPanelLayout.setHorizontalGroup(
            prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(prestamo_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(prestamo_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(42, 42, 42)
                        .addComponent(totalPrestamo_jTextField))
                    .addGroup(prestamo_jPanelLayout.createSequentialGroup()
                        .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cliente_jComboBox, 0, 169, Short.MAX_VALUE)
                            .addComponent(jTextField2)
                            .addComponent(prestamoMonto_jTextField))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(interesPorcentual_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(frecuenciaInteres_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 244, Short.MAX_VALUE))))
        );
        prestamo_jPanelLayout.setVerticalGroup(
            prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(prestamo_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cliente_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(prestamoMonto_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(interesPorcentual_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frecuenciaInteres_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addGroup(prestamo_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalPrestamo_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(prestamo_jPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void interesPorcentual_jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interesPorcentual_jTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interesPorcentual_jTextFieldActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baseMonto_jTextField;
    private javax.swing.JComboBox cliente_jComboBox;
    private javax.swing.JComboBox cobrador_jComboBox;
    private javax.swing.JPanel cobrador_jPanel;
    private com.toedter.calendar.JDateChooser fecha_jDateChooser;
    private javax.swing.JComboBox frecuenciaInteres_jComboBox;
    private javax.swing.JTextField interesPorcentual_jTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField prestamoMonto_jTextField;
    private javax.swing.JPanel prestamo_jPanel;
    private javax.swing.JTextField totalPrestamo_jTextField;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
