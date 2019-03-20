//package vosk.ruta.ui.wizard;
//
//import com.intellij.ide.util.projectWizard.ModuleWizardStep;
//import com.intellij.openapi.options.ConfigurationException;
//import com.intellij.openapi.util.text.StringUtil;
//import vosk.ruta.ui.module.RutaModuleBuilder;
//import vosk.ruta.ui.wizard.RutaSdkPanel.RutaSdkPanel;
//
//import javax.swing.*;
//
///**
// * @author Konstantin Bulenkov
// */
//public class RutaModuleWizardStep extends ModuleWizardStep {
//  private final RutaSdkPanel sdkPanel = new RutaSdkPanel();
//  private RutaModuleBuilder myBuilder;
//
//  public RutaModuleWizardStep(RutaModuleBuilder builder) {
//    myBuilder = builder;
//  }
//
//  @Override
//  public JComponent getComponent() {
//    return sdkPanel;
//  }
//
//  @Override
//  public void updateDataModel() {
//    myBuilder.setSdk(sdkPanel.getSdk());
//  }
//
//
//  @Override
//  public boolean validate() throws ConfigurationException {
//    if (StringUtil.isEmpty(sdkPanel.getSdkName())) {
//      throw new ConfigurationException("Specify Redline Smalltalk SDK");
//    }
//    return super.validate();
//  }
//}
