package de.hscoburg.etif.vbis.lagerix.appclient.windows;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.Item;
import de.hscoburg.etif.vbis.lagerix.appclient.utils.TableColumnAdjuster;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * GUI Class for all Article and Articletype operations
 *
 * @author tima0900
 */
public class JPanelStockManagerArticles extends javax.swing.JPanel {

    private ArticleManagerEJBRemoteInterface articleManager = null;
    private PlaceManagerEJBRemoteInterface placeManager = null;
    private Integer articleTypeId = null;
    private PrintService selectedPrintService = null;
    private PrintService pss[] = null;
    private JFrameJavaAppClientMainWindow mainWindow = null;
    private File saveAsFile = null;

    /**
     * Creates new form JPanelStockManagerArticletypes
     *
     * @param articleManager
     * @param placeManager
     */
    public JPanelStockManagerArticles(ArticleManagerEJBRemoteInterface articleManager, PlaceManagerEJBRemoteInterface placeManager, JFrameJavaAppClientMainWindow mainWindow) {
        initComponents();
        this.articleManager = articleManager;
        this.placeManager = placeManager;
        this.mainWindow = mainWindow;

        jTextFieldStockManagerSearchName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }

            public void removeUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }

            public void insertUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }
        });

        jTextFieldStockManagerArticleSearchDescription.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }

            public void removeUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }

            public void insertUpdate(DocumentEvent e) {
                createJTableStockManagerArticletypesInGUIThread();
            }
        });

        jTableStockManagerArticletypeTable.getSelectionModel().addListSelectionListener(
                new javax.swing.event.ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        jTableStockManagerArticletypeTableValueChanged(e);
                    }
                ;
        });
        
        jTableStockManagerArticles.getSelectionModel().addListSelectionListener(
                new javax.swing.event.ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        jTableStockManagerArticlesTableValueChanged(e);
                    }
                ;
    }

    );        
    }

    /**
     * update the gui elements
     */
    private void jTableStockManagerArticlesTableValueChanged(ListSelectionEvent e) {
        if (jTableStockManagerArticles.isEnabled() && articleTypeId != null && jTableStockManagerArticles.getSelectedRow() >= 0) {
            jButtonStockManagerArticlePrintBarcode.setEnabled(true);
            jButtonStockManagerArticleNewArticle.setEnabled(true);
            jButtonStockManagerArticleDelete.setEnabled(true);
        } else {
            jButtonStockManagerArticleDelete.setEnabled(false);
            if (articleTypeId == null) {
                jButtonStockManagerArticleNewArticle.setEnabled(false);
            } else {
                jButtonStockManagerArticleNewArticle.setEnabled(true);
            }
            jButtonStockManagerArticlePrintBarcode.setEnabled(false);
        }
    }

    /**
     * Updates the article table
     */
    public void createJTableStockManagerArticles() {
        mainWindow.setBusy();

        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                DefaultTableModel model = new DefaultTableModel(0, 1);
                model.setColumnIdentifiers(new Object[]{"Artikel-ID"});

                if (placeManager.getStorages().size() > 0 && articleTypeId != null) {
                    List<ArticleDTO> articles = articleManager.getAllArticleByArticleType(articleTypeId);
                    for (ArticleDTO article : articles) {
                        model.addRow(new Object[]{article.getId()});
                    }

                    return model;
                } else {
                    return null;
                }
            }

            @Override
            public void done() {
                try {
                    Object returnParam = get();
                    if (returnParam != null) {
                        jTableStockManagerArticles.setModel((DefaultTableModel) returnParam);
                        TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticles);
                        tca.adjustColumns();
                        jTableStockManagerArticles.setEnabled(true);
                    } else {
                        DefaultTableModel model = new DefaultTableModel(0, 1);
                        model.setColumnIdentifiers(new Object[]{"Artikel-ID"});
                        jButtonStockManagerArticleDelete.setEnabled(false);
                        jButtonStockManagerArticleNewArticle.setEnabled(false);
                        jButtonStockManagerArticlePrintBarcode.setEnabled(false);
                        jTableStockManagerArticles.setModel(model);
                        TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticles);
                        tca.adjustColumns();
                        jTableStockManagerArticles.setEnabled(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                mainWindow.clearBusy();
            }
        };

        worker.execute();
    }

    /**
     * updates the gui elements
     */
    private void jTableStockManagerArticletypeTableValueChanged(ListSelectionEvent e) {
        if (jTableStockManagerArticletypeTable.isEnabled()) {
            if (jTableStockManagerArticletypeTable.getSelectedRow() >= 0) {
                String articleName = jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 0).toString();
                String articleDescription = jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 1).toString();

                articleTypeId = (Integer) ((Item) jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 0)).getObj();
                jTextFieldStockManagerArticletypesName.setText(articleName);
                jTextAreaStockManagerArticletypeDescription.setText(articleDescription);
                jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(true);
                jButtonStockManagerArticletypeModify.setEnabled(true);

                jButtonStockManagerArticleDelete.setEnabled(false);
                jButtonStockManagerArticleNewArticle.setEnabled(true);
                jButtonStockManagerArticlePrintBarcode.setEnabled(false);
                jTableStockManagerArticles.setEnabled(true);

                createJTableStockManagerArticles();
            } else {
                articleTypeId = null;
                jButtonStockManagerArticletypeModify.setEnabled(false);
                jButtonStockManagerArticletypeModify.setEnabled(false);
                jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
                jTextFieldStockManagerArticletypesName.setText("");
                jTextAreaStockManagerArticletypeDescription.setText("");
                jButtonStockManagerArticleDelete.setEnabled(false);
                jButtonStockManagerArticleNewArticle.setEnabled(false);
                jButtonStockManagerArticlePrintBarcode.setEnabled(false);
                jTableStockManagerArticles.setEnabled(false);

                DefaultTableModel model = new DefaultTableModel(0, 1);
                model.setColumnIdentifiers(new Object[]{"Artikel-ID"});
                jTableStockManagerArticles.setModel(model);
            }
        }
    }

    /*
    is used to update the articletypes table
    */
    private class SwingWorkerHelperArticletypes extends SwingWorker {

        public String filterName;
        public String filterDescr;

        @Override
        protected Object doInBackground() {
            articleTypeId = null;
            DefaultTableModel model = new DefaultTableModel(0, 3);
            model.setColumnIdentifiers(new Object[]{"Artikelartname", "Artikelartbeschreibung", "Artikelart-ID"});

            if (placeManager.getStorages().size() > 0) {
                //List<ArticleTypeDTO> articles = articleManager.getAllArticleTypes(placeManager.getStorages().get(0).getId());
                List<ArticleTypeDTO> articles = articleManager.searchArticleType(
                        filterName,
                        filterDescr, null);

                for (ArticleTypeDTO article : articles) {
                    model.addRow(new Object[]{new Item((Integer) article.getId(), article.getName()), article.getDescription(), article.getId()});
                }

                return model;
            } else {
                return null;
            }
        }

        @Override
        public void done() {
            try {
                Object returnParam = get();

                if (returnParam != null) {
                    jTableStockManagerArticletypeTable.setModel((DefaultTableModel) returnParam);
                    TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticletypeTable);
                    tca.adjustColumns();
                } else {
                    jButtonStockManagerArticletypeModify.setEnabled(false);
                    jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
                    jButtonStockManagerArticletypeNewArticletype.setEnabled(false);
                    jTextAreaStockManagerArticletypeDescription.setEnabled(false);
                    jTextFieldStockManagerArticletypesName.setEnabled(false);
                    jButtonStockManagerArticleDelete.setEnabled(false);
                    jButtonStockManagerArticleNewArticle.setEnabled(false);
                    jButtonStockManagerArticlePrintBarcode.setEnabled(false);
                    jTableStockManagerArticles.setEnabled(false);

                    DefaultTableModel model = new DefaultTableModel(0, 3);
                    model.setColumnIdentifiers(new Object[]{"Artikelartname", "Artikelartbeschreibung", "Artikelart-ID"});

                    jTableStockManagerArticletypeTable.setModel(model);
                    TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticletypeTable);
                    tca.adjustColumns();
                }

                createJTableStockManagerArticles();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mainWindow.clearBusy();
        }
    };

    /**
     * updates the articletypes table
     */
    public void createJTableStockManagerArticletypes() {
        mainWindow.setBusy();

        SwingWorkerHelperArticletypes worker = new SwingWorkerHelperArticletypes();

        worker.filterName = jTextFieldStockManagerSearchName.getText();
        worker.filterDescr = jTextFieldStockManagerArticleSearchDescription.getText();
        worker.execute();
    }

    /*
    updates the articletypes and article tables in same thread as gui, needed because of lost focus of textfield when filter are applied
    */
    private void createJTableStockManagerArticletypesInGUIThread() {
        String filterName = jTextFieldStockManagerSearchName.getText();;
        String filterDescr = jTextFieldStockManagerArticleSearchDescription.getText();

        articleTypeId = null;
        DefaultTableModel model = new DefaultTableModel(0, 3);
        model.setColumnIdentifiers(new Object[]{"Artikelartname", "Artikelartbeschreibung", "Artikelart-ID"});

        if (placeManager.getStorages().size() > 0) {
            //List<ArticleTypeDTO> articles = articleManager.getAllArticleTypes(placeManager.getStorages().get(0).getId());
            List<ArticleTypeDTO> articles = articleManager.searchArticleType(
                    filterName,
                    filterDescr, null);

            for (ArticleTypeDTO article : articles) {
                model.addRow(new Object[]{new Item((Integer) article.getId(), article.getName()), article.getDescription(), article.getId()});
            }

            jTableStockManagerArticletypeTable.setModel(model);
            TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticletypeTable);
            tca.adjustColumns();
        } else {
            jButtonStockManagerArticletypeModify.setEnabled(false);
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
            jButtonStockManagerArticletypeNewArticletype.setEnabled(false);
            jTextAreaStockManagerArticletypeDescription.setEnabled(false);
            jTextFieldStockManagerArticletypesName.setEnabled(false);
            jButtonStockManagerArticleDelete.setEnabled(false);
            jButtonStockManagerArticleNewArticle.setEnabled(false);
            jButtonStockManagerArticlePrintBarcode.setEnabled(false);
            jTableStockManagerArticles.setEnabled(false);

            model = new DefaultTableModel(0, 3);
            model.setColumnIdentifiers(new Object[]{"Artikelartname", "Artikelartbeschreibung", "Artikelart-ID"});

            jTableStockManagerArticletypeTable.setModel(model);
            TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticletypeTable);
            tca.adjustColumns();
        }

        model = new DefaultTableModel(0, 1);
        model.setColumnIdentifiers(new Object[]{"Artikel-ID"});
        jButtonStockManagerArticleDelete.setEnabled(false);
        jButtonStockManagerArticleNewArticle.setEnabled(false);
        jButtonStockManagerArticlePrintBarcode.setEnabled(false);
        jTableStockManagerArticles.setModel(model);
        TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerArticles);
        tca.adjustColumns();
        jTableStockManagerArticles.setEnabled(false);
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

        jDialog1 = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanelAdminStoragesPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPaneStockManagerArticletypManagment = new javax.swing.JScrollPane();
        jTableStockManagerArticletypeTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldStockManagerSearchName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldStockManagerArticleSearchDescription = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldStockManagerArticletypesName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButtonStockManagerArticletypeDeleteAndDiscard = new javax.swing.JButton();
        jButtonStockManagerArticletypeNewArticletype = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaStockManagerArticletypeDescription = new javax.swing.JTextArea();
        jButtonStockManagerArticletypeModify = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButtonStockManagerArticlePrintBarcode = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableStockManagerArticles = new javax.swing.JTable();
        jButtonStockManagerArticleDelete = new javax.swing.JButton();
        jButtonStockManagerArticleNewArticle = new javax.swing.JButton();

        jDialog1.setAlwaysOnTop(true);
        jDialog1.setMinimumSize(new java.awt.Dimension(380, 170));
        jDialog1.setModal(true);
        jDialog1.setPreferredSize(new java.awt.Dimension(380, 170));
        jDialog1.setResizable(false);

        jLabel5.setText("Bitte wählen Sie einen Drucker aus:");

        jButton2.setText("Drucken");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Abbruch");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Als Datei speichern");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        setLayout(new java.awt.GridBagLayout());

        jPanelAdminStoragesPanel.setPreferredSize(new java.awt.Dimension(750, 300));
        jPanelAdminStoragesPanel.setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPaneStockManagerArticletypManagment.setMaximumSize(null);
        jScrollPaneStockManagerArticletypManagment.setMinimumSize(null);

        jTableStockManagerArticletypeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTableStockManagerArticletypeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableStockManagerArticletypeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneStockManagerArticletypManagment.setViewportView(jTableStockManagerArticletypeTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPaneStockManagerArticletypManagment, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jTextFieldStockManagerSearchName.setColumns(15);
        jTextFieldStockManagerSearchName.setMinimumSize(new java.awt.Dimension(126, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jTextFieldStockManagerSearchName, gridBagConstraints);

        jLabel2.setText("Artikelartname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Filter:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Artikelartbeschreibung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel3.add(jTextFieldStockManagerArticleSearchDescription, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelAdminStoragesPanel.add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jTextFieldStockManagerArticletypesName.setColumns(20);
        jTextFieldStockManagerArticletypesName.setEnabled(false);
        jTextFieldStockManagerArticletypesName.setMinimumSize(new java.awt.Dimension(166, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel8.add(jTextFieldStockManagerArticletypesName, gridBagConstraints);

        jLabel11.setText("Artikelartname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jLabel11, gridBagConstraints);

        jButtonStockManagerArticletypeDeleteAndDiscard.setText("Löschen");
        jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
        jButtonStockManagerArticletypeDeleteAndDiscard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticletypeDeleteAndDiscardActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonStockManagerArticletypeDeleteAndDiscard, gridBagConstraints);

        jButtonStockManagerArticletypeNewArticletype.setText("Neue Artikelart anlegen");
        jButtonStockManagerArticletypeNewArticletype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticletypeNewArticletypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonStockManagerArticletypeNewArticletype, gridBagConstraints);

        jLabel1.setText("Artikelartbeschreibung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jLabel1, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(100, 100));

        jTextAreaStockManagerArticletypeDescription.setColumns(20);
        jTextAreaStockManagerArticletypeDescription.setRows(5);
        jTextAreaStockManagerArticletypeDescription.setEnabled(false);
        jScrollPane1.setViewportView(jTextAreaStockManagerArticletypeDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jScrollPane1, gridBagConstraints);

        jButtonStockManagerArticletypeModify.setText("Artikelart bearbeiten");
        jButtonStockManagerArticletypeModify.setEnabled(false);
        jButtonStockManagerArticletypeModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticletypeModifyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel8.add(jButtonStockManagerArticletypeModify, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jPanel8, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButtonStockManagerArticlePrintBarcode.setText("Barcode drucken");
        jButtonStockManagerArticlePrintBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticlePrintBarcodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jButtonStockManagerArticlePrintBarcode, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(350, 302));
        jScrollPane3.setName(""); // NOI18N

        jTableStockManagerArticles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableStockManagerArticles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableStockManagerArticles.setName(""); // NOI18N
        jTableStockManagerArticles.setPreferredSize(null);
        jTableStockManagerArticles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jTableStockManagerArticles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane3, gridBagConstraints);

        jButtonStockManagerArticleDelete.setText("Artikel löschen");
        jButtonStockManagerArticleDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticleDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jButtonStockManagerArticleDelete, gridBagConstraints);

        jButtonStockManagerArticleNewArticle.setText("Neuen Artikel anlegen");
        jButtonStockManagerArticleNewArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockManagerArticleNewArticleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(jButtonStockManagerArticleNewArticle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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

    /*
    deletes the selected articletype or discards any changes
    */
    private void jButtonStockManagerArticletypeDeleteAndDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticletypeDeleteAndDiscardActionPerformed
        if (jButtonStockManagerArticletypeDeleteAndDiscard.getText().equals("Löschen")) {
            if (JOptionPane.showConfirmDialog(this, "Moechten Sie die Artikelart: "
                    + jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 0)
                    + " wirklich loeschen?", "Sind Sie sicher?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                try {
                    articleManager.deleteArticleType((Integer) (((Item) jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 0)).getObj()));
                    createJTableStockManagerArticletypes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fehler beim loeschen der Artikelart: "
                            + jTableStockManagerArticletypeTable.getModel().getValueAt(jTableStockManagerArticletypeTable.getSelectedRow(), 0)
                            + ". Bitte loeschen Sie zuerst alle Artikel dieser Artikelart.",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Löschen");
            jButtonStockManagerArticletypeNewArticletype.setEnabled(true);
            jTextFieldStockManagerArticletypesName.setEnabled(false);
            jTextFieldStockManagerArticletypesName.setText("");
            jTextAreaStockManagerArticletypeDescription.setEnabled(false);
            jTextAreaStockManagerArticletypeDescription.setText("");
            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Löschen");
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
            jButtonStockManagerArticletypeNewArticletype.setText("Neue Artikelart anlegen");
            jButtonStockManagerArticletypeModify.setEnabled(false);
            jButtonStockManagerArticletypeModify.setText("Artikelart bearbeiten");
            jTableStockManagerArticletypeTable.setEnabled(true);

            jButtonStockManagerArticleDelete.setEnabled(false);
            jButtonStockManagerArticleNewArticle.setEnabled(false);
            jButtonStockManagerArticlePrintBarcode.setEnabled(false);
            jTableStockManagerArticles.setEnabled(false);

            createJTableStockManagerArticletypes();
        }
    }//GEN-LAST:event_jButtonStockManagerArticletypeDeleteAndDiscardActionPerformed

    /*
    creates a new articletype or saves the articletype
    */
    private void jButtonStockManagerArticletypeNewArticletypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticletypeNewArticletypeActionPerformed
        if (!jButtonStockManagerArticletypeNewArticletype.getText().equals("Speichern")) {
            articleTypeId = null;
            jButtonStockManagerArticletypeModify.setEnabled(false);
            jButtonStockManagerArticletypeNewArticletype.setText("Speichern");
            jTextFieldStockManagerArticletypesName.setEnabled(true);
            jTextAreaStockManagerArticletypeDescription.setEnabled(true);

            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Abbrechen");
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(true);
            jTableStockManagerArticletypeTable.setEnabled(false);

            jTextFieldStockManagerArticletypesName.setText("");
            jTextAreaStockManagerArticletypeDescription.setText("");
        } else {
            String articletypeName = jTextFieldStockManagerArticletypesName.getText();
            String articletypeDescription = jTextAreaStockManagerArticletypeDescription.getText();

            articleManager.createNewArticleType(articletypeName, articletypeDescription,
                    placeManager.getStorages().get(0).getId());

            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Löschen");
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);
            jButtonStockManagerArticletypeNewArticletype.setText("Neue Artikelart anlegen");
            jTableStockManagerArticletypeTable.setEnabled(true);
            jTextFieldStockManagerArticletypesName.setEnabled(false);
            jTextFieldStockManagerArticletypesName.setText("");
            jTextAreaStockManagerArticletypeDescription.setEnabled(false);
            jTextAreaStockManagerArticletypeDescription.setText("");
            createJTableStockManagerArticletypes();
        }
    }//GEN-LAST:event_jButtonStockManagerArticletypeNewArticletypeActionPerformed

    /*
    modifies the selected articletype or saves the changes
    */
    private void jButtonStockManagerArticletypeModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticletypeModifyActionPerformed
        if (!jButtonStockManagerArticletypeModify.getText().equals("Speichern")) {
            jButtonStockManagerArticletypeModify.setText("Speichern");
            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Abbrechen");
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(true);
            jButtonStockManagerArticletypeNewArticletype.setEnabled(false);
            jTextAreaStockManagerArticletypeDescription.setEnabled(true);
            jTextFieldStockManagerArticletypesName.setEnabled(true);
            jButtonStockManagerArticleDelete.setEnabled(false);
            jButtonStockManagerArticleNewArticle.setEnabled(false);
            jButtonStockManagerArticlePrintBarcode.setEnabled(false);
            jTableStockManagerArticles.setEnabled(false);
        } else {
            Integer artTypeId = articleTypeId;
            String desription = jTextAreaStockManagerArticletypeDescription.getText();
            String name = jTextFieldStockManagerArticletypesName.getText();
            ArticleTypeDTO article = new ArticleTypeDTO();
            article.setId(artTypeId);
            article.setDescription(desription);
            article.setName(name);
            articleManager.updateArticleType(article);

            jTextAreaStockManagerArticletypeDescription.setText("");
            jTextAreaStockManagerArticletypeDescription.setEnabled(false);

            jTextFieldStockManagerArticletypesName.setText("");
            jTextFieldStockManagerArticletypesName.setEnabled(false);

            jButtonStockManagerArticletypeDeleteAndDiscard.setText("Löschen");
            jButtonStockManagerArticletypeDeleteAndDiscard.setEnabled(false);

            jButtonStockManagerArticletypeModify.setText("Artikelart bearbeiten");
            jButtonStockManagerArticletypeModify.setEnabled(false);

            jButtonStockManagerArticleDelete.setEnabled(false);
            jButtonStockManagerArticleNewArticle.setEnabled(false);
            jButtonStockManagerArticlePrintBarcode.setEnabled(false);
            jTableStockManagerArticles.setEnabled(false);
            createJTableStockManagerArticletypes();
        }
    }//GEN-LAST:event_jButtonStockManagerArticletypeModifyActionPerformed

    /*
    creates a new article or saves the article
    */
    private void jButtonStockManagerArticleNewArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticleNewArticleActionPerformed
        ArticleDTO article = articleManager.createNewArticle(articleTypeId);
        int id = article.getId();

        int width = 440;
        int height = 48;

        selectedPrintService = null;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
        pss = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, pras);

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int cnt = 0; cnt < pss.length; cnt++) {
            model.addElement(pss[cnt].getName());
        }

        jComboBox1.setModel(model);

        jComboBox1.doLayout();
        jComboBox1.invalidate();

        jDialog1.setModal(true);
        jDialog1.setVisible(true);

        if (selectedPrintService != null || saveAsFile != null) {
            BitMatrix bitMatrix;
            try {
                bitMatrix = new Code128Writer().encode("A" + id, BarcodeFormat.CODE_128, width, height, null);
                ByteArrayOutputStream streamMemoryStream = new ByteArrayOutputStream();

                BufferedImage buffImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                BufferedImage barcodeAndText = process(buffImage, "A" + id);
                
                if(saveAsFile != null)
                {
                    ImageIO.write(barcodeAndText, "png", saveAsFile);
                }
                else
                {
                    ImageIO.write(barcodeAndText, "png", streamMemoryStream);

                    byte[] barcodeImage = streamMemoryStream.toByteArray();
                    ByteArrayInputStream streamInput = new ByteArrayInputStream(barcodeImage);

                    DocPrintJob job = selectedPrintService.createPrintJob();
                    Doc doc = new SimpleDoc(streamInput, DocFlavor.INPUT_STREAM.PNG, null);
                    job.print(doc, pras);
                    streamInput.close();
                }
            } catch (Exception e) {
            }
        }

        createJTableStockManagerArticles();
    }//GEN-LAST:event_jButtonStockManagerArticleNewArticleActionPerformed

    /*
    helper method for printing the article id's, adds the id under the barcode
    */
    private BufferedImage process(BufferedImage old, String text) {
        int w = old.getWidth();
        int h = old.getHeight() + 50;
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (img.getWidth() - fm.stringWidth(text)) / 2;
        int y = h - fm.getHeight();
        g2d.drawString(text, x, y);
        g2d.dispose();
        return img;
    }
    /*
    prints the barcode for the selected article
    */
    private void jButtonStockManagerArticlePrintBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticlePrintBarcodeActionPerformed
        int id = (Integer) jTableStockManagerArticles.getValueAt(jTableStockManagerArticles.getSelectedRow(), 0);

        int width = 440;
        int height = 48;

        selectedPrintService = null;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
        pss = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, pras);

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int cnt = 0; cnt < pss.length; cnt++) {
            model.addElement(pss[cnt].getName());
        }

        jComboBox1.setModel(model);

        jComboBox1.doLayout();
        jComboBox1.invalidate();

        jDialog1.setModal(true);
        jDialog1.setVisible(true);

        if (selectedPrintService != null || saveAsFile != null) {
            BitMatrix bitMatrix;
            try {
                bitMatrix = new Code128Writer().encode("A" + id, BarcodeFormat.CODE_128, width, height, null);
                ByteArrayOutputStream streamMemoryStream = new ByteArrayOutputStream();
                BufferedImage buffImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                BufferedImage barcodeAndText = process(buffImage, "A" + id);
                
                if(saveAsFile != null)
                {
                    ImageIO.write(barcodeAndText, "png", saveAsFile);
                }
                else
                {
                    ImageIO.write(barcodeAndText, "png", streamMemoryStream);
                    byte[] barcodeImage = streamMemoryStream.toByteArray();
                    ByteArrayInputStream streamInput = new ByteArrayInputStream(barcodeImage);

                    DocPrintJob job = selectedPrintService.createPrintJob();
                    Doc doc = new SimpleDoc(streamInput, DocFlavor.INPUT_STREAM.PNG, null);
                    job.print(doc, pras);
                    streamInput.close();
                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButtonStockManagerArticlePrintBarcodeActionPerformed

    /*
    deletes the selected article
    */
    private void jButtonStockManagerArticleDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockManagerArticleDeleteActionPerformed
        int id = (Integer) jTableStockManagerArticles.getValueAt(jTableStockManagerArticles.getSelectedRow(), 0);
        if (JOptionPane.showConfirmDialog(this, "Moechten Sie den Artikel mit der ID: "
                + id
                + " wirklich loeschen?", "Sind Sie sicher?", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            try {
                articleManager.deleteArticle(id);
                jButtonStockManagerArticleDelete.setEnabled(false);
                jButtonStockManagerArticlePrintBarcode.setEnabled(false);
                createJTableStockManagerArticles();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fehler beim loeschen des Artikels mit der ID: "
                        + id
                        + ".",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonStockManagerArticleDeleteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        selectedPrintService = pss[jComboBox1.getSelectedIndex()];
        saveAsFile = null;
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        selectedPrintService = null;
        saveAsFile = null;
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("Bilddatei", "png"));
        if(jFileChooser1.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String filepath = jFileChooser1.getSelectedFile().getPath();
            if (!filepath.endsWith(".png") && !filepath.endsWith(".PNG"))
            {
                filepath = filepath + ".png";
            }
            saveAsFile = new File(filepath);
            selectedPrintService = null;
            jDialog1.setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonStockManagerArticleDelete;
    private javax.swing.JButton jButtonStockManagerArticleNewArticle;
    private javax.swing.JButton jButtonStockManagerArticlePrintBarcode;
    private javax.swing.JButton jButtonStockManagerArticletypeDeleteAndDiscard;
    private javax.swing.JButton jButtonStockManagerArticletypeModify;
    private javax.swing.JButton jButtonStockManagerArticletypeNewArticletype;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelAdminStoragesPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneStockManagerArticletypManagment;
    private javax.swing.JTable jTableStockManagerArticles;
    private javax.swing.JTable jTableStockManagerArticletypeTable;
    private javax.swing.JTextArea jTextAreaStockManagerArticletypeDescription;
    private javax.swing.JTextField jTextFieldStockManagerArticleSearchDescription;
    private javax.swing.JTextField jTextFieldStockManagerArticletypesName;
    private javax.swing.JTextField jTextFieldStockManagerSearchName;
    // End of variables declaration//GEN-END:variables
}
