package org.hsm.view.tab;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.hsm.controller.ControllerImpl;
import org.hsm.view.dialog.PlantAddDialog;
import org.hsm.view.enumeration.PlantCharacteristics;
import org.hsm.view.gui.GUIComponent;
import org.hsm.view.utility.MyGUIFactory;
import org.hsm.view.utility.Utilities;

/**
 *This tab contains all the information about the plants inside the current greenhouse.
 *
 */
public class PlantsTab extends Observable implements GUIComponent, Table {

    private static final int FILTER_TXT_SIZE = 50;
    private final JTable table;
    private final JPanel panel;

    /**
     * Create the tab for the plants inside the current greenhouse.
     * @param frame the main frame of the app
     */
    public PlantsTab(final JFrame frame) {
        this.table = new MyGUIFactory().createTable(PlantCharacteristics.getNameList().toArray());
        //pannello con buttoni per azioni su piante
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        final JButton add = new JButton("Add Plant");
        add.addActionListener(e -> {
            if (ControllerImpl.getController().isDbEmpty()) {
                Utilities.errorMessage(frame, "The Database is empty or not loaded");
            } else {
                new PlantAddDialog(frame).start();
            }
        });
        final JLabel filterLabel = new JLabel("Find:");
        final JTextField filterField = new JTextField(FILTER_TXT_SIZE);
        final JButton findButton = new JButton("Filter");
        final JButton exitFilter = new JButton("Exit");
        filterField.setMaximumSize(new Dimension(filterField.getPreferredSize().width, filterField.getPreferredSize().height));

        /*set filter option*/
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.table.getModel());
        table.setRowSorter(sorter);
        findButton.addActionListener(e -> {
            final String text = filterField.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
              } else {
                sorter.setRowFilter(RowFilter.regexFilter(text));
              }
        });
        exitFilter.addActionListener(e -> {
            sorter.setRowFilter(null);
        });

        southPanel.add(filterLabel);
        southPanel.add(filterField);
        southPanel.add(findButton);
        southPanel.add(exitFilter);
        southPanel.add(Box.createHorizontalGlue());
        final JButton remove = new JButton("Remove Plant");
        remove.addActionListener(e -> ControllerImpl.getController().delPlant());
        final JButton updateValues = new JButton("Update Plant Values");
        southPanel.add(add);
        southPanel.add(remove);
        southPanel.add(updateValues);
        southPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        final JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane);
        panel.add(southPanel);
    }

    /**
     * Get the ID of the raw selected.
     * @return the ID of the plant
     * @throws IllegalStateException no row is selected
     */
    public int getSelectedIDPlant() throws IllegalStateException {
        if (this.table.getSelectedRow() == -1) {
            throw new IllegalStateException();
        }
        final int selectedRowIndex = this.table.getSelectedRow();
        return (int) this.table.getModel().getValueAt(selectedRowIndex, PlantCharacteristics.ID.ordinal());
    }

    @Override
    public void insertRow(final Object... row) {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.addRow(row);
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void removeSelectedRow() {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.removeRow(this.table.getSelectedRow());
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Insert the row in the table without advise observers.
     * @param row the row to insert
     */
    public void updateRow(final Object... row) {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.addRow(row);
    }

    @Override
    public void clean() {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
    }

    @Override
    public JComponent getComponent() {
        return this.panel;
    }

}