/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2016 Aaron Madlon-Kay
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This file is part of OmegaT.

 OmegaT is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OmegaT is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **************************************************************************/

package org.omegat.gui.preferences.view;

import javax.swing.JPanel;

import org.omegat.util.OStrings;

/**
 * @author Aaron Madlon-Kay
 */
@SuppressWarnings("serial")
public class AutoCompleterPreferencesPanel extends JPanel {

    /** Creates new form AutoCompleterPanel */
    public AutoCompleterPreferencesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        automaticCheckBox = new javax.swing.JCheckBox();
        switchWithLRCheckBox = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(automaticCheckBox, OStrings.getString("PREFS_AUTOCOMPLETE_SHOW_AUTOMATICALLY")); // NOI18N
        add(automaticCheckBox);

        org.openide.awt.Mnemonics.setLocalizedText(switchWithLRCheckBox, OStrings.getString("PREFS_AUTOCOMPLETE_SWITCH_VIEWS_LR")); // NOI18N
        add(switchWithLRCheckBox);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JCheckBox automaticCheckBox;
    javax.swing.JCheckBox switchWithLRCheckBox;
    // End of variables declaration//GEN-END:variables
}
