package vosk.ruta.ui.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleConfigurationEditor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.concurrency.Promise;
import vosk.ruta.ui.facet.RutaFacet;
import vosk.ruta.ui.facet.RutaModuleSettings;
import vosk.ruta.ui.forms.RutaVersionSelectionComponent;
import vosk.ruta.ui.maven.RutaDependency;

import javax.swing.*;
import java.util.Collection;

public class RutaModuleConfigurationEditor implements ModuleConfigurationEditor {

    private final RutaModuleSettings settings;
    private final RutaFacet facet;
    RutaVersionSelectionComponent rutaVersionSelectionComponent;
    public RutaModuleConfigurationEditor(Module module, RutaFacet facet,RutaModuleSettings settings){
        this.settings = settings;
        rutaVersionSelectionComponent = new RutaVersionSelectionComponent(settings);
        handleVersionsPromise(RutaDependency.getRutaVersionsFromMaven(module));
        this.facet = facet;
    }
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Ruta";
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        return rutaVersionSelectionComponent;
    }

    @Override
    public boolean isModified() {
        return !StringUtil.equals(settings.rutaVersion, (CharSequence) rutaVersionSelectionComponent.getVersionComboBox().getSelectedItem());
    }

    @Override
    public void apply() throws ConfigurationException {
        settings.rutaVersion = (String) rutaVersionSelectionComponent.getVersionComboBox().getSelectedItem();
        moduleStateChanged();
//    mySettings.rutasdkname = mySdkPanel.getSdkName();
    }

    @Override
    public void reset() {
//    final Sdk sdk = ProjectJdkTable.getInstance().findJdk(mySettings.rutasdkname);
//    if (sdk != null) {
//      mySdkPanel.setSdk(sdk);
//    }
    }
    @Override
    public void moduleStateChanged() {
        RutaFacet.initializeProjectStructure(facet);
    }


    public void handleVersionsPromise(Promise<Collection<String>> rutaVersionsFromMaven){
        rutaVersionsFromMaven.onSuccess(this.rutaVersionSelectionComponent::setAvailableVersions);
    }
}
