/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.utils;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mti578
 */
class TabbedPaneFocus extends JTabbedPane implements ChangeListener, PropertyChangeListener {

    private Hashtable tabFocus;

    public TabbedPaneFocus() {
        tabFocus = new Hashtable();
        addChangeListener(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if ("permanentFocusOwner".equals(e.getPropertyName())) {
            final Object value = e.getNewValue();
            if (value != null) {
                tabFocus.put(getTitleAt(getSelectedIndex()), value);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        Object value = tabFocus.get(getTitleAt(getSelectedIndex()));
        if (value != null) {
            ((Component) value).requestFocusInWindow();
        }
    }
}
