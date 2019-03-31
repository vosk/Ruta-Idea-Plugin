package vosk.ruta.ui.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.ui.maven.RutaDependency;

import static com.intellij.util.xmlb.XmlSerializerUtil.copyBean;

/**
 * @author Konstantin Bulenkov
 */
public class RutaFacetConfiguration implements FacetConfiguration, PersistentStateComponent<RutaModuleSettings> {
    private RutaModuleSettings mySettings = new RutaModuleSettings();

    public FacetEditorTab[] createEditorTabs(final FacetEditorContext editorContext,
                                             final FacetValidatorsManager validatorsManager) {


        this.mySettings.rutaVersion = RutaDependency.getActiveRutaVersionFromMavenModule(editorContext.getModule());
        RutaFacetTab rutaFacetTab = new RutaFacetTab(editorContext, mySettings);
        rutaFacetTab.handleVersionsPromise(RutaDependency.getRutaVersionsFromMaven(editorContext.getModule()));
        return new FacetEditorTab[]{rutaFacetTab};
    }

    @Override
    @NotNull
    public RutaModuleSettings getState() {
        return mySettings;
    }

//  public void setVersion(String version) {
//    mySettings.rutaVersion=version;
//  }

    @Override
    public void loadState(RutaModuleSettings state) {
        copyBean(state, mySettings);
    }
}
