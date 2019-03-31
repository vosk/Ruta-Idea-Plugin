package vosk.ruta.ui.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.concurrency.Promise;
import vosk.ruta.ui.forms.RutaVersionSelectionComponent;

import javax.swing.*;
import java.util.Collection;

/**
 * @author Konstantin Bulenkov
 */
public class RutaFacetTab extends FacetEditorTab {

    private final RutaVersionSelectionComponent mySdkPanel;
    private FacetEditorContext context;
    private final RutaModuleSettings mySettings;

    public RutaFacetTab(FacetEditorContext context, RutaModuleSettings settings) {
        this.context = context;
        mySettings = settings;
        mySdkPanel = new RutaVersionSelectionComponent(settings);
    }

    public void handleVersionsPromise(Promise<Collection<String>> rutaVersionsFromMaven){
        rutaVersionsFromMaven.onSuccess(this.mySdkPanel::setAvailableVersions);
    }
    @Nls
    @Override
    public String getDisplayName() {
        return "UIMA Ruta";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return mySdkPanel;
    }

    @Override
    public boolean isModified() {
        return !StringUtil.equals(mySettings.rutaVersion, (CharSequence) mySdkPanel.getVersionComboBox().getSelectedItem());
    }

    @Override
    public void apply() throws ConfigurationException {
        mySettings.rutaVersion = (String) mySdkPanel.getVersionComboBox().getSelectedItem();
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
    public void onFacetInitialized(Facet facet) {
        RutaFacet.initializeProjectStructure((RutaFacet) facet);
        return;
    }

    @Override
    public void disposeUIResources() {
    }
}