/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.windows;

import de.hscoburg.etif.vbis.lagerix.appclient.utils.TableColumnAdjuster;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mti578
 */
public class JPanelStockManagerOverview extends javax.swing.JPanel {
    private PlaceManagerEJBRemoteInterface placeManager = null;
    private ArticleManagerEJBRemoteInterface articleManager = null;
    private JFrameJavaAppClientMainWindow mainWindow = null;
        
    /**
     * Creates new form JPanelStockManagerOverview
     */
    public JPanelStockManagerOverview(PlaceManagerEJBRemoteInterface placeManager, ArticleManagerEJBRemoteInterface articleManager, JFrameJavaAppClientMainWindow mainWindow) {
        initComponents();
        this.articleManager = articleManager;
        this.placeManager = placeManager;
        this.mainWindow = mainWindow;
    }

    public void createTables()
    {
        mainWindow.setBusy();
        
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                DefaultTableModel modelFree = new DefaultTableModel(0, 1);
                modelFree.setColumnIdentifiers(new Object[] {"Lagerplatz-ID"});
                DefaultTableModel modelOccupied = new DefaultTableModel(0, 4);
                modelOccupied.setColumnIdentifiers(new Object[] {"Lagerplatz-ID", "Artikeltypname", "Artikeltyp-ID", "Artikel-ID"});
                if(placeManager.getStorages().size() > 0)
                {
                    List<Integer> occupiedYards = new ArrayList<Integer>();
                    List<Integer> freeYards = new ArrayList<Integer>();
                    StorageDTO storage = placeManager.getStorages().get(0);
                    List<YardDTO> yards = placeManager.getAllYards(storage.getId());
                    List<ArticleTypeDTO> articleTypes = articleManager.getAllArticleTypes(storage.getId());
                    for(ArticleTypeDTO articleType : articleTypes)
                    {
                        List<ArticleDTO> articles = articleManager.getAllArticleByArticleType(articleType.getId());
                        for(ArticleDTO article : articles)
                        {
                            if(article.getYardID() != 0)
                            {
                                occupiedYards.add(article.getYardID());
                                modelOccupied.addRow(new Object[] {article.getYardID(), articleType.getName(),
                                                            articleType.getId(), article.getId()});
                            }
                        }
                    }            

                    for(YardDTO yard : yards)
                    {
                        boolean inOccupied = false;
                        for(Integer occupiedYard : occupiedYards)
                        {
                            if(occupiedYard == yard.getId())
                            {
                                inOccupied = true;
                                break;
                            }
                        }

                        if(inOccupied == false)
                        {
                            freeYards.add(yard.getId());
                        }
                    }

                    for(Integer yard : freeYards)
                    {
                        modelFree.addRow(new Object[] {yard});
                    }
                }
                return new Object[] {modelFree, modelOccupied};
            }
                
            @Override
            public void done() {
                try {
                    Object[] returnParam = (Object[]) get();
                    DefaultTableModel modelFree = (DefaultTableModel)returnParam[0];
                    DefaultTableModel modelOccupied = (DefaultTableModel)returnParam[1];
                    
                    jTableStockManagerOverviewFreeYards.setModel(modelFree);
                    jTableStockManagerOverviewOccupiedYards.setModel(modelOccupied);
                    TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerOverviewFreeYards);
                    tca.adjustColumns();
                    TableColumnAdjuster tca2 = new TableColumnAdjuster(jTableStockManagerOverviewOccupiedYards); 
                    tca2.adjustColumns();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mainWindow.clearBusy();
            }
        }; 
        
        worker.execute();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStockManagerOverviewFreeYards = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableStockManagerOverviewOccupiedYards = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jTableStockManagerOverviewFreeYards.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableStockManagerOverviewFreeYards);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jTableStockManagerOverviewOccupiedYards.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableStockManagerOverviewOccupiedYards);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane2, gridBagConstraints);

        jLabel1.setText("Freie Lagerplaetze:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Belegte Lagerplaetze");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(jLabel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableStockManagerOverviewFreeYards;
    private javax.swing.JTable jTableStockManagerOverviewOccupiedYards;
    // End of variables declaration//GEN-END:variables
}
