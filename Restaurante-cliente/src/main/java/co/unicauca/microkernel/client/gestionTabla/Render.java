package co.unicauca.microkernel.client.gestionTabla;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import static javax.swing.UIManager.getColor;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderización de tablas
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Render extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof JButton) {
            var btn = (JButton) value;
            if (isSelected) {
                btn.setForeground(table.getSelectionForeground());
                btn.setBackground(table.getSelectionBackground());
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(getColor("Button.background"));
            }
            return btn;
        }
        if (value instanceof JCheckBox) {
            var ch = (JCheckBox) value;
            return ch;
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }  
}
