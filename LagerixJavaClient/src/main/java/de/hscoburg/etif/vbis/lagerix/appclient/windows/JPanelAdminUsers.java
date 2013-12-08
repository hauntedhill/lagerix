/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.windows;

import de.hscoburg.etif.vbis.lagerix.appclient.utils.Item;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.TableColumnAdjuster;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mti578
 */
public class JPanelAdminUsers extends javax.swing.JPanel {
    private UserManagerEJBRemoteInterface userManager = null;
    private PlaceManagerEJBRemoteInterface placeManager = null;
    private UserDTO loggedInUsr = null;
    /**
     * Creates new form JPanelAdminUsers
     * @param userManager
     * @param placeManager
     * @param user
     */
    public JPanelAdminUsers(UserManagerEJBRemoteInterface userManager, PlaceManagerEJBRemoteInterface placeManager, UserDTO user) {
        initComponents();
        this.userManager = userManager;
        this.placeManager = placeManager;
        loggedInUsr = user;
        
        jTableAdminUsersTable.getSelectionModel().addListSelectionListener(
            new javax.swing.event.ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        JTableAdminUserValueChanged(e);
                    };
            });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelAdminUsers = new javax.swing.JPanel();
        jScrollPaneAdminUserManagment = new javax.swing.JScrollPane();
        jTableAdminUsersTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldAdminUserFirstName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldAdminUserName = new javax.swing.JTextField();
        jTextFieldAdminUserLastName = new javax.swing.JTextField();
        jPasswordFieldAdminUserPassword = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxAdminUserStorage = new javax.swing.JComboBox();
        jLabelAdminUserStorage = new javax.swing.JLabel();
        jComboBoxAdminUserGroup = new javax.swing.JComboBox();
        jButtonAdminUserEditAndSave = new javax.swing.JButton();
        jButtonAdminUserDeleteAndDiscard = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPasswordFieldAdminUserPassword2 = new javax.swing.JPasswordField();
        jButtonAdminUserNewUser = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanelAdminUsers.setPreferredSize(new java.awt.Dimension(750, 300));
        jPanelAdminUsers.setLayout(new java.awt.GridBagLayout());

        jScrollPaneAdminUserManagment.setMaximumSize(null);
        jScrollPaneAdminUserManagment.setMinimumSize(null);

        jTableAdminUsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTableAdminUsersTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableAdminUsersTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneAdminUserManagment.setViewportView(jTableAdminUsersTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelAdminUsers.add(jScrollPaneAdminUserManagment, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jTextFieldAdminUserFirstName.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jTextFieldAdminUserFirstName, gridBagConstraints);

        jLabel13.setText("Nachname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel13, gridBagConstraints);

        jTextFieldAdminUserName.setColumns(20);
        jTextFieldAdminUserName.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jTextFieldAdminUserName, gridBagConstraints);

        jTextFieldAdminUserLastName.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jTextFieldAdminUserLastName, gridBagConstraints);

        jPasswordFieldAdminUserPassword.setEnabled(false);
        jPasswordFieldAdminUserPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldAdminUserPasswordActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jPasswordFieldAdminUserPassword, gridBagConstraints);

        jLabel14.setText("Passwort:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel14, gridBagConstraints);

        jLabel12.setText("Vorname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel12, gridBagConstraints);

        jLabel11.setText("Benutzername/E-Mail:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel11, gridBagConstraints);

        jLabel15.setText("Benutzergruppe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel15, gridBagConstraints);

        jComboBoxAdminUserStorage.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jComboBoxAdminUserStorage, gridBagConstraints);

        jLabelAdminUserStorage.setText("Zustaendig fuer Lager:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabelAdminUserStorage, gridBagConstraints);

        jComboBoxAdminUserGroup.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jComboBoxAdminUserGroup, gridBagConstraints);

        jButtonAdminUserEditAndSave.setText("Bearbeiten");
        jButtonAdminUserEditAndSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdminUserEditAndSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonAdminUserEditAndSave, gridBagConstraints);

        jButtonAdminUserDeleteAndDiscard.setText("Löschen");
        jButtonAdminUserDeleteAndDiscard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdminUserDeleteAndDiscardActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonAdminUserDeleteAndDiscard, gridBagConstraints);

        jLabel3.setText("Passwort Wiederholung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel8.add(jLabel3, gridBagConstraints);

        jPasswordFieldAdminUserPassword2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jPasswordFieldAdminUserPassword2, gridBagConstraints);

        jButtonAdminUserNewUser.setText("Neuen Benutzer anlegen");
        jButtonAdminUserNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdminUserNewUserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonAdminUserNewUser, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        jPanelAdminUsers.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelAdminUsers, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldAdminUserPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordFieldAdminUserPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordFieldAdminUserPasswordActionPerformed

    private void jButtonAdminUserEditAndSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdminUserEditAndSaveActionPerformed
        if(!jButtonAdminUserEditAndSave.getText().equals("Speichern"))
        {
            if(((String)jTableAdminUsersTable.getModel().getValueAt(jTableAdminUsersTable.getSelectedRow(), 0))
                .equals(loggedInUsr.getEmail()))
            {
                JOptionPane.showMessageDialog(this, "Der aktuell angemeldete Benutzer "
                    + "kann nicht bearbeitet werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                jButtonAdminUserEditAndSave.setText("Speichern");
                jButtonAdminUserDeleteAndDiscard.setText("Abbrechen");
                jTableAdminUsersTable.setEnabled(false);
                jComboBoxAdminUserGroup.setEnabled(true);
                jComboBoxAdminUserStorage.setEnabled(true);
                jButtonAdminUserNewUser.setEnabled(false);
            }
        }
        else
        {
            UserDTO user = userManager.find(jTextFieldAdminUserName.getText());
            GroupDTO group = new GroupDTO();
            group.setGroup((GroupType)((Item)jComboBoxAdminUserGroup.getSelectedItem()).getObj());

            Item storage = (Item)jComboBoxAdminUserStorage.getSelectedItem();
            if(storage.getObj() != null)
            {
                List storagesUser = new ArrayList<Integer>();
                storagesUser.add(((StorageDTO)storage.getObj()).getId());
                group.setStorageId(storagesUser);
            }
            List<GroupDTO> groups = new ArrayList<GroupDTO>();
            groups.add(group);
            user.setGroups(groups);
            userManager.editUserGroups(user);

            jButtonAdminUserEditAndSave.setText("Bearbeiten");
            jButtonAdminUserDeleteAndDiscard.setText("Löschen");
            jComboBoxAdminUserGroup.setEnabled(false);
            jComboBoxAdminUserStorage.setEnabled(false);
            jButtonAdminUserNewUser.setEnabled(true);
            jTableAdminUsersTable.setEnabled(true);
            createJTableAdminUsers();
        }
    }//GEN-LAST:event_jButtonAdminUserEditAndSaveActionPerformed

    private void jButtonAdminUserDeleteAndDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdminUserDeleteAndDiscardActionPerformed
        if(jButtonAdminUserDeleteAndDiscard.getText().equals("Löschen"))
        {
            if(JOptionPane.showConfirmDialog(this, "Moechten Sie den Benutzer: " +
                jTableAdminUsersTable.getModel().getValueAt(jTableAdminUsersTable.getSelectedRow(), 0) +
                " wirklich loeschen?" , "Sind Sie sicher?", JOptionPane.YES_NO_OPTION)
            == JOptionPane.YES_OPTION)
        {
            if(((String)jTableAdminUsersTable.getModel().getValueAt(jTableAdminUsersTable.getSelectedRow(), 0))
                .equals(loggedInUsr.getEmail()))
            {
                JOptionPane.showMessageDialog(this, "Der aktuell angemeldete Benutzer "
                    + "kann nicht geloescht werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                userManager.deleteUser((String)jTableAdminUsersTable.getModel().getValueAt(jTableAdminUsersTable.getSelectedRow(), 0));
                createJTableAdminUsers();
            }
        }
        }
        else
        {
            jButtonAdminUserEditAndSave.setText("Bearbeiten");
            jButtonAdminUserDeleteAndDiscard.setText("Löschen");
            jComboBoxAdminUserGroup.setEnabled(false);
            jComboBoxAdminUserStorage.setEnabled(false);
            jButtonAdminUserNewUser.setEnabled(true);
            jTextFieldAdminUserFirstName.setEnabled(false);
            jTextFieldAdminUserLastName.setEnabled(false);
            jTextFieldAdminUserName.setEnabled(false);
            jPasswordFieldAdminUserPassword.setEnabled(false);
            jPasswordFieldAdminUserPassword2.setEnabled(false);
            jComboBoxAdminUserGroup.setEnabled(false);
            jComboBoxAdminUserStorage.setEnabled(false);
            jButtonAdminUserDeleteAndDiscard.setText("Löschen");
            jButtonAdminUserDeleteAndDiscard.setEnabled(false);
            jButtonAdminUserNewUser.setText("Neuen Benutzer anlegen");
            jTableAdminUsersTable.setEnabled(true);
            createJTableAdminUsers();
        }
    }//GEN-LAST:event_jButtonAdminUserDeleteAndDiscardActionPerformed

    private void jButtonAdminUserNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdminUserNewUserActionPerformed
        if(!jButtonAdminUserNewUser.getText().equals("Speichern"))
        {
            jButtonAdminUserEditAndSave.setEnabled(false);
            jButtonAdminUserNewUser.setText("Speichern");

            jTextFieldAdminUserFirstName.setEnabled(true);
            jTextFieldAdminUserLastName.setEnabled(true);
            jTextFieldAdminUserName.setEnabled(true);
            jPasswordFieldAdminUserPassword.setEnabled(true);
            jPasswordFieldAdminUserPassword2.setEnabled(true);
            jComboBoxAdminUserGroup.setEnabled(true);
            jComboBoxAdminUserStorage.setEnabled(true);

            jButtonAdminUserDeleteAndDiscard.setText("Abbrechen");
            jButtonAdminUserDeleteAndDiscard.setEnabled(true);
            jTableAdminUsersTable.setEnabled(false);

            DefaultComboBoxModel modelGroups = new DefaultComboBoxModel();
            for(GroupType type : GroupType.values())
            {
                modelGroups.addElement(new Item(type, type.toString()));
            }
            jComboBoxAdminUserGroup.setModel(modelGroups);

            DefaultComboBoxModel modelStorages = new DefaultComboBoxModel();
            modelStorages.addElement(new Item(null, ""));
            for(StorageDTO storage : placeManager.getAllStorages())
            {
                Item itm = new Item(storage, storage.getName());
                modelStorages.addElement(itm);
            }
            jComboBoxAdminUserStorage.setModel(modelStorages);

            jTextFieldAdminUserFirstName.setText("");
            jTextFieldAdminUserLastName.setText("");
            jTextFieldAdminUserName.setText("");
            jPasswordFieldAdminUserPassword.setText("");
            jPasswordFieldAdminUserPassword2.setText("");
        }
        else
        {
            String passwd1 = new String(jPasswordFieldAdminUserPassword.getPassword());
            String passwd2 = new String(jPasswordFieldAdminUserPassword2.getPassword());
            if(!passwd1.equals(passwd2))
            {
                JOptionPane.showMessageDialog(this, "Die Passwort-Felder sind nicht identisch.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String email = jTextFieldAdminUserName.getText();
                if(!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!"
                        + "#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-"
                        + "z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))
                {
                    JOptionPane.showMessageDialog(this, "Der Bentzername muss eine E-Mail Adresse sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    UserDTO user = new UserDTO();
                    user.setEmail(jTextFieldAdminUserName.getText());
                    user.setFname(jTextFieldAdminUserFirstName.getText());
                    user.setLname(jTextFieldAdminUserLastName.getText());
                    user.setPassword1(passwd1);
                    user.setPassword2(passwd2);

                    GroupDTO group = new GroupDTO();
                    group.setGroup((GroupType)((Item)jComboBoxAdminUserGroup.getSelectedItem()).getObj());

                    Item storage = (Item)jComboBoxAdminUserStorage.getSelectedItem();
                    if(storage.getObj() != null && group.getGroup() != GroupType.ADMINISTRATOR
                        && group.getGroup() != GroupType.EINKAEUFER)
                    {
                        List storagesUser = new ArrayList<Integer>();
                        storagesUser.add((Integer)((StorageDTO)storage.getObj()).getId());
                        group.setStorageId(storagesUser);
                    }
                    List<GroupDTO> groups = new ArrayList<GroupDTO>();
                    groups.add(group);
                    user.setGroups(groups);

                    try
                    {
                        userManager.register(user);

                        jTextFieldAdminUserFirstName.setEnabled(false);
                        jTextFieldAdminUserLastName.setEnabled(false);
                        jTextFieldAdminUserName.setEnabled(false);
                        jPasswordFieldAdminUserPassword.setEnabled(false);
                        jPasswordFieldAdminUserPassword2.setEnabled(false);
                        jComboBoxAdminUserGroup.setEnabled(false);
                        jComboBoxAdminUserStorage.setEnabled(false);
                        jButtonAdminUserDeleteAndDiscard.setText("Löschen");
                        jButtonAdminUserDeleteAndDiscard.setEnabled(false);
                        jButtonAdminUserNewUser.setText("Neuen Benutzer anlegen");
                        jTableAdminUsersTable.setEnabled(true);
                        createJTableAdminUsers();
                    } catch(Exception es)
                    {
                        JOptionPane.showMessageDialog(this, "Fehler beim Erstellen des Benutzer."
                            + "(evtl. ist der Benutzername schon vergeben)", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonAdminUserNewUserActionPerformed

       
    public void createJTableAdminUsers()
    {
        jButtonAdminUserDeleteAndDiscard.setEnabled(false);
        jButtonAdminUserEditAndSave.setEnabled(false);
        
        List<UserDTO> users = userManager.getAllUsers();

        DefaultTableModel model = new DefaultTableModel(0, 5);
        model.setColumnIdentifiers(new Object[] {"Benutzername", "Rolle", "Lager", "Vorname", "Nachname"});
        for(UserDTO user : users)
        {
            String storageName = "";
            List<GroupDTO> groups = user.getGroups();
            
            if(groups.get(0).getGroup() == GroupType.LAGERARBEITER 
                    || groups.get(0).getGroup() == GroupType.LAGERVERWALTER)
            {
                Integer storageId = null;
                try
                {
                    storageId = groups.get(0).getStorageId().get(0);
                } catch(Exception ex) {  }
                
                if(storageId != null)
                {
                    try
                    {
                        storageName = placeManager.getStorage(storageId).getName();
                    } catch(Exception ex){ }
                }
            }
            
            model.addRow(new Object[] {user.getEmail(), groups.get(0).getGroup().toString(),
                        storageName, user.getFname(), user.getLname()});
        }
        jTableAdminUsersTable.setModel(model);
        TableColumnAdjuster tca = new TableColumnAdjuster(jTableAdminUsersTable);
        tca.adjustColumns();
    }
    
    
        public void JTableAdminUserValueChanged(ListSelectionEvent e) {
        if(jTableAdminUsersTable.isEnabled())
        {
            if(jTableAdminUsersTable.getSelectedRow() >= 0)
            {
                String userName = (String) jTableAdminUsersTable.getModel().getValueAt(jTableAdminUsersTable.getSelectedRow(), 0);
                UserDTO selectedUser = userManager.find(userName);

                jTextFieldAdminUserFirstName.setText(selectedUser.getFname());
                jTextFieldAdminUserLastName.setText(selectedUser.getLname());
                jTextFieldAdminUserName.setText(selectedUser.getEmail());
                jPasswordFieldAdminUserPassword.setText("");
                jPasswordFieldAdminUserPassword2.setText("");
                List<GroupDTO> groups = selectedUser.getGroups();
                if(groups.isEmpty()) //User doesn't belong to a Group, not possible make him sth...
                {
                    GroupDTO group = new GroupDTO();
                    group.setGroup(GroupType.ADMINISTRATOR);

                    groups.add(group);
                    selectedUser.setGroups(groups);
                    userManager.editUserGroups(selectedUser);
                }

                DefaultComboBoxModel modelGroups = new DefaultComboBoxModel();
                Item selectedGroup = null;
                for(GroupType type : GroupType.values())
                {
                    Item itmGroup = new Item(type, type.toString());
                    if(groups.get(0).getGroup().equals(type))
                    {
                        selectedGroup = itmGroup;
                    }
                    modelGroups.addElement(itmGroup);
                }

                jComboBoxAdminUserGroup.setModel(modelGroups);
                if(selectedGroup != null)
                {
                    jComboBoxAdminUserGroup.setSelectedItem(selectedGroup);
                }

                DefaultComboBoxModel modelStorages = new DefaultComboBoxModel();

                modelStorages.addElement(new Item(null, ""));
                List<Integer> listStorages = groups.get(0).getStorageId();
                Item selectedItem = null;
                for(StorageDTO storage : placeManager.getAllStorages())
                {
                    Item itm = new Item(storage, storage.getName());
                    modelStorages.addElement(itm);
                    if(!listStorages.isEmpty())
                    {              
                        if(listStorages.get(0) == storage.getId())
                        {
                            selectedItem = itm;
                        }
                    }
                }

                jComboBoxAdminUserStorage.setModel(modelStorages);

                if(selectedItem != null)
                {
                    jComboBoxAdminUserStorage.setSelectedItem(selectedItem);
                }

                jButtonAdminUserEditAndSave.setEnabled(true);
                jButtonAdminUserDeleteAndDiscard.setEnabled(true);
            }
            else
            {
                jButtonAdminUserEditAndSave.setText("Benutzer bearbeiten");
                jButtonAdminUserEditAndSave.setEnabled(false);

                jButtonAdminUserDeleteAndDiscard.setEnabled(false);

                jTextFieldAdminUserFirstName.setText("");
                jTextFieldAdminUserLastName.setText("");
                jTextFieldAdminUserName.setText("");
                jPasswordFieldAdminUserPassword.setText("");
                jPasswordFieldAdminUserPassword2.setText("");
                jComboBoxAdminUserGroup.setModel(new DefaultComboBoxModel());
                jComboBoxAdminUserStorage.setModel(new DefaultComboBoxModel());
            }
        }
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdminUserDeleteAndDiscard;
    private javax.swing.JButton jButtonAdminUserEditAndSave;
    private javax.swing.JButton jButtonAdminUserNewUser;
    private javax.swing.JComboBox jComboBoxAdminUserGroup;
    private javax.swing.JComboBox jComboBoxAdminUserStorage;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelAdminUserStorage;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelAdminUsers;
    private javax.swing.JPasswordField jPasswordFieldAdminUserPassword;
    private javax.swing.JPasswordField jPasswordFieldAdminUserPassword2;
    private javax.swing.JScrollPane jScrollPaneAdminUserManagment;
    private javax.swing.JTable jTableAdminUsersTable;
    private javax.swing.JTextField jTextFieldAdminUserFirstName;
    private javax.swing.JTextField jTextFieldAdminUserLastName;
    private javax.swing.JTextField jTextFieldAdminUserName;
    // End of variables declaration//GEN-END:variables
}