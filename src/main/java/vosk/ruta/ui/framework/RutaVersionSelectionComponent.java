package vosk.ruta.ui.framework;

import vosk.ruta.ui.facet.RutaModuleSettings;

import javax.swing.*;
import java.awt.*;

public class RutaVersionSelectionComponent extends JPanel{
    private JComboBox comboBox1;
    private JPanel rootPanel;

    public RutaVersionSelectionComponent(RutaModuleSettings settings){
        super(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }
}
