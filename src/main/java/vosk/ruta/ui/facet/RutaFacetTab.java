package vosk.ruta.ui.facet;

import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.ui.framework.RutaVersionSelectionComponent;
import vosk.ruta.ui.maven.RutaVersion;

import javax.swing.*;

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
    mySdkPanel.getComboBox1().setModel(new DefaultComboBoxModel(RutaVersion.getRutaVersionsFromMavenModule(context.getModule()).toArray()));
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
    return !StringUtil.equals(mySettings.rutaVersion, (CharSequence) mySdkPanel.getComboBox1().getSelectedItem());
  }

  @Override
  public void apply() throws ConfigurationException {
    mySettings.rutaVersion= (String) mySdkPanel.getComboBox1().getSelectedItem();
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
  public void disposeUIResources() {
  }
}