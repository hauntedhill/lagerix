/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.appclient.windows;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.Item;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.TableColumnAdjuster;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import static de.hscoburg.etif.vbis.lagerixjavaclient.Main.serverConfig;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tima0900
 */
public class MainWindow extends javax.swing.JFrame {
    private JPanelStockManagerArticletypes jPanelStockManagerArticletypes = null;
    private ArticleManagerEJBRemoteInterface articleManager = null;
    private UserManagerEJBRemoteInterface userManager = null;
    private PlaceManagerEJBRemoteInterface placeManager = null;
    private boolean initDone = false;
    private ProgrammaticLogin pl = new ProgrammaticLogin();
    private MainWindow thisWindow = this;
    private JPanelAdminUsers jPanelAdminUsers = null;
    private JPanelAdminStorages jPanelAdminStorages = null;
    private JPanelStockManagerYards jPanelStockManagerYards = null;
    private JPanelStockManagerOverview jPanelStockManagerOverview = null;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        initDone = true;
    }

    final Integer busyLock = 1;
    Integer busyCounter = 0;
    
    private void setBusy()
    {
        synchronized (busyLock) {
            if(busyCounter == 0)
            {
                CardLayout cl = (CardLayout)(jPanelWindow.getLayout());
                cl.show(jPanelWindow, "BusyCard");
            }

            busyCounter++;
        }
    }
    
    private void clearBusy()
    {
        synchronized (busyLock) {
            busyCounter--;
            if(busyCounter == 0)
            {
                CardLayout cl = (CardLayout)(jPanelWindow.getLayout());
                cl.show(jPanelWindow, "ControlCard");
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

        jPanelWindow = new javax.swing.JPanel();
        jPanelControls = new javax.swing.JPanel();
        jLabelControlsHeader = new javax.swing.JLabel();
        jPanelControlsMain = new javax.swing.JPanel();
        JPanelLogin = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jButtonLogIn = new javax.swing.JButton();
        jTextFieldUserName = new javax.swing.JTextField();
        jLabelLogIn = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanelAdministrator = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jTabbedPaneAdministrator = new javax.swing.JTabbedPane();
        JPanelStockManOverview = new javax.swing.JPanel();
        jTabbedPaneStockMan = new javax.swing.JTabbedPane();
        jButton4 = new javax.swing.JButton();
        jPanelBusyIndicator = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lagerix");
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(850, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jPanelWindow.setLayout(new java.awt.CardLayout());

        jPanelControls.setName(""); // NOI18N
        jPanelControls.setLayout(new java.awt.GridBagLayout());

        jLabelControlsHeader.setIcon(new javax.swing.ImageIcon("C:\\Users\\mti578\\Documents\\NetBeansProjects\\lagerix\\LagerixJavaClient\\src\\main\\resources\\META-INF\\lagerix-logo.png")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelControls.add(jLabelControlsHeader, gridBagConstraints);

        jPanelControlsMain.setLayout(new java.awt.CardLayout());

        JPanelLogin.setName("LogInCard"); // NOI18N
        JPanelLogin.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Passwort:");

        jButtonLogIn.setText("Anmelden");
        jButtonLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogInActionPerformed(evt);
            }
        });

        jLabelLogIn.setText("<html>Bitte melden Sie sich an:<br><br></html>");
        jLabelLogIn.setToolTipText("");

        jLabel2.setText("Benutzername:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonLogIn)
                            .addComponent(jTextFieldUserName)
                            .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogIn)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLogIn)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        JPanelLogin.add(jPanel3, gridBagConstraints);

        jPanelControlsMain.add(JPanelLogin, "LogInCard");

        jPanelAdministrator.setLayout(new java.awt.GridBagLayout());

        jButton5.setText("Benutzer abmelden");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutUser(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelAdministrator.add(jButton5, gridBagConstraints);

        jTabbedPaneAdministrator.setMaximumSize(null);
        jTabbedPaneAdministrator.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneAdministratorStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelAdministrator.add(jTabbedPaneAdministrator, gridBagConstraints);
        jTabbedPaneAdministrator.getAccessibleContext().setAccessibleName("");

        jPanelControlsMain.add(jPanelAdministrator, "AdminCard");

        JPanelStockManOverview.setEnabled(false);
        JPanelStockManOverview.setLayout(new java.awt.GridBagLayout());

        jTabbedPaneStockMan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStockManStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        JPanelStockManOverview.add(jTabbedPaneStockMan, gridBagConstraints);

        jButton4.setText("Benuzter abmelden");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutUser(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        JPanelStockManOverview.add(jButton4, gridBagConstraints);

        jPanelControlsMain.add(JPanelStockManOverview, "UserCard");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelControls.add(jPanelControlsMain, gridBagConstraints);

        jPanelWindow.add(jPanelControls, "ControlCard");

        jPanelBusyIndicator.setName(""); // NOI18N
        jPanelBusyIndicator.setLayout(new java.awt.GridBagLayout());

        jProgressBar1.setIndeterminate(true);
        jPanelBusyIndicator.add(jProgressBar1, new java.awt.GridBagConstraints());

        jPanelWindow.add(jPanelBusyIndicator, "BusyCard");

        getContentPane().add(jPanelWindow);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButtonLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogInActionPerformed
        setBusy();
        final String usrName = jTextFieldUserName.getText();
        final String password = new String(jPasswordFieldPassword.getPassword());
        
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try
                {        
                    Properties environment = new Properties();
                    
                    boolean configLoaded = false;
                    File f = new File("server.conf");
                    if(f.exists()) 
                    {
                        try
                        {
                            BufferedReader br = new BufferedReader(new FileReader("server.conf"));
                            try {
                                StringBuilder sb = new StringBuilder();
                                String line = br.readLine();

                                while (line != null) {
                                    sb.append(line);
                                    sb.append('\n');
                                    line = br.readLine();
                                }
                                String fileContent = sb.toString().trim();
                                String[] splittedtFileContents = fileContent.split(":");
                                if(splittedtFileContents.length > 1)
                                {
                                    environment.put("org.omg.CORBA.ORBInitialHost", splittedtFileContents[0].trim());
                                    environment.put("org.omg.CORBA.ORBInitialPort", splittedtFileContents[1].trim());
                                    configLoaded = true;
                                }
                            } catch(Exception ex)
                            {

                            } finally {
                                br.close();
                            }
                        } catch(Exception e)
                        {
                        }
                    }
		
                    if(!configLoaded)
                    {
                        environment.put("org.omg.CORBA.ORBInitialHost", "localhost");
                        environment.put("org.omg.CORBA.ORBInitialPort", "3700");
                        JOptionPane.showMessageDialog(thisWindow, "Fehler beim Laden der server.conf Datei. "
                                + "Es werden die Standard Einstellungen benutzt.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    System.setProperty("com.sun.corba.ee.transport.ORBWaitForResponseTimeout","10000");//10 seconds timeout for connection to glassfish ever
                    System.setProperty("java.security.auth.login.config", "auth.conf");
                
                    InitialContext ctx = new InitialContext(environment);
                    
                    pl.login(usrName, password.toCharArray(), "userMgmtJdbcRealm", true);
                    articleManager = (ArticleManagerEJBRemoteInterface) 
                             ctx.lookup("java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/ArticleManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface");
                    placeManager = (PlaceManagerEJBRemoteInterface)
                             ctx.lookup("java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/PlaceManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface");

                    userManager = (UserManagerEJBRemoteInterface) 
                            ctx.lookup("java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/UserManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface");
                         //if that works getUserRoles and return it...
                    
                } catch (Exception e)
                {
                    e.printStackTrace();   
                    articleManager = null;
                    userManager = null;
                    placeManager = null;
                    pl.logout();                    
                }               
                return null;
            }
            
            @Override
            public void done() {
                try {
                    if(userManager != null)
                    {
                        jTextFieldUserName.setText("");
                        jPasswordFieldPassword.setText("");

                        jLabelLogIn.setText("<html>Bitte melden Sie sich an:<br><br></html>");
                     
                         if(userManager.isInGroup(GroupType.ADMINISTRATOR))
                         {
                             UserDTO loggedInUsr = userManager.find(usrName);
                             jPanelAdminUsers = new JPanelAdminUsers(userManager, placeManager, loggedInUsr);
                             jPanelAdminStorages = new JPanelAdminStorages(placeManager);
                             jTabbedPaneAdministrator.removeAll();
                             jTabbedPaneAdministrator.addTab("Benutzerverwaltung", jPanelAdminUsers);
                             jTabbedPaneAdministrator.addTab("Lagerverwaltung", jPanelAdminStorages);
                             jPanelAdminUsers.createJTableAdminUsers();
                             jPanelAdminStorages.createJTableAdminStorage();

                            CardLayout cl = (CardLayout)(jPanelControlsMain.getLayout());
                            cl.show(jPanelControlsMain, "AdminCard");            
                         }
                         else if(userManager.isInGroup(GroupType.LAGERVERWALTER))
                         {
                            jPanelStockManagerOverview = new JPanelStockManagerOverview(placeManager, articleManager);
                            jPanelStockManagerArticletypes = new JPanelStockManagerArticletypes(articleManager, placeManager);
                            jPanelStockManagerYards = new JPanelStockManagerYards(placeManager);
                            jTabbedPaneStockMan.removeAll();
                            jTabbedPaneStockMan.addTab("Lagerplatzverwaltung", jPanelStockManagerYards);
                            jTabbedPaneStockMan.addTab("Artikelverwaltung", jPanelStockManagerArticletypes);
                            jTabbedPaneStockMan.addTab("Lagerbelegung", jPanelStockManagerOverview);
                            jPanelStockManagerYards.createJTableStockManagerYards();
                            jPanelStockManagerArticletypes.createJTableStockManagerArticletypes();
                            jPanelStockManagerOverview.createTables();
                            
                            CardLayout cl = (CardLayout)(jPanelControlsMain.getLayout());
                            cl.show(jPanelControlsMain, "UserCard");   
                         } else
                         {
                             jLabelLogIn.setText("<html>Bitte melden Sie sich an:<br><br>"
                                + "<font color='red'>Benutzer ist kein Lagerverwalter oder Administrator.</font></html>");
                         }
                    }
                    else
                    {
                        jLabelLogIn.setText("<html>Bitte melden Sie sich an:<br><br>"
                                + "<font color='red'>Anmeldung nicht m&ouml;lich.</font></html>");
                    }                    
                } catch (Exception ex) {
                   // ex.printStackTrace();
                    System.out.println(ex.toString());
                }
                clearBusy();
            }
        };
       
       worker.execute();
    }//GEN-LAST:event_jButtonLogInActionPerformed
    
    
    private PrintService selectedPrintService = null;
    private PrintService pss[] = null;
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void LogOutUser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutUser
        CardLayout cl = (CardLayout)(jPanelControlsMain.getLayout());
        cl.show(jPanelControlsMain, "LogInCard");
        
        this.JPanelLogin.setVisible(true);
        this.JPanelLogin.setEnabled(true);
        pl.logout(); 
    }//GEN-LAST:event_LogOutUser

    private void jTabbedPaneAdministratorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneAdministratorStateChanged
        int selectedTabb = jTabbedPaneAdministrator.getSelectedIndex();
        switch(selectedTabb)
        {
            case 0:
                if(jPanelAdminUsers != null)
                {
                    jPanelAdminUsers.createJTableAdminUsers();
                }
                break;
                
            case 1:
                if(jPanelAdminStorages != null)
                {
                    jPanelAdminStorages.createJTableAdminStorage();
                }
                break;
        }
    }//GEN-LAST:event_jTabbedPaneAdministratorStateChanged

    private void jTabbedPaneStockManStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStockManStateChanged
        int selectedTabb = jTabbedPaneStockMan.getSelectedIndex();
        switch(selectedTabb)
        {
            case 1:
                if(jPanelStockManagerArticletypes != null)
                {
                    jPanelStockManagerArticletypes.createJTableStockManagerArticletypes();
                }
                break;
                
            case 0:
                if(jPanelStockManagerYards != null)
                {
                    jPanelStockManagerYards.createJTableStockManagerYards();
                }
                break;
                
            case 2:
                if(jPanelStockManagerOverview != null)
                {
                    jPanelStockManagerOverview.createTables();
                }
                break;
        }
    }//GEN-LAST:event_jTabbedPaneStockManStateChanged
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelLogin;
    private javax.swing.JPanel JPanelStockManOverview;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonLogIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelControlsHeader;
    private javax.swing.JLabel jLabelLogIn;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelAdministrator;
    private javax.swing.JPanel jPanelBusyIndicator;
    private javax.swing.JPanel jPanelControls;
    private javax.swing.JPanel jPanelControlsMain;
    private javax.swing.JPanel jPanelWindow;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTabbedPane jTabbedPaneAdministrator;
    private javax.swing.JTabbedPane jTabbedPaneStockMan;
    private javax.swing.JTextField jTextFieldUserName;
    // End of variables declaration//GEN-END:variables
}
