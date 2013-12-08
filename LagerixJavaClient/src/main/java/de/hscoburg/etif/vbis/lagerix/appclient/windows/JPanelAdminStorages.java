/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.windows;

import de.hscoburg.etif.vbis.lagerix.appclient.utils.Item;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.TableColumnAdjuster;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mti578
 */
public class JPanelAdminStorages extends javax.swing.JPanel {

    private PlaceManagerEJBRemoteInterface placeManager = null;
    /**
     * Creates new form JPanelAdminStorages
     */
    public JPanelAdminStorages(PlaceManagerEJBRemoteInterface placeManager) {
        initComponents();
        this.placeManager = placeManager;
        
        jTableAdminStoragesTable.getSelectionModel().addListSelectionListener(
            new javax.swing.event.ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        jTableAdminStoragesTableValueChanged(e);
                    };
            });
    }

    
    public void jTableAdminStoragesTableValueChanged(ListSelectionEvent e) {
        if(jTableAdminStoragesTable.isEnabled())
        {
            if(jTableAdminStoragesTable.getSelectedRow() >= 0)
            {
                String storageName = (String) jTableAdminStoragesTable.getModel().getValueAt(jTableAdminStoragesTable.getSelectedRow(), 0);
                jTextFieldAdminStorageName.setText(storageName);
                jButtonAdminStorageDeleteAndDiscard.setEnabled(true);
            }
            else
            {
                jButtonAdminStorageDeleteAndDiscard.setEnabled(false);
                jTextFieldAdminStorageName.setText("");
            }
        }        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelAdminStoragesPanel = new javax.swing.JPanel();
        jScrollPaneAdminStorageManagment = new javax.swing.JScrollPane();
        jTableAdminStoragesTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldAdminStorageName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButtonAdminStorageDeleteAndDiscard = new javax.swing.JButton();
        jButtonAdminStorageNewStorage = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanelAdminStoragesPanel.setPreferredSize(new java.awt.Dimension(750, 300));
        jPanelAdminStoragesPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPaneAdminStorageManagment.setMaximumSize(null);
        jScrollPaneAdminStorageManagment.setMinimumSize(null);

        jTableAdminStoragesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTableAdminStoragesTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableAdminStoragesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneAdminStorageManagment.setViewportView(jTableAdminStoragesTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelAdminStoragesPanel.add(jScrollPaneAdminStorageManagment, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jTextFieldAdminStorageName.setColumns(20);
        jTextFieldAdminStorageName.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jTextFieldAdminStorageName, gridBagConstraints);

        jLabel11.setText("Lagername:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel11, gridBagConstraints);

        jButtonAdminStorageDeleteAndDiscard.setText("Löschen");
        jButtonAdminStorageDeleteAndDiscard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdminStorageDeleteAndDiscardActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonAdminStorageDeleteAndDiscard, gridBagConstraints);

        jButtonAdminStorageNewStorage.setText("Neues Lager anlegen");
        jButtonAdminStorageNewStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdminStorageNewStorageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonAdminStorageNewStorage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        jPanelAdminStoragesPanel.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelAdminStoragesPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAdminStorageDeleteAndDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdminStorageDeleteAndDiscardActionPerformed
        if(jButtonAdminStorageDeleteAndDiscard.getText().equals("Löschen"))
        {
            if(JOptionPane.showConfirmDialog(this, "Moechten Sie das Lager: " +
                jTableAdminStoragesTable.getModel().getValueAt(jTableAdminStoragesTable.getSelectedRow(), 0) +
                " wirklich loeschen?" , "Sind Sie sicher?", JOptionPane.YES_NO_OPTION)
            == JOptionPane.YES_OPTION)
            {
                try
                {
                    placeManager.deleteStorage((Integer)jTableAdminStoragesTable.getModel().getValueAt(jTableAdminStoragesTable.getSelectedRow(), 1));
                    createJTableAdminStorage();
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(this, "Fehler beim loeschen des Lagers: "
                            + jTableAdminStoragesTable.getModel().getValueAt(jTableAdminStoragesTable.getSelectedRow(), 0)
                            + ". Bitte loeschen Sie zuerst alle Artikeltypen und Lagerplaetze des Lagers.", 
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            jButtonAdminStorageDeleteAndDiscard.setText("Löschen");
            jButtonAdminStorageNewStorage.setEnabled(true);
            jTextFieldAdminStorageName.setEnabled(false);
            jButtonAdminStorageDeleteAndDiscard.setText("Löschen");
            jButtonAdminStorageDeleteAndDiscard.setEnabled(false);
            jButtonAdminStorageNewStorage.setText("Neues Lager anlegen");
            jTableAdminStoragesTable.setEnabled(true);
            createJTableAdminStorage();
        }
    }//GEN-LAST:event_jButtonAdminStorageDeleteAndDiscardActionPerformed

    private void jButtonAdminStorageNewStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdminStorageNewStorageActionPerformed
        if(!jButtonAdminStorageNewStorage.getText().equals("Speichern"))
        {
            jButtonAdminStorageNewStorage.setText("Speichern");
            jTextFieldAdminStorageName.setEnabled(true);

            jButtonAdminStorageDeleteAndDiscard.setText("Abbrechen");
            jButtonAdminStorageDeleteAndDiscard.setEnabled(true);
            jTableAdminStoragesTable.setEnabled(false);

            jTextFieldAdminStorageName.setText("");
        }
        else
        {
            String storageName = jTextFieldAdminStorageName.getText();
            placeManager.createNewStorage(storageName);
            
            jButtonAdminStorageDeleteAndDiscard.setText("Löschen");
            jButtonAdminStorageDeleteAndDiscard.setEnabled(false);
            jButtonAdminStorageNewStorage.setText("Neues Lager anlegen");
            jTableAdminStoragesTable.setEnabled(true);
            jTextFieldAdminStorageName.setEnabled(false);
            jTextFieldAdminStorageName.setText("");
            createJTableAdminStorage();
        }
    }//GEN-LAST:event_jButtonAdminStorageNewStorageActionPerformed

    public void createJTableAdminStorage()
    {
        jButtonAdminStorageDeleteAndDiscard.setEnabled(false);
        DefaultTableModel model = new DefaultTableModel(0, 2);
        model.setColumnIdentifiers(new Object[] {"Lagername", "Lager-ID"});
 
        List<StorageDTO> storages = placeManager.getAllStorages();
        
        for(StorageDTO storage : storages)
        {
            model.addRow(new Object[] {storage.getName(), storage.getId()});
        }
 
        jTableAdminStoragesTable.setModel(model);
        TableColumnAdjuster tca = new TableColumnAdjuster(jTableAdminStoragesTable);
        tca.adjustColumns();
    }
  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdminStorageDeleteAndDiscard;
    private javax.swing.JButton jButtonAdminStorageNewStorage;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelAdminStoragesPanel;
    private javax.swing.JScrollPane jScrollPaneAdminStorageManagment;
    private javax.swing.JTable jTableAdminStoragesTable;
    private javax.swing.JTextField jTextFieldAdminStorageName;
    // End of variables declaration//GEN-END:variables
}
