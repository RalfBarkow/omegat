/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2013 Zoltan Bartko, Aaron Madlon-Kay
               2014-2015 Aaron Madlon-Kay
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

package org.omegat.gui.editor.autocompleter;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.omegat.gui.shortcuts.PropertiesShortcuts;

/**
 * Table-based auto-completer view
 * 
 * @author bartkoz
 * @author Aaron Madlon-Kay
 */
public abstract class AutoCompleterTableView extends AbstractAutoCompleterView {
    
    private final static KeyStroke KEYSTROKE_UP = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableUp");
    private final static KeyStroke KEYSTROKE_UP_EMACS = KeyStroke.getKeyStroke("ctrl P");
    private final static KeyStroke KEYSTROKE_DOWN = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableDown");
    private final static KeyStroke KEYSTROKE_DOWN_EMACS = KeyStroke.getKeyStroke("ctrl N");
    private final static KeyStroke KEYSTROKE_LEFT = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableLeft");
    private final static KeyStroke KEYSTROKE_LEFT_EMACS = KeyStroke.getKeyStroke("ctrl B");
    private final static KeyStroke KEYSTROKE_RIGHT = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableRight");
    private final static KeyStroke KEYSTROKE_RIGHT_EMACS = KeyStroke.getKeyStroke("ctrl F");
    private final static KeyStroke KEYSTROKE_PAGE_UP = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTablePageUp");
    private final static KeyStroke KEYSTROKE_PAGE_DOWN = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTablePageDown");
    private final static KeyStroke KEYSTROKE_FIRST = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableFirst");
    private final static KeyStroke KEYSTROKE_LAST = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableLast");
    private final static KeyStroke KEYSTROKE_FIRST_IN_ROW = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableFirstInRow");
    private final static KeyStroke KEYSTROKE_LAST_IN_ROW = PropertiesShortcuts.getEditorShortcuts()
            .getKeyStroke("autocompleterTableLastInRow");

    /**
     * the table. Use getTable() to access the value;
     */
    private static JTable table;
    
    public AutoCompleterTableView(String name) {
        super(name);
        getTable().changeSelection(0, 0, false, false);
    }
    
    /**
     * Set the selection.
     * @param p the new point
     */
    public void setSelection(Point p) {
        getTable().changeSelection(p.y, p.x, false, false);
    }
    
    public JTable getTable() {
        if (table == null) {
            table = new JTable();
            table.setCellSelectionEnabled(true);
            table.setFocusable(false);
            table.setTableHeader(null);
            table.addMouseListener(mouseAdapter);
        }
        return table;
    }
    
    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Point p = e.getPoint();
                int r = table.rowAtPoint(p);
                int c = table.columnAtPoint(p);
                if (table.getSelectedRow() == r && table.getSelectedColumn() == c) {
                    completer.doSelection();
                }
            }
        }
    };
    
    @Override
    public Component getViewContent() {
        return getTable();
    }
    
    /**
     * Get the point selected in the table.
     * @return 
     */
    public Point getSelectionPoint() {
        return new Point(getTable().getSelectedColumn(), getTable().getSelectedRow());
    }
    
    @Override
    public boolean processKeys(KeyEvent e) {

        KeyStroke s = KeyStroke.getKeyStrokeForEvent(e);

        if (s.equals(KEYSTROKE_UP) || s.equals(KEYSTROKE_UP_EMACS)) {
            selectPreviousPossibleValueUp();
            return true;
        }

        if (s.equals(KEYSTROKE_LEFT) || s.equals(KEYSTROKE_LEFT_EMACS)) {
            selectPreviousPossibleValueLeft();
            return true;
        }
        
        if (s.equals(KEYSTROKE_DOWN) || s.equals(KEYSTROKE_DOWN_EMACS)) {
            selectNextPossibleValueDown();
            return true;
        }

        if (s.equals(KEYSTROKE_RIGHT) || s.equals(KEYSTROKE_RIGHT_EMACS)) {
            selectNextPossibleValueRight();
            return true;
        }
        
        if (s.equals(KEYSTROKE_PAGE_UP)) {
            selectPreviousPossibleValueByPage();
            return true;
        }

        if (s.equals(KEYSTROKE_PAGE_DOWN)) {
            selectNextPossibleValueByPage();
            return true;
        }
        
        if (s.equals(KEYSTROKE_FIRST)) {
            selectFirstPossibleValue();
            return true;
        }

        if (s.equals(KEYSTROKE_LAST)) {
            selectLastPossibleValue();
            return true;
        }
        
        if (s.equals(KEYSTROKE_FIRST_IN_ROW)) {
            selectFirstPossibleValueInLine();
            return true;
        }

        if (s.equals(KEYSTROKE_LAST_IN_ROW)) {
            selectLastPossibleValueInLine();
            return true;
        }
        
        return false;
    } 
    
    /** 
     * Selects the next item in the list.
     */ 
    protected void selectNextPossibleValueDown() { 
        Point p = getSelectionPoint();
        
        int y = (p.y + 1) % getTable().getModel().getRowCount();
        
        setSelection(new Point(p.x, y));
    }
    
    /**
     * Select the first value in the table (top left).
     */
    protected void selectFirstPossibleValue() {
        setSelection(new Point(0, 0));
    }
    
    /**
     * Select the last value in the table (bottom right).
     */
    protected void selectLastPossibleValue() {
        setSelection(new Point(getTable().getModel().getColumnCount() - 1, 
                getTable().getModel().getRowCount() - 1));
    }
    
    /**
     * Select the first value in the current line.
     */
    protected void selectFirstPossibleValueInLine() {
        setSelection(new Point(0, getTable().getSelectedRow()));
    }
    
    /**
     * Select the last value in the current line.
     */
    protected void selectLastPossibleValueInLine() {
        setSelection(new Point(getTable().getModel().getColumnCount() - 1, 
                getTable().getSelectedRow()));
    }
    
    /**
     * Go one cell to the right.
     */
    protected void selectNextPossibleValueRight() { 
        Point p = getSelectionPoint();
        
        int x = (p.x + 1) % getTable().getModel().getColumnCount();
        
        setSelection(new Point(x, p.y));
    }
    
    /** 
     * Selects the item in the list following the current one by completer.pageRowCount items or go to the first item. 
     * currently selected item is already the last item. 
     */ 
    protected void selectNextPossibleValueByPage() { 
        Point p = getSelectionPoint();
        
        int size = getTable().getModel().getRowCount();
        setSelection(new Point(
                p.x,
                Math.min(p.y + AutoCompleter.PAGE_ROW_COUNT, size - 1)));
    }

    /** 
     * Selects the previous item in the list.
     */ 
    protected void selectPreviousPossibleValueUp() { 
        Point p = getSelectionPoint();
        
        int size = getTable().getModel().getRowCount();
        int y = (p.y - 1 + size) % size;
        
        setSelection(new Point(p.x, y));
    } 
    
    /**
     * Go one cell to the left.
     */
    protected void selectPreviousPossibleValueLeft() { 
        Point p = getSelectionPoint();
        
        int size = getTable().getModel().getColumnCount();
        int x = (p.x - 1 + size) % size;
        
        setSelection(new Point(x, p.y));
    }
    
    /** 
     * Selects the item in the list preceding the current one by completer.pageRowCount items or go to the first item.  It won't change the selection if the 
     * currently selected item is already the first item. 
     */ 
    protected void selectPreviousPossibleValueByPage() { 
        Point p = getSelectionPoint();
        
        setSelection(new Point(
                p.x,
                Math.max(p.y - AutoCompleter.PAGE_ROW_COUNT, 0)));
    }

    @Override
    public int getRowCount() {
        return getTable().getModel().getRowCount();
    }
    
    @Override
    public int getPreferredHeight() {
        return getModifiedRowCount() * getTable().getRowHeight();
    }
    
    @Override
    public int getPreferredWidth() {
        return getTable().getPreferredSize().width;
    }
    
    @Override
    public AutoCompleterItem getSelectedValue() {
        Point p = getSelectionPoint();
        Object selection = getTable().getModel().getValueAt(p.y, p.x);
        if (selection instanceof Character) {
            return new AutoCompleterItem(selection.toString(), null, 0);
        }
        return new AutoCompleterItem((String) selection, null, 0);
    }
}
