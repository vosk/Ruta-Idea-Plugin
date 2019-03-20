package vosk.ruta.ui.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;

import static com.intellij.util.xmlb.XmlSerializerUtil.copyBean;

/**
 * @author Konstantin Bulenkov
 */
public class RutaFacetConfiguration implements FacetConfiguration, PersistentStateComponent<RutaModuleSettings> {
  private RutaModuleSettings mySettings = new RutaModuleSettings();

  public FacetEditorTab[] createEditorTabs(final FacetEditorContext editorContext,
                                           final FacetValidatorsManager validatorsManager) {
    return new FacetEditorTab[]{new RutaFacetTab(editorContext,mySettings)};
  }

  @Override
  @NotNull
  public RutaModuleSettings getState() {
    return mySettings;
  }

  public void setVersion(String version) {
    mySettings.rutaVersion=version;
  }

  @Override
  public void loadState(RutaModuleSettings state) {
    copyBean(state, mySettings);
  }
}
