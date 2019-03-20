//package vosk.ruta.ui.module;
//
//import com.intellij.openapi.module.ModuleType;
//import vosk.ruta.RutaIcons;
//
//import javax.swing.*;
//
///**
// * @author Konstantin Bulenkov
// */
//public class RutaModuleType extends ModuleType<RutaModuleBuilder> {
//  private static final RutaModuleType INSTANCE = new RutaModuleType();
//
//  public RutaModuleType() {
//    super("REDLINE_MODULE");
//  }
//
//  @Override
//  public RutaModuleBuilder createModuleBuilder() {
//    return new RutaModuleBuilder();
//  }
//
//  @Override
//  public String getName() {
//    return "Redline Smalltalk";
//  }
//
//  @Override
//  public String getDescription() {
//    return "Add support for Redline Smalltalk";
//  }
//
//
//  @Override
//  public Icon getNodeIcon(@Deprecated boolean isOpened) {
//    return RutaIcons.FILE;
//  }
//
//  public static RutaModuleType getInstance() {
//    return INSTANCE;
//  }
//}
