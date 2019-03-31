package vosk.ruta.ui.forms;

import vosk.ruta.ui.facet.RutaModuleSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class RutaVersionSelectionComponent extends JPanel{
    private JComboBox versionComboBox;
    private JPanel rootPanel;
    private RutaModuleSettings settings;

    public RutaVersionSelectionComponent(RutaModuleSettings settings){
        super(new BorderLayout());
        this.settings = settings;
        add(rootPanel, BorderLayout.CENTER);
//        this.versionComboBox.setModel(new DefaultComboBoxModel(settings.availableRutaVersions.toArray()));
        this.versionComboBox.setEnabled(false);
        String pleaseWait = "Please Wait...";
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        defaultComboBoxModel.addElement(pleaseWait);
        this.versionComboBox.setModel(defaultComboBoxModel);
        this.versionComboBox.setSelectedItem(pleaseWait);

    }

    public JComboBox getVersionComboBox() {
        return versionComboBox;
    }

    public void setAvailableVersions(Collection<String> data) {
        this.versionComboBox.setModel(new DefaultComboBoxModel(data.toArray()));
        this.versionComboBox.setSelectedItem(settings.rutaVersion);
        this.versionComboBox.setEnabled(true);
    }
}
