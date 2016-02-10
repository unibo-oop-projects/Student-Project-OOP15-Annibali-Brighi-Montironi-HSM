package org.hsm.view.tab;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hsm.controller.ControllerImpl;
import org.hsm.view.dialog.PlantCreateDialog;
import org.hsm.view.enumeration.PlantModelCharacteristics;
import org.hsm.view.gui.GUIComponent;
import org.hsm.view.utility.MyGUIFactory;

/**
 *This tab contains all the information about the database of plants.
 *
 */
public class DatabaseTab implements GUIComponent, Table {

    private final JTable table;
    private final JPanel panel;

    /**
     * Create the tab for the plant database.
     * @param frame the main frame of the app
     */
    public DatabaseTab(final JFrame frame) {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.table = new MyGUIFactory().createTable(PlantModelCharacteristics.getNameList().toArray());
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        final JButton createPlant = new JButton("Insert new type of plant");
        createPlant.addActionListener(e -> new PlantCreateDialog(frame).start());
        final JButton removePlant = new JButton("Remove selected plant");
        removePlant.addActionListener(e -> ControllerImpl.getController().deleteDbPlant());
        southPanel.add(createPlant);
        southPanel.add(removePlant);
        southPanel.setSize(southPanel.getPreferredSize());

        final JScrollPane scrollPane = new JScrollPane(this.table);
        this.panel.add(scrollPane);
        this.panel.add(southPanel);
    }

    @Override
    public JComponent getComponent() {
        return this.panel;
    }

    /**
     * Get the botanical name of the raw selected.
     * @return the botanical name selected
     * @throws IllegalStateException no row is selected
     */
    public String getSelectedBotanicalName() throws IllegalStateException {
        if (this.table.getSelectedRow() == -1) {
            throw new IllegalStateException();
        }
        final int selectedRowIndex = this.table.getSelectedRow();
        final String botanicalName = (String) this.table.getModel().getValueAt(selectedRowIndex, 
                                     PlantModelCharacteristics.BOTANICAL_NAME.ordinal());
        return botanicalName;
    }

    @Override
    public void insertRow(final Object... row) {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.addRow(row);
    }

    @Override
    public void removeSelectedRow() {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.removeRow(this.table.getSelectedRow());
    }

    @Override
    public void clean() {
        final DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
    }

}